package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
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
 * Created by PerryLiu on 3/5/16.
 */
public class ViewActivity extends Activity{

    private int MAX_CLASSES = 5;

    private Button backButton;
    private Button messageButton;

    private TextView[] classes = new TextView[MAX_CLASSES];
    private TextView nameText;

    private String otherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pre_profile);

        //Get the Id from MatchedActivity
        otherID = getIntent().getExtras().getString("ID");
        Toast.makeText(getApplicationContext(), otherID, Toast.LENGTH_LONG).show();


        //Then assign each Id to the respective button
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", otherID);


        //Make each Text Field Invisibile
        nameText = (TextView) findViewById(R.id.PersonName);
        messageButton = (Button) findViewById(R.id.messageButton);

        for (int i = 0; i < MAX_CLASSES; i++) {
            switch (i) {
                case (0):
                    classes[i] = (TextView) findViewById(R.id.Class1);
                    break;
                case (1):
                    classes[i] = (TextView) findViewById(R.id.Class2);
                    break;
                case (2):
                    classes[i] = (TextView) findViewById(R.id.Class3);
                    break;
                case (3):
                    classes[i] = (TextView) findViewById(R.id.Class4);
                    break;
                case (4):
                    classes[i] = (TextView) findViewById(R.id.Class5);
                    break;
            }
            classes[i].setVisibility(View.GONE);
        }


        try {
            ParseObject matchedProfile = query.getFirst();

            //Initialize the Text Fields
            nameText.setText(matchedProfile.getString("Name"));
            for (int i = 0; i < matchedProfile.getInt("currentClass"); i++) {

                classes[i].setText(matchedProfile.getString("class" + i));
                classes[i].setVisibility(View.VISIBLE);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Back Button for after view profile
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this, MatchedPortfolio.class);
                startActivity(intent);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConversation(otherID);
            }
        });
    }

    //open a conversation with one person
    public void openConversation(String ID) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("Name", ID);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, com.parse.ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
                    intent.putExtra("RECIPIENT_ID", otherID);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error finding that user",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
