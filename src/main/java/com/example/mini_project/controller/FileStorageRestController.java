package com.example.mini_project.controller;

import com.example.mini_project.exception.NotFoundExceptionClass;
import com.example.mini_project.exception.NullExceptionClass;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import com.example.mini_project.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<?> saveFile(@RequestParam(required = false)  MultipartFile file,
                                           HttpServletRequest request) throws IOException {
        if(file != null){
            return ResponseEntity.status(200).body(fileService.saveFile(file,request));
        }
        throw new NotFoundExceptionClass("No filename to upload");
    }

    @PostMapping(value = "/uploadMultipleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload multiple file")
    public ResponseEntity<?> saveMultiFile(@RequestParam(required = false) List<MultipartFile> files,
                                           HttpServletRequest request) throws IOException {
        if(files != null){
            return ResponseEntity.status(200).body(fileService.saveListFile(files,request));
        }
        throw new NotFoundExceptionClass("No filename to upload");
    }
    @GetMapping("/download/{fileName}")
    @Operation(summary = "download file")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {

        if(fileName.isBlank()){
            throw new NullExceptionClass("No filename to download", "File");
        }

        String filePath = "src/main/resources/storage/" + fileName;
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new NotFoundExceptionClass("File Not Found");
        }

        byte[] file = fileService.getFileContent(fileName);

        ByteArrayResource resource = new ByteArrayResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        headers.setContentType(mediaType);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
