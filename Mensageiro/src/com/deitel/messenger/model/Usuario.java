package com.deitel.messenger.model;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Usuario {
    
    private int USER_ID;
    private String NICK;
    private String PASS;
    private String EMAIL;
    private Calendar CREATED_AT;
    private String PUBLIC_KEY1;
    
    public Usuario()
    {
        //Construtor padrao
    }
    
    public Usuario(int uSER_ID, String nICK, String pASS, String eMAIL, Date cREATED_AT)
    {
        this.USER_ID = uSER_ID;
        this.NICK = nICK;
        this.PASS = pASS;
        this.EMAIL = eMAIL;
        this.CREATED_AT = new GregorianCalendar();
        this.CREATED_AT.setTime(cREATED_AT);
    }
    
    public Usuario(int uSER_ID, String nICK, String pASS, String eMAIL, Date cREATED_AT, String pUBLIC_KEY1)
    {
        this.USER_ID = uSER_ID;
        this.NICK = nICK;
        this.PASS = pASS;
        this.EMAIL = eMAIL;
        this.CREATED_AT = new GregorianCalendar();
        this.CREATED_AT.setTime(cREATED_AT);
        this.PUBLIC_KEY1 = pUBLIC_KEY1;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getNICK() {
        return NICK;
    }

    public void setNICK(String NICK) {
        this.NICK = NICK;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public Calendar getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(Calendar CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public String getPUBLIC_KEY1() {
        return PUBLIC_KEY1;
    }

    public void setPUBLIC_KEY1(String PUBLIC_KEY1) {
        this.PUBLIC_KEY1 = PUBLIC_KEY1;
    }
    
}
