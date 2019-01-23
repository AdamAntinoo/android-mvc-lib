//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.datasource;

import org.dimensinfin.android.mvc.core.BasePart;
import org.dimensinfin.android.mvc.core.RootPart;
import org.dimensinfin.android.mvc.interfaces.IDataSource;
import org.dimensinfin.android.mvc.interfaces.IPartFactory;
import org.dimensinfin.android.mvc.interfaces.IRootPart;
import org.dimensinfin.core.model.RootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class MVCRootPart<T> extends BasePart<T> implements IRootPart {
//	private static Logger logger = LoggerFactory.getLogger(MVCRootPart.class);

	// - F I E L D - S E C T I O N
	private IDataSource ds;

	// - C O N S T R U C T O R - S E C T I O N
//	public MVCRootPart(final RootNode node, final IPartFactory factory) {
//		super(node, factory);
//	}

	/**
	 * This constructor connect the root part to the DS and then top the other initialization elements that define the DS functionality
	 * but at a time that is not the creation time. Then the Factory and other data structures become available to the part hierarchy
	 * without affecting any other Part implementation.
	 * @param dataSource the parent and owner data source for this Part hierarchy.
	 */
	public MVCRootPart(final T model, final IDataSource dataSource) {
		super(model);
		this.ds = dataSource;
	}

	// - M E T H O D - S E C T I O N
	public IPartFactory getPartFacgtory(){
		return this.ds.getPartFactory();
	}
}
