package com.example.mesablet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mesablet.R;
import com.example.mesablet.entities.IntroItem;

public class IntroFragment extends Fragment {

    private IntroItem introItem;
    private int position;

    public static IntroFragment newInstance(IntroItem item, int position){
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putSerializable("introItem",item);
        b.putInt("position",position);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introItem = (IntroItem) getArguments().getSerializable("introItem");
        position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_intro,container,false);
        view.setTag(position);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {

        ImageView imageView = view.findViewById(R.id.IV_intro);
        imageView.setImageResource(introItem.getImg());

        TextView textView = view.findViewById(R.id.TV_description);
        textView.setText(introItem.getText());

        TextView textView2 = view.findViewById(R.id.TV_title);
        textView2.setText(introItem.getTitle());
    }
}
