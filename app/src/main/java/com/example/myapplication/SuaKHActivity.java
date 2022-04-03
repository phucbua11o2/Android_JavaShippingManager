package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import java.io.IOException;
import java.util.ArrayList;

import static com.example.myapplication.ext.ConstExt.POSITION;

public class SuaKHActivity extends AppCompatActivity {

    DBHelper DBhelper;
    EditText edtTenKH,edtEmail,edtSdt;
    Spinner PVCspinner;
    Button btnSuaKH;
    int maKH;
    int selected_positionPVC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBhelper= new DBHelper(SuaKHActivity.this,"qlvc.sqlite",null,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_kh);
        create();

        ArrayList<KH> ArrKH  = new ArrayList<>();
        DBhelper = new DBHelper(this,"qlvc.sqlite",null,1);
        Cursor dt= DBhelper.GetData("select * from KH");
        while(dt.moveToNext())
        {
            Log.d("SelectKH", dt.getString(0) );
            KH KH = new KH(dt.getInt(0),dt.getString(1),dt.getString(2),dt.getString(3),dt.getString(4));
            ArrKH.add(KH);
        }
        CustomAdapter_KhachHang adapter = new CustomAdapter_KhachHang(ArrKH);
        KH kh = (KH) adapter.getItem(POSITION);
        edtTenKH.setText(kh.getTenKH());
        edtEmail.setText(kh.getEmail());
        edtSdt.setText(kh.getSdt());
//        Bitmap bitmap= BitmapFactory.decodeByteArray(vt.getHinh(), 0, vt.getHinh().length);
//        imageView.setImageBitmap(bitmap);
        maKH =kh.getMaKH();
        setEvent();

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
//            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
//                Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,600,600,true);
//
//                imageView.setImageBitmap(bitmap1);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public byte[] ConverttoArrayByte(ImageView img)
//    {
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
//        Bitmap bitmap=bitmapDrawable.getBitmap();
//
//        ByteArrayOutputStream stream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        return stream.toByteArray();
//    }


    private void setEvent() {
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent gallery = new Intent();
//                gallery.setType("image/*");
//                gallery.setAction(gallery.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(gallery,"Chọn Hình Ảnh"), PICK_IMAGE);
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
        btnSuaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagValid = true;
                if(edtTenKH.getText().toString().trim().isEmpty())
                {
                    edtTenKH.setError("Tên khách hàng không được trống");
                    flagValid = false;
                }
                if(edtEmail.getText().toString().trim().isEmpty())
                {
                    edtEmail.setError("Email không được trống");
                    flagValid = false;
                }
                if(edtSdt.getText().toString().trim().isEmpty())
                {
                    edtSdt.setError("SDT không được trống");
                    flagValid = false;
                }
                try {
                    if(flagValid)
                    {
                        Log.d("edit", "KH: " +  edtTenKH.getText().toString().trim());

                        DBhelper.updateKH(edtTenKH.getText().toString(), edtEmail.getText().toString(),edtSdt.getText().toString(),dsPVC.get(selected_positionPVC).getMaPVC(), maKH);
                       // DBhelper.QueryData("Update into KH values(null,'"   + edtTenKH.getText().toString() + "','" + edtEmail.getText().toString()+ "','"  + edtSdt.getText().toString() +"','" + dsPVC.get(selected_positionPVC).getMaPVC() + "')");
                        Toast.makeText(SuaKHActivity.this, "sửa khách hàng thành công!", Toast.LENGTH_LONG).show();
                        //thêm xong thì về lại danh sách
                        Intent intent = new Intent(SuaKHActivity.this, KhachHangActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SuaKHActivity.this, "không thành công!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void create() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtEmail = findViewById(R.id.edtEmail);
        edtSdt = findViewById(R.id.edtSdt);
        PVCspinner=findViewById(R.id.spnPVC);
        btnSuaKH = findViewById(R.id.btnSuaKH);
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