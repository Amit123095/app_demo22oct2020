package com.mindyourlovedone.healthcare.database;

public class ProxyInformation {

    private String KEY_ID = "_id";
    private String KEY_PROXY_USERNAME = "username";
    private byte[] KEY_PROXY_IMAGEPATH = null;
    private String KEY_PROXY_RELATION = "relation";
    private String KEY_PROXY_HPHNO = "homephonenumber";
    private String KEY_PROXY_OPHNO = "officephonenumber";
    private String KEY_PROXY_PPHONO = "personalphonenumber";
    private String KEY_PROXY_EMAIL = "email";
    private String KEY_PROXY_isPRIMARY = "1";
    private String KEY_PROXY_ID;

    public ProxyInformation(String kEY_ID, String kEY_PROXY_USERNAME,
                            byte[] profileImgByte, String kEY_PROXY_RELATION,
                            String kEY_PROXY_HPHNO, String kEY_PROXY_OPHNO,
                            String kEY_PROXY_PPHONO, String kEY_PROXY_EMAIL, String kEY_PROXY_isPRIMARY, String proxyid) {
        super();
        KEY_ID = kEY_ID;
        KEY_PROXY_USERNAME = kEY_PROXY_USERNAME;
        KEY_PROXY_IMAGEPATH = profileImgByte;
        KEY_PROXY_RELATION = kEY_PROXY_RELATION;
        KEY_PROXY_HPHNO = kEY_PROXY_HPHNO;
        KEY_PROXY_OPHNO = kEY_PROXY_OPHNO;
        KEY_PROXY_PPHONO = kEY_PROXY_PPHONO;
        KEY_PROXY_EMAIL = kEY_PROXY_EMAIL;
        KEY_PROXY_isPRIMARY = kEY_PROXY_isPRIMARY;
        KEY_PROXY_ID = proxyid;
    }

    public String getKEY_ID() {
        return KEY_ID;
    }

    public void setKEY_ID(String kEY_PROXY_ID) {
        KEY_ID = kEY_PROXY_ID;
    }

    public String getKEY_PROXY_ID() {
        return KEY_PROXY_ID;
    }

    public void setKEY_PROXY_ID(String kEY_PROXY_ID) {
        KEY_PROXY_ID = kEY_PROXY_ID;
    }


    public String getKEY_PROXY_USERNAME() {
        return KEY_PROXY_USERNAME;
    }

    public void setKEY_PROXY_USERNAME(String kEY_PROXY_USERNAME) {
        KEY_PROXY_USERNAME = kEY_PROXY_USERNAME;
    }

    public String getKEY_PROXY_RELATION() {
        return KEY_PROXY_RELATION;
    }

    public void setKEY_PROXY_RELATION(String kEY_PROXY_RELATION) {
        KEY_PROXY_RELATION = kEY_PROXY_RELATION;
    }

    public String getKEY_PROXY_HPHNO() {
        return KEY_PROXY_HPHNO;
    }

    public void setKEY_PROXY_HPHNO(String kEY_PROXY_HPHNO) {
        KEY_PROXY_HPHNO = kEY_PROXY_HPHNO;
    }

    public String getKEY_PROXY_OPHNO() {
        return KEY_PROXY_OPHNO;
    }

    public void setKEY_PROXY_OPHNO(String kEY_PROXY_OPHNO) {
        KEY_PROXY_OPHNO = kEY_PROXY_OPHNO;
    }

    public String getKEY_PROXY_PPHONO() {
        return KEY_PROXY_PPHONO;
    }

    public void setKEY_PROXY_PPHONO(String kEY_PROXY_PPHONO) {
        KEY_PROXY_PPHONO = kEY_PROXY_PPHONO;
    }

    public String getKEY_PROXY_EMAIL() {
        return KEY_PROXY_EMAIL;
    }

    public void setKEY_PROXY_EMAIL(String kEY_PROXY_EMAIL) {
        KEY_PROXY_EMAIL = kEY_PROXY_EMAIL;
    }


    public String getKEY_PROXY_isPRIMARY() {
        return KEY_PROXY_isPRIMARY;
    }


    public void setKEY_PROXY_isPRIMARY(String kEY_PROXY_isPRIMARY) {
        KEY_PROXY_isPRIMARY = kEY_PROXY_isPRIMARY;
    }


    public byte[] getKEY_PROXY_IMAGEPATH() {
        return KEY_PROXY_IMAGEPATH;
    }


    public void setKEY_PROXY_IMAGEPATH(byte[] kEY_PROXY_IMAGEPATH) {
        KEY_PROXY_IMAGEPATH = kEY_PROXY_IMAGEPATH;
    }


}
