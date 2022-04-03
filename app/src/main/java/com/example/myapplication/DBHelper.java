package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
// factory con trỏ dùng để duyêt dữ liệu
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
//duyệt từng dòng trong database ,trả dữ liệu về dạng con trỏ
    public Cursor GetData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    void InsertVT(String tenVT, String dvTinh, Float giaVC, byte[] hinh) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into vattu values (null,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenVT);
        statement.bindString(2, dvTinh);
        statement.bindDouble(3, giaVC);
        statement.bindBlob(4, hinh);
        statement.executeInsert();
    }
    void InsertKH(String hoTenKH, String Email,String Sdt) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into KH values (null,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, hoTenKH);
        statement.bindString(2, Email);
        statement.bindString(3, Sdt);
        statement.executeInsert();
    }

    void InsertCT(String maCT, String tenVT, String diaChi) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into congtrinh values (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, maCT);
        statement.bindString(2, tenVT);
        statement.bindString(3, diaChi);
        statement.executeInsert();
    }

    public void deleteCT(CongTrinh ct) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM congtrinh WHERE maCT='" + ct.getMaCT() + "'";
        db.execSQL(query);
    }
    public void deleteVT(VatTu vt) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM vattu WHERE maVT='" + vt.getMaVt() + "'";
        db.execSQL(query);
    }

    public void deleteKH(KH kh) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM kh WHERE maKH='" + kh.getMaKH() + "'";
        db.execSQL(query);
    }
    public void deleteCTVC(ChiTietPVC ctvc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM chitietPVC WHERE maPVC='" + ctvc.getMaPVC() + "'";
        db.execSQL(query);
    }
    public void deletePVC(PhieuVanChuyen pvc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM PVC WHERE maPVC='" + pvc.getMaPVC() + "'";
        db.execSQL(query);
    }
    public void updateCT(String tenCT, String diachi, String maCT) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update  congtrinh  set ";
        sql += "tenCT  = '" + tenCT + "' ,  ";
        sql += "diachi  = '" + diachi + "'";
        sql += "  WHERE maCT  = '" + maCT + "'";
        db.execSQL(sql);
    }
//    public void updateVT(int maVT, String tenVT, String dvTinh, float giaVC, byte[] hinh) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "Update  vattu  set ";
//        sql += "tenVT  = '" + tenVT + "' ,  ";
//        sql += "dvTinh  = '" + dvTinh + "'";
//        sql += "giaVC  = '" + giaVC + "'";
//        sql += "hinh  = '" + hinh + "'";
//        sql += "  WHERE maVT  = '" + maVT + "'";
//        db.execSQL(sql);
//    }
public void updateVT(String tenVT, String dvTinh, Float giaVC,byte[] hinh,int maVT) {
    SQLiteDatabase db = getWritableDatabase();
    String sql = "Update vattu set tenVT= ?,dvTinh=?,giaVC =?,hinh= ? where maVT=?";
    SQLiteStatement statement = db.compileStatement(sql);
    statement.clearBindings();
    statement.bindString(1, tenVT);
    statement.bindString(2, dvTinh);
    statement.bindDouble(3, giaVC);
    statement.bindBlob(4, hinh);
    statement.bindLong(5, maVT);
    statement.executeUpdateDelete();
}
    public void updateKH(String hoTenKH, String Email, String Sdt,String maPVC,int maKH) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Update KH set hoTenKH= ?,email=?,sdt =?,maPVC=? where maKH=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, hoTenKH);
        statement.bindString(2, Email);
        statement.bindString(3, Sdt);
        statement.bindString(4,maPVC );
        statement.bindLong(5, maKH);
        statement.executeUpdateDelete();
    }
    public void updatePVC(String maPVC, Date ngayVC, String maCT) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update  vattu  set ";
        sql += "ngayVC  = '" + ngayVC + "' ,  ";
        sql += "maCT  = '" + maCT + "'";
        sql += "  WHERE maPVC  = '" + maPVC + "'";
        db.execSQL(sql);
    }
    public void updateCTPVC(String maPVC,int maVT,int soluong,int culy, Date ngayVC) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update  vattu  set ";
        sql += "soluong  = '" + soluong + "' ,  ";
        sql += "culy  = '" + culy + "'";
        sql += "ngayVC  = '" + ngayVC + "'";
        sql += "  WHERE maPVC  = '" + maPVC + "'";
        sql += "  WHERE maVT  = '" + maVT + "'";
        db.execSQL(sql);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

