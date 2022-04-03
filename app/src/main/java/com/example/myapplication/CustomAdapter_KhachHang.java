package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter_KhachHang extends BaseAdapter {
    ArrayList<KH> arrayList;
    private DBHelper DBhelper;

    public CustomAdapter_KhachHang(ArrayList<KH> arrayList) {
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
        View viewitem = View.inflate(parent.getContext(), R.layout.item_dskh, null);
        KH KH = (KH) getItem(position);
        TextView tvMaKH = (TextView) viewitem.findViewById(R.id.tvMaKH);
        tvMaKH.setText(String.valueOf(KH.getMaKH()));
        TextView tvTenKH = (TextView) viewitem.findViewById(R.id.tvTenKH);
        tvTenKH.setText(KH.getTenKH());
        TextView tvEmail = (TextView) viewitem.findViewById(R.id.tvEmail);
        tvEmail.setText(KH.getEmail());
        TextView tvSdt = (TextView) viewitem.findViewById(R.id.tvSdt);
        tvSdt.setText(KH.getSdt());
        TextView tvMaPVC = (TextView) viewitem.findViewById(R.id.tvMaPVC);
        tvMaPVC.setText(KH.getMaPVC());

        ImageView btnDelete = viewitem.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DBhelper.deleteKH(arrayList.get(position));
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