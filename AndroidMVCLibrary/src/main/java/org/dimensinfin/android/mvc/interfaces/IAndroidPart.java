package org.dimensinfin.android.mvc.interfaces;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import org.dimensinfin.android.mvc.core.AbstractRender;

/**
 * Created by Adam on 15/11/2017.
 */

public interface IAndroidPart extends IPart {
	// - M E T H O D - S E C T I O N ..........................................................................
	Activity getActivity ();

	Fragment getFragment ();

	/**
	 * Returns a numeric identifier for this part model item that should be unique from all other system wide
	 * parts to allow for easy management of the corresponding parts and views.
	 *
	 * @return <code>long</code> identifier with the model number.
	 */
	long getModelID ();

	AbstractRender getRenderer(Activity activity);

	AbstractRender getRenderer(Fragment fragment);

	View getView ();

	void invalidate ();

	void needsRedraw ();

	void setView(View convertView);
}
