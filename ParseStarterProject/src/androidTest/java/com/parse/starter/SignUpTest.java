package com.parse.starter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by jarnin on 3/1/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpTest {
        @Rule
        public ActivityTestRule<SignUpActivity> mActivityRule = new ActivityTestRule(SignUpActivity.class);

        @Test
        public void listGoesOverTheFold() {

        }
}
