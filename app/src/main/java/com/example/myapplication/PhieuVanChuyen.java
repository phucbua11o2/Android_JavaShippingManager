package com.example.myapplication;

public class PhieuVanChuyen {
    String maPVC, ngayVC, maCT,TT;


    public PhieuVanChuyen(String string) {
    }

    public PhieuVanChuyen(String maPVC, String ngayVC, String maCT,String TT) {
        this.maPVC = maPVC;
        this.ngayVC = ngayVC;
        this.maCT = maCT;
        this.TT=TT;
    }

    public String getMaPVC() {
        return maPVC;
    }

    public void setMaPVC(String maPVC) {
        this.maPVC = maPVC;
    }

    public String getNgayVC() {
        return ngayVC;
    }

    public void setNgayVC(String ngayVC) {
        this.ngayVC = ngayVC;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }
    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    @Override
    public String toString() {
        return getMaPVC();
    }
}
