package org.dimensinfin.android.mvc.acceptance.test.steps;

import androidx.test.rule.ActivityTestRule;

import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity;
import org.junit.Assert;
import org.junit.Rule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class MVC01InterceptRenderException {
	private ActivityTestRule<AcceptanceActivity> mActivityRule = new ActivityTestRule<>(AcceptanceActivity.class, false, false);
//	@Rule
//	private ActivityTestRule<AcceptanceActivity> mActivityRule = new ActivityTestRule<>(AcceptanceActivity.class);
	private AcceptanceActivity activity;

	//	public CalculatorActivitySteps( SomeDependency dependency) {
//		assertNotNull(dependency);
//	}
	@Before
	public void launchActivity() throws Exception {
		this.mActivityRule.launchActivity(null);
	}

	/**
	 * All the clean up of application's data and state after each scenario must happen here
	 */
	@After
	public void finishActivity() throws Exception {
//		this.mActivityRule.getActivity().finish();
	}

	@Given("a TitlePanel render")
	public void aTitlePanelRender() {
		this.activity = this.mActivityRule.getActivity();
		Assert.assertNotNull(this.activity);
	}
}
