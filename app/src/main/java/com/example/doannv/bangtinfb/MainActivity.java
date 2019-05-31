package com.example.doannv.bangtinfb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.doannv.bangtinfb.adapter.StatusAdapter;
import com.example.doannv.bangtinfb.fragment.MainFragment;
import com.example.doannv.bangtinfb.fragment.MenuFragment;
import com.example.doannv.bangtinfb.fragment.ThongTinFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    StatusAdapter statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        BottomNavigation();
    }
    private void BottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (menuItem.getItemId () == R.id.Main) {
                    fragment = new MainFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.commit();

                    Intent intent = getIntent();

                    Bundle bundle = new Bundle();
                    String Hinh = intent.getStringExtra("hinhanh");
                    String hoten = intent.getStringExtra("hoten");
                    String ID = intent.getStringExtra("idok");


                    bundle.putString("HinhAnh",Hinh);
                    bundle.putString("Hoten",hoten);
                    bundle.putString("Id",ID);

                    fragment.setArguments(bundle);
                }
                if (menuItem.getItemId () == R.id.ThongTin) {
                    fragment = new ThongTinFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.commit();
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    String Hinh = intent.getStringExtra("hinhanh");
                    String hoten = intent.getStringExtra("hoten");
                    String ID = intent.getStringExtra("idok");


                    bundle.putString("HinhAnh",Hinh);
                    bundle.putString("Hoten",hoten);
                    bundle.putString("Id",ID);
                    fragment.setArguments(bundle);
                }
                if (menuItem.getItemId () == R.id.Menu) {
                    fragment = new MenuFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
    }

    private void AnhXa() {
        bottomNavigationView = findViewById (R.id.btNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Main);
        Fragment fragment = null;
        fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        String Hinh = intent.getStringExtra("hinhanh");
        String hoten = intent.getStringExtra("hoten");
        String ID = intent.getStringExtra("idok");

        bundle.putString("HinhAnh",Hinh);
        bundle.putString("Hoten",hoten);
        bundle.putString("Id",ID);
        fragment.setArguments(bundle);
    }
}

