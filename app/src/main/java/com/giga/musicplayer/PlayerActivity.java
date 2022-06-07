package com.giga.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Button btnplay, btnnext, btnprev, btnfastforward, btnrewind;
    TextView txtsname, txtsstart, txtsstop;
    SeekBar seekBar;

    String sname;
    public static  final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    ArrayList<File> mySongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnplay = findViewById(R.id.btnplay);
        btnnext = findViewById(R.id.btnnext);
        btnprev = findViewById(R.id.btnprev);
        btnfastforward = findViewById(R.id.btnfastforward);
        btnrewind = findViewById(R.id.btnfastprevious);
        seekBar = findViewById(R.id.seekbar);
        txtsname = findViewById(R.id.txtsn);
        txtsstart = findViewById(R.id.txtsstart);
        txtsstop = findViewById(R.id.txtsstop);

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        int position = bundle.getInt("pos", 0);
        txtsname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname = mySongs.get(position).getName();
        txtsname.setText(sname);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    btnplay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else{
                    btnplay.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();

                }
            }
        });
    }
}