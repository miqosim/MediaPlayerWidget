package com.mobsquad.mediaplayer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobsquad.mediaplayer.R;
import com.mobsquad.mediaplayer.adapters.MusicListAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Miqayel on 6/5/2017.
 */

public class MusicListFragment extends Fragment {

    private ArrayList<String> mMp3List;


    String[] locales;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        getMp3FromAssets();

        RecyclerView musicListRecyclerView = (RecyclerView)view.findViewById(R.id.music_list_recycler_view);
        MusicListAdapter musicListAdapter = new MusicListAdapter(mMp3List);
        musicListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        musicListRecyclerView.setAdapter(musicListAdapter);



    }


    public void getMp3FromAssets(){
        if(mMp3List == null){
            mMp3List = new ArrayList<>();
        }
        try {
            locales = getResources().getAssets().list("sounds");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String a: locales){
            if(a.substring(a.lastIndexOf('.'), a.length()).equals(".mp3")){
                mMp3List.add(a);
            }
        }

    }








}
