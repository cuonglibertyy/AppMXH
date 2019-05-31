package com.example.doannv.bangtinfb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.fragment.MainFragment;
import com.example.doannv.bangtinfb.unti.Server;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusActivity extends AppCompatActivity {
     Toolbar toolbardangtin;
     CircleImageView CIVbaiviet;
     RelativeLayout menu_progressbar;
     TextView namedangtin;
     EditText edstatusdangtin;
     ImageView imgdangtin;
     TextView tvDangtin,tvDangTin2,tvchonanh;
    private int REQUET_FODER = 999;
    Bitmap bitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        AnhXa();
        ActionToolbar();
        CheckValue();
        EventOnclick();

    }

    private void EventOnclick() {
        tvDangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_progressbar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(StatusActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandangtin, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = getIntent();
                        String Hinh = intent.getStringExtra("Hinh");
                        String Ten = intent.getStringExtra("Ten");
                        String ID = intent.getStringExtra("ID23");
                       Intent intent1 = new Intent(StatusActivity.this,MainActivity.class);
                        intent1.putExtra("hoten",Ten);
                        intent1.putExtra("hinhanh",Hinh);
                        String ID2 = String.valueOf(ID);
                        intent1.putExtra("idok",ID2);
                        menu_progressbar.setVisibility(View.GONE);
                        startActivity(intent1);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        RequestQueue requestQueue = Volley.newRequestQueue(StatusActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandangtin, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = getIntent();
                                String Hinh = intent.getStringExtra("Hinh");
                                String Ten = intent.getStringExtra("Ten");
                                String ID = intent.getStringExtra("ID23");
                                Intent intent1 = new Intent(StatusActivity.this,MainActivity.class);
                                intent1.putExtra("hoten",Ten);
                                intent1.putExtra("hinhanh",Hinh);
                                String ID2 = String.valueOf(ID);
                                intent1.putExtra("idok",ID2);
                                menu_progressbar.setVisibility(View.GONE);
                                startActivity(intent1);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> hashMap = new HashMap<>();
                                Intent intent = getIntent();
                                String Hinh = intent.getStringExtra("Hinh");
                                String Ten = intent.getStringExtra("Ten");
                                String ID = intent.getStringExtra("ID23");

                                Calendar calendartwo = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                                String ngay = dateFormat.format(calendartwo.getTime());
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
                                String thang = dateFormat1.format(calendartwo.getTime());
                                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
                                String nam = dateFormat2.format(calendartwo.getTime());
                                SimpleDateFormat dateFormat3 = new SimpleDateFormat("mm");
                                String phut = dateFormat3.format(calendartwo.getTime());
                                SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH");
                                String gio = dateFormat4.format(calendartwo.getTime());

                                String status = edstatusdangtin.getText().toString().trim();
                                Log.d("okokoko",Hinh);

                                hashMap.put("nameok",Ten);
                                hashMap.put("anh",Hinh);
                                hashMap.put("phut",phut);
                                hashMap.put("gio",gio);
                                hashMap.put("ngay",ngay);
                                hashMap.put("thang",thang);
                                hashMap.put("nam",nam);
                                hashMap.put("statusok",status);
                                hashMap.put("idtaikhoan",ID);
                                hashMap.put("imgstatus","");
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<>();
                        String imageData = imageToString(bitmap);
                        Intent intent = getIntent();
                         String Hinh = intent.getStringExtra("Hinh");
                         String Ten = intent.getStringExtra("Ten");
                         String ID = intent.getStringExtra("ID23");

                        Calendar calendartwo = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                         String ngay = dateFormat.format(calendartwo.getTime());
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
                         String thang = dateFormat1.format(calendartwo.getTime());
                        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
                         String nam = dateFormat2.format(calendartwo.getTime());
                        SimpleDateFormat dateFormat3 = new SimpleDateFormat("mm");
                         String phut = dateFormat3.format(calendartwo.getTime());
                        SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH");
                         String gio = dateFormat4.format(calendartwo.getTime());

                         String status = edstatusdangtin.getText().toString().trim();

                        hashMap.put("nameok",Ten);
                        hashMap.put("anh",Hinh);
                        hashMap.put("phut",phut);
                        hashMap.put("gio",gio);
                        hashMap.put("ngay",ngay);
                        hashMap.put("thang",thang);
                        hashMap.put("nam",nam);
                        hashMap.put("statusok",status);
                        hashMap.put("idtaikhoan",ID);

                        hashMap.put("imgstatus",imageData);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        tvchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(StatusActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUET_FODER);
            }
        });
    }

    private void CheckValue() {
        edstatusdangtin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s2 = s.toString().trim();

                if (s2.length() == 0  ) {
                    if (bitmap == null){
                        tvDangtin.setVisibility(View.GONE);
                    }else {
                        tvDangtin.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDangtin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardangtin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardangtin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }

    private void AnhXa() {
        tvDangtin = findViewById(R.id.tvdangtin);
        tvDangTin2 = findViewById(R.id.tvdangtin2);
        toolbardangtin = (Toolbar) findViewById(R.id.toolbardangtin);
        CIVbaiviet = (CircleImageView) findViewById(R.id.CIVbaiviet);
        namedangtin = (TextView) findViewById(R.id.namedangtin);
        edstatusdangtin = (EditText) findViewById(R.id.edstatusdangtin);
        imgdangtin = (ImageView) findViewById(R.id.imgdangtin);
        tvchonanh = findViewById(R.id.tvchonanh);
        menu_progressbar = findViewById(R.id.menu_progressbar);

        Intent intent = getIntent();
        String Hinh = intent.getStringExtra("Hinh");
        String Ten = intent.getStringExtra("Ten");
        String ID = intent.getStringExtra("ID");
        namedangtin.setText(Ten);
        Picasso.get().load(Server.duongdananh+Hinh)
                .placeholder(R.drawable.ic_avatar)
                .error(R.drawable.ic_avatar)
                .into(CIVbaiviet);
    }
    // cấp quyền cho truy cập
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUET_FODER){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                  Intent intent = new  Intent(Intent.ACTION_PICK);
//                Intent intent = new  Intent(Intent.ACTION_ATTACH_DATA);
                Intent intent = new  Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),REQUET_FODER);
            }
            else{
                Toast.makeText(this, "Bạn ko có quyền truy cập thư viện", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // hiện thị ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUET_FODER && resultCode == RESULT_OK && data!= null ){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangtin.setImageBitmap(bitmap);
                imgdangtin.setVisibility(View.VISIBLE);
                tvDangtin.setVisibility(View.VISIBLE);
                edstatusdangtin.setHint("Hãy nói gì đó về bức ảnh này...");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    // phương thức post ảnh
    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[]  imageBytes = outputStream.toByteArray();
        String edcodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return  edcodeImage;
    }
}
