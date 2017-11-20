package com.oregonstate.riemeri.namemangler;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ian on 10/9/2017.
 */

public class MangledNameActivity extends AppCompatActivity {
    private static final String EXTRA_FIRST_NAME = "com.oregonstate.riemeri.namemangler.first_name";
    private static final String EXTRA_IS_RUDE = "com.oregonstate.riemeri.namemangler.is_rude";
    private static final String FULL_NAME = "fullName";
    private static final String LAST_NAME = "lastName";

    private Button mResetButton;
    private Button mRemangleButton;
    private TextView mMangledNameText;
    private ImageView mImageView;

    private String mFullName;
    private String mLastName;
    private Name mName;
    private Boolean isRude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();

        //Restore saved full and last name
        if (savedInstanceState != null) {
            mFullName = savedInstanceState.getString(FULL_NAME, mFullName);
            mLastName = savedInstanceState.getString(LAST_NAME, mLastName);
            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME),
                    res.getStringArray(R.array.niceNameList), mLastName);
        }
        else {
            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME), res.getStringArray(R.array.niceNameList),
                    res.getStringArray(R.array.rudeNameList));
            isRude = getIntent().getBooleanExtra(EXTRA_IS_RUDE, false);
            if (isRude) {
                mName.randomizeRudeName();
            }
            else {
                mName.randomizeNiceName();
            }
            mFullName = mName.getFullName();
            mLastName = mName.getLastName();
        }

        if (isRude) {
            setContentView(R.layout.activity_mangled_name_rude);
        } else {
            setContentView(R.layout.activity_mangled_name_nice);
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
                if (isRude) {
                    mName.randomizeRudeName();
                }
                else {
                    mName.randomizeNiceName();
                }
                mFullName = mName.getFullName();
                mLastName = mName.getLastName();
                mMangledNameText.setText(mFullName);
            }
        });

        mImageView = (ImageView) findViewById(R.id.imageView);

        if (isRude) {
            mImageView.setImageResource(R.drawable.rude_again);
        } else {
            mImageView.setImageResource(R.drawable.amazing_butterfly);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(FULL_NAME, mFullName);
        savedInstanceState.putString(LAST_NAME, mLastName);
    }

    public static Intent newIntent(Context packageContext, String firstName, boolean isRude) {
        Intent intent = new Intent(packageContext, MangledNameActivity.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        intent.putExtra(EXTRA_IS_RUDE, isRude);
        return intent;
    }
}
