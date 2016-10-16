package com.deitel.messenger.model;

import java.util.Calendar;

public class Mensagem {
    
    private int ID_MSG;
    private int ID_FROM;
    private int ID_TO;
    private int CRYPTO_TYPE;
    private int MSG_TYPE;
    private String MSG_TEXT;
    private Calendar MSG_DATETIME;
    
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

    public Calendar getMSG_DATETIME() {
        return MSG_DATETIME;
    }

    public void setMSG_DATETIME(Calendar MSG_DATETIME) {
        this.MSG_DATETIME = MSG_DATETIME;
    }

}
