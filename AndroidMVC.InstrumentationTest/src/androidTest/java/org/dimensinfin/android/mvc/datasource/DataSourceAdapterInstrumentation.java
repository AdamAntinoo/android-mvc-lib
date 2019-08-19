package org.dimensinfin.android.mvc.datasource;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.dimensinfin.android.mvc.instrumentation.activity.AcceptanceActivity00;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

//@RunWith(AndroidJUnit4.class)
public class DataSourceAdapterInstrumentation {
	private AcceptanceActivity00 activity;
	private AcceptanceActivity00.MVC00Fragment fragment;
	@Rule
	public ActivityTestRule<AcceptanceActivity00> activityRule = new ActivityTestRule<>(AcceptanceActivity00.class,
	                                                                                         false, false);

	@Test
	public void getView() {
		Assert.assertNotNull(this.activityRule);
		this.activityRule.launchActivity(null);
		this.activity = this.activityRule.getActivity();
		Assert.assertNotNull(this.activity);
		this.fragment = (AcceptanceActivity00.MVC00Fragment) this.activity.accessPageAdapter().getItem(0);
		Assert.assertNotNull(this.fragment);
		final View panel = this.fragment.accessHeaderContainer().getChildAt(0);
		Assert.assertNotNull(panel);
	}
}