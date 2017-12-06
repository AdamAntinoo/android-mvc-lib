//	PROJECT:        NeoCom.MVC (NEOC.MVC)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Library that defines a generic Model View Controller core classes to be used
//									on Android projects. Defines the Part factory and the Part core methods to manage
//									the extended GEF model into the Android View to be used on ListViews.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.interfaces.IPart;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.model.RootNode;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Logger;

// - CLASS IMPLEMENTATION ...................................................................................
public class RootPart extends AbstractPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -8085543451527813221L;
	private static Logger logger = Logger.getLogger("GenericConfigurableIconPart");

	// - F I E L D - S E C T I O N ............................................................................
	private boolean sort = false;
	private Comparator<? super IPart> partComparator = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public RootPart (final RootNode node, final IPartFactory factory) {
		super(node, factory);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public long getModelID () {
		return this.getModel().getClass().hashCode();
	}

	@Override
	public RootPart getRoot () {
		return this;
	}

	@Override
	public boolean isEmpty () {
		if ( getChildren().size() > 0 ) return false;
		else return true;
	}

	//	public void needsRedraw() {
	//	}

	/**
	 * Most of root parts should order the result by alphabetical name but this should be configurable. If the
	 * sort flag is active and the nodes are INamed then sort them.
	 */
	@Override
	public Vector<IPart> runPolicies (final Vector<IPart> targets) {
		if ( this.doSort() ) {
			Collections.sort(targets, partComparator);
		}
		return targets;
	}

	@Override
	public IPart setRenderMode (final String renderMode) {
		//		return this.setRenderMode(renderMode.hashCode());
		return this.setRenderMode(renderMode);
	}

	public RootPart setSorting (final Comparator<? super IPart> sortComparator) {
		if ( null != sortComparator ) {
			partComparator = sortComparator;
			sort = true;
		}
		return this;
	}

	private boolean doSort () {
		if ( sort )
			if ( null == partComparator )
				return false;
			else
				return sort;
		else
			return sort;
	}
}

// - UNUSED CODE ............................................................................................
