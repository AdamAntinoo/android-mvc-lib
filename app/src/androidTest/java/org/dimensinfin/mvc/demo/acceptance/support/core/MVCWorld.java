package org.dimensinfin.mvc.demo.acceptance.support.core;

import android.content.Intent;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.dimensinfin.mvc.demo.activity.DemoMultiPageActivity;
import org.dimensinfin.mvc.demo.activity.MVCDemoSimpleActivity;
import org.dimensinfin.mvc.demo.activity.PageSelectionDashboardActivity;

public class MVCWorld extends World {
	private ActivityScenario<PageSelectionDashboardActivity> pageSelectionDashboardActivityScenario;
	private ActivityScenario<DemoMultiPageActivity> demoMultiPageActivityScenario;
	private ActivityScenario<MVCDemoSimpleActivity> mvcDemoSimpleActivityScenario;

	public ActivityScenario<DemoMultiPageActivity> getDemoMultiPageActivityScenario() {
		return this.demoMultiPageActivityScenario;
	}

	public MVCWorld setDemoMultiPageActivityScenario( final ActivityScenario<DemoMultiPageActivity> demoMultiPageActivityScenario ) {
		this.demoMultiPageActivityScenario = demoMultiPageActivityScenario;
		return this;
	}

	public ActivityScenario<MVCDemoSimpleActivity> getMvcDemoSimpleActivityScenario() {
		return this.mvcDemoSimpleActivityScenario;
	}

	public MVCWorld setMvcDemoSimpleActivityScenario( final ActivityScenario<MVCDemoSimpleActivity> mvcDemoSimpleActivityScenario ) {
		this.mvcDemoSimpleActivityScenario = mvcDemoSimpleActivityScenario;
		return this;
	}

	public ActivityScenario<PageSelectionDashboardActivity> getPageSelectionDashboardActivityScenario() {
		return this.pageSelectionDashboardActivityScenario;
	}

	public MVCWorld setPageSelectionDashboardActivityScenario( final ActivityScenario<PageSelectionDashboardActivity> pageSelectionDashboardActivityScenario ) {
		this.pageSelectionDashboardActivityScenario = pageSelectionDashboardActivityScenario;
		return this;
	}

	public Intent generateIntent( final Class destinationActivity ) {
		final Intent requestIntent = new Intent( ApplicationProvider.getApplicationContext(), destinationActivity );
		Bundle bundle = new Bundle();
		requestIntent.putExtras( bundle );
		return requestIntent;
	}
}
