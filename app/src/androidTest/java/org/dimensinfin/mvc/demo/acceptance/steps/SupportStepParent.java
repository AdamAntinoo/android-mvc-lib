package org.dimensinfin.mvc.demo.acceptance.steps;

import android.app.Activity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;

import org.dimensinfin.android.mvcannotations.logging.LoggerWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.MVCValidators;
import org.dimensinfin.mvc.demo.acceptance.support.core.MVCWorld;
import org.dimensinfin.mvc.demo.acceptance.support.ristretto.Ristretto;

public class SupportStepParent {
	static {
		System.setProperty( "org.mockito.android.target", ApplicationProvider.getApplicationContext().getCacheDir().getPath() );
	}

	protected MVCWorld world;

	public SupportStepParent( final MVCWorld world ) {
		this.world = world;
		Ristretto.setWorld( this.world );
		MVCValidators.setWorld( this.world );
	}

	protected Activity getActivityInstance() {
		final Activity[] currentActivity = { null };

		InstrumentationRegistry.getInstrumentation().runOnMainSync( new Runnable() {
			public void run() {
				Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage( Stage.RESUMED );
				Iterator<Activity> it = resumedActivity.iterator();
				currentActivity[0] = it.next();
			}
		} );

		return currentActivity[0];
	}

	protected void verifyActivityLifecycleCompletion( final ActivityScenario<?> scenario, final int pageNumber ) {
		scenario.onActivity( ( activity ) -> {
			LoggerWrapper.info( "Verify the activity starts without exceptions..." );
			Assert.assertNotNull( activity );
			LoggerWrapper.info( "Store the activity..." + activity.getClass().getSimpleName() );
			this.world.setActiveActivity( activity );
			LoggerWrapper.info( "Set the configured page..." + pageNumber );
			this.world.setSelectedPage( pageNumber );
			LoggerWrapper.info( "Verify the current activity state..." );
			final ActivityLifecycleMonitor registry = ActivityLifecycleMonitorRegistry.getInstance();
			final Stage stage = registry.getLifecycleStageOf( activity );
			Assert.assertEquals( Stage.RESUMED, stage );
		} );
		Ristretto.waitForBackground( () -> {
			Ristretto.waitForCompletion( () -> {
				LoggerWrapper.info( "Invalidate all the display and wait termination..." );
				Ristretto.updateDisplay();
			} );
		} );
	}
}
