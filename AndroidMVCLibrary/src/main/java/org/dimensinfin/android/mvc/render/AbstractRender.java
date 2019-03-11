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
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.Separator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class encapsulates the core definition for a component render. It is transparent to the Model type that is defined as a generic
 * type. Renders are tied to model types but can be used and connected to the models through multiple controllers.
 * @param <M> the model class type tht can be accessed through the controller.
 */
public abstract class AbstractRender<M extends ICollaboration> implements IRender {
	protected static Logger logger = LoggerFactory.getLogger(AbstractRender.class);

	// - F I E L D - S E C T I O N
	private AAndroidController<M> controller; // Holds the parent controller that is associated to this render. Used to access the model.
	private Context context; // Reference to the context. Usually the application singleton.
	private View convertView = null;

	// - L A Y O U T   F I E L D S
	// - C O N S T R U C T O R - S E C T I O N
	public AbstractRender(final AAndroidController<M> controller, final Context context) {
		this.controller = controller;
		this.context = context;
		this.createView(); // Inflate the layout to have the containers ready for identification.
		this.initializeViews(); // Connect the inflated fields to the render variables.
	}

	// - M E T H O D - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	public Context getContext() {
		return context;
	}

	public AAndroidController<M> getController() {
		return controller;
	}

	// - P R O T E C T E D   I N T E R F A C E
	/**
	 * The is the generic code all Renders can share. The only element missing and that should be provided by each Render
	 * implementation is the layout identifier to be used on the inflation. This is not a new method that is made abstract
	 * to force developers to fill the gap on ne instances.
	 */
	private void createView() {
		convertView = this.inflateView(this.accessLayoutReference());
		convertView.setTag(this);
	}

	private LayoutInflater getInflater() {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	private View inflateView(final int _layoutIdentifier) {
		return this.getInflater().inflate(_layoutIdentifier, null);
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
			convertView.setBackground(getContext().getResources().getDrawable(themeColor, getContext().getTheme()));
		} else convertView.setBackground(getContext().getResources().getDrawable(themeColor));
	}

	protected abstract void initializeViews();
	protected abstract int accessLayoutReference();

	// - I R E N D E R   I N T E R F A C E
	public View getView() {
		return convertView;
	}

	public abstract void updateContent();

//	// -  B U I L D E R
//	public abstract static class Builder<M extends ICollaboration> {
//		private AAndroidController<M> controller;
//		private Context context;
//
//		public Builder(final AAndroidController<M> controller, final Context context) {
//			this.controller = controller;
//			this.context = context;
//		}
//
//		public Builder controller(final AAndroidController<M> controller) {
//			this.controller = controller;
//			return this;
//		}
//
//		public Builder context(final Context context) {
//			this.context = context;
//			return this;
//		}
//		public abstract AbstractRender<M> build();
//	}
}
