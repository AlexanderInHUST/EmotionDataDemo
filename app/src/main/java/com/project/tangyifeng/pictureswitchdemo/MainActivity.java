package com.project.tangyifeng.pictureswitchdemo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";
    private static final int ANGER_STATUS = 1;
    private static final int HAPPINESS_STATUS = 2;
    private static final int SADNESS_STATUS = 3;

    private int status;

    @BindView(R.id.image_view_1)
    ImageView imageView;

    @BindView(R.id.text_view_1)
    TextView textView;

    private Bundle bundle;

    @OnClick(R.id.next_button_1)
    public void next() {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        status = getEmotion();
        switch (status) {
            case 1:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.forest));
                break;
            case 2:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grassland));
                break;
            case 3:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sea));
                break;
        }

    }

    private int getEmotion() {
        double anger = 0, happiness = 0, sadness = 0;
        Uri uri = Uri.parse("content://com.viseator/emotion-db");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"anger", "happiness", "sadness"}, null, null, null);
        int count = 0;

        cursor.moveToLast();
        while (cursor.moveToPrevious()) {
            if (++count >= 2)
                break;
            anger += cursor.getDouble(cursor.getColumnIndex("anger"));
            happiness += cursor.getDouble(cursor.getColumnIndex("happiness"));
            sadness += cursor.getDouble(cursor.getColumnIndex("sadness"));
        }
        anger /= count;
        happiness /= count;
        sadness /= count;
        bundle = new Bundle();
        bundle.putDouble("anger", anger);
        bundle.putDouble("happiness", happiness);
        bundle.putDouble("sadness", sadness);
        textView.setText("Current emotion value of anger: " + anger + ", happiness: " + happiness + ", sadness" +
            sadness + ". That's why we choose this picture for you");
        cursor.close();
        return (anger > happiness) ? (anger > sadness) ? ANGER_STATUS : SADNESS_STATUS :
                (sadness > happiness) ? SADNESS_STATUS : HAPPINESS_STATUS;
    }
}
