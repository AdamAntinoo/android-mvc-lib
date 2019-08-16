package org.dimensinfin.android.mvc.acceptance.test.steps;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.dimensinfin.android.mvc.acceptance.R;
import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity;
import org.dimensinfin.android.mvc.acceptance.activity.MVC01Fragment;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MVC01InterceptRenderException {
	private static final String EXCEPTION_NAME = "exceptionName";
	private ActivityTestRule<AcceptanceActivity> mActivityRule = new ActivityTestRule<>(AcceptanceActivity.class, false, false);
	private AcceptanceActivity activity;
	private MVC01Fragment fragment;

//	@After
//	public void finishActivity() throws Exception {
//		this.mActivityRule.getActivity().finish();
//	}

	@Given("a TitlePanel render")
	public void aTitlePanelRender() {
		Assert.assertNotNull(this.mActivityRule);
		this.mActivityRule.launchActivity(null);
		this.activity = this.mActivityRule.getActivity();
		Assert.assertNotNull(this.activity);
		this.fragment = (MVC01Fragment) this.activity.accessPageAdapter().getItem(0);
		Assert.assertNotNull(this.fragment);
	}

	@When("creating the view")
	public void creatingTheView() {
		Assert.assertTrue(this.activity.isOnResumeReached());
	}

	@Then("^show the next information on the ExceptionRender$")
	public void showTheNextInformationOnTheExceptionRender( final List<Map<String, String>> cucumberTable ) {
		final ListView container = this.fragment.accessDataSectionContainer();
		Assert.assertEquals(1, container.getChildCount());
		final View exceptionView = container.getChildAt(0);
		final Map<String, String> row = cucumberTable.get(0);

		final TextView exceptionField = exceptionView.findViewById(R.id.exceptionCode);
		Assert.assertNotNull(exceptionField);
		Assert.assertEquals(row.get(EXCEPTION_NAME), exceptionField.getText());
	}
}
