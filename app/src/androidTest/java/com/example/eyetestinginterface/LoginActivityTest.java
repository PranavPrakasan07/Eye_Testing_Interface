package com.example.eyetestinginterface;

import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LoginActivityTest {

    @Test
    public void test_isActivityDisplay(){

        ActivityScenario<LoginActivity> activityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.login_page))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
    }

    @Test
    public void test_ifDisplayed() {

        ActivityScenario<LoginActivity> activityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.google_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.materialTextView))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.signup_link))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.login_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
//                        .perform((ViewAction) isClickable());               // click() is a ViewAction
    }

    @Test
    public void test_isCorrectText() {

        ActivityScenario<LoginActivity> activityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.login_button))            // withId(R.id.my_view) is a ViewMatcher
        .check(matches(withText("Login")));

        onView(withId(R.id.materialTextView))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(withText("Login")));

//        onView(withId(R.id.signup_link))            // withId(R.id.my_view) is a ViewMatcher
//                .check(matches(withText("Signup")));
    }

}