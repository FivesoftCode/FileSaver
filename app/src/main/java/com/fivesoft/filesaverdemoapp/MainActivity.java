package com.fivesoft.filesaverdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fivesoft.filesaver.FileSaver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button save = findViewById(R.id.button);
        TextView res = findViewById(R.id.resCode);


        save.setOnClickListener(v -> {
            FileSaver.from(this)
                    .setFile("Hello world!".getBytes())
                    .setName("test.txt")
                    .setType("plain/text")
                    .setListener((fileLocation, resCode) -> {
                        res.setText("Result code: " + resCode);
                        res.invalidate();
                    })
                    .save();
        });
    }
}