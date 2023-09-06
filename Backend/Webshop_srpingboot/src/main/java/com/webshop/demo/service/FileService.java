package com.webshop.demo.service;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
    
    public String upload(MultipartFile file);

    Resource get(String reference);
}
