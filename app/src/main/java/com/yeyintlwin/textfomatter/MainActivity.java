package com.yeyintlwin.textfomatter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int[] BUTTONS = {R.id.bold_text, R.id.italic_text, R.id.underline_text, R.id.strike_through_text, R.id.super_script, R.id.sub_script};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int id : BUTTONS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private boolean isActive(int id) {
        return ((TextView) findViewById(id)).getCurrentTextColor() == Color.BLUE;
    }

    private void setColor(int id) {
        ((TextView) findViewById(id)).setTextColor(!isActive(id) ? Color.BLUE : Color.BLACK);
    }

    @Override
    public void onClick(View v) {
        setColor(v.getId());
    }

}
