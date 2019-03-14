package org.dimensinfin.android.mvc;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.dimensinfin.android.mvc.activity.AbstractPagerActivity;
import org.dimensinfin.android.mvc.demo.activity.AndroidMVCDemoActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest {

	@Rule
	public ActivityTestRule<AndroidMVCDemoActivity> activityRule =
			new ActivityTestRule<>(AndroidMVCDemoActivity.class);

	@Test
	public void listGoesOverTheFold() {
		onView(withText("ANDROIDMVC")).check(matches(isDisplayed()));
	}
}