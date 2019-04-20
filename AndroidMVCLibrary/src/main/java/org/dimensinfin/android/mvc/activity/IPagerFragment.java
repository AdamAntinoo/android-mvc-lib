package org.dimensinfin.android.mvc.activity;

import android.content.Context;
import android.os.Bundle;

import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.ITitledFragment;

public interface IPagerFragment extends ITitledFragment {
	public Context getAppContext();
	public IPagerFragment setAppContext(final Context appContext);
	public String getVariant();
	public IPagerFragment setVariant(final String selectedVariant);
	public Bundle getExtras();
	public IPagerFragment setExtras(final Bundle extras);
//	public IMenuActionTarget getListCallback();
}
