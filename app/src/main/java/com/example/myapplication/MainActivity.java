package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    DBHelper DBhelper;
    private MaterialCardView cardViewQLVT, cardViewQLCT, cardViewChuyenVT, cardViewCTVC, cardViewThongKe,cardViewKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        cardViewQLVT = findViewById(R.id.cv_qlvt);
        //cardViewQLVT.startAnimation(animation);
        cardViewQLCT = findViewById(R.id.cv_qlct);
        //cardViewQLCT.startAnimation(animation);
        cardViewChuyenVT = findViewById(R.id.cv_chuyenVT);
        //cardViewChuyenVT.startAnimation(animation);
        cardViewCTVC = findViewById(R.id.cv_qlchitiet);
        //cardViewCTVC.startAnimation(animation);
        cardViewThongKe = findViewById(R.id.cv_thongke);
        //cardViewThongKe.startAnimation(animation4);
        cardViewKhachHang = findViewById(R.id.cv_khachhang);
        //cardViewKhachHang.startAnimation(animation);

        cardViewQLVT.setOnClickListener(view -> {
            startActivity(new Intent(this, DSVatTuActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        });

        cardViewQLCT.setOnClickListener(view -> {
            startActivity(new Intent(this, CongTrinhActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        cardViewChuyenVT.setOnClickListener(view -> {
            startActivity(new Intent(this, PhieuVanChuyenActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        cardViewCTVC.setOnClickListener(view -> {
            startActivity(new Intent(this, ChiTietPVCActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        cardViewThongKe.setOnClickListener(view -> {
            startActivity(new Intent(this, ThongKeActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        cardViewKhachHang.setOnClickListener(view -> {
            startActivity(new Intent(this, KhachHangActivity.class));
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        Init_DB();


    }

    public void Init_DB() {
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        DBhelper.QueryData("create table if not exists vattu(maVT integer primary key Autoincrement,tenVT nvarchar(100),dvTinh nvarchar(30),giaVC float,hinh Blob)");
        DBhelper.QueryData("create table if not exists congtrinh( maCT nvarchar(30) primary key,tenCT nvarchar(50), diachi nvarchar(100))");
        DBhelper.QueryData("create table if not exists PVC(maPVC nchar(10) primary key, ngayVC date, maCT varchar(30),TT nvarchar(50), FOREIGN KEY(maCT) REFERENCES congtrinh(maCT) )");
        DBhelper.QueryData("create table if not exists chitietPVC( maPVC nchar(10), maVT int, soluong int, culy int,PRIMARY KEY (maPVC, maVT) FOREIGN KEY(maPVC) REFERENCES PVC(maPVC),  FOREIGN KEY (maVT) REFERENCES vattu(maVT))");
        DBhelper.QueryData("create table if not exists KH(maKH integer primary key Autoincrement,hoTenKH nvarchar(50),email varchar(50),sdt varchar(13),maPVC nchar(10),FOREIGN KEY(maPVC) REFERENCES PVC(maPVC))");
    }
}