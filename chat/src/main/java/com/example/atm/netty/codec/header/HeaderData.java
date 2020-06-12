package com.example.atm.netty.codec.header;

import io.netty.util.AttributeKey;

public class HeaderData {

    public static final AttributeKey<HeaderData> HEADER_DATA_ATTRIBUTE_KEY = AttributeKey.newInstance("HeaderData.attr");

    private final String id;

    public HeaderData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "HeaderData{" +
                "id='" + id + '\'' +
                '}';
    }

}
