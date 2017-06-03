package com.project.tangyifeng.pictureswitchdemo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThirdActivity extends AppCompatActivity {

    private double anger, happiness, sadness;
    private int status;

    private static final int ANGER_STATUS = 1;
    private static final int HAPPINESS_STATUS = 2;
    private static final int SADNESS_STATUS = 3;

    @BindView(R.id.text_view_3)
    TextView textView;

    @BindView(R.id.book)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        anger = bundle.getDouble("anger");
        happiness = bundle.getDouble("happiness");
        sadness = bundle.getDouble("sadness");
        status = (anger > happiness) ? (anger > sadness) ? ANGER_STATUS : SADNESS_STATUS :
                (sadness > happiness) ? SADNESS_STATUS : HAPPINESS_STATUS;
        textView.setText("Current emotion value of anger: " + anger + ", happiness: " + happiness + ", sadness" +
                sadness + ". That's why we choose this book for you");
        switch (status) {
            case 1:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.reddream));
                break;
            case 2:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hechanges));
                break;
            case 3:
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.compiletheory));
                break;
        }
    }
}
