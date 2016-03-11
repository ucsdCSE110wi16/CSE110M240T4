package com.parse.starter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PreProfileActivity extends AppCompatActivity {
    private int MAX_CLASSES = 5;
    private TextView uploadText;
    EditText[] classes = new EditText[MAX_CLASSES];
    boolean newProfile;
    TextView nameText;
    Button addClassButton;
    ImageView messageButton;
    Button[] removeClasses = new Button[MAX_CLASSES];
    Button submitButton;
    int currentClass = 0;
    int passedInCurrentClass;
    ParseUser user;
    ParseObject profile;
    private ParseImageView profilePicture;
    private ImageView nonParsePic;
    private ParseFile uploadedPic;
    private String[] greetings = {"Hey", "Sup", "Hello"};

    int SELECT_PHOTO = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preprofile);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        nameText = (TextView) findViewById(R.id.PersonName);
        nameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        addClassButton = (Button) findViewById(R.id.AddClass);
        classes[0] = (EditText) findViewById(R.id.Class1);
        removeClasses[0] = (Button) findViewById(R.id.RemoveClass1);
        removeClasses[0].setVisibility(View.GONE);
        for (int i = 1; i < MAX_CLASSES; i++) {
            switch (i) {
                case (1):
                    classes[i] = (EditText) findViewById(R.id.Class2);
                    removeClasses[i] = (Button) findViewById(R.id.RemoveClass2);
                    break;
                case (2):
                    classes[i] = (EditText) findViewById(R.id.Class3);
                    removeClasses[i] = (Button) findViewById(R.id.RemoveClass3);
                    break;
                case (3):
                    classes[i] = (EditText) findViewById(R.id.Class4);
                    removeClasses[i] = (Button) findViewById(R.id.RemoveClass4);
                    break;
                case (4):
                    classes[i] = (EditText) findViewById(R.id.Class5);
                    removeClasses[i] = (Button) findViewById(R.id.RemoveClass5);
                    break;
            }
            classes[i].setVisibility(View.GONE);
            removeClasses[i].setVisibility(View.GONE);
        }


        user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", user.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.isEmpty()) {
                        newProfile = true;

                    } else {
                        passedInCurrentClass = objects.get(0).getInt("currentClass");
                        String[] name = objects.get(0).getString("Name").split(" ");
                        int rand = (int) (Math.random() * 3);
                        nameText.setText(greetings[rand] + ", " + name[0] + "!");
                        for (int i = 0; i < passedInCurrentClass; i++) {
                            classes[i].setText(objects.get(0).getString("class" + i));
                        }

                        updateContent();
                        newProfile = false;
                    }
                }
            }
        });


        for(int k = 0; k < MAX_CLASSES; k++) {
            final EditText currClass = classes[k];
            currClass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard(v);
                    }
                }
            });
        }

        submitButton = (Button) findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                name = name.trim();

                user = ParseUser.getCurrentUser();

                if (newProfile) {
                    ParseQuery<ParseObject> myQuery = ParseQuery.getQuery("_User");
                    myQuery.whereEqualTo("objectId", user.getObjectId());
                    myQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                profile = object;
                            }
                        }
                    });
                    addProfileContent(profile, name, user);
                    newProfile = false;

                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                    query.whereEqualTo("objectId", user.getObjectId());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                String name = nameText.getText().toString();
                                name = name.trim();

                                ParseObject profile = objects.get(0);
                                addProfileContent(profile, name, user);

                            }
                        }
                    });
                }


                Intent intent = new Intent(PreProfileActivity.this, MatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        for(int i = 0; i < MAX_CLASSES; i++) {
            final Button removeButton = removeClasses[i];
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < MAX_CLASSES; j++) {
                        if (removeClasses[j].getId() == v.getId()) {
                            for (int k = j; k < currentClass; k++) {
                                classes[k].setText(classes[k + 1].getText());
                                classes[k + 1].setText("");
                            }
                            if (addClassButton.getVisibility() == View.INVISIBLE) {
                                addClassButton.setVisibility(View.VISIBLE);
                                removeClasses[currentClass].setVisibility(View.INVISIBLE);
                            } else {
                                removeClasses[currentClass - 1].setVisibility(View.INVISIBLE);
                                classes[currentClass].setVisibility(View.INVISIBLE);
                                currentClass--;
                                classes[currentClass].requestFocus();
                            }
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                                    addClassButton.getLayoutParams();
                            params.addRule(RelativeLayout.ALIGN_BOTTOM, classes[currentClass].getId());
                            addClassButton.setLayoutParams(params);
                        }
                    }
                }
            });
        }

        messageButton = (ImageView) findViewById(R.id.messagebutton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), MatchedPortfolio.class);
                final Intent serviceIntent = new Intent(getApplicationContext(), MessageService.class);
                startActivity(intent);
                startService(serviceIntent);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classes[currentClass].getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The Class is Empty", Toast.LENGTH_LONG).show();
                }
                else {
                    removeClasses[currentClass].setVisibility(View.VISIBLE);
                    if (currentClass == (MAX_CLASSES - 1)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PreProfileActivity.this);
                        builder.setMessage("Limit is 5 class.")
                                .setTitle(R.string.login_error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        addClassButton.setVisibility(View.INVISIBLE);
                    } else {
                        currentClass++;
                        classes[currentClass].setVisibility(View.VISIBLE);
                        classes[currentClass].requestFocus();
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                                addClassButton.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_BOTTOM, classes[currentClass].getId());
                        addClassButton.setLayoutParams(params);
                    }

                }
            }
        });

        uploadText = (TextView) findViewById(R.id.uploadText);

        uploadedPic = user.getParseFile("ProfPic");
        if (uploadedPic != null) {
            profilePicture = (ParseImageView) findViewById(R.id.profileImage);
            profilePicture.setParseFile(uploadedPic);
            uploadText.setVisibility(View.INVISIBLE);
            profilePicture.loadInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    System.out.println("yay it loaded");
                    // The image is loaded and displayed!
                }
            });
        }
        else {
            nonParsePic = (ImageView) findViewById(R.id.profileImage);
            nonParsePic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_profpic));
        }

        profilePicture = (ParseImageView) findViewById(R.id.profileImage);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        // If the user has not matched with anyone, do not show matched button
        List<ParseObject> file = (List<ParseObject>) user.get("MatchedProfiles");
        if(file == null) {
            messageButton.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            //layoutParams.removeRule(RelativeLayout.ALIGN_LEFT);
            //layoutParams.removeRule(RelativeLayout.ALIGN_START);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            submitButton.setLayoutParams(layoutParams);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            cursor.close();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress image to lower quality scale 1 - 100
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] image = stream.toByteArray();
            profilePicture.setImageBitmap(bitmap);
            // Create the ParseFile
            ParseFile file = new ParseFile("profpic.png", image);
            // Upload the image into Parse Cloud
            file.saveInBackground();

            user.put("ProfPic", file);

            // Create the class and the columns
            user.saveInBackground();

            // Show a simple toast message
            Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    //adds content to each profile
    public void addProfileContent(ParseObject profile, String name, ParseUser user){
        for (int i = 0; i < currentClass; i++) {
            String course = classes[i].getText().toString();
            course = course.replaceAll("\\s+", "").toUpperCase();
            profile.put("class" + i, course);
        }
        for(int i = currentClass; i < MAX_CLASSES; i++) {
            profile.put("class" + i, "");
        }

        profile.put("Name",name );
        profile.put("user", user);
        profile.put("currentClass", currentClass);
        profile.saveInBackground();
        Toast.makeText(getApplicationContext(), "Fill out your Profile", Toast.LENGTH_SHORT).show();
    }

    //fixes the layout of the program given number of classes
    public void updateContent(){
        for(int i = 0; i < passedInCurrentClass; i++) {
            removeClasses[currentClass].setVisibility(View.VISIBLE);

            currentClass++;
            classes[currentClass].setVisibility(View.VISIBLE);
            //classes[currentClass].requestFocus();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    addClassButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_BOTTOM, classes[currentClass].getId());
            addClassButton.setLayoutParams(params);
        }
        classes[currentClass].requestFocus();
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            stopService(new Intent(getApplicationContext(), MessageService.class));
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
