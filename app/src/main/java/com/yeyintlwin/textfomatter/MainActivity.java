package com.yeyintlwin.textfomatter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private final int[] BUTTONS = {R.id.bold_text, R.id.italic_text, R.id.underline_text, R.id.strike_through_text, R.id.super_script, R.id.sub_script};
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int id : BUTTONS) {
            findViewById(id).setOnClickListener(this);
        }
        editText = findViewById(R.id.editText);
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                menu.clear();
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

    }

    private boolean isActive(int id) {
        return ((TextView) findViewById(id)).getCurrentTextColor() == Color.BLUE;
    }

    private void setColor(int id) {
        ((TextView) findViewById(id)).setTextColor(isActive(id) ? Color.BLACK : Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        setColor(v.getId());

        if (editText.hasSelection()) {
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();
            String selectedText = editText.getText().toString().substring(selectionStart, selectionEnd);
            Log.d("SelectedText", selectedText);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
