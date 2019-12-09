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
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.domain.SpacerType;

/**
 * This class encapsulates the core definition for a component render. It is transparent to the Model type that is
 * defined as a generic type. Renders are tied to model types but can be used and connected to the models through
 * multiple controllers.
 */
public abstract class MVCRender implements IRender {
	protected static final Handler handler = new Handler(Looper.getMainLooper());
	protected static Logger logger = LoggerFactory.getLogger(MVCRender.class);
	// - F I E L D - S E C T I O N
	private View convertView;
	private IAndroidController controller; // Holds the parent controller that is associated to this render. Used to access the model.
	private Context context; // Reference to the context. Usually the application singleton.

	// - L A Y O U T   F I E L D S
	// - C O N S T R U C T O R S
	public MVCRender( @NonNull final IAndroidController controller, @NonNull final Context context ) {
		Objects.requireNonNull(controller);
		Objects.requireNonNull(context);
		this.controller = controller;
		this.context = context;
	}

	// - G E T T E R S   &   S E T T E R S
	public Context getContext() {
		return context;
	}

	public IAndroidController getController() {
		return controller;
	}

	// - P R O T E C T E D   I N T E R F A C E

	/**
	 * The is the generic code all Renders can share. The only element missing and that should be provided by each Render
	 * implementation is the layout identifier to be used on the inflation. This is not a new method that is made abstract
	 * to force developers to fill the gap on ne instances.
	 */
	protected void createView() {
		this.convertView = this.inflateView(this.accessLayoutReference());
		// this.convertView.setTag(this);
	}

	protected void setNewView( final View newView ) {
		this.convertView = newView;
	}

	private LayoutInflater getInflater() {
		return (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	private View inflateView( final int _layoutIdentifier ) {
		return this.getInflater().inflate(_layoutIdentifier, null);
	}

	protected Drawable getDrawable( final int resourceId ) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return getContext().getResources().getDrawable(resourceId);
		} else return getContext().getResources().getDrawable(resourceId, getContext().getTheme());
	}

	protected void setPanelBorderColor( final SpacerType panelTheme ) {
		int themeColor;
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
			this.convertView.setBackground(getContext().getResources().getDrawable(themeColor, getContext().getTheme()));
		} else this.convertView.setBackground(getContext().getResources().getDrawable(themeColor));
	}

	// - I R E N D E R   I N T E R F A C E

	/**
	 * Complete reimplementation to create and instantiate the render layout and perform the field associations.
	 */
	public View getView() {
		if (null == this.convertView) {
			this.createView(); // Inflate the layout to have the containers ready for identification.
			this.initializeViews(); // Connect the inflated fields to the render variables.
		}
		return this.convertView;
	}
}
