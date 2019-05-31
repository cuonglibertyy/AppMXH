package com.example.doannv.bangtinfb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.adapter.StatusAdapter;
import com.example.doannv.bangtinfb.unti.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout usernameWrapper;
    EditText username;
    TextInputLayout textInputPassword;
    EditText password;
    CheckBox checkbox;
    TextView tvDangKy;
    Button btndangnhap;
    RelativeLayout menu_progressbar;
    SharedPreferences preferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        Showpassword();
        EventButton();
        Check();

    }

    private void Check() {
        String username2 = preferences.getString("usernameok", "");
        String password2 = preferences.getString("passwordok", "");
        username.setText(username2);
        password.setText(password2);
        if (username.equals("")){
            checkbox.setChecked(false);
        }else {
            checkbox.setChecked(true);
        }
    }

    private void EventButton() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String taikhoan1 = username.getText().toString().trim();
                final String matkhau1 = password.getText().toString().trim();

                if (taikhoan1.equals("")){
                    username.setError("Chưa có dữ liệu");
                    return;
                }
                if (matkhau1.equals("")){
                    password.setError("Chưa có dữ liệu");
                    return;
                }
                menu_progressbar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdandn, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            String taikhoan = "";
                            String matkhau = "";
                            String hoten = "";
                            String hinhanh = "";
                            int ID =0;
                            for (int i=0; i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    taikhoan = jsonObject.getString("taikhoan");
                                    matkhau = jsonObject.getString("matkhau");
                                    hoten = jsonObject.getString("hoten");
                                    hinhanh = jsonObject.getString("hinhanh");
                                    ID = jsonObject.getInt("id");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (taikhoan1.equals(taikhoan) && matkhau1.equals(matkhau) ){
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("hoten",hoten);
                                    intent.putExtra("hinhanh",hinhanh);
                                    String ID2 = String.valueOf(ID);
                                    intent.putExtra("idok",ID2);


                                    Bundle bundle = new Bundle();
                                    bundle.putString("OK",ID2);
                                    intent.putExtras(bundle);

                                    if (checkbox.isChecked()){
                                        preferences.edit().putString("usernameok", taikhoan1).commit();
                                        preferences.edit().putString("passwordok", matkhau1).commit();
                                    }else {
                                        preferences.edit().putString("usernameok", "").commit();
                                        preferences.edit().putString("passwordok", "").commit();
                                    }
                                    menu_progressbar.setVisibility(View.INVISIBLE);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.ani_enter,R.anim.ani_exit);
                                    return;
                                }
                            }


                        }
                        menu_progressbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Tài khoản mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_enter,R.anim.ani_exit);
            }
        });
    }

    private void Showpassword() {
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    textInputPassword.setPasswordVisibilityToggleEnabled(false);
                } else {
                    textInputPassword.setPasswordVisibilityToggleEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void AnhXa() {
        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        username = (EditText) findViewById(R.id.username);
        textInputPassword = (TextInputLayout) findViewById(R.id.text_input_password);
        password = (EditText) findViewById(R.id.password);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        tvDangKy = (TextView) findViewById(R.id.tvDangKy);
        btndangnhap = (Button) findViewById(R.id.btndangnhap);
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        menu_progressbar = findViewById(R.id.menu_progressbar);
        menu_progressbar.setVisibility(View.INVISIBLE);
    }
}
