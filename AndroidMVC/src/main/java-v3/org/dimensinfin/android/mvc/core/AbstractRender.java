//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.core;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.core.model.Separator;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractRender implements IRender {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = LoggerFactory.getLogger("AbstractRender");

	// - F I E L D - S E C T I O N ............................................................................
	protected View _convertView = null;
	private Activity _context = null;
	protected AbstractPart _part = null;
	private final HashMap<String, Object> _extras = new HashMap<String, Object>();

	//- L A Y O U T   F I E L D S
	//	protected ImageView _rightArrow = null;

	// - C O N S T R U C T O R S
	public AbstractRender(){}
	public AbstractRender( final AbstractPart newPart, final Activity context ) {
		super();
		_part = newPart;
		this.setContext(context);
		this.createView();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Activity getContext() {
		return _context;
	}

	public AbstractPart getPart() {
		return _part;
	}


	//	public int getExtraInteger ( final String key ) {
	//		final Object data = extras.get(key);
	//		if ( data instanceof Integer )
	//			return ((Integer) data).intValue();
	//		else
	//			return 0;
	//	}


	public void setContext( final Activity context ) {
		_context = context;
	}

	//	public void setExtraInteger ( final String key, final Integer integer ) {
	//		extras.put(key, integer);
	//	}


	private LayoutInflater getInflater() {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	protected View inflateView( int layoutIdentifier ) {
		return getInflater().inflate(layoutIdentifier, null);
	}

	/**
	 * The is the generic code all Renders can share. The only element missing and that should be provided by each Render implementation is
	 * the layout identifier to be used on the inflation. This is not a new methos that is made abstract to force developers to fill the
	 * gap on ne instances.
	 */
	protected void createView() {
		_convertView = inflateView(accessLayoutReference());
		_convertView.setTag(this);
	}

	protected Drawable getDrawable( final int resourceid ) {
		if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP ) {
			return getContext().getResources().getDrawable(resourceid);
		} else return getContext().getResources().getDrawable(resourceid, getContext().getTheme());
	}

	protected void setPanelBorderColor( final Separator.ESeparatorType panelTheme ) {
		int themeColor = R.drawable.uipanelborderwhite;
		switch (panelTheme) {
			case LINE_WHITE:
				themeColor = R.drawable.uipanelborderwhite;
				break;
			case LINE_RED:
				themeColor = R.drawable.uipanelborderred;
				break;
			case LINE_ROSE:
				themeColor = R.drawable.uipanelborderrose;
				break;
			case LINE_ORANGE:
				themeColor = R.drawable.uipanelborderorange;
				break;
			case LINE_YELLOW:
				themeColor = R.drawable.uipanelborderyellow;
				break;
			case LINE_GREEN:
				themeColor = R.drawable.uipanelbordergreen;
				break;
			case LINE_LIGHTBLUE:
				themeColor = R.drawable.uipanelborderlightblue;
				break;
			case LINE_DARKBLUE:
				themeColor = R.drawable.uipanelborderdarkblue;
				break;
			case LINE_PURPLE:
				themeColor = R.drawable.uipanelborderpurple;
				break;
			case LINE_GREY:
				themeColor = R.drawable.uipanelbordergrey;
				break;
			case LINE_BLACK:
				themeColor = R.drawable.uipanelborderblack;
				break;
			default:
				themeColor = R.drawable.uipanelborderwhite;
				break;
		}
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
			_convertView.setBackground(getContext().getResources().getDrawable(themeColor, getContext().getTheme()));
		} else _convertView.setBackground(getContext().getResources().getDrawable(themeColor));
	}

	// --- I R E N D E R   I N T E R F A C E
	public abstract void initializeViews();
	// Get the view of the rightArrow if found.
	//		_rightArrow = (ImageView) _convertView.findViewById(R.id.rightArrow);
	//	}

	public abstract void updateContent();
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
	//	}

	public View getView() {
		return _convertView;
	}

	public abstract int accessLayoutReference();
}

// - UNUSED CODE ............................................................................................
