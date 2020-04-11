package org.dimensinfin.android.mvc.factory;

import android.content.Context;
import android.content.Intent;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.exception.MVCException;
import org.dimensinfin.core.interfaces.ICollaboration;

public interface IControllerFactory {
	IAndroidController createController( ICollaboration model );

	String getVariant();

	IControllerFactory registerActivity( final String activityCode, final Class activityClass );

	Intent prepareActivity( final String activityCode, final Context context ) throws MVCException;
}
