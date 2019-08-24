package org.dimensinfin.android.mvc.domain;

import android.content.Context;
import android.content.Intent;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;

public interface IControllerFactory {
	IAndroidController createController( ICollaboration model );

	String getVariant();

	IControllerFactory registerActivity( final String activityCode, final Class activityClass );

	Intent prepareActivity( String planet_facilities, Context context );
}
