package com.example.eyetestinginterface;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HomeTest {

    @Test
    public void testHomeFragmentToLoginFragment() {

        ActivityScenario<Home> activityScenario = ActivityScenario.launch(Home.class);

        FragmentScenario<TestFragment> navhostScenario = FragmentScenario.launchInContainer(TestFragment.class);

        navhostScenario.onFragment(fragment -> {

            // Create a NavController and set the NavController property on the fragment
            assertNotNull(fragment.getActivity());
            TestNavHostController navController = new TestNavHostController(fragment.getActivity());
            fragment.getActivity().runOnUiThread(() -> navController.setGraph(R.navigation.nav_host_l));
            Navigation.setViewNavController(fragment.requireView(), navController);

            // Then navigate
            navController.navigate(R.id.profileFragment);
            NavDestination destination = navController.getCurrentDestination();
            assertNotNull(destination);
            assertEquals(destination.getId(), R.id.profileFragment);


        });
//            onView(withId(R.id.profile_fragment)).check(matches(isDisplayed()));

    }

}