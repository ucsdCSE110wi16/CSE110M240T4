package com.parse.starter;

import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by jarnin on 3/1/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpTest {
        @Rule
        public ActivityTestRule<SignUpActivity> mActivityRule = new ActivityTestRule(SignUpActivity.class);

        /* TESTING FOR IF SIGNING UP WITH EMAIL THAT'S ALREADY TAKEN SHOWS A DIALOG WITH TEXT
         * SAYING THAT THE EMAIL IS TAKEN
         */
        @Test
        public void enterInformationAndSignUp() {
                try {
                        Thread.sleep(2000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                onView(withId(R.id.firstnamefield)).perform(click()).perform(typeText("Bob"));
                onView(withId(R.id.firstnamefield)).perform(closeSoftKeyboard());
                onView(withId(R.id.lastnamefield)).perform(click()).perform(typeText("Smith"));
                onView(withId(R.id.lastnamefield)).perform(closeSoftKeyboard());
                onView(withId(R.id.emailField)).perform(click()).perform(typeText("abcd14@ucsd.edu"));
                onView(withId(R.id.emailField)).perform(closeSoftKeyboard());
                onView(withId(R.id.passwordField)).perform(click()).perform(typeText("abcd"));
                onView(withId(R.id.passwordField)).perform(closeSoftKeyboard());
                onView(withId(R.id.confirmPassword)).perform(click()).perform(typeText("abcd"));
                onView(withId(R.id.confirmPassword)).perform(closeSoftKeyboard());
                onView(withId(R.id.signupButton)).perform(click());
                //intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
                //intended(toPackage("com.parse.starter.LoginActivity"));
                /*for (int i = 0; i < 5; i++) {
                        SystemClock.sleep(1000);
                }*/

                //Supposed to stay here because email has already been used
                //onView(withId(R.id.signupButton)).check(matches(isDisplayed()));
                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                onView(withText("username abcd14@ucsd.edu already taken")).check(matches(isDisplayed()));
        }
}
