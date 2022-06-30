package com.example.mesablet.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;

import com.example.mesablet.R;
import com.example.mesablet.activities.HomePage;

import java.util.Locale;


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
        Spinner spinner_lang;
        ImageButton btnExit;
        if (getDialog() == null){
            darkModeSwitch = getView().findViewById(R.id.switch_darkMode);
            btnExit = getView().findViewById(R.id.Btn_exitSettings);
            spinner_lang = getView().findViewById(R.id.spinner_lang);
        }
        else
        {
            darkModeSwitch = getDialog().findViewById(R.id.switch_darkMode);
            btnExit = getDialog().findViewById(R.id.Btn_exitSettings);
            spinner_lang = getDialog().findViewById(R.id.spinner_lang);
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
        String languges[]=new String[]{"","English","español","Français"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,languges);
        spinner_lang.setAdapter(adapter);

        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position){
                    case 1:
                        upadteLanguage("en");
                        Intent intent= new Intent(getActivity(), HomePage.class);
                        intent= new Intent(getActivity(), HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dismiss();
                        break;
                    case 2:
                        upadteLanguage("es");
                        intent= new Intent(getActivity(), HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dismiss();
                        break;
                    case 3:
                        upadteLanguage("fr");
                        intent= new Intent(getActivity(), HomePage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dismiss();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void upadteLanguage(String code)
    {
        Locale locale=new Locale(code);
        Resources resources=getResources();
        DisplayMetrics displayMetrics= resources.getDisplayMetrics();
        Configuration configuration= resources.getConfiguration();
        configuration.locale=locale;
        resources.updateConfiguration(configuration,displayMetrics);
    }

}

