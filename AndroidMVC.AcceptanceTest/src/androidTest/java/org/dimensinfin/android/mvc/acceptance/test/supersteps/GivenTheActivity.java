package org.dimensinfin.android.mvc.acceptance.test.supersteps;

import androidx.test.rule.ActivityTestRule;

import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity02;
import org.dimensinfin.android.mvc.acceptance.activity.AcceptanceActivity04;
import org.dimensinfin.android.mvc.acceptance.test.support.MVCWorld;
import org.junit.Assert;

import cucumber.api.java.en.Given;

public class GivenTheActivity {
	private MVCWorld world;
//	private ActivityTestRule<AcceptanceActivity04>

	public GivenTheActivity( final MVCWorld world ) {
		this.world = world;
	}

	@Given("the activity {string}")
	public void theActivity( final String activityClass ) {
		switch (activityClass) {
			case "AcceptanceActivity02":
				final ActivityTestRule<AcceptanceActivity02> activityRule02 = new ActivityTestRule<>(AcceptanceActivity02.class,
				                                                                                   false, false);

				Assert.assertNotNull(activityRule02);
				this.world.setActivityRule(activityRule02);
				break;
			case "AcceptanceActivity04":
				final ActivityTestRule<AcceptanceActivity04> activityRule04 = new ActivityTestRule<>(AcceptanceActivity04.class,
				                                                                                   false, false);

				Assert.assertNotNull(activityRule04);
				this.world.setActivityRule(activityRule04);
				break;
		}
		this.world.getActivityRule().launchActivity(null);
		this.world.setActiveActivity(this.world.getActivityRule().getActivity());
		Assert.assertNotNull(this.world.getActiveActivity());
		Assert.assertEquals(activityClass, this.world.getActiveActivity().getClass().getSimpleName());
	}
}
