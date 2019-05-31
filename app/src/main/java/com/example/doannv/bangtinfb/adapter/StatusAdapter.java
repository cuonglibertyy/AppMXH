package com.example.doannv.bangtinfb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doannv.bangtinfb.R;
import com.example.doannv.bangtinfb.fragment.MainFragment;
import com.example.doannv.bangtinfb.model.Status;
import com.example.doannv.bangtinfb.unti.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.ID;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ItemHolder>{
    Context context;
    ArrayList<Status> arrayStatus;

    public StatusAdapter( Context context, ArrayList<Status> arrayStatus) {
        this.context = context;
        this.arrayStatus = arrayStatus;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item,null);
            ItemHolder itemHolder = new ItemHolder(view);
            return itemHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder itemHolder, int i) {
        final Status status = arrayStatus.get(i);
            Calendar calendarone = Calendar.getInstance();
            int phut = status.getPhut();
            int gio = status.getGio();
            int ngay = status.getNgay();
            int thang = status.getThang();
            int nam = status.getNam();
            calendarone.set(nam,thang,ngay,gio,phut);
        Calendar calendartwo = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String ngay1 = dateFormat.format(calendartwo.getTime());
        int ngay2 = Integer.valueOf(ngay1);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM");
        String thang1 = dateFormat1.format(calendartwo.getTime());
        int thang2 = Integer.valueOf(thang1);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy");
        String nam1 = dateFormat2.format(calendartwo.getTime());
        int nam2 = Integer.valueOf(nam1);
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("mm");
        String phut1 = dateFormat3.format(calendartwo.getTime());
        int phut2 = Integer.valueOf(phut1);
        SimpleDateFormat dateFormat4 = new SimpleDateFormat("HH");
        String gio1 = dateFormat4.format(calendartwo.getTime());
        int gio2 = Integer.valueOf(gio1);
        calendartwo.set(nam2,thang2,ngay2,gio2,phut2);
        int x = ngay2-ngay;

        if (nam2-nam>=1 && thang2-thang>=0){
            if (nam2-nam==1 && thang2-thang==0 && ngay2-ngay>=0 ){
                itemHolder.Timestamp.setText("11 tháng trước");
            }else if (nam2-nam==1 && thang2-thang==0 && ngay2-ngay<0){
                itemHolder.Timestamp.setText("1 năm trước");
            }else {
                itemHolder.Timestamp.setText((nam2-nam)+" năm trước");
            }
        }else if (nam2-nam>1 && thang2-thang<0){
            itemHolder.Timestamp.setText((nam2-nam-1)+" năm trước");
        }else if (nam2-nam==1 && thang2-thang<0 ){
            itemHolder.Timestamp.setText((thang2-thang+12)+" tháng trước");
        }else if (thang2-thang>=1 && ngay2-ngay>=0){
            itemHolder.Timestamp.setText(thang2-thang+" tháng trước");
        }else if (thang2-thang>1 && ngay2-ngay<0){
            itemHolder.Timestamp.setText((thang2-thang-1)+" tháng trước");
        }else if (thang2-thang==1 && ngay2-ngay<0){
            itemHolder.Timestamp.setText(ngay+" tháng "+thang+"  "+gio+":"+phut);
        }else if (ngay2-ngay>1 && gio2-gio>=0){
            if ((x)/7 ==1 ){
                itemHolder.Timestamp.setText("1 tuần trước");
            }else if ((x)/7==2){
                itemHolder.Timestamp.setText("2 tuần trước");
            }else if ((x)/7==3 ){
                itemHolder.Timestamp.setText("3 tuần trước");
            }else {
                itemHolder.Timestamp.setText(ngay+" tháng "+thang+"  "+gio+":"+phut);
            }
        } else if (ngay2-ngay>1 && gio2-gio<0 ){
            if ((x-1)/7 ==1 ){
                itemHolder.Timestamp.setText("1 tuần trước");
            }else if ((x-1)/7==2 ){
                itemHolder.Timestamp.setText("2 tuần trước");
            }else if ((x-1)/7==3 ){
                itemHolder.Timestamp.setText("3 tuần trước");
            }
            else {
                itemHolder.Timestamp.setText(ngay+" tháng "+thang+"  "+gio+":"+phut);
            }
        }else if (ngay2-ngay == 1 && gio2-gio>=0){
            itemHolder.Timestamp.setText("Hôm qua " +gio+":"+phut);
        } else if (ngay2-ngay==1 && gio2-gio<0){
            itemHolder.Timestamp.setText("Hôm qua " +gio+":"+phut);
        }else if (gio2-gio>=1 && phut2-phut>=0){
            itemHolder.Timestamp.setText(gio2-gio +" giờ trước");
        }else if (gio2-gio>1 && phut2-phut<0){
            itemHolder.Timestamp.setText(gio2-gio-1 +" giờ trước");
        }else if (gio2-gio==1 && phut2-phut<0){
            itemHolder.Timestamp.setText((phut2-phut+60) +" phút trước");
        }else if (phut2-phut>0){
            itemHolder.Timestamp.setText(phut2-phut +" phút trước");
        }else {
            itemHolder.Timestamp.setText("1 phút trước");
        }
            itemHolder.Name.setText(status.getName());
            if (status.getStatus().equals("")){
                itemHolder.Descriptio.setVisibility(View.GONE);
            }else {
                itemHolder.Descriptio.setText(status.getStatus());
            }
            Picasso.get().load(Server.duongdananh+status.getAnh())
                    .placeholder(R.drawable.ic_avatar)
                    .error(R.drawable.ic_avatar)
                    .into(itemHolder.Thumbnail);

            if (status.getImgstatus().equals("")){
                itemHolder.Imgstatus.setVisibility(View.GONE);
            }else {
                Picasso.get().load(Server.duongdananh+status.getImgstatus())
                        .placeholder(R.drawable.ic_avatar)
                        .error(R.drawable.ic_avatar)
                        .into(itemHolder.Imgstatus);
            }
             final int IDOKk =  status.getIDtaikhoanOnLine();
             Log.d("okokokokoko",""+IDOKk);

              itemHolder.tvlike.setText("Chưa Like");
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanlikeok, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    if (response != null) {
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                int   ID1 = jsonObject.getInt("idtk");
//                                String  Name = jsonObject.getString("trangthai");
//                               if (ID1 == IDOKk && Name.equals("true")){
//                                       itemHolder.tvlike.setText("Đã Like");
//                               }else if (ID1 == IDOKk && Name.equals("false")){
//                                   itemHolder.tvlike.setText("Chưa Like");
//                               }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    HashMap<String,String> hashMap = new HashMap<>();
//                    int OK = status.getID();
//                    hashMap.put("idstatus",""+OK);
//                    return hashMap;
//                }
//            };
//            requestQueue.add(stringRequest);
//
//        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.duongdanlike, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response != null) {
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            itemHolder.tvsoLike.setText(""+(i+1));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> hashMap = new HashMap<>();
//                int OK = status.getID();
//                hashMap.put("idstatus",""+OK);
//                hashMap.put("trangthai","true");
//                return hashMap;
//            }
//        };
//        requestQueue.add(stringRequest1);


    }

    @Override
    public int getItemCount() {
        return arrayStatus.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public CircleImageView Thumbnail;
        public TextView Name,Timestamp,Descriptio;
        public ImageView Imgstatus;
        public TextView tvlike;
        public TextView tvsoLike;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            Thumbnail = itemView.findViewById(R.id.thumbnail);
            Name = itemView.findViewById(R.id.name);
            Timestamp = itemView.findViewById(R.id.timestamp);
            Descriptio = itemView.findViewById(R.id.description);
            Imgstatus = itemView.findViewById(R.id.imgstatus);
            tvlike = itemView.findViewById(R.id.tvLike);
            tvsoLike = itemView.findViewById(R.id.tvsolike);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ChiTietSpActivity.class);
//                    intent.putExtra("thongtinsanpham", arraySanpham.get(getPosition()));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }
//            });

        }
    }
}
