package com.example.atm.netty.codec.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeaderUtil {

    public static final int HEADER_LENGTH = 6;

    static ByteBuf create(HeaderData h) {
        ByteBuf header = Unpooled.buffer(HEADER_LENGTH);

        header.writeBytes(h.getId().getBytes(), 0, HEADER_LENGTH);

        return header;
    }

    public static HeaderData read(ByteBuf header) {
        byte[] id = new byte[HEADER_LENGTH];
        header.readBytes(id, 0, HEADER_LENGTH);

        HeaderData headerData = new HeaderData(new String(id));

        return headerData;
    }

}
