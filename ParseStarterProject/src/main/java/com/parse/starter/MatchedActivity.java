package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by PerryLiu on 2/22/16.
 */


public class MatchedActivity extends AppCompatActivity {
    private int MAX_PROFILES = 5;

    int numMatched;

    Button backButton;
    Button[] profiles = new Button[MAX_PROFILES];

    String[] objectId = new String[MAX_PROFILES];
    String ID;

    List<ParseObject> matchedList = new ArrayList<ParseObject>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matched_profiles);


        //Makes every profile invisible
        for (int i = 0; i < MAX_PROFILES; i++) {
            switch (i) {
                case (0):
                    profiles[i] = (Button) findViewById(R.id.name1);
                    break;
                case (1):
                    profiles[i] = (Button) findViewById(R.id.name2);
                    break;
                case (2):
                    profiles[i] = (Button) findViewById(R.id.name3);
                    break;
                case (3):
                    profiles[i] = (Button) findViewById(R.id.name4);
                    break;
                case (4):
                    profiles[i] = (Button) findViewById(R.id.name5);
                    break;
            }
            profiles[i].setVisibility(View.GONE);
        }

        //Gets the current profile
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> myQuery = ParseQuery.getQuery("_User");
        myQuery.whereEqualTo("objectId", user.getObjectId());
        try {
            ParseObject userContent = myQuery.getFirst();

            //Stores the Matched Profiles list of the Current Profile
            matchedList = (List<ParseObject>) userContent.get("MatchedProfiles");
            numMatched = matchedList.size();


            //Check to make sure only 5 profiles max
            if(numMatched > MAX_PROFILES){
                numMatched = MAX_PROFILES;
            }


            //Goes into each Profile of the Matched profile list
            for (int i = 0; i < numMatched; i++) {

                try {

                    //Retrieves the Matched Profile and stores the Name to the Button
                    ParseQuery<ParseObject> matchedQuery = ParseQuery.getQuery("_User");
                    matchedQuery.whereEqualTo("objectId", matchedList.get(i).getObjectId());

                    objectId[i] = (matchedList.get(i).getObjectId());

                    ParseObject matchedProfile = matchedQuery.getFirst();

                    profiles[i].setText(matchedProfile.getString("Name"));
                    profiles[i].setVisibility(View.VISIBLE);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        //Create Listener for all the Buttons
        profiles[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewProfile(0);

            }

        });
        profiles[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewProfile(1);
            }

        });
        profiles[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewProfile(2);
            }

        });
        profiles[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewProfile(3);
            }

        });
        profiles[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViewProfile(4);
            }

        });


        //Back Button for before viewing profile
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchedActivity.this, PreProfileActivity.class);
                startActivity(intent);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void updateViewProfile(int j) {

        //Obtain the object Id from MatchedProfile List
        ID = objectId[j];

        //Switching to view only profile
        Intent intent = new Intent(MatchedActivity.this, ViewActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);



    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Matched Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Matched Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.parse.starter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
