package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChiTietPVCActivity extends AppCompatActivity {

    DBHelper DBhelper;
    ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_pvc);
        // GET THONG TIN VT
        ImageButton imgbtn_addctvc = findViewById(R.id.imgbtn_addctvc);
        ListView listView = findViewById(R.id.lvCTVC);
        btnReturn = findViewById(R.id.btnReturn);

        ArrayList<ChiTietPVC> ArrCTVC = new ArrayList<>();
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        Cursor dt = DBhelper.GetData("select * from chitietPVC");
        while (dt.moveToNext()) {
            Log.d("SelectCTPVC", dt.getString(0) + " " + dt.getString(2));
            ChiTietPVC CTVC = new ChiTietPVC(dt.getString(0), dt.getInt(1), dt.getInt(2), dt.getInt(3));
            ArrCTVC.add(CTVC);
        }
        CustomAdapter_CTVC adapter = new CustomAdapter_CTVC(ArrCTVC);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("magiaovien" , "ggg");
//                Toast.makeText(DSGVActivity.this,ArrGV.get(position).getID(),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(DSGVActivity.this, DSMHActivity.class);
//                GiangVien GV= (GiangVien) ArrGV.get(position);
//                intent.putExtra("message", ArrGV.get(position).getID());
//                startActivity(intent);
//            }
//        });


        imgbtn_addctvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietPVCActivity.this, ThemCTVCActivity.class);
                startActivity(intent);
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChiTietPVCActivity.this, MainActivity.class));
            }
        });
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_danhsach, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.memu_home:
                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}