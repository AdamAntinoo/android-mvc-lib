package org.dimensinfin.android.mvc.demo.activity;

import com.google.common.truth.Truth;
import junit.framework.Assert;
import org.dimensinfin.android.mvc.demo.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Adam Antinoo
 */
@RunWith(RobolectricTestRunner.class)
public class AndroidMVCDemoActivityTest {
	@Test
	public void clickingButton_shouldChangeMessage() {
		// Given
	final	AndroidMVCDemoActivity activity = Robolectric.setupActivity(AndroidMVCDemoActivity.class);
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

