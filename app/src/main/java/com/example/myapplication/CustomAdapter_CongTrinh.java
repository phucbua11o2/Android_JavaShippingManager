package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CustomAdapter_CongTrinh extends BaseAdapter {
    ArrayList<CongTrinh> arrayList;
    DBHelper DBhelper;

    public CustomAdapter_CongTrinh(ArrayList<CongTrinh> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DBhelper = new DBHelper(parent.getContext(), "qlvc.sqlite", null, 1);
        View viewitem = View.inflate(parent.getContext(), R.layout.item_dsct, null);
        CongTrinh CT = (CongTrinh) getItem(position);
        TextView tvMaCT = (TextView) viewitem.findViewById(R.id.tvMaCT);
        tvMaCT.setText(String.valueOf(CT.getMaCT()));
        TextView tvTenCT = (TextView) viewitem.findViewById(R.id.tvTenCT);
        tvTenCT.setText(CT.getTenCT());
        TextView tvDChi = (TextView) viewitem.findViewById(R.id.tvDC);
        tvDChi.setText(CT.getDiaChi());

        ImageView btnXoa = viewitem.findViewById(R.id.btnDeleteCT);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DBhelper.deleteCT(arrayList.get(position));
                } catch (Exception ex) {
                    Log.d("huy", "ko xoa");
                }
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });


        return viewitem;
    }
}