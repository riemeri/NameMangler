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

//Name model object for managing name information
public class Name {

    private String mFirstName;
    private String mLastName;

    private String[] mLastNames;

    public Name(String[] lastNames){
        mFirstName = "Default";
        mLastNames = lastNames;
        randomizeLastName();
    }

    public Name(String firstName, String[] lastNames) {
        mFirstName = firstName;
        mLastNames = lastNames;
        randomizeLastName();
    }

    //Constructor option to retain a previously used last name. Made for "resuming" after instance state changes
    public Name(String firstName, String[] lastNames, String currentLastName){
        mFirstName = firstName;
        mLastName = currentLastName;
        mLastNames = lastNames;
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

    public String getFullName() {
        return mFirstName + " " + getLastName();
    }

    //Function to set the last name to a random name from the list
    public  void randomizeLastName() {
        int rand = 0;
        String lastName;

        Random random = new Random();
        random.setSeed(SystemClock.currentThreadTimeMillis());

        if (mLastName == null) {
            mLastName = "";
        }
        
        if (mLastNames.length > 0) {
            do {
                rand = random.nextInt();
                rand = Math.abs(rand);
                rand = rand % mLastNames.length;
                lastName = mLastNames[rand];
            }
            while (lastName.compareTo(mLastName) == 0);

            mLastName = lastName;
        }
    }
}
