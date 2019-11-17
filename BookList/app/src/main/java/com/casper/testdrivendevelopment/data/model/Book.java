package com.casper.testdrivendevelopment.data.model;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book implements Serializable {
    private String name;
    private int pictureId;

    public Book(String name,int pictureId) {
        this.name = name;
        this.pictureId = pictureId;
    }

    public String getTitle(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoverResourceId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
