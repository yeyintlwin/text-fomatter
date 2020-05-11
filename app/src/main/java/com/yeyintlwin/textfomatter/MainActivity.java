package com.yeyintlwin.textfomatter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        TextWatcher, OnSelectionChangeListener, View.OnTouchListener {

    private final int[] BUTTONS = {R.id.bold_text, R.id.italic_text, R.id.underline_text,
            R.id.strike_through_text, R.id.super_script, R.id.sub_script};

    int selectionTemp = 0;

    private MyEditText editText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int id : BUTTONS) {
            findViewById(id).setOnClickListener(this);
        }

        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(this);

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // To remove the content menu that shown up when we selected the text from EditText
                // which consist (select all, copy, cut, paste, etc,...) options.
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

        editText.setOnSelectionChangeListener(this);
        editText.setOnTouchListener(this);

    }

    private boolean isActive(int id) {
        return ((TextView) findViewById(id)).getCurrentTextColor() == Color.BLUE;
    }

    private void setOnOffById(int id) {
        ((TextView) findViewById(id)).setTextColor(isActive(id) ? Color.BLACK : Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        setOnOffById(v.getId());

        if (editText.hasSelection()) {
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();

            // Original text
            StringBuilder originalText = new StringBuilder(editText.getText());

            // Selected-text extract from the original string
            String selectedText = originalText.substring(selectionStart, selectionEnd);

            // Format the extracted string
            String formattedText = getFormattedStr(selectedText);

            // The formatted string push to the original string
            String newStr = originalText.replace(selectionStart, selectionEnd, formattedText).toString();

            // to protect onTextChange() when setText to editText
            editText.removeTextChangedListener(this);

            // Update text to EditText
            editText.setText(newStr);

            // Register again
            editText.addTextChangedListener(this);

            // set cursor to right position
            editText.setSelection(selectionStart + formattedText.length());

            // If only text change switch off the button
            setOnOffById(v.getId());
        }
    }

    private String getFormattedStr(String str) {

        // (normal/bold/italic) sub/super

        if (isActive(R.id.bold_text) && isActive(R.id.italic_text)) return getBoldItalicStr(str);
        if (isActive(R.id.bold_text)) return getBoldStr(str);
        if (isActive(R.id.italic_text)) return getItalicStr(str);

        return str;
    }

    private String getFormattedStr(char ch) {

        // (normal/bold/italic) sub/super

        if (isActive(R.id.bold_text) && isActive(R.id.italic_text)) return char2bold_italic(ch);
        if (isActive(R.id.bold_text)) return char2bold(ch);
        if (isActive(R.id.italic_text)) return char2italic(ch);
        return String.valueOf(ch);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int selectionEnd = editText.getSelectionEnd();
        if (selectionTemp < selectionEnd) {
            CharSequence typedSequent = s.subSequence(selectionTemp, selectionEnd);
            //


        }
        selectionTemp = selectionEnd;


        // editText.setSelection(selectionEnd);

       /* //TODO: 2nd logic here.
        Log.d("start", start + "");
        Log.d("before", before + "");

        //int selectionStart = editText.getSelectionStart();

        if ((isActive(R.id.bold_text) || isActive(R.id.italic_text)))
            try {

                // Get string from EditText
                StringBuilder stringBuilder = new StringBuilder(editText.getText());

                // To get the last char typed in EditText
                char typedChar = stringBuilder.charAt(start + before);

                // if (!Character.isAlphabetic(typedChar)) return;
                //if (Character.isDigit(typedChar) && isActive(R.id.bold_text) && !isActive(R.id.italic_text))
                //  return;

                // Formatted text
                String formattedStr = getFormattedStr(typedChar);

                // string replacement
                String newStr = stringBuilder.replace(start, start + 1, formattedStr).toString();

                // to protect onTextChanged() function recursion
                editText.removeTextChangedListener(this);

                // update to editText
                editText.setText(newStr);

                // Register again
                editText.addTextChangedListener(this);

                // set cursor to right position
                editText.setSelection(start + formattedStr.length());

            } catch (Exception e) {
                Log.e("onTextChanged()", e.getMessage());
            }*/
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private String getBoldStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(char2bold(str.charAt(i)));
        }
        return stringBuilder.toString();
    }

    private String getItalicStr(String str) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(char2italic(str.charAt(i)));
        }
        return stringBuilder.toString();
    }

    private String getBoldItalicStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(char2bold_italic(str.charAt(i)));
        }
        return stringBuilder.toString();

    }

    private String getUnderlineStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(char2underline(str.charAt(i)));
        }
        return stringBuilder.toString();
    }


    private String getSktStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            stringBuilder.append(char2strike_through(str.charAt(i)));
        }
        return stringBuilder.toString();
    }


    private String char2bold(char ch) {
        StringBuilder stringBuilder = new StringBuilder();
        if (Character.isDigit(ch)) {
            stringBuilder.append('\uD835').append((char) ('\uDFEC' - '0' + ch));
            return stringBuilder.toString();
        }

        if (Character.isAlphabetic(ch)) {
            stringBuilder.append('\uD835');
            if (Character.isUpperCase(ch)) {
                stringBuilder.append((char) ('\uDDD4' - 'A' + ch));
            } else {
                stringBuilder.append((char) ('\uDDEE' - 'a' + ch));
            }
            return stringBuilder.toString();
        }
        return String.valueOf(ch);
    }

    private String char2italic(char ch) {
        StringBuilder stringBuilder = new StringBuilder();

        // Italic will not support for numeric alphabet.

        if (Character.isAlphabetic(ch)) {
            stringBuilder.append('\uD835');
            if (Character.isUpperCase(ch)) {
                stringBuilder.append((char) ('\uDE08' - 'A' + ch));
            } else {
                stringBuilder.append((char) ('\uDE22' - 'a' + ch));
            }
            return stringBuilder.toString();
        }
        return String.valueOf(ch);
    }


    private String char2bold_italic(char ch) {
        StringBuilder stringBuilder = new StringBuilder();

        // Bold/Italic will not support for numeric alphabet.

        if (Character.isAlphabetic(ch)) {
            stringBuilder.append('\uD835');
            if (Character.isUpperCase(ch)) {
                stringBuilder.append((char) ('\uDE3C' - 'A' + ch));
            } else {
                stringBuilder.append((char) ('\uDE56' - 'a' + ch));
            }
            return stringBuilder.toString();
        }
        return String.valueOf(ch);
    }

    private String char2underline(char ch) {
        //TODO
        return null;
    }

    private String char2strike_through(char ch) {
        //TODO
        return null;
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        selectionTemp = editText.getSelectionEnd();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        selectionTemp = editText.getSelectionEnd();
        return false;
    }
}
