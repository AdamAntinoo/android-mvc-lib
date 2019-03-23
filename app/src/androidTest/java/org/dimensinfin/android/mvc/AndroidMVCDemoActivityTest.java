package org.dimensinfin.android.mvc.demo.activity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import com.viewpagerindicator.CirclePageIndicator;
import org.dimensinfin.android.mvc.demo.R;
import org.junit.Assert;

/**
 * @author Adam Antinoo
 */
//@RunWith(AndroidJUnit4::class)

//@Config( sdk = 18)
public class AndroidMVCDemoActivityTest extends ActivityInstrumentationTestCase2<AndroidMVCDemoActivity> {
	public AndroidMVCDemoActivityTest() {
		super("com.example.android.activityinstrumentation", AndroidMVCDemoActivity.class);
	}

	// BEGIN_INCLUDE (test_name)
	public void testIndicatorNotShown4SinglePage() {
		// END_INCLUDE (test_name)
		Activity activity = getActivity();
		final CirclePageIndicator indicator = activity.findViewById(R.id.indicator);
		activity.runOnUiThread(() -> {
			// Attempts to manipulate the UI must be performed on a UI thread.
			// Calling this outside runOnUiThread() will cause an exception.
			//
			// You could also use @UiThreadTest, but activity lifecycle methods
			// cannot be called if this annotation is used.
			indicator.requestFocus();
			final int actual = indicator.getVisibility();
			Assert.assertEquals("The visisbility state should match.", View.GONE, actual);
		});
	}

//	//public class AndroidMVCDemoActivityTest {
//	@RunWith(RobolectricTestRunner.class)
//	@Test
//	public void clickingButton_shouldChangeMessage() {
//		// Given
//		final AndroidMVCDemoActivity activity = Robolectric.setupActivity(AndroidMVCDemoActivity.class);
//		final AndroidMVCDemoFragment fragment = new AndroidMVCDemoFragment();
////		final String expected = "";
//
//		// Tests
//		activity.addPage(fragment);
//		final String expected = "";
//
//		// Asserts
//		Truth.assertThat(fragment.getTitle()).isEqualTo("DEMO MVC");
//		Truth.assertThat(fragment.getSubtitle()).isEqualTo("->No Expandable Section");
//	}
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

