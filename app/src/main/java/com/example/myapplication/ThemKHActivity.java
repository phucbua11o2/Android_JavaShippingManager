package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ThemKHActivity extends AppCompatActivity {

   // private int REQUEST_CODE = 8888;
    DBHelper DBhelper;
    EditText edtTenKH, edtEmail, edtSdt;
    Button btnTaoKH;
    Spinner PVCspinner;
    int selected_positionPVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper = new DBHelper(ThemKHActivity.this, "qlvc.sqlite", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_kh);
        create();
        setEvent();

    }
    //Sau khi chụp ảnh và gửi về rồi, chúng ta cần một hàm để nhận dữ liệu này và hiển thị lên trên ImageView
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //kiểm tra xem biến requestCode trả về có mang giá trị giống như biến REQUEST_CODE đã khai báo hay không,
//        // vì có thể nhiều Activity sẽ có nhiều REQUEST_CODE khác nhau
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            ivHinh.setImageBitmap(bitmap);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    //hình ảnh lưu dưới dạng byte
//    //lấy mảng byte lưu vào csdl
//    public byte[] ConverttoArrayByte(ImageView img) {
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return stream.toByteArray();
//    }


    private void setEvent() {

//        ivHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //kết nối camera
//                startActivityForResult(intent, REQUEST_CODE); //trả về dữ liệu khi chụp xong và ss dk khi trả về dlieu
//                                                                //có đúng Activity đang cần ko
//            }
//        });
        ArrayList<PhieuVanChuyen> dsPVC= new ArrayList<PhieuVanChuyen>();
        Cursor dt= DBhelper.GetData("select * from PVC");
        while (dt.moveToNext()){
            PhieuVanChuyen ct=new PhieuVanChuyen(dt.getString(0), dt.getString(1),dt.getString(2),dt.getString(3));
            dsPVC.add(ct);
        }

        ArrayAdapter spinnerAdapterPVC = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dsPVC);
        PVCspinner.setAdapter(spinnerAdapterPVC);

        PVCspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selected_positionPVC= position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

        btnTaoKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if (edtTenKH.getText().toString().trim().isEmpty()) {
                    edtTenKH.setError("Tên khách hàng không được trống");
                    flagValid = false;
                }
                if (edtEmail.getText().toString().trim().isEmpty()) {
                    edtEmail.setError("Email không được trống");
                    flagValid = false;
                }
                if (edtSdt.getText().toString().trim().isEmpty()) {
                    edtSdt.setError("SDT không được trống");
                    flagValid = false;
                }
                try {
                    if (flagValid) {
                        Log.d("addd", "KH: " + edtTenKH.getText().toString().trim());

                        DBhelper.QueryData("insert into KH values(null,'"   + edtTenKH.getText().toString() + "','" + edtEmail.getText().toString()+ "','"  + edtSdt.getText().toString() +"','" + dsPVC.get(selected_positionPVC).getMaPVC() + "')");
                        Toast.makeText(ThemKHActivity.this, "thêm khách hàng thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(ThemKHActivity.this, KhachHangActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(ThemKHActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void create() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSdt);
        PVCspinner =findViewById(R.id.spnPVC1);
        btnTaoKH = findViewById(R.id.btnTaoKH);
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