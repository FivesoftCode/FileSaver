package com.fivesoft.filesaverdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
                        res.setText("Result code: " + resCode + "\n" + fileLocation.getPath());
                        res.invalidate();
                        share(fileLocation.getPath());
                    })
                    .save();
        });
    }

    private void share(String path){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri uri = Uri.parse("file://" + path);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share"));
    }
}