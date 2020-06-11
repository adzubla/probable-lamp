package com.example.atm.netty.codec.header;

import io.netty.util.AttributeKey;

public class HeaderData {

    public static final AttributeKey<HeaderData> HEADER_DATA_ATTRIBUTE_KEY = AttributeKey.newInstance("HeaderData.attr");

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HeaderData{" +
                "id='" + id + '\'' +
                '}';
    }

}
