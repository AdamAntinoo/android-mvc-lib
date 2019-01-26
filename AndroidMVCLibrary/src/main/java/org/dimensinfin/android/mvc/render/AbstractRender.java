//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.render;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import lombok.Builder;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.core.model.Separator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder
public abstract class AbstractRender<C> implements IRender {
	protected static Logger logger = LoggerFactory.getLogger(AbstractRender.class);

	// - F I E L D - S E C T I O N
	private C controller; // Holds the parent controller that is associated to this render. Used to access the model.
	private Activity context; // Reference to the context. Usually the application singleton.
	private View _convertView = null;
//	private final HashMap<String, Object> _extras = new HashMap<String, Object>();


	// - L A Y O U T   F I E L D S
	//	protected ImageView _rightArrow = null;

	// - C O N S T R U C T O R - S E C T I O N
	protected AbstractRender(final AbstractRender.Builder<C> builder) {
		this.controller = builder.controller;
		this.context = builder.context;
		this.createView(); // Inflate the layout to have the containers ready for identification.
	}

	// - M E T H O D - S E C T I O N

	/**
	 * The is the generic code all Renders can share. The only element missing and that should be provided by each Render
	 * implementation is the layout identifier to be used on the inflation. This is not a new method that is made abstract
	 * to force developers to fill the gap on ne instances.
	 */
	private void createView() {
		_convertView = inflateView(accessLayoutReference());
		_convertView.setTag(this);
	}

	// - G E T T E R S   &   S E T T E R S
	public Activity getContext() {
		return context;
	}

	public C getController() {
		return controller;
	}

	// - P R O T E C T E D   I N T E R F A C E


	private LayoutInflater getInflater() {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	private View inflateView(int layoutIdentifier) {
		return getInflater().inflate(layoutIdentifier, null);
	}


	protected Drawable getDrawable(final int resourceid) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return getContext().getResources().getDrawable(resourceid);
		} else return getContext().getResources().getDrawable(resourceid, getContext().getTheme());
	}

	protected void setPanelBorderColor(final Separator.ESeparatorType panelTheme) {
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			_convertView.setBackground(getContext().getResources().getDrawable(themeColor, getContext().getTheme()));
		} else _convertView.setBackground(getContext().getResources().getDrawable(themeColor));
	}

	// - I R E N D E R   I N T E R F A C E
	public abstract void initializeViews();

	public abstract void updateContent();

	public View getView() {
		return _convertView;
	}

	public abstract int accessLayoutReference();

	// -  B U I L D E R
	public static class Builder<C> {
		private C controller;
		private Activity context;

		public Builder() {
		}

		public Builder(final C controller, final Activity context) {
			this.controller = controller;
			this.context = context;
		}

		public Builder controller(final C controller) {
			this.controller = controller;
			return this;
		}

		public Builder activity(final Activity context) {
			this.context = context;
			return this;
		}
	}
}

// - UNUSED CODE ............................................................................................
