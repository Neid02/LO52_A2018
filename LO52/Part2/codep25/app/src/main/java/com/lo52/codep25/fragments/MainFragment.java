package com.lo52.codep25.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.lo52.codep25.R;
import com.lo52.codep25.modules.main.equipe.EquipeActivity;


public class MainFragment extends Fragment {


    private View view;
    LinearLayout linearLayout;

    private CircleProgress circleProgress;
    private ArcProgress arcProgress;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefragment, container, false);

        linearLayout = view.findViewById(R.id.equipe);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EquipeActivity.class);
                startActivity(intent);
            }
        });
        return view;



    }


}
