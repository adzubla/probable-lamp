package com.example.atm.netty.codec.header;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HeaderEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        out.writeBytes(prependHeader(msg, ctx));
    }

    private ByteBuf prependHeader(ByteBuf data, ChannelHandlerContext ctx) {
        HeaderData headerData = ctx.channel().attr(HeaderData.HEADER_DATA_ATTRIBUTE_KEY).get();

        ByteBuf header = HeaderUtil.create(headerData);
        return header.writeBytes(data);
    }

}