package com.changjiashuai.cornerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_corner_view);

        CornerView cornerView = findViewById(R.id.cornerView);

        cornerView.setTriangleColor(getResources().getColor(R.color.colorPrimaryDark));
        cornerView.setTriangleSideA(ViewExtKt.dp2px(cornerView, 20f));
        cornerView.setTriangleSideB(ViewExtKt.dp2px(cornerView, 20f));

        cornerView.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        cornerView.setBorderWidth(ViewExtKt.dp2px(cornerView, 8f));
    }
}
