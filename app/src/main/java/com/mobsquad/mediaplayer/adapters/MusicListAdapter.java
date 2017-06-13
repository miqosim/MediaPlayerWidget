package com.mobsquad.mediaplayer.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobsquad.mediaplayer.R;
import com.mobsquad.mediaplayer.services.MusicPlayerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;



/**
 * Created by Miqayel on 6/5/2017.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    ArrayList<String> musicPathArrayList;
    public static final String SELECTED_MUSIC_KEY = "selected music";
    public static final String PLAY_FIRST_TIME_ACTION = "com.mobsquad.play_first_time";

    public MusicListAdapter(ArrayList<String> m){
        musicPathArrayList = m;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(listItemView);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(parent.getContext(), MusicPlayerService.class);
                i.setAction(PLAY_FIRST_TIME_ACTION);
                i.putExtra(SELECTED_MUSIC_KEY, viewHolder.mTextView.getText());
                parent.getContext().startService(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(musicPathArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return musicPathArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.song_name_text_view);
        }
    }


}
