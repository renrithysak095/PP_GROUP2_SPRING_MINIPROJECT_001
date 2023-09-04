package com.example.mini_project.service.implementation;

import com.example.mini_project.model.File;
import com.example.mini_project.model.request.FileRequest;
import com.example.mini_project.repository.FileRepository;
import com.example.mini_project.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public FileRequest saveFile(MultipartFile file,HttpServletRequest request) {
        File obj = new File();
        obj.setFileName(file.getOriginalFilename());
        obj.setFileType(file.getContentType());
        obj.setSize(file.getSize());
        obj.setFileUrl(String.valueOf(request.getRequestURL()).substring(0,22)+"images/"+obj.getFileName());
        fileRepository.save(obj);
        return new FileRequest(obj.getFileName(), obj.getFileUrl(),obj.getFileType(),obj.getSize());
    }

    @Override
    @Transactional
    public List<FileRequest> saveListFile(List<MultipartFile> files, HttpServletRequest request) {
        List<FileRequest> filesResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            File obj = new File();
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
    public byte[] getFileContent(String fileName) {
        return new byte[0];
    }
}
