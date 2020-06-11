package com.example.atm.netty.codec.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeaderUtil {

    public static final int HEADER_LENGTH = 4;

    static ByteBuf create(HeaderData h) {
        ByteBuf header = Unpooled.buffer(HEADER_LENGTH);

        header.writeBytes(h.getId().getBytes(), 0, 4);

        return header;
    }

    public static HeaderData read(ByteBuf header) {
        byte[] id = new byte[4];
        header.readBytes(id, 0, 4);

        HeaderData headerData = new HeaderData();
        headerData.setId(new String(id));

        return headerData;
    }

}
