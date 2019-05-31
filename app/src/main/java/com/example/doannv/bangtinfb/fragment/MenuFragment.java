package com.example.doannv.bangtinfb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doannv.bangtinfb.LoginActivity;
import com.example.doannv.bangtinfb.MainActivity;
import com.example.doannv.bangtinfb.R;

public class MenuFragment extends Fragment {
    private View view;
    private TextView tvdangxuat;
    private TextView tvBangtin;
    private TextView tvFriend;
    private TextView tvCaidat;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.menu_fragment, container, false );
        tvdangxuat = view.findViewById(R.id.tvDangxuat);
        tvBangtin = (TextView) view.findViewById(R.id.tvBangtin);
        tvFriend = (TextView) view.findViewById(R.id.tvFriend);
        tvCaidat = (TextView) view.findViewById(R.id.tvCaidat);

        tvdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvBangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
