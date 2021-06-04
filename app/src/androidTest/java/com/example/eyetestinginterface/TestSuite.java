package com.example.eyetestinginterface;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Instrumented test, Activity Test Suite which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SignUpActivityTest.class, LoginActivityTest.class, TestFragmentTest.class, HomeTest.class})

public class TestSuite {
}
