package com.davidos.inconvenientcalcandroid;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

public class Display {

    private TextView mCalcTextView;
    private TextView mProcessTextView;

    public Display(Context context){
        mCalcTextView = (TextView) ((Activity)context).findViewById(R.id.calculationTextView);
        mProcessTextView = (TextView) ((Activity)context).findViewById(R.id.processTextView);
    }
    public String getProcessTextView() {
        return mProcessTextView.getText() + "";
    }

    public void setProcessTextView(String processTextView) {
        mProcessTextView.setText(processTextView);
    }

    public String getCalcTextView() {
        return mCalcTextView.getText() + "";
    }

    public void setCalcTextView(String calcTextView) {
        mCalcTextView.setText(calcTextView);
    }

}