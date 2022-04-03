package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemPVCActivity extends AppCompatActivity {
    DBHelper DBhelper;
    int selected_position;
    ImageView btnReturn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper = new DBHelper(this, "qlvc.sqlite", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_pvc);


        EditText edtMaPVC = findViewById(R.id.edtMaPVC);
        EditText edtTT=findViewById(R.id.spnTT);
        Button btnadd = findViewById(R.id.btnTaoPVC);
        Spinner gvspinner = findViewById(R.id.spnCT);
        btnReturn7 = findViewById(R.id.btnReturn7);

        ArrayList<CongTrinh> dsCT = new ArrayList<CongTrinh>();
        Cursor dt = DBhelper.GetData("select * from congtrinh");
        while (dt.moveToNext()) {
            CongTrinh ct = new CongTrinh(dt.getString(0), dt.getString(1), dt.getString(2));
            dsCT.add(ct);
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dsCT);
        gvspinner.setAdapter(spinnerAdapter);

        gvspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected_position = position;
                //đối số postion là vị trí phần tử trong list Data
//                String msg = "position :" + position + " value :" + dsgiangvien.get(position);
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePicker ngaygiao = findViewById(R.id.dpCT);
                int day = ngaygiao.getDayOfMonth();
                int month = ngaygiao.getMonth() + 1;
                int year = ngaygiao.getYear();

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String ngaychon = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);

                Date date2 = Calendar.getInstance().getTime();
                String strtodate = dateFormat.format(date2);
                Boolean flagValid = true;
                String MaPVC = edtMaPVC.getText().toString();
                String TT=edtTT.getText().toString();
                if (edtMaPVC.getText().toString().trim().isEmpty()) {
                    edtMaPVC.setError("Mã phiếu vận chuyển không được trống");
                    flagValid = false;
                }

                try {
                    if (flagValid) {
                        // Kiểm tra xem ngày giao có lớn hơn ngày hiện tại không
                        if (dateFormat.parse(ngaychon).before(dateFormat.parse(strtodate)) || dateFormat.parse(ngaychon).equals(dateFormat.parse(strtodate))) {
                            Toast.makeText(getApplicationContext(), "Ngày giao phải lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                DBhelper.QueryData("insert into PVC values('" + MaPVC + "','" + dsCT.get(selected_position).getTenCT() + "','" + ngaychon + "','" +TT+ "')");
                                Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ThemPVCActivity.this, PhieuVanChuyenActivity.class);
                                startActivity(intent);
                            } catch (Exception ex) {
                                Toast.makeText(getApplicationContext(), "Lỗi ghi CSDL! Không thể thêm!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
        btnReturn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemPVCActivity.this, MainActivity.class));
            }
        });
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.memu_home:
                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.memu_back:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
