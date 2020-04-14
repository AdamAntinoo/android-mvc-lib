package org.dimensinfin.android.mvc.instrumentation.render;

import android.content.Intent;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.instrumentation.support.MVCRenderActivity;
import org.dimensinfin.logging.LogWrapper;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
@SmallTest
public class MVCRenderInstrumentation {
	@Test
	public void mvcRender() {
		LogWrapper.info( "[INSTRUMENTATION] mvcRender" );
		LogWrapper.info( "Going to launch the scenario with the MVCRenderActivity..." );
		final ActivityScenario<MVCRenderActivity> scenario = ActivityScenario.launch(
				this.generateIntent( MVCRenderActivity.class )
		);
		Assert.assertNotNull( scenario );
//		LogWrapper.info( "Store the scenario..." );
//		this.world.setMvcDemoSimpleActivityScenario( scenario );
		scenario.onActivity( ( activity ) -> {
			LogWrapper.info( "Verify the activity starts without exceptions..." );
			Assert.assertNotNull( activity );
//			LogWrapper.info( "Store the activity..." + activity.getClass().getSimpleName() );
//			this.world.setActiveActivity( activity );
//			LogWrapper.info( "Set the configured page..." + pageNumber );
//			this.world.setSelectedPage( Integer.parseInt( pageNumber ) );
			LogWrapper.info( "Verify the current activity state..." );
			final ActivityLifecycleMonitor registry = ActivityLifecycleMonitorRegistry.getInstance();
			final Stage stage = registry.getLifecycleStageOf( activity );
			Assert.assertEquals( Stage.RESUMED, stage );

			// Do the render test.
			this.doMVCRenderTest();
		} );
//		Ristretto.waitForBackground( () -> {
//			Ristretto.waitForCompletion( () -> {
//				LogWrapper.info( "Invalidate all the display and wait termination..." );
//				Ristretto.updateDisplay();
//			} );
//		} );
	}

	private Intent generateIntent( final Class destinationActivity ) {
		final Intent requestIntent = new Intent( ApplicationProvider.getApplicationContext(), destinationActivity );
		Bundle bundle = new Bundle();
		requestIntent.putExtras( bundle );
		return requestIntent;
	}

	private void doMVCRenderTest() {
		onView( ViewMatchers.withId( R.id.label ) )
//				.perform(ViewAction)
				.check( ViewAssertions.matches( withText( "-TEST-NAME-VALUE-" ) ) );
	}
}