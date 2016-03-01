/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.sinch.android.rtc.SinchClient;


public class MainActivity extends AppCompatActivity {
    private SinchClient sinchClient = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    ParseUser currentUser = ParseUser.getCurrentUser();
    loadLoginView();

    Button skipButton = (Button)findViewById(R.id.shortcutButton);
    skipButton.setOnClickListener(
            new Button.OnClickListener(){
              public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                setContentView(R.layout.matching);
                startActivity(intent);

              }
            }
    );
            /*
    if (currentUser == null) {
        ParseUser.logOut();
      loadLoginView();
    }
    else{
        loadGotInView();
    }*/


  }

  private void loadGotInView() {
        //setContentView(R.layout.login);
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
  private void loadLoginView() {
    //setContentView(R.layout.login);
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
  }
  private void loadMatching() {
    Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
    startActivity(intent);
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
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
