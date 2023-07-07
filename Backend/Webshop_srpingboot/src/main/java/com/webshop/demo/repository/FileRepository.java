package com.webshop.demo.repository;

import com.webshop.demo.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface FileRepository extends JpaRepository<File, Long>{

    }
