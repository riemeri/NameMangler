package com.oregonstate.riemeri.namemangler;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Gallery;
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
    private static final String IS_RUDE = "mIsRude";

    private Button mResetButton;
    private Button mRemangleButton;
    private TextView mMangledNameText;
    private ImageView mImageView;

    private String mFullName;
    private String mLastName;
    private Name mName;
    private Boolean mIsRude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();

        //Restore saved full and last name
        if (savedInstanceState != null) {
            mFullName = savedInstanceState.getString(FULL_NAME, mFullName);
            mLastName = savedInstanceState.getString(LAST_NAME, mLastName);
            mIsRude = savedInstanceState.getBoolean(IS_RUDE, false);

            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME),
                    res.getStringArray(R.array.rudeNameList),
                    res.getStringArray(R.array.niceNameList),
                    mLastName);
        }
        else {
            mName = new Name(getIntent().getStringExtra(EXTRA_FIRST_NAME),
                    res.getStringArray(R.array.niceNameList),
                    res.getStringArray(R.array.rudeNameList));

            mIsRude = getIntent().getBooleanExtra(EXTRA_IS_RUDE, false);

            if (mIsRude) {
                mName.randomizeRudeName();
            }
            else {
                mName.randomizeNiceName();
            }
            mFullName = mName.getFullName();
            mLastName = mName.getLastName();
        }

        if (mIsRude) {
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
                if (mIsRude) {
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

        if (!DeviceIsInPortraitOrientation()) {
            ViewGroup.LayoutParams myParams = mImageView.getLayoutParams();
            int currentDisplayHeight = displayHeight();
            myParams.width = (displayHeight() / 3) * 2; //- 500;
            mImageView.setLayoutParams(myParams);
        }

        if (mIsRude) {
            mImageView.setImageResource(R.drawable.rude_again);
        } else {
            mImageView.setImageResource(R.drawable.amazing_butterfly);
        }
    }

    private boolean DeviceIsInPortraitOrientation() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();
        if(orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            return false;
        }
        else {
            return true;
        }
    }

    private int displayHeight(){
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        Point screenSize = new Point();
        display.getSize(screenSize);
        return screenSize.y;

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(FULL_NAME, mFullName);
        savedInstanceState.putString(LAST_NAME, mLastName);
        savedInstanceState.putBoolean(IS_RUDE, mIsRude);
    }

    public static Intent newIntent(Context packageContext, String firstName, boolean isRude) {
        Intent intent = new Intent(packageContext, MangledNameActivity.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        intent.putExtra(EXTRA_IS_RUDE, isRude);
        return intent;
    }
}
