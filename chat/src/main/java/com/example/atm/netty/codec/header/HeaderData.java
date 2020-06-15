package com.example.atm.netty.codec.header;

import io.netty.util.AttributeKey;

public class HeaderData {

    public static final AttributeKey<HeaderData> HEADER_DATA_ATTRIBUTE_KEY = AttributeKey.newInstance("HeaderData.attr");

    public static int ID_LENGTH = 12;

    private final byte versao = 1;
    private final byte formato = 1;
    private final byte servico = 5;
    private final byte tipo = 3;
    private final byte formatoId = 1;
    private final byte[] reservado = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private String id;

    public HeaderData(String id) {
        this.id = id;
        checkId();
    }

    public HeaderData(String localidade, String idTerminal) {
        this(buildId(localidade, idTerminal));
    }

    public byte getVersao() {
        return versao;
    }

    public byte getFormato() {
        return formato;
    }

    public byte getServico() {
        return servico;
    }

    public byte getTipo() {
        return tipo;
    }

    public byte getFormatoId() {
        return formatoId;
    }

    public String getId() {
        return id;
    }

    public byte[] getReservado() {
        return reservado;
    }

    @Override
    public String toString() {
        return "HeaderData{" +
                "id='" + id + '\'' +
                '}';
    }

    public String getLocalidade() {
        return id.substring(5, 5 + 3);
    }

    public String getIdTerminal() {
        return id.substring(8, 8 + 4);
    }

    private void checkId() {
        if (id == null) {
            throw new IllegalArgumentException("Id nao pode ser null");
        }

        if (id.length() < ID_LENGTH) {
            StringBuilder sb = new StringBuilder("000000000000");
            id = sb.substring(id.length()) + id;
        }

        if (id.length() > ID_LENGTH) {
            throw new IllegalArgumentException("Id nao pode ser maior que " + ID_LENGTH);
        }
    }

    private static String buildId(String localidade, String idTerminal) {
        if (localidade == null || localidade.length() != 3) {
            throw new IllegalArgumentException("Localidade invalida");
        }
        if (idTerminal == null || idTerminal.length() != 4) {
            throw new IllegalArgumentException("IdTerminal invalida");
        }
        return localidade + idTerminal;
    }

}
