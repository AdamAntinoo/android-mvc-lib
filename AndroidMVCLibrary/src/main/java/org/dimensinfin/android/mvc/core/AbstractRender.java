//	PROJECT:        AndroidMVC
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.

package org.dimensinfin.android.mvc.core;

// - IMPORT SECTION .........................................................................................
import java.util.HashMap;
import java.util.logging.Logger;

import org.dimensinfin.android.mvc.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractRender {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger									logger				= Logger.getLogger("AbstractHolder");

	// - F I E L D - S E C T I O N ............................................................................
	protected View												_convertView	= null;
	private Activity											_context			= null;
	private AbstractPart									_part					= null;
	private final HashMap<String, Object>	_extras				= new HashMap<String, Object>();

	//- L A Y O U T   F I E L D S
	protected ImageView										_rightArrow		= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractRender(final AbstractPart newPart, final Activity context) {
		super();
		_part = newPart;
		this.setContext(context);
		this.createView();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Activity getContext() {
		return _context;
	}

	public int getExtraInteger(final String key) {
		final Object data = _extras.get(key);
		if (data instanceof Integer)
			return ((Integer) data).intValue();
		else
			return 0;
	}

	public AbstractPart getPart() {
		return _part;
	}

	public View getView() {
		return _convertView;
	}

	public void initializeViews() {
		// Get the view of the rightArrow if found.
		_rightArrow = (ImageView) _convertView.findViewById(R.id.rightArrow);
	}

	public void setContext(final Activity context) {
		_context = context;
	}

	public void setExtraInteger(final String key, final Integer integer) {
		_extras.put(key, integer);
	}

	public void updateContent() {
		// Control the arrow to be shown.
		if (null != _rightArrow) {
			if (this.getPart().isExpanded()) {
				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowdown));
			} else {
				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowright));
			}
		}
		_convertView.invalidate();
	}
	protected LayoutInflater getInflater() {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	protected View inflateView(int layoutIdentifier) {
		return getInflater().inflate(layoutIdentifier, null);
	}

	protected abstract void createView();
}

// - UNUSED CODE ............................................................................................
