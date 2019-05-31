package com.example.doannv.bangtinfb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.unti.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DangKyActivity extends AppCompatActivity {
    TextInputLayout usernameWrapper1;
    EditText dkusername;
    TextInputLayout usernameWrapper2;
    EditText edhoten;
    TextInputLayout textInputPassword1;
    EditText dkpassword;
    TextInputLayout textInputPassword2;
    EditText nlpassword;
    Button btndangky;
    CircleImageView imgcameda,profile_image;
    RelativeLayout menu_progressbar;

    Bitmap bitmap;
    int  REQUET_FODER = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        Showpassword();
        EventButton();
    }

    private void EventButton() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = dkusername.getText().toString().trim();
                final String password = dkpassword.getText().toString().trim();
                String password1 = nlpassword.getText().toString().trim();
                final String hoten = edhoten.getText().toString().trim();
                if (username.equals("")){
                    dkusername.setError("Vui lòng nhập dữ liệu");
                }else if (password.equals("")){
                    dkpassword.setError("Vui lòng nhập dữ liệu");
                }else if (password1.equals("")){
                    nlpassword.setError("Vui lòng nhập dữ liệu");
                }else if (hoten.equals("")){
                    edhoten.setError("Vui lòng nhập dữ liệu");
                }else if (password.equals(password1)){
                    menu_progressbar.setVisibility(View.VISIBLE);
                    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdandn, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null){
                                String taikhoan = "";
                                for (int i=0; i<response.length();i++){
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        taikhoan = jsonObject.getString("taikhoan");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (username.equals(taikhoan)){
                                        Toast.makeText(DangKyActivity.this, "Tài Khoản Đã được sử dụng", Toast.LENGTH_SHORT).show();
                                        menu_progressbar.setVisibility(View.INVISIBLE);
                                        return;
                                    }
                                }

                            }
                            RequestQueue requestQueue = Volley.newRequestQueue(DangKyActivity.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandk, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(DangKyActivity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                    menu_progressbar.setVisibility(View.GONE);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    RequestQueue requestQueue = Volley.newRequestQueue(DangKyActivity.this);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandk, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(DangKyActivity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                            menu_progressbar.setVisibility(View.GONE);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String,String> hashMap = new HashMap<>();
                                            hashMap.put("hinhanh","");
                                            hashMap.put("taikhoan",username);
                                            hashMap.put("matkhau",password);
                                            hashMap.put("hoten",hoten);
                                            return hashMap;
                                        }
                                    };
                                    Intent intent = new Intent(DangKyActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    requestQueue.add(stringRequest);
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    String imageData = imageToString(bitmap);
                                    hashMap.put("taikhoan",username);
                                    hashMap.put("matkhau",password);
                                    hashMap.put("hinhanh",imageData);
                                    hashMap.put("hoten",hoten);
                                    return hashMap;
                                }
                            };
                            Intent intent = new Intent(DangKyActivity.this, LoginActivity.class);
                            startActivity(intent);
                            requestQueue.add(stringRequest);

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });

                    requestQueue1.add(jsonArrayRequest);

                }else {
                    nlpassword.setError("Mật khẩu không trùng khớp");
                }

            }
        });
        imgcameda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(DangKyActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUET_FODER);
            }
        });
    }

    private void Showpassword() {
        dkpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    textInputPassword1.setPasswordVisibilityToggleEnabled(false);
                } else {
                    textInputPassword1.setPasswordVisibilityToggleEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        nlpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    textInputPassword2.setPasswordVisibilityToggleEnabled(false);
                } else {
                    textInputPassword2.setPasswordVisibilityToggleEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void AnhXa() {
        usernameWrapper1 = (TextInputLayout) findViewById(R.id.usernameWrapper1);
        dkusername = (EditText) findViewById(R.id.dkusername);
        usernameWrapper2 = (TextInputLayout) findViewById(R.id.usernameWrapper2);
        edhoten = (EditText) findViewById(R.id.edhoten);
        textInputPassword1 = (TextInputLayout) findViewById(R.id.text_input_password1);
        dkpassword = (EditText) findViewById(R.id.dkpassword);
        textInputPassword2 = (TextInputLayout) findViewById(R.id.text_input_password2);
        nlpassword = (EditText) findViewById(R.id.nlpassword);
        btndangky = (Button) findViewById(R.id.btndangky);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        imgcameda = (CircleImageView) findViewById(R.id.imgcameda);
        menu_progressbar = findViewById(R.id.menu_progressbar);
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
                profile_image.setImageBitmap(bitmap);
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