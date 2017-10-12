package com.oregonstate.riemeri.namemangler;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ian on 10/9/2017.
 */

public class MangledNameActivity extends AppCompatActivity {
    private static final String EXTRA_FIRST_NAME = "com.oregonstate.riemeri.namemangler.first_name";
    private static final String FULL_NAME = "fullName";
    private static final String LAST_NAME = "lastName";

    private Button mResetButton;
    private Button mRemangleButton;
    private TextView mMangledNameText;

    private String mFullName;
    private String mLastName;
    private Name mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangled_name);

        Resources res = getResources();

        //Restore saved full and last name
        if (savedInstanceState != null) {
            mFullName = savedInstanceState.getString(FULL_NAME, mFullName);
            mLastName = savedInstanceState.getString(LAST_NAME, mLastName);
            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME),
                    res.getStringArray(R.array.lastNameList), mLastName);
        }
        else {
            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME), res.getStringArray(R.array.lastNameList));
            mFullName = mName.getFullName();
            mLastName = mName.getLastName();
        }

        mMangledNameText = (TextView) findViewById(R.id.mangled_name_text);
        mMangledNameText.setText(mFullName);

        mResetButton = (Button) findViewById(R.id.reset_button);
        mRemangleButton = (Button) findViewById(R.id.remangle_button);

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mRemangleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName.randomizeLastName();
                mFullName = mName.getFullName();
                mLastName = mName.getLastName();
                mMangledNameText.setText(mFullName);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(FULL_NAME, mFullName);
        savedInstanceState.putString(LAST_NAME, mLastName);
    }

    public static Intent newIntent(Context packageContext, String firstName) {
        Intent intent = new Intent(packageContext, MangledNameActivity.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }
}
