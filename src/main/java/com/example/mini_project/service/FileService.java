package com.example.mini_project.service;

import com.example.mini_project.model.request.FileRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FileService {
    FileRequest saveFile(MultipartFile file,HttpServletRequest request) throws IOException;
    List<FileRequest> saveListFile(List<MultipartFile> files , HttpServletRequest request) throws IOException;
    byte[] getFileContent(String fileName) throws IOException;
}
