package com.davidos.inconvenientcalcandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends Activity {
    Calc mCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ComingSoon.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

       mCalc = new Calc(MainActivity.this);
    }

    public void clicked(View view){
        Button btn = (Button) view;
        char symbol = btn.getText().charAt(0);
        mCalc.handleSymbols(symbol);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}