package com.example.appmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtName, txtTime, txtTotalTime;
    SeekBar skSong;
    ImageButton btnPrev, btnNext, btnPause, btnStop, btnPlay;
    ArrayList<Song> listSong;
    int position = 0;
    SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddSong();
        MediaPlayer();
        play();
        stop();
        next();
        prev();
    }

    private void play(){
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.mipmap.playicon);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.mipmap.pauseicon);
                }
            }
        });
    }

    private void stop(){
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.mipmap.playicon);
                MediaPlayer();
            }
        });
    }

    private void next(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > listSong.size() -1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                MediaPlayer();
                skSong.setProgress(0);
                btnPlay.setImageResource(R.mipmap.pauseicon);
                mediaPlayer.start();
            }
        });
    }

    private void prev(){
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position = 0;
                }
                if(mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                MediaPlayer();
                skSong.setProgress(0);
                btnPlay.setImageResource(R.mipmap.pauseicon);
                mediaPlayer.start();
            }
        });

    }

    private void SeekBarTime(){
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }



    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTime.setText(format.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(mediaPlayer.getCurrentPosition() == skSong.getProgress()) {
                            position++;
                            if (position > listSong.size() - 1) {
                                position = 0;
                            }
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                            }
                            MediaPlayer();
                            skSong.setProgress(0);
                            btnPlay.setImageResource(R.mipmap.pauseicon);
                            mediaPlayer.start();
                        }
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }

    private void TimeTotal(){
        txtTotalTime.setText(format.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void MediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(position).getFile());
        txtName.setText(listSong.get(position).getName());
        TimeTotal();
        SeekBarTime();
        UpdateTimeSong();
    }

    private void AddSong() {
        listSong = new ArrayList<>();
        listSong.add(new Song("Cau hen cau the",R.raw.cauhencauthe));
        listSong.add(new Song("Cau hua chua tron ven",R.raw.cauhuachuatronven));
        listSong.add(new Song("De vuong",R.raw.devuong));
        listSong.add(new Song("Sai gon dau long qua",R.raw.saigondaulongqua));
        listSong.add(new Song("The luong",R.raw.theluong));
    }

    private void AnhXa() {
        txtName = (TextView) findViewById(R.id.txtTen);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTotalTime = (TextView) findViewById(R.id.txtTotalTime);
        skSong = (SeekBar) findViewById(R.id.seekBar);
        btnPrev = (ImageButton) findViewById(R.id.imgBtnPrev);
        btnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        btnStop = (ImageButton) findViewById(R.id.imgBtnStop);
        btnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
    }
}