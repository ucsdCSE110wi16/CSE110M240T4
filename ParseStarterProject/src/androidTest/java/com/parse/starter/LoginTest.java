package com.parse.starter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jarnin on 3/8/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void tryLoggingIn() {
        onView(withId(R.id.emailField)).perform(click()).perform(typeText("abcd14@ucsd.edu"));
        onView(withId(R.id.emailField)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(click()).perform(typeText("abcd"));
        onView(withId(R.id.passwordField)).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.profileImage)).check(matches(isDisplayed()));
    }

    @Test
    public void goToSignUp() {
        onView(withId(R.id.signupButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.emailField)).check(matches(isDisplayed()));
    }
}
