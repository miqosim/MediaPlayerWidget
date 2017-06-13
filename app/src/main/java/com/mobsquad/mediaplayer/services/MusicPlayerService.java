package com.mobsquad.mediaplayer.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.mobsquad.mediaplayer.MainActivity;
import com.mobsquad.mediaplayer.R;
import com.mobsquad.mediaplayer.adapters.MusicListAdapter;
import java.io.IOException;


/**
 * Created by Miqayel on 6/5/2017.
 */

public class MusicPlayerService extends Service {

    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_PLAY = "action_play";
    private boolean isPlaying = true;
    private String intentAction;
    MediaPlayer mediaPlayer;
    Intent playIntent;
    PendingIntent playPendingIntent;
    RemoteViews notificationView;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationView = new RemoteViews(getPackageName(), R.layout.notification_layout);
        if(mediaPlayer == null){
        mediaPlayer = new MediaPlayer();}
         if(intent.getAction()==ACTION_PAUSE){
             notificationView.setImageViewBitmap(R.id.play_button, BitmapFactory.decodeResource(getResources(), R.drawable.play_btn));

             isPlaying = false;
             intentAction = ACTION_PLAY;
             mediaPlayer.pause();
             showNotification();
         }else if(intent.getAction()==MusicListAdapter.PLAY_FIRST_TIME_ACTION){
             if(mediaPlayer.isPlaying()){
                 mediaPlayer.stop();
                 mediaPlayer.release();
             }
             mediaPlayer = new MediaPlayer();
             isPlaying = true;
             intentAction = ACTION_PAUSE;
             notificationView.setImageViewBitmap(R.id.play_button, BitmapFactory.decodeResource(getResources(), R.drawable.pause_btn));

//        TEST
             AssetFileDescriptor assetFileDescriptor = null;
             try {
                 assetFileDescriptor = getAssets().openFd("sounds/" + intent.getExtras().getString(MusicListAdapter.SELECTED_MUSIC_KEY));
             } catch (IOException e) {
                 e.printStackTrace();
             }

//        END OF TEST


             mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
             try {
//            mediaPlayer.setDataSource(getApplicationContext(), musicUri);
                 mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                 mediaPlayer.prepare();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             mediaPlayer.start();
             showNotification();

         }
         else{
             isPlaying = true;
             intentAction = ACTION_PAUSE;
             notificationView.setImageViewBitmap(R.id.play_button, BitmapFactory.decodeResource(getResources(), R.drawable.pause_btn));
             mediaPlayer.start();
             showNotification();
         }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showNotification(){

        playIntent = new Intent(this, MusicPlayerService.class);
        playIntent.setAction(intentAction);
        playPendingIntent = PendingIntent.getService(this, 0, playIntent, 0);


        notificationView.setOnClickPendingIntent(R.id.play_button, playPendingIntent);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notification_play_icon)
                .setContentIntent(pendingNotificationIntent)
                .setContent(notificationView)
                .build();

        startForeground(123, notification);

    }


}
