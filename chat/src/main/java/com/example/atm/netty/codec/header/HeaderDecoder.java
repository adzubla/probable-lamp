package com.example.atm.netty.codec.header;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.example.atm.netty.codec.header.HeaderUtil.HEADER_LENGTH;

public class HeaderDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(processHeader(in, ctx));
    }

    private ByteBuf processHeader(ByteBuf data, ChannelHandlerContext ctx) {
        int length = data.readableBytes();

        ByteBuf header = data.readSlice(HEADER_LENGTH);
        ByteBuf content = data.readSlice(length - HEADER_LENGTH);

        HeaderData headerData = HeaderUtil.deserialize(header);
        ctx.channel().attr(HeaderData.HEADER_DATA_ATTRIBUTE_KEY).set(headerData);

        return content.retain();
    }

}
