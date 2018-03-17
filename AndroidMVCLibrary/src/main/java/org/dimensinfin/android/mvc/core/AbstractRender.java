//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchycal Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.core;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import org.dimensinfin.android.mvc.interfaces.IRender;

import java.util.HashMap;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractRender implements IRender {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("AbstractRender");

	// - F I E L D - S E C T I O N ............................................................................
	protected View _convertView = null;
	private Activity _context = null;
	private AbstractPart _part = null;
	private final HashMap<String, Object> _extras = new HashMap<String, Object>();

	//- L A Y O U T   F I E L D S
//	protected ImageView _rightArrow = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractRender (final AbstractPart newPart, final Activity context) {
		super();
		_part = newPart;
		this.setContext(context);
		this.createView();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Activity getContext () {
		return _context;
	}

	public int getExtraInteger (final String key) {
		final Object data = _extras.get(key);
		if ( data instanceof Integer )
			return ((Integer) data).intValue();
		else
			return 0;
	}

	public AbstractPart getPart () {
		return _part;
	}

	public View getView () {
		return _convertView;
	}

	public void initializeViews () {
		// Get the view of the rightArrow if found.
//		_rightArrow = (ImageView) _convertView.findViewById(R.id.rightArrow);
	}

	public void setContext (final Activity context) {
		_context = context;
	}

	public void setExtraInteger (final String key, final Integer integer) {
		_extras.put(key, integer);
	}

	public void updateContent () {
//		// Control the arrow to be shown.
//		if ( null != _rightArrow ) {
//			if ( this.getPart().isExpanded() ) {
//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowdown, getContext().getTheme()));
//			} else {
//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowright, getContext().getTheme()));
//			}
//		}
//		_convertView.invalidate();
//	}
//	public void updateContent () {
//		// Control the arrow to be shown.
//		if ( null != _rightArrow ) {
//			if ( this.getPart().isExpanded() ) {
//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowdown, getContext().getTheme()));
//			} else {
//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowright, getContext().getTheme()));
//			}
//		}
//		_convertView.invalidate();
	}

	private LayoutInflater getInflater () {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	protected View inflateView (int layoutIdentifier) {
		return getInflater().inflate(layoutIdentifier, null);
	}

	protected abstract void createView ();

	protected Drawable getDrawable (final int resourceid) {
		return getContext().getResources().getDrawable(resourceid, getContext().getTheme());
	}
}

// - UNUSED CODE ............................................................................................
