package com.project.tangyifeng.pictureswitchdemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    private double anger, happiness, sadness;
    private int status;
    private MediaPlayer player;

    private static final int ANGER_STATUS = 1;
    private static final int HAPPINESS_STATUS = 2;
    private static final int SADNESS_STATUS = 3;

    @BindView(R.id.text_view_2)
    TextView textView;

    @OnClick(R.id.play_button)
    public void play() {
        switch (status) {
            case 1:
                player = MediaPlayer.create(this, R.raw.foranger);
                break;
            case 2:
                player = MediaPlayer.create(this, R.raw.forhappniess);
                break;
            case 3:
                player = MediaPlayer.create(this, R.raw.forsadness);
                break;
        }
        player.start();
    }

    @OnClick(R.id.next_button_2)
    public void next() {
        Bundle bundle = new Bundle();
        bundle.putDouble("anger", anger);
        bundle.putDouble("happiness", happiness);
        bundle.putDouble("sadness", sadness);
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null) {
            player.stop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        anger = bundle.getDouble("anger");
        happiness = bundle.getDouble("happiness");
        sadness = bundle.getDouble("sadness");
        status = (anger > happiness) ? (anger > sadness) ? ANGER_STATUS : SADNESS_STATUS :
                (sadness > happiness) ? SADNESS_STATUS : HAPPINESS_STATUS;
        textView.setText("Current emotion value of anger: " + anger + ", happiness: " + happiness + ", sadness" +
                sadness + ". That's why we choose this music for you");
    }

}
