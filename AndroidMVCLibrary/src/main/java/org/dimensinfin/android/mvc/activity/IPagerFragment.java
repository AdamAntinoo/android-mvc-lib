package org.dimensinfin.android.mvc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.ITitledFragment;

public interface IPagerFragment {
	Activity getActivityContext();

	AMVCFragment setActivityContext( final Activity activity );

	String getVariant();

	IPagerFragment setVariant( final String selectedVariant );

	Bundle getExtras();

	IPagerFragment setExtras( final Bundle extras );

	IMenuActionTarget getListCallback();

	View generateActionBarView();
}
