package com.oregonstate.riemeri.namemangler;

import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ian on 10/9/2017.
 */

public class Name extends AppCompatActivity {

    private String mFirstName;
    private String mLastName;

    private List<String> mLastNames;

    public Name(Resources res){
        setNameList(res);
        randomizeLastName();
    }

    public Name(String firstName, Resources res) {
        mFirstName = firstName;
        setNameList(res);
        randomizeLastName();
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        if (mLastName == null || mLastName.length() < 2) {
            randomizeLastName();
        }
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getWholeName() {
        return mFirstName + " " + getLastName();
    }

    public  void randomizeLastName() {
        int rand = 0;
        String lastName;

        Random random = new Random();
        random.setSeed(SystemClock.currentThreadTimeMillis());

        if (mLastName == null) {
            mLastName = "";
        }
        
        if (mLastNames.size() > 0) {
            do {
                rand = random.nextInt();
                rand = Math.abs(rand);
                rand = rand % 5;
                lastName = mLastNames.get(rand);
            }
            while (lastName.compareTo(mLastName) == 0);

            mLastName = lastName;
        }
    }

    private void setNameList(Resources res) {
        mLastNames = new ArrayList<>();
        mLastNames.add(res.getString(R.string.name1));
        mLastNames.add(res.getString(R.string.name2));
        mLastNames.add(res.getString(R.string.name3));
        mLastNames.add(res.getString(R.string.name4));
        mLastNames.add(res.getString(R.string.name5));
    }
}
