//	PROJECT:        AndroidMVC
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
package org.dimensinfin.android.mvc.core;

import android.app.Activity;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractRender extends AbstractHolder {
	// - S T A T I C - S E C T I O N ..........................................................................
	//	private static Logger									logger				= Logger.getLogger("AbstractHolder");

	// - F I E L D - S E C T I O N ............................................................................
	//	protected View												_convertView	= null;
	//	private Activity											_context			= null;
	//	private AbstractPart									_part					= null;
	//	private final HashMap<String, Object>	_extras				= new HashMap<String, Object>();

	//- L A Y O U T   F I E L D S
	//	protected ImageView										_rightArrow		= null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractRender(final AbstractPart newPart, final Activity context) {
		super(newPart, context);
		//		_part = newPart;
		//		this.setContext(context);
		//		this.createView();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	//	@Override
	//	public Activity getContext() {
	//		return _context;
	//	}
	//
	//	@Override
	//	public int getExtraInteger(final String key) {
	//		final Object data = _extras.get(key);
	//		if (data instanceof Integer)
	//			return ((Integer) data).intValue();
	//		else
	//			return 0;
	//	}
	//
	//	@Override
	//	public AbstractPart getPart() {
	//		return _part;
	//	}
	//
	//	@Override
	//	public View getView() {
	//		return _convertView;
	//	}
	//
	//	@Override
	//	public void initializeViews() {
	//		// Get the view of the rightArrow if found.
	//		_rightArrow = (ImageView) _convertView.findViewById(R.id.rightArrow);
	//	}
	//
	//	@Override
	//	public void setContext(final Activity context) {
	//		_context = context;
	//	}
	//
	//	@Override
	//	public void setExtraInteger(final String key, final Integer integer) {
	//		_extras.put(key, integer);
	//	}
	//
	//	@Override
	//	public void updateContent() {
	//		// Control the arrow to be shown.
	//		if (null != _rightArrow) {
	//			if (this.getPart().isExpanded()) {
	//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowright));
	//			} else {
	//				_rightArrow.setImageDrawable(this.getContext().getResources().getDrawable(R.drawable.arrowleft));
	//			}
	//		}
	//		_convertView.invalidate();
	//	}
	//
	//	@Override
	//	protected abstract void createView();
}

// - UNUSED CODE ............................................................................................
