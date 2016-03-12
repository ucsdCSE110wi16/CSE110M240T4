package com.parse.starter;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by jarnin on 3/1/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreProfileTest {
    @Rule
    public ActivityTestRule<PreProfileActivity> mActivityRule = new ActivityTestRule(PreProfileActivity.class);

    /*
       TEST IF CLICKING THE PROFILE PICTURE IMAGE OPENS UP THE GALLERY
     */
    @Test
    public void checkProfilePicture() {
        Intents.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.profileImage)).perform(click());
        intended(toPackage("com.android.gallery"));
    }

    @Test
    public void checkIfButtonsAreThere() {
        /*
        onView(withId(R.id.AddClass)).perform(click());
        onView(withId(R.id.Class2)).check(matches(isDisplayed()));*/
        onView(withId(R.id.AddClass)).check(matches(isDisplayed()));
    }

    /*
        Test if clicking find a partner causes MatchActivity to show up
     */

    @Test
    public void checkForMatchActivity() {
        onView(withId(R.id.submitbutton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.Stinder)).check(matches(isDisplayed()));
    }

}
