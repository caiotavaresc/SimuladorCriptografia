package com.deitel.messenger.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mensagem {
    
    private int ID_MSG;
    private int ID_FROM;
    private String NICK_FROM;
    private int ID_TO;
    private int CRYPTO_TYPE;
    private int MSG_TYPE;
    private String MSG_TEXT;
    private Date MSG_DATETIME;
    private String DataHoraFormatado;
    
    public Mensagem(int iD_MSG, int iD_FROM, int iD_TO, int cRYPTO_TYPE, int mSG_TYPE, String mSG_TEXT, Date dataHoraMsg)
    {
        this.ID_MSG = iD_MSG;
        this.ID_FROM = iD_FROM;
        this.ID_TO = iD_TO;
        this.CRYPTO_TYPE = cRYPTO_TYPE;
        this.MSG_TYPE = mSG_TYPE;
        this.MSG_TEXT = mSG_TEXT;
        this.MSG_DATETIME = dataHoraMsg;
    }

    public Mensagem(int iD_FROM, int iD_TO, int cRYPTO_TYPE, int mSG_TYPE, String mSG_TEXT)
    {
        this.ID_FROM = iD_FROM;
        this.ID_TO = iD_TO;
        this.CRYPTO_TYPE = cRYPTO_TYPE;
        this.MSG_TYPE = mSG_TYPE;
        this.MSG_TEXT = mSG_TEXT;
    }
    
    public int getID_MSG() {
        return ID_MSG;
    }

    public int getID_FROM() {
        return ID_FROM;
    }

    public void setID_FROM(int ID_FROM) {
        this.ID_FROM = ID_FROM;
    }

    public int getID_TO() {
        return ID_TO;
    }

    public void setID_TO(int ID_TO) {
        this.ID_TO = ID_TO;
    }

    public int getCRYPTO_TYPE() {
        return CRYPTO_TYPE;
    }

    public void setCRYPTO_TYPE(int CRYPTO_TYPE) {
        this.CRYPTO_TYPE = CRYPTO_TYPE;
    }

    public int getMSG_TYPE() {
        return MSG_TYPE;
    }

    public void setMSG_TYPE(int MSG_TYPE) {
        this.MSG_TYPE = MSG_TYPE;
    }

    public String getMSG_TEXT() {
        return MSG_TEXT;
    }

    public void setMSG_TEXT(String MSG_TEXT) {
        this.MSG_TEXT = MSG_TEXT;
    }

    public Date getMSG_DATETIME() {
        return MSG_DATETIME;
    }

    public void setMSG_DATETIME(Date MSG_DATETIME) {
        this.MSG_DATETIME = MSG_DATETIME;
    }

    public String getNICK_FROM() {
        return NICK_FROM;
    }

    public void setNICK_FROM(String NICK_FROM) {
        this.NICK_FROM = NICK_FROM;
    }

    public String getDataHoraFormatado() {
        return DataHoraFormatado;
    }

    public void setDataHoraFormatado(String DataHoraFormatado) {
        this.DataHoraFormatado = DataHoraFormatado;
    }

}
