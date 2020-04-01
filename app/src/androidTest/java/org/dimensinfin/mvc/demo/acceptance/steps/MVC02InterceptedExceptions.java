package org.dimensinfin.mvc.demo.acceptance.steps;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.util.HumanReadables;

import org.junit.Assert;

import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;

import cucumber.api.java.en.And;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MVC02InterceptedExceptions {
	private static final String EXCEPTION_MESSAGE = "The data source cannot be a null reference. Please review the fragment code" +
			                                                " and implement the 'createDS' method.";
	private MVCWorld world;
	//	private AcceptanceActivity02 activity;
	private Fragment fragment;

//	private ActivityTestRule<AcceptanceActivity02> mActivityRule = new ActivityTestRule<>(AcceptanceActivity02.class, false,
//	                                                                                      false);

	//	@Before
//	public void launchActivity() throws Exception {
//		this.mActivityRule.launchActivity(null);
//	}
//	@After
//	public void finishActivity() throws Exception {
//		this.mActivityRule.getActivity().finish();
//	}

	public MVC02InterceptedExceptions( final MVCWorld world ) {
		this.world = world;
	}

	public static ViewAssertion isNotDisplayed() {
		return new ViewAssertion() {
			@Override
			public void check( View view, NoMatchingViewException noView ) {
				if (view != null && isDisplayed().matches( view )) {
					throw new AssertionError( "View is present in the hierarchy and Displayed: "
							                          + HumanReadables.describe( view ) );
				}
			}
		};
	}

//	@Given("the activity {string}")
//	public void theActivity( final String activityClass ) {
//		Assert.assertNotNull(this.mActivityRule);
//		this.mActivityRule.launchActivity(null);
//		this.activity = this.mActivityRule.getActivity();
//		Assert.assertNotNull(this.activity);
//		Assert.assertEquals(activityClass, this.activity.getClass().getSimpleName());
//	}

//	@And("a Fragment that do not defines a DataSource")
//	public void aFragmentThatDoNotDefinesADataSource() {
//		this.activity = (AcceptanceActivity02) this.world.getActiveActivity();
//		Assert.assertNotNull(this.activity);
//		this.fragment = (MVC02Fragment) this.activity.accessPageAdapter().getItem(0);
//		Assert.assertNotNull(fragment);
//	}

//	@When("reaching the onResume phase")
//	public void reachingTheOnResumePhase() {
//		Assert.assertTrue(this.activity.isOnResumeReached());
//	}
//
//	@Then("get an exception at the onCreateView phase")
//	public void getAnExceptionAtTheOnCreateViewPhase() {
//		Assert.assertNotNull(this.fragment.getLastException());
//	}

	@And("deactivate data panels")
	public void deactivateDataPanels() {
//		onView(withId(R.id.headerContainer)).check(this.isNotDisplayed());
//		onView(withId(R.id.listContainer)).check(this.isNotDisplayed());
	}

	@And("render exception to page")
	public void renderExceptionToPage() {
		// TODO - Obsolete test code
		final ViewGroup exceptionContainer =
				this.fragment.getView().findViewById( org.dimensinfin.android.mvc.R.id.exceptionContainer );
		Assert.assertEquals( 1, exceptionContainer.getChildCount() );
		final View exceptionView = exceptionContainer.getChildAt( 0 );
		Assert.assertNotNull( exceptionView );
		final String expected = EXCEPTION_MESSAGE;
		final String obtained =
				((TextView) exceptionView.findViewById( org.dimensinfin.android.mvc.R.id.exceptionMessage )).getText().toString();
		Assert.assertEquals( expected, obtained );
	}
}
