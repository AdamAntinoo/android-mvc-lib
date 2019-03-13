package org.dimensinfin.android.mvc.demo.activity;

import com.google.common.truth.Truth;
import org.dimensinfin.android.mvc.demo.BuildConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Adam Antinoo
 */
@RunWith(RobolectricTestRunner.class)
//@RunWith(AndroidJUnit4::class)

//@Config( sdk = 18)
public class AndroidMVCDemoActivityTest {
	@Test
	public void clickingButton_shouldChangeMessage() {
		// Given
		final AndroidMVCDemoActivity activity = Robolectric.setupActivity(AndroidMVCDemoActivity.class);
		final AndroidMVCDemoFragment fragment = new AndroidMVCDemoFragment();
//		final String expected = "";

		// Tests
		activity.addPage(fragment);
		final String expected = "";

		// Asserts
		Truth.assertThat(fragment.getTitle()).isEqualTo("DEMO MVC");
		Truth.assertThat(fragment.getSubtitle()).isEqualTo("->No Expandable Section");
	}
}

//@RunWith(AndroidJUnit4::class)
//class FragmentTest {
//	@Test fun testEventFragment() {
//		val fragmentArgs = Bundle()
//		val factory = MyFragmentFactory()
//		val scenario = launchFragmentInContainer<MyFragment>(fragmentArgs, factory)
//		onView(withId(R.id.text)).check(matches(withText("Hello World!")))
//	}
//}

