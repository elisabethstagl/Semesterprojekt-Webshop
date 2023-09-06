package com.webshop.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.webshop.demo.dto.FileUploadResponse;
import com.webshop.demo.service.FileService;

    @Controller
    @RequestMapping("/files")
public class FileController {
    
    private final FileService fileService;

    public FileController (FileService fileService){
        this.fileService = fileService;}

    @PostMapping
    public @ResponseBody FileUploadResponse upload(
        @RequestParam("file") MultipartFile file
    ) {
        String reference = fileService.upload(file);

        return new FileUploadResponse(
                true, reference
        );
    }
    
}
