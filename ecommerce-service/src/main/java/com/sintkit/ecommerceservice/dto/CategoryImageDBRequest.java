package com.sintkit.ecommerceservice.dto;

import lombok.Data;

@Data
public class CategoryImageDBRequest {
    private Long id;
    private String name;
    private String url;
    private String type;
    private byte[] imageDB;

    public CategoryImageDBRequest(Long id, String name, String url, String type, byte[] imageDB) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.imageDB = imageDB;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public byte[] getSize() {
        return imageDB;
    }
    public void setSize(byte[] imageDB) {
        this.imageDB = imageDB;
    }

}
