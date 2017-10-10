package com.oregonstate.riemeri.namemangler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ian on 10/9/2017.
 */

public class MangledNameActivity extends AppCompatActivity {
    private static final String EXTRA_FIRST_NAME = "com.oregonstate.riemeri.namemangler.first_name";

    private Button mResetButton;
    private Button mRemangleButton;
    private TextView mMangledNameText;

    private String mFullName;
    private Name mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangled_name);

        //mFullName = savedInstanceState.getString(EXTRA_FULL_NAME, "No Name");
        mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME), getResources());

        mMangledNameText = (TextView) findViewById(R.id.mangled_name_text);
        mMangledNameText.setText(mName.getWholeName());

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
                mMangledNameText.setText(mName.getWholeName());
            }
        });

    }

    public static Intent newIntent(Context packageContext, String firstName) {
        Intent intent = new Intent(packageContext, MangledNameActivity.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        return intent;
    }
}
