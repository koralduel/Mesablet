package com.example.mesablet.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import com.example.mesablet.R;


public class CustomDialog extends DialogFragment {

    View v;

    public interface OnClickListener{
        void onClick(int id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.settings_dialog, null));

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Switch darkModeSwitch;
        ImageButton btnExit;
        if (getDialog() == null){
            darkModeSwitch = getView().findViewById(R.id.switch_darkMode);
            btnExit = getView().findViewById(R.id.Btn_exitSettings);
        }
        else
        {
            darkModeSwitch = getDialog().findViewById(R.id.switch_darkMode);
            btnExit = getDialog().findViewById(R.id.Btn_exitSettings);
            }
        int nightModeFlag= getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(nightModeFlag == Configuration.UI_MODE_NIGHT_YES)
            darkModeSwitch.setChecked(true);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

}

