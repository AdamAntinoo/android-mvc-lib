package org.dimensinfin.android.mvc.acceptance.test.support;

import android.app.Activity;

import androidx.test.rule.ActivityTestRule;

public class MVCWorld {
	private ActivityTestRule activityRule;
	private Activity activeActivity;

	public ActivityTestRule getActivityRule() {
		return activityRule;
	}

	public MVCWorld setActivityRule( final ActivityTestRule activityRule ) {
		this.activityRule = activityRule;
		return this;
	}

	public Activity getActiveActivity() {
		return activeActivity;
	}

	public MVCWorld setActiveActivity( final Activity activeActivity ) {
		this.activeActivity = activeActivity;
		return this;
	}
}
