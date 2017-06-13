package com.mobsquad.mediaplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobsquad.mediaplayer.fragments.MusicListFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MusicListFragment()).commit();
    }


}
