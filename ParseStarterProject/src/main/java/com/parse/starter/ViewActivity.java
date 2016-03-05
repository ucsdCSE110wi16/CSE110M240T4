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
 * Created by PerryLiu on 3/5/16.
 */
public class ViewActivity extends AppCompatActivity{

    private int MAX_CLASSES = 5;

    Button backButton;

    EditText[] classes = new EditText[MAX_CLASSES];
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pre_profile);

        //Get the Id from MatchedActivity
        String data = getIntent().getExtras().getString("ID");

        //Then assign each Id to the respective button
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", data);


        //Make each Text Field Invisibile
        nameText = (EditText) findViewById(R.id.PersonName);

        for (int i = 0; i < MAX_CLASSES; i++) {
            switch (i) {
                case (0):
                    classes[i] = (EditText) findViewById(R.id.Class1);
                    break;
                case (1):
                    classes[i] = (EditText) findViewById(R.id.Class2);
                    break;
                case (2):
                    classes[i] = (EditText) findViewById(R.id.Class3);
                    break;
                case (3):
                    classes[i] = (EditText) findViewById(R.id.Class4);
                    break;
                case (4):
                    classes[i] = (EditText) findViewById(R.id.Class5);
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
                Intent intent = new Intent(ViewActivity.this, MatchedActivity.class);
                startActivity(intent);
            }
        });
    }
}
