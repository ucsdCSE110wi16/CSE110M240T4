package com.parse.starter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PreProfileActivity extends AppCompatActivity {

    EditText[] classes = new EditText[5];
    Button addClassButton;
    int currentClass = 0;
    int maxClasses = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_profile);

        addClassButton = (Button)findViewById(R.id.AddClass);
        classes[0] = (EditText)findViewById(R.id.Class1);
        for (int i = 1; i < 5; i++) {
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
                if (currentClass+1 == maxClasses) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreProfileActivity.this);
                    builder.setMessage("Stahp. No more classes pls.")
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                }
                classes[++currentClass].setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                        addClassButton.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, classes[currentClass].getId());
                addClassButton.setLayoutParams(params);

            }
        });
    }

}
