package org.dimensinfin.android.mvc.acceptance.support.core;

import android.content.Intent;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.dimensinfin.android.mvc.demo.activity.MVCDemoSimpleActivity;

public class MVCWorld  extends World{
private ActivityScenario<MVCDemoSimpleActivity> sMVCDemoSimpleActivityScenario;
	public Intent generateIntent( final Class destinationActivity ) {
		final Intent requestIntent = new Intent( ApplicationProvider.getApplicationContext(), destinationActivity );
		Bundle bundle = new Bundle();
		requestIntent.putExtras( bundle );
		return requestIntent;
	}

	public ActivityScenario<MVCDemoSimpleActivity> getsMVCDemoSimpleActivityScenario() {
		return this.sMVCDemoSimpleActivityScenario;
	}

	public MVCWorld setsMVCDemoSimpleActivityScenario( final ActivityScenario<MVCDemoSimpleActivity> sMVCDemoSimpleActivityScenario ) {
		this.sMVCDemoSimpleActivityScenario = sMVCDemoSimpleActivityScenario;
		return this;
	}
}
