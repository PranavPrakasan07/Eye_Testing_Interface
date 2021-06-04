package com.example.eyetestinginterface;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, Login Activity tested, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void test_isActivityDisplay(){

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
    }

    @Test
    public void test_isCorrectText() {

        ActivityScenario<LoginActivity> activityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.login_button))            // withId(R.id.my_view) is a ViewMatcher
        .check(matches(withText("Login")));

        onView(withId(R.id.materialTextView))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(withText("Login")));
    }


    @Test
    public void test_navigateToLogin() {

        ActivityScenario<LoginActivity> activityActivityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.signup_link)).perform(click());

        onView(withId(R.id.signup_page)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.login_page)).check(matches(isDisplayed()));
    }

    @Test
    public void test_loginWithPassword(){

        ActivityScenario<LoginActivity> activityActivityScenario = ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.email)).perform(typeText("tester.app@gmail.com"));
        onView(withId(R.id.email)).perform(clearText());
        onView(withId(R.id.email)).perform(typeText("tester.app@gmail.com"));

        onView(withId(R.id.password)).perform(typeText("123456"));
        onView(withId(R.id.password)).perform(clearText());
        onView(withId(R.id.password)).perform(typeText("123456"));

        onView(withId(R.id.login_button)).perform(click());
    }

}