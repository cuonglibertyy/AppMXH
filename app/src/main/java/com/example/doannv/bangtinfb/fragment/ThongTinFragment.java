package com.example.doannv.bangtinfb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.R;
import com.example.doannv.bangtinfb.adapter.StatusAdapter;
import com.example.doannv.bangtinfb.model.Status;
import com.example.doannv.bangtinfb.unti.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
     Toolbar toolbarthongtin;
     NestedScrollView nestedScrollview;
     CircleImageView CIVthongtin;
     CircleImageView imgthongtin;
     TextView tvnamethongtin;
     RecyclerView RCVthongtin;
     ProgressBar PBthongtin;
    ArrayList<Status> mangstatus;
    StatusAdapter statusAdapter;
    int page = 1;
    int IDOK;
    boolean isloading;
    boolean limitdata = false;


    private View statics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        statics = inflater.inflate( R.layout.thongtin_fragment, container, false );
        AnhXa();
        DangTin();
        GetData(page);
        LoadMoreData();
        return statics;
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdantrangcanhan + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String ID1 = bundle.getString("Id");
                    IDOK = Integer.valueOf(ID1);
                }
                int ID = 0;
                String Name = "";
                String Anh = "";
                int Phut = 0;
                int Gio = 0;
                int Ngay = 0;
                int Thang = 0;
                int Nam = 0;
                String Status = "";
                String ImgStatus = "";
                int IDTK = 0;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Name = jsonObject.getString("nameok");
                            Anh = jsonObject.getString("anh");
                            Phut = jsonObject.getInt("phut");
                            Gio = jsonObject.getInt("gio");
                            Ngay = jsonObject.getInt("ngay");
                            Thang = jsonObject.getInt("thang");
                            Nam = jsonObject.getInt("nam");
                            Status = jsonObject.getString("statusok");
                            IDTK = jsonObject.getInt("idtaikhoan");
                            ImgStatus = jsonObject.getString("imgstatus");
                            mangstatus.add(new Status(ID, Name, Anh, Phut, Gio, Ngay, Thang, Nam, Status, IDTK, ImgStatus,IDOK));
                            statusAdapter.notifyDataSetChanged();
                            limitdata = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitdata = true;
                    PBthongtin.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String ID = bundle.getString("Id");
                    param.put("idtaikhoan",String.valueOf(ID));
                }
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        bottomNavigationView = statics.findViewById (R.id.btNavigation);
        toolbarthongtin = statics.findViewById(R.id.toolbarthongtin);
//        final AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbarthongtin);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        activity.getMenuInflater();
//        toolbarthongtin.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        nestedScrollview = (NestedScrollView) statics.findViewById(R.id.NSVthongtin);
        CIVthongtin = (CircleImageView) statics.findViewById(R.id.CIVthongtin);
        imgthongtin = (CircleImageView) statics.findViewById(R.id.imgthongtin);
        tvnamethongtin = (TextView) statics.findViewById(R.id.tvnamethongtin);
        RCVthongtin = (RecyclerView) statics.findViewById(R.id.RCVthongtin);
        PBthongtin = (ProgressBar) statics.findViewById(R.id.PBthongtin);

        mangstatus = new ArrayList<>();
        statusAdapter = new StatusAdapter(getContext(), mangstatus);
        RCVthongtin.setHasFixedSize(true);
        RCVthongtin.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RCVthongtin.setAdapter(statusAdapter);
    }
    private void DangTin() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String Hinh = bundle.getString("HinhAnh");
            String Ten = bundle.getString("Hoten");
            String ID = bundle.getString("Id");
            tvnamethongtin.setText(Ten);
            Picasso.get().load(Server.duongdananh + Hinh)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(CIVthongtin);
        }
    }
    private void LoadMoreData() {
        nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int i, int i1, int i2, int i3) {
                if (i1 == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (limitdata == false) {
                        isloading = true;
                        if (isloading) {
                            PBthongtin.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    PBthongtin.setVisibility(View.GONE);
                                    GetData(page = page + 1);

                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
    }
}
