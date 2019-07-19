package com.ctao.O2O.dto;

import java.io.InputStream;

public class ImageHoder {
    private String imageName;
    private InputStream image;
    public ImageHoder(String imageName,InputStream image){
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
