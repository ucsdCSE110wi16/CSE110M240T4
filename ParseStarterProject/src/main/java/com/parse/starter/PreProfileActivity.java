package com.parse.starter;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PreProfileActivity extends AppCompatActivity {
    private int MAX_CLASSES = 5;
    EditText[] classes = new EditText[MAX_CLASSES];
    Button addClassButton;
    Button submitButton;
    int currentClass = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_profile);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);        addClassButton = (Button) findViewById(R.id.AddClass);
        classes[0] = (EditText) findViewById(R.id.Class1);
        for (int i = 1; i < MAX_CLASSES; i++) {
            switch (i) {
                case (1):
                    classes[i] = (EditText) findViewById(R.id.Class2);
                    classes[i].setVisibility(View.GONE);
                    break;
                case (2):
                    classes[i] = (EditText) findViewById(R.id.Class3);
                    classes[i].setVisibility(View.GONE);
                    break;
                case (3):
                    classes[i] = (EditText) findViewById(R.id.Class4);
                    classes[i].setVisibility(View.GONE);
                    break;
                case (4):
                    classes[i] = (EditText) findViewById(R.id.Class5);
                    classes[i].setVisibility(View.GONE);

                    break;
            }
        }

        submitButton = (Button) findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseClasses = new ParseObject("classes");
                parseClasses.put("class1", classes[0]);
                parseClasses.put("class2", classes[1]);
                parseClasses.put("class3", classes[2]);
                parseClasses.put("class4", classes[3]);
                parseClasses.saveInBackground();

            }
        });
        /*try {
            File f = new File("profilepic");
            InputStream is = getResources().openRawResource(R.drawable.exam);
            OutputStream out = new FileOutputStream(f);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
                out.close();
                is.close();
            }
            byte[] data = f.getAbsolutePath().getBytes();
            ParseFile file = new ParseFile("img1.jpg",data);
            file.saveInBackground();
        }
        catch(IOException e) {}*/


        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentClass == (MAX_CLASSES - 1)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreProfileActivity.this);
                    builder.setMessage("Stahp. No more classes pls.")
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                }
                else {
                    currentClass++;
                    classes[currentClass].setVisibility(View.VISIBLE);
                    classes[currentClass].requestFocus();
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                            addClassButton.getLayoutParams();
                    params.addRule(RelativeLayout.ALIGN_BOTTOM, classes[currentClass].getId());
                    addClassButton.setLayoutParams(params);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "PreProfile Page", // TODO: Define a title for the content shown.
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
                "PreProfile Page", // TODO: Define a title for the content shown.
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
