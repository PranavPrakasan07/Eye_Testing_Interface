package com.example.eyetestinginterface;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SignUpActivityTest {

    @Test
    public void test_isActivityDisplay(){

        ActivityScenario<SignUpActivity> activityScenario = ActivityScenario.launch(SignUpActivity.class);

        onView(withId(R.id.signup_page))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
    }

    @Test
    public void test_ifDisplayed() {

        ActivityScenario<SignUpActivity> activityScenario = ActivityScenario.launch(SignUpActivity.class);

        onView(withId(R.id.google_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.materialTextView))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.login_link))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

        onView(withId(R.id.signup_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
//                        .perform((ViewAction) isClickable());               // click() is a ViewAction
    }

    @Test
    public void test_isCorrectText() {

        ActivityScenario<SignUpActivity> activityScenario = ActivityScenario.launch(SignUpActivity.class);

        onView(withId(R.id.signup_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(withText("Sign Up")));

        onView(withId(R.id.materialTextView))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(withText("Sign Up")));

        onView(withId(R.id.signup_button))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(withText("SIGN UP")));
    }

    @Test
    public void test_navigateToLogin() {

        ActivityScenario<SignUpActivity> activityActivityScenario = ActivityScenario.launch(SignUpActivity.class);

        onView(withId(R.id.login_link)).perform(click());

        onView(withId(R.id.login_page)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.signup_page)).check(matches(isDisplayed()));

    }
}