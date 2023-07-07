package com.webshop.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class File {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String fileName;

    private String type;

    private String reference;

    private boolean assigned;

    public File() {
    }


    public File(Long id, String fileName, String type, String reference, boolean assigned) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.reference = reference;
        this.assigned = assigned;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isAssigned() {
        return this.assigned;
    }

    public boolean getAssigned() {
        return this.assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

}
