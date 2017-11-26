//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.android.mvc.part;

import android.util.Log;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.dimensinfin.android.model.Separator;
import org.dimensinfin.android.model.Separator.ESeparatorType;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;

// - CLASS IMPLEMENTATION ...................................................................................
public class DemoHeaderTitlePart<T> extends AbstractAndroidPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long	serialVersionUID	= -7103273035430243825L;

	// - F I E L D - S E C T I O N ............................................................................
	private int								priority					= 10;
	private int								iconReference			= R.drawable.defaultitemicon;
	private final String			renderModeName		= "-DEFAULT-RENDER-MODE-";

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public DemoHeaderTitlePart(final <T> node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public String get_counter() {
		return EveAbstractPart.qtyFormatter.format(this.getChildren().size());
	}

	public Separator getCastedModel() {
		return (Separator) this.getModel();
	}

	public int getChildrenCount() {
		return this.getChildren().size();
	}

	public int getIconReference() {
		return iconReference;
	}

	@Override
	public long getModelID() {
		return GregorianCalendar.getInstance().getTimeInMillis();
	}

	public String getTitle() {
		return this.getCastedModel().getTitle();
	}

	/**
	 * The default actions inside this method usually are the sorting of the children nodes. Sort the container
	 * contents by name.
	 */
	@Override
	public Vector<IPart> runPolicies(final Vector<IPart> targets) {
		// Order the contents by alphabetical name.
		Collections.sort(targets, NeoComApp.createPartComparator(AppWideConstants.comparators.COMPARATOR_NAME));
		return targets;
	}

	public GroupPart setIconReference(final int ref) {
		Log.i("REMOVE", "-- GroupPart.setIconReference - " + this.toString() + " change value to: " + ref);
		iconReference = ref;
		return this;
	}

	public EveAbstractPart setPriority(final int pri) {
		priority = pri;
		return this;
	}

	//	@Override
	//	public IPart setRenderMode(int renderMode) {
	//		if (null != renderMode) {
	//			renderModeName = renderMode;
	//		}
	//		return this;
	//	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("GroupPart [");
		buffer.append(this.getTitle()).append(" ");
		buffer.append(priority).append(" ");
		buffer.append("chCount: ").append(this.getChildren().size()).append(" ");
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	protected AbstractRender selectRenderer() {
		if (this.getRenderMode() == AppWideConstants.rendermodes.RENDER_GROUPMARKETSIDE)
			return new MarketSideRender(this, _activity);
		if (this.getRenderMode() == AppWideConstants.rendermodes.RENDER_GROUPJOBSTATE)
			return new JobStateRender(this, _activity);
		if (this.getRenderMode() == AppWideConstants.rendermodes.RENDER_GROUPSHIPFITTING)
			return new ShipSlotRender(this, _activity);
		if (this.getRenderModeName() == ESeparatorType.EMPTY_FITTINGLIST.name())
			return new EmptySeparatorBoardRender(this, _activity);
		return new IndustryGroupRender(this, _activity);
	}

	private String getRenderModeName() {
		return renderModeName;
	}
}

// - UNUSED CODE ............................................................................................
