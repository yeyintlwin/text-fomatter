package com.yeyintlwin.textfomatter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private final int[] BUTTONS = {R.id.bold_text, R.id.italic_text, R.id.underline_text,
            R.id.strike_through_text, R.id.super_script, R.id.sub_script};
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

            // Original text
            StringBuilder originalText = new StringBuilder(editText.getText());

            // Selected-text extract from the original string
            String selectedText = originalText.substring(selectionStart, selectionEnd);

            // Format the extracted string
            selectedText = getFormattedStr(selectedText);

            // converted the formatted string push to the original string
            originalText.replace(selectionStart, selectionEnd, selectedText);

            // Update text to EditText
            editText.setText(originalText.toString());
        }
    }

    private String getFormattedStr(String str) {
        //TODO: 1st logic here.
        return "apple, orange";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //TODO: 2nd logic here.
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
