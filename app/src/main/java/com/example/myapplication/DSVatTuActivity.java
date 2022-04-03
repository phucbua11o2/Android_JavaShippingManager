package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.myapplication.ext.ConstExt.POSITION;

public class DSVatTuActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1;
    DBHelper DBhelper;
    ImageView btnReturn2;
    ImageView btnUpdate11,btnNotify;
    //lấy dl hiển thị lên listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsvt);
        // GET THONG TIN VT
        ImageButton imgbtn_addvt = findViewById(R.id.imgbtn_addvt);
        ListView listView = findViewById(R.id.lvDSVT);
        btnReturn2 = findViewById(R.id.btnReturn2);
        btnUpdate11 = findViewById(R.id.btnUpdate11);
        btnNotify=findViewById(R.id.btnNotify);

        ArrayList<VatTu> ArrVT = new ArrayList<>();
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        // lấy dữ liệu bảng vật tư
        Cursor dt = DBhelper.GetData("select * from vattu");
        while (dt.moveToNext()) { //di chyển đến kế bên đến khi hết dữ liệu
            Log.d("SelectVT", dt.getString(0) + " " + dt.getString(3));
            VatTu VT = new VatTu(dt.getInt(0), dt.getString(1), dt.getString(2), dt.getFloat(3), dt.getBlob(4));
            ArrVT.add(VT);
        }
        CustomAdapter_VatTu adapter = new CustomAdapter_VatTu(ArrVT);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POSITION = position;
                Intent intent = new Intent(DSVatTuActivity.this, SuaVTActivity.class);
                startActivity(intent);
            }
        });
//        btnUpdate11.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // POSITION = position;
//                Intent intent = new Intent(DSVatTuActivity.this, SuaVTActivity.class);
//                startActivity(intent);
//            }
//        });
        imgbtn_addvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DSVatTuActivity.this, ThemVTActivity.class);
                startActivity(intent);
            }
        });
        btnReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DSVatTuActivity.this, MainActivity.class));
            }
        });
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DSVatTuActivity.this, NotifyActivity.class));
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
