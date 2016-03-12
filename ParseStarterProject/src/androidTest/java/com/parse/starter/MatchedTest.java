package com.parse.starter;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jarnin on 3/11/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MatchedTest {
    @Rule
    public ActivityTestRule<MatchActivity> mActivityRule = new ActivityTestRule(MatchActivity.class);

    @Test
    public void checkLogOut() {
        onView(withId(R.id.action_logout)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    //CHECK IF OPENING MESSENGER GOES TO THE MATCHED_PROFILES ACTIVITY
    @Test
    public void checkMessenger() {
        onView(withId(R.id.MessageButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
    }


}
