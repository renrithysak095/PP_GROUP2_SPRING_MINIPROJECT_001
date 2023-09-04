package com.example.mini_project.controller;

import com.example.mini_project.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import com.example.mini_project.model.request.FileRequest;
import com.example.mini_project.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("api/v1/file")
@Tag(name = "Files")
public class FileStorageRestController {

    private final FileService fileService;

    public FileStorageRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/file-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload file")
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public FileRequest saveFile(@RequestParam(required = false)  MultipartFile file,
                                HttpServletRequest request){
        return fileService.saveFile(file,request);
    }

    @PostMapping(value = "/uploadMultipleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload multiple file")
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public List<FileRequest> saveMultiFile(@RequestParam(required = false) List<MultipartFile> files,
                                           HttpServletRequest request){
        return fileService.saveListFile(files,request);
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "download file")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        byte[] fileContent = fileService.getFileContent(fileName);
        ByteArrayResource resource = new ByteArrayResource(fileContent);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        headers.setContentType(mediaType);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileContent.length)
                .body(resource);
    }

}
