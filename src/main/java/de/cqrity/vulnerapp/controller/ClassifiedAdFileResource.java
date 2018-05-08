package de.cqrity.vulnerapp.controller;

import org.springframework.web.multipart.MultipartFile;

public class ClassifiedAdFileResource {

    private MultipartFile adfile = null;

    public ClassifiedAdFileResource() {
        // Empty default constructor...
    }

    public ClassifiedAdFileResource(MultipartFile adfile) {
        super();
        this.adfile = adfile;
    }

    public MultipartFile getAdfile() {
        return adfile;
    }

    public void setAdfile(MultipartFile adfile) {
        this.adfile = adfile;
    }

}
