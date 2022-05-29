package com.example.listatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.listatelefonica.TelaPrincipal.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new MainFragment()).commit();
        }
    }
}