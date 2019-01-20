//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.core.model.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class MVCRootPart extends RootPart {
	private static Logger logger = LoggerFactory.getLogger(MVCRootPart.class);

	// - F I E L D - S E C T I O N
	private IDataSource ds;

	// - C O N S T R U C T O R - S E C T I O N
	public MVCRootPart(final RootNode node, final IPartFactory factory) {
		super(node, factory);
	}

	public MVCRootPart(final IDataSource dataSource) {
		this(null,null);
		this.ds = dataSource;
	}

	// - M E T H O D - S E C T I O N
}
