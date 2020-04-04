package org.dimensinfin.mvc.demo.acceptance.support;

import android.view.View;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.acceptance.support.core.LoggerWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.World;

public class MVCValidators {
	private static World world;

	public static void setWorld( final World newworld ) {
		world = newworld;
	}

	public static boolean validateAppVersionActionBar( final Map<String, String> expectedData, final View targetView ) {
		final String APP_NAME = "AppName";
		final String APP_VERSION = "AppVersion";

		final TextView appName = Objects.requireNonNull( targetView.findViewById( R.id.applicationName ) );
		LoggerWrapper.info( "[THEN] appName: {}", appName.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( APP_NAME ) ), appName.getText().toString() );
		final TextView appVersion = Objects.requireNonNull( targetView.findViewById( R.id.applicationVersion ) );
		LoggerWrapper.info( "[THEN] appVersion: {}", appVersion.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( APP_NAME ) ), appVersion.getText().toString() );
		return true;
	}

	public static boolean validateApplicationHeaderTitlePanel( final Map<String, String> expectedData, final View targetView ) {
		final String APPLICATION_NAME = "applicationName";
		final String APPLICATION_VERSION = "applicationVersion";

//		final ViewInteraction view = onView( withId( R.id.applicationName ) );
//		view.check(matches(withText(decodePredefinedValue( expectedData.get( APPLICATION_NAME ) ))));

		final TextView appName = Objects.requireNonNull( targetView.findViewById( R.id.applicationName ) );
		LoggerWrapper.info( "[THEN] applicationName: {}", appName.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( APPLICATION_NAME ) ), appName.getText().toString() );
		final TextView appVersion = Objects.requireNonNull( targetView.findViewById( R.id.applicationVersion ) );
		LoggerWrapper.info( "[THEN] applicationVersion: {}", appVersion.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( APPLICATION_VERSION ) ), appVersion.getText().toString() );
		return true;
	}

	public static boolean validateTitleLabelPanel( final Map<String, String> expectedData, final View targetView ) {
		final String TITLE = "title";

		final TextView appName = Objects.requireNonNull( targetView.findViewById( R.id.title ) );
		LoggerWrapper.info( "[THEN] title: {}", appName.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( TITLE ) ), appName.getText().toString() );
		return true;
	}

	public static boolean validateDemoLabelPanel( final Map<String, String> expectedData, final View targetView ) {
		final String TITLE = "title";

		final TextView appName = Objects.requireNonNull( targetView.findViewById( R.id.nodeName ) );
		LoggerWrapper.info( "[THEN] title: {}", appName.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( TITLE ) ), appName.getText().toString() );
		return true;
	}

	private static String decodePredefinedValue( final String value ) {
		Objects.requireNonNull( value );
		if (value.contains( "<" )) {
			switch (value) {
				case "<version>":
					return world.getActiveActivity().getResources().getString( R.string.appversion );
			}
		}
		return value;
	}

	public static boolean validateMultiPageActionBar( final Map<String, String> expectedData, final View targetView ) {
		final String TITLE = "title";

		final TextView pageTitle = Objects.requireNonNull( targetView.findViewById( R.id.title ) );
		LoggerWrapper.info( "[THEN] title: {}", pageTitle.getText().toString() );
		Assert.assertEquals( decodePredefinedValue( expectedData.get( TITLE ) ), pageTitle.getText().toString() );
		return true;
	}
}
