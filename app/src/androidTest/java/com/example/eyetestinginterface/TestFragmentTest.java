package com.example.eyetestinginterface;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class TestFragmentTest {

    @Test
    public void test_isFragmentDisplay() {

        ActivityScenario<Home> activityScenario = ActivityScenario.launch(Home.class);

        FragmentScenario<TestFragment> fragmentScenario = FragmentScenario.launchInContainer(TestFragment.class);

        onView(withId(R.id.test_fragment))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion
    }


}