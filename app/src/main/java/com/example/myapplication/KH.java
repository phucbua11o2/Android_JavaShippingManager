package com.example.myapplication;

public class KH {
    int maKH;
    String tenKH, email,sdt,maPVC;

    public KH() {
    }

    public KH(int maKH, String tenKH, String email, String sdt,String maPVC) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.email = email;
        this.sdt = sdt;
        this.maPVC=maPVC;
    }

    public String getMaPVC() {
        return maPVC;
    }

    public void setMaPVC(String maPVC) {
        this.maPVC = maPVC;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "KH{}";
    }
}
