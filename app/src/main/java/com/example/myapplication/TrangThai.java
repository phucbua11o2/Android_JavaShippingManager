package com.example.myapplication;

public class TrangThai {
    String thongTin;
    int MaTT;

    public TrangThai() {
    }

    public TrangThai(String thongTin, int maTT) {
        this.thongTin = thongTin;
        MaTT = maTT;
    }

    public String getThongTin() {
        return thongTin;
    }

    public void setThongTin(String thongTin) {
        this.thongTin = thongTin;
    }

    public int getMaTT() {
        return MaTT;
    }

    public void setMaTT(int maTT) {
        MaTT = maTT;
    }

    @Override
    public String toString() {
        return "TrangThai{" +
                "thongTin='" + thongTin + '\'' +
                ", MaTT=" + MaTT +
                '}';
    }
}
