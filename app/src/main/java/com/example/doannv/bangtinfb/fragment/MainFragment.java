package com.example.doannv.bangtinfb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.R;
import com.example.doannv.bangtinfb.StatusActivity;
import com.example.doannv.bangtinfb.adapter.StatusAdapter;
import com.example.doannv.bangtinfb.adapter.StatusAdapter.ItemHolder;
import com.example.doannv.bangtinfb.model.Status;
import com.example.doannv.bangtinfb.unti.Server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends Fragment {
    private View statics;
    BottomNavigationView bottomNavigationView;
    ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerview;
    RelativeLayout ReLdangtin;

    CircleImageView CIVdangtin;
    TextView eddangtin;
    ProgressBar progressBar;
    ArrayList<Status> mangstatus;
    StatusAdapter statusAdapter;
    int page = 1;
    int IDOK;
    boolean isloading;
    boolean limitdata = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        statics = inflater.inflate(R.layout.main_fragment, container, false);
        AnhXa();
        DangTin();
        GetData(page);
        LoadMoreData();
        return statics;
    }

    private void DangTin() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String Hinh = bundle.getString("HinhAnh");
            Picasso.get().load(Server.duongdananh + Hinh)
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(CIVdangtin);
        }
        ReLdangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatusActivity.class);
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String Hinh = bundle.getString("HinhAnh");
                    String Hoten = bundle.getString("Hoten");
                    String ID = bundle.getString("Id");

                    intent.putExtra("Hinh", Hinh);
                    intent.putExtra("Ten", Hoten);
                    intent.putExtra("ID23", ID);
                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
        eddangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StatusActivity.class);
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String Hinh = bundle.getString("HinhAnh");
                    String Hoten = bundle.getString("Hoten");
                    String ID = bundle.getString("Id");

                    intent.putExtra("Hinh", Hinh);
                    intent.putExtra("Ten", Hoten);
                    intent.putExtra("ID23", ID);
                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
        CIVdangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new ThongTinFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }

        });
    }


    private void LoadMoreData() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int i, int i1, int i2, int i3) {
                if (i1 == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (limitdata == false) {
                        isloading = true;
                        if (isloading) {
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    GetData(page = page + 1);

                                }
                            }, 1000);
                        }
                    }
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = Server.duongdanstatus + String.valueOf(page);
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
                    progressBar.setVisibility(View.GONE);
                }

                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    private void AnhXa() {
        bottomNavigationView = statics.findViewById(R.id.btNavigation);
        mShimmerViewContainer = statics.findViewById(R.id.shimmer_view_container);

        recyclerview = statics.findViewById(R.id.RCVstatus);
        mangstatus = new ArrayList<>();
        statusAdapter = new StatusAdapter(getContext(), mangstatus);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerview.setAdapter(statusAdapter);
        nestedScrollView = statics.findViewById(R.id.nestedScrollview);
        progressBar = statics.findViewById(R.id.progressBarRCV);

        ReLdangtin = statics.findViewById(R.id.ReLdangtin);
        CIVdangtin = statics.findViewById(R.id.CIVdangtin);
        eddangtin = statics.findViewById(R.id.eddangtin);
    }


    @Override
    public void onResume() {
        mShimmerViewContainer.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}

