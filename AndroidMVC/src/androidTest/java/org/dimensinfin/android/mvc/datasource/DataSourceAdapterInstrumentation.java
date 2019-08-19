package org.dimensinfin.android.mvc.datasource;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.dimensinfin.android.mvc.activity.InstrumentationActivity;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

//@RunWith(AndroidJUnit4.class)
public class DataSourceAdapterInstrumentation {
	private InstrumentationActivity activity;
	private InstrumentationActivity.InstrumentationFragment fragment;
	@Rule
	public ActivityTestRule<InstrumentationActivity> activityRule = new ActivityTestRule<>(InstrumentationActivity.class,
	                                                                                         false, false);

	@Test
	public void getView() {
		Assert.assertNotNull(this.activityRule);
		this.activityRule.launchActivity(null);
		this.activity = this.activityRule.getActivity();
		Assert.assertNotNull(this.activity);
		this.fragment = (InstrumentationActivity.InstrumentationFragment) this.activity.accessPageAdapter().getItem(0);
		Assert.assertNotNull(this.fragment);
		final View panel = this.fragment.accessDataSectionContainer().getChildAt(0);
		Assert.assertNotNull(panel);
	}
}