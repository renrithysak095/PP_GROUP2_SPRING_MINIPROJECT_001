package com.example.mini_project.service;

import com.example.mini_project.model.File;
import com.example.mini_project.model.request.FileRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface FileService {
    FileRequest saveFile(MultipartFile file,HttpServletRequest request);
    List<FileRequest> saveListFile(List<MultipartFile> files , HttpServletRequest request);
    byte[] getFileContent(String fileName);
}
