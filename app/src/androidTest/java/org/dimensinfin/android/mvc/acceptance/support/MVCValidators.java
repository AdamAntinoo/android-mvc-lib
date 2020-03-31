package org.dimensinfin.android.mvc.acceptance.support;

import android.view.View;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.acceptance.support.core.AcceptanceNeoComLogger;

public class MVCValidators {
	public static boolean  validateAppVersionActionBar( final Map<String, String> expectedData, final View targetView ){
		final String APP_NAME = "AppName";
		final String APP_VERSION = "AppVersion";

		final TextView appName = Objects.requireNonNull( targetView.findViewById( 1000055 ) );
		AcceptanceNeoComLogger.info( "[THEN] appName: {}", appName.getText().toString() );
		Assert.assertEquals( expectedData.get( APP_NAME ), appName.getText().toString() );
		final TextView appVersion = Objects.requireNonNull( targetView.findViewById(1000010 ) );
		AcceptanceNeoComLogger.info( "[THEN] appVersion: {}", appVersion.getText().toString() );
		Assert.assertEquals( expectedData.get( APP_NAME ), appVersion.getText().toString() );
		return true;
	}
}
