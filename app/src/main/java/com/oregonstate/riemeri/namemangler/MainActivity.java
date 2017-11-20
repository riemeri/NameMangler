package com.oregonstate.riemeri.namemangler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FIRST_NAME = "firstName";
    private static final int REQUEST_CODE_RESET = 0;

    private Button mMangleNicelyButton;
    private Button mMangleRudelyButton;
    private EditText mNameBox;

    private String mFirstName;
    private List<String> mLastNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameBox = (EditText) findViewById(R.id.name_text);

        if (savedInstanceState != null) {
            mFirstName = savedInstanceState.getString(FIRST_NAME, "");
            mNameBox.setText(mFirstName);
        }

        mMangleNicelyButton = (Button) findViewById(R.id.mangle_nicely);
        mMangleNicelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mNameBox.getText().toString().trim();
                if (firstName.length() > 0) {
                    mFirstName = firstName;
                    Intent intent = MangledNameActivity.newIntent(MainActivity.this, mFirstName, false);
                    startActivityForResult(intent, REQUEST_CODE_RESET);
                }
                else {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "No name entered. Please enter a name.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        //Create button and set onClickListener
        mMangleRudelyButton = (Button) findViewById(R.id.mangle_rudely);
        mMangleRudelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mNameBox.getText().toString().trim();
                if (firstName.length() > 0) {
                    mFirstName = firstName;
                    Intent intent = MangledNameActivity.newIntent(MainActivity.this, mFirstName, true);
                    startActivityForResult(intent, REQUEST_CODE_RESET);
                }
                else {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "No name entered. Please enter a name.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        mNameBox.setText("");
        mFirstName = "";
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(FIRST_NAME, mFirstName);
    }
}
