package com.example.atm.netty.codec.header;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeaderUtil {

    public static final int HEADER_LENGTH = 30;

    static ByteBuf serialize(HeaderData h) {
        ByteBuf header = Unpooled.buffer(HEADER_LENGTH);

        header.writeByte(h.getVersao());
        header.writeByte(h.getFormato());
        header.writeByte(h.getServico());
        header.writeByte(h.getTipo());
        header.writeByte(h.getFormatoId());
        header.writeBytes(h.getId().getBytes());
        header.writeBytes(h.getReservado());

        return header;
    }

    public static HeaderData deserialize(ByteBuf header) {
        byte versao = header.readByte();
        byte formato = header.readByte();
        byte servico = header.readByte();
        byte tipo = header.readByte();
        byte formatoId = header.readByte();

        byte[] id = new byte[HeaderData.ID_LENGTH];
        header.readBytes(id, 0, HeaderData.ID_LENGTH);

        byte[] reservado = new byte[13];
        header.readBytes(reservado, 0, 13);

        HeaderData headerData = new HeaderData(new String(id));
        headerData.setVersao(versao);
        headerData.setFormato(formato);
        headerData.setServico(servico);
        headerData.setTipo(tipo);
        headerData.setFormatoId(formatoId);
        headerData.setReservado(reservado);

        return headerData;
    }

}
