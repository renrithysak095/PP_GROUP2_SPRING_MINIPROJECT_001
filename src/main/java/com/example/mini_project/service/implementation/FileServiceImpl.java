package com.example.mini_project.service.implementation;

import com.example.mini_project.configuration.FileStorageProperties;
import com.example.mini_project.model.FileStorage;
import com.example.mini_project.model.request.FileRequest;
import com.example.mini_project.repository.FileRepository;
import com.example.mini_project.service.FileService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageProperties fileStorageProperties;

    public FileServiceImpl(FileRepository fileRepository, FileStorageProperties fileStorageProperties) {
        this.fileRepository = fileRepository;
        this.fileStorageProperties = fileStorageProperties;
    }

    @Transactional
    public FileRequest saveFile(MultipartFile file,HttpServletRequest request) throws IOException {

        FileStorage obj = new FileStorage();
        obj.setFileName(file.getOriginalFilename());
        obj.setFileType(file.getContentType());
        obj.setSize(file.getSize());
        obj.setFileUrl(String.valueOf(request.getRequestURL()).substring(0,22)+"images/"+obj.getFileName());

        String uploadPath = fileStorageProperties.getUploadPath();
        Path directoryPath = Paths.get(uploadPath).toAbsolutePath().normalize();
        java.io.File directory = directoryPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(directoryPath.toFile(), fileName);
        file.transferTo(dest);

        fileRepository.save(obj);


        return new FileRequest(obj.getFileName(), obj.getFileUrl(),obj.getFileType(),obj.getSize());
    }

    @Override
    @Transactional
    public List<FileRequest> saveListFile(List<MultipartFile> files, HttpServletRequest request) throws IOException {
        List<FileRequest> filesResponses = new ArrayList<>();

        for (MultipartFile file : files) {

            String uploadPath = fileStorageProperties.getUploadPath();
            Path directoryPath = Paths.get(uploadPath).toAbsolutePath().normalize();
            java.io.File directory = directoryPath.toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            File dest = new File(directoryPath.toFile(), fileName);
            file.transferTo(dest);

            FileStorage obj = new FileStorage();
            obj.setFileName(file.getOriginalFilename());
            obj.setFileType(file.getContentType());
            obj.setSize(file.getSize());
            obj.setFileUrl(String.valueOf(request.getRequestURL()).substring(0,22)+"images/"+obj.getFileName());
            fileRepository.save(obj);
            filesResponses.add(new FileRequest(obj.getFileName(), obj.getFileUrl(),obj.getFileType(),obj.getSize()));
        }
        return filesResponses;
    }
    @Override
    public byte[] getFileContent(String fileName) throws IOException {

        String uploadPath = fileStorageProperties.getUploadPath();
        Path path = Paths.get(uploadPath + fileName);
        Resource file = new ByteArrayResource(Files.readAllBytes(path));
        return file.getContentAsByteArray();
    }
}
