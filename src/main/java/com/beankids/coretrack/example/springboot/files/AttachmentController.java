package com.beankids.coretrack.example.springboot.files;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by coretrack on 2016-11-14
 */

@RestController
@RequestMapping("/file/attachments")
public class AttachmentController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());


    @Value("${resource.nas.file.dir:/}")
    private String saveFileDirPath;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> uploadAttachment(@RequestPart MultipartFile sourceFile) throws IOException {
        logger.info("saveFileDirPath => {}", saveFileDirPath);

        String srcFileName = sourceFile.getOriginalFilename();
        String srcFileNmaeExt = FilenameUtils.getExtension(srcFileName).toLowerCase();

        File destFile;
        String destFileName;
        do {
            destFileName = RandomStringUtils.randomAlphabetic(32) + "." + srcFileNmaeExt;
            destFile = new File(saveFileDirPath, destFileName);
            logger.info("destFile => {}", destFile.getAbsolutePath());
        } while (destFile.exists());
        destFile.getParentFile().mkdirs();
        sourceFile.transferTo(destFile);

        UploadAttachmentResponse response = new UploadAttachmentResponse();
        response.setFileName(sourceFile.getOriginalFilename());
        response.setFileSize(sourceFile.getSize());
        response.setFileContentType(sourceFile.getContentType());
        response.setAttachmentUrl("http://localhost:8080/file/attachments/" + destFileName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{file_name:.+}", method = RequestMethod.GET)
    public FileSystemResource downloadFile(@PathVariable("file_name") String fileNmae) {
        return new FileSystemResource(new File(saveFileDirPath, fileNmae));
    }

    @NoArgsConstructor
    @Data
    private class UploadAttachmentResponse {
        private String fileName;

        private long fileSize;

        private String fileContentType;

        private String attachmentUrl;
    }
}
