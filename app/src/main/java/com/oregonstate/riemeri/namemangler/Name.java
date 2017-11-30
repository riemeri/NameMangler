package com.oregonstate.riemeri.namemangler;

import android.os.SystemClock;

import java.util.Random;

/**
 * Created by Ian on 10/9/2017.
 */

//Name model object for managing name information
public class Name {

    private String mFirstName;
    private String mLastName;

    private String[] mNiceNames;
    private String[] mRudeNames;

    public Name(String[] niceNames, String[] rudeNames){
        mFirstName = "Default";
        mNiceNames = niceNames;
        mRudeNames = rudeNames;
        randomizeNiceName();
    }

    public Name(String firstName, String[] niceNames, String[] rudeNames) {
        mFirstName = firstName;
        mNiceNames = niceNames;
        mRudeNames = rudeNames;
        randomizeNiceName();
    }

    //Constructor option to retain a previously used last name. Made for "resuming" after instance state changes
    public Name(String firstName, String[] lastNames, String[] rudeNames, String currentLastName){
        mFirstName = firstName;
        mLastName = currentLastName;
        mNiceNames = lastNames;
        mRudeNames = rudeNames;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        if (mLastName == null || mLastName.length() < 2) {
            randomizeNiceName();
        }
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFullName() {
        return mFirstName + " " + getLastName();
    }

    //Function to set the last name to a random name from the list
    public  void randomizeNiceName() {
        int rand = 0;
        String lastName;

        Random random = new Random();
        random.setSeed(SystemClock.currentThreadTimeMillis());

        if (mLastName == null) {
            mLastName = "";
        }
        
        if (mNiceNames.length > 0) {
            do {
                rand = random.nextInt();
                rand = Math.abs(rand);
                rand = rand % mNiceNames.length;
                lastName = mNiceNames[rand];
            }
            while (lastName.compareTo(mLastName) == 0);

            mLastName = lastName;
        }
    }

    public  void randomizeRudeName() {
        int rand = 0;
        String lastName;

        Random random = new Random();
        random.setSeed(SystemClock.currentThreadTimeMillis());

        if (mLastName == null) {
            mLastName = "";
        }

        if (mRudeNames.length > 0) {
            do {
                rand = random.nextInt();
                rand = Math.abs(rand);
                rand = rand % mRudeNames.length;
                lastName = mRudeNames[rand];
            }
            while (lastName.compareTo(mLastName) == 0);

            mLastName = lastName;
        }
    }
}
