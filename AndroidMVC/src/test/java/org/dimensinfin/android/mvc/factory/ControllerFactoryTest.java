package org.dimensinfin.android.mvc.factory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.text.MessageFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.controller.ExceptionReportController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.controller.ProgressSpinnerController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.exception.MVCException;
import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.android.mvc.support.Test4ModelFailure;
import org.dimensinfin.core.domain.Node;

public class ControllerFactoryTest {
	private static final String TEST_VARIANT = "-TAG-";

	@Test
	void constructorContract() {
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		Assertions.assertNotNull( factory );
	}

	@Test
	void constructorFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			final ControllerFactory factory = new ControllerFactory( null );
		} );
	}

	@Test
	public void createControllerAllPredefined() {
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		IAndroidController obtained = factory.createController( new Spacer.Builder().build() );
		Assertions.assertTrue( obtained instanceof SpacerController );
		obtained = factory.createController( new ExceptionReport.Builder( new NullPointerException( "Test exception" ) ).build() );
		Assertions.assertTrue( obtained instanceof ExceptionReportController );
		obtained = factory.createController( new Node.Builder().build() );
		Assertions.assertTrue( obtained instanceof ProgressSpinnerController );
	}

	@Test
	void createControllerNotFound() {
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		IAndroidController obtained = factory.createController( new Test4ModelFailure.Builder().build() );
		// Assertions
		Assertions.assertTrue( obtained instanceof SpacerController );
		final String expected = MessageFormat.format( "-No Model-Controller match-[{0}]-", "Test4ModelFailure" );
		Assertions.assertEquals( expected, ((SpacerController) obtained).getModel().getLabel() );
	}

	@Test
	void gettersContract() {
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		// Assertions
		Assertions.assertEquals( "-TAG-", factory.getVariant() );
	}

	@Test
	public void registerActivitySuccess() {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		// Assertions
		final IControllerFactory obtained = factory.registerActivity( activityCode, Activity.class );
		Assertions.assertNotNull( obtained );
		Assertions.assertTrue( factory.isRegistered( activityCode ) );
	}

	@Test
	public void registerActivityFailure() {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final IControllerFactory obtained = factory.registerActivity( null, Activity.class );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final IControllerFactory obtained = factory.registerActivity( activityCode, null );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final IControllerFactory obtained = factory.registerActivity( null, null );
		} );
	}

	@Test
	void isRegistered() {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		factory.registerActivity( activityCode, Activity.class );
		// Assertions
		Assertions.assertTrue( factory.isRegistered( activityCode ) );
		Assertions.assertFalse( factory.isRegistered( "-NOT-FOUND-ACTIVITY-" ) );
	}

	@Test
	void cleanRegistry() {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		factory.registerActivity( activityCode, Activity.class );
		// Assertions
		Assertions.assertTrue( factory.isRegistered( activityCode ) );
		Assertions.assertFalse( factory.isRegistered( "-NOT-FOUND-ACTIVITY-" ) );
		factory.cleanRegistry();
		Assertions.assertFalse( factory.isRegistered( activityCode ) );
		Assertions.assertFalse( factory.isRegistered( "-NOT-FOUND-ACTIVITY-" ) );
	}

	@Test
	void prepareActivitySuccess() throws MVCException {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		final Context context = Mockito.mock( Context.class );
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		factory.registerActivity( activityCode, Activity.class );
		final Intent obtained = factory.prepareActivity( activityCode, context );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	void prepareActivityFailure() {
		// Given
		final String activityCode = "-TEST-PAGE-ACTIVITY-CODE-";
		final Context context = Mockito.mock( Context.class );
		// Test
		final ControllerFactory factory = new ControllerFactory( TEST_VARIANT );
		factory.registerActivity( activityCode, Activity.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final Intent obtained = factory.prepareActivity( "-NOT-FOUND-ACTIVITY-", context );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final Intent obtained = factory.prepareActivity( null, context );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final Intent obtained = factory.prepareActivity( "-NOT-FOUND-ACTIVITY-", null );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final Intent obtained = factory.prepareActivity( null, null );
		} );
	}
}