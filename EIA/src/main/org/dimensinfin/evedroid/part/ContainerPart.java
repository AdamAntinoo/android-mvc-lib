//	PROJECT:        EveIndustrialAssistant (EIA)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application helper for Eve Online Industrialists. Will help on Minery and mainly on Manufacture.

package org.dimensinfin.evedroid.part;

// - IMPORT SECTION .........................................................................................
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.android.mvc.core.AbstractHolder;
import org.dimensinfin.core.model.AbstractGEFNode;
import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.evedroid.EVEDroidApp;
import org.dimensinfin.evedroid.constant.AppWideConstants;
import org.dimensinfin.evedroid.holder.ContainerHolder;

import android.view.View;
import android.view.View.OnClickListener;

// - CLASS IMPLEMENTATION ...................................................................................
public class ContainerPart extends AssetPart implements OnClickListener {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long	serialVersionUID	= -2462731579059844711L;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public ContainerPart(final AbstractGEFNode node) {
		super(node);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Container may be user named so check if the user label is defined and then return that value.
	 */
	public String get_assetName() {
		String userName = getCastedModel().getUserLabel();
		if (null == userName)
			return "#" + getCastedModel().getAssetID();
		else
			return userName;
	}

	public String get_containerCategory() {
		return getCastedModel().getItem().getName();
	}

	public String get_contentCount() {
		return qtyFormatter.format(getChildren().size());
	}

	public String getName() {
		return get_assetName();
	}

	public ArrayList<AbstractAndroidPart> getPartChildren() {
		ArrayList<AbstractAndroidPart> result = new ArrayList<AbstractAndroidPart>();
		Vector<IGEFNode> ch = getChildren();
		Collections.sort(ch, EVEDroidApp.createComparator(AppWideConstants.comparators.COMPARATOR_NAME));

		for (IGEFNode node : ch) {
			// Convert the node to a part.
			AbstractAndroidPart part = (AbstractAndroidPart) node;
			result.add(part);
			// Check if the node is expanded. Then add its children.
			if (part.isExpanded()) {
				ArrayList<AbstractAndroidPart> grand = part.getPartChildren();
				result.addAll(grand);
			}
		}
		return result;
	}

	public int getTypeID() {
		return getCastedModel().getTypeID();
	}

	public void onClick(final View view) {
		// Toggle location to show its contents.
		toggleExpanded();
		fireStructureChange(SystemWideConstants.events.EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE, this, this);
	}

	public boolean onLongClick(final View target) {
		return false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("ContainerPart [");
		buffer.append(get_assetName());
		buffer.append(" ]");
		return buffer.toString();
	}

	protected AbstractHolder selectHolder() {
		// Get the proper holder from the render mode.
		if (getRenderMode() == AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION)
			return new ContainerHolder(this, _activity);
		return new ContainerHolder(this, _activity);
	}

}

// - UNUSED CODE ............................................................................................
