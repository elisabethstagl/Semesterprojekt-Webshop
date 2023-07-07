package com.webshop.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/* Der LocalFileService wird verwendet, um Dateien lokal hochzuladen und zu speichern. 
Die hochgeladenen Dateien werden unter Verwendung einer eindeutigen UUID und des Originaldateinamens 
in einem bestimmten Verzeichnis (hier "images/") abgelegt.
 */

@Service
public class LocalFileService implements FileService {
    
    @Override
    public String upload(MultipartFile file){
        Path uploadPath = Paths.get("images/",UUID.randomUUID() + "_" + file.getOriginalFilename());

    try {
            Files.copy(file.getInputStream(), uploadPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "null";
    }

    @Override
    public Resource get(String reference) {
        return null;
    }
    }
