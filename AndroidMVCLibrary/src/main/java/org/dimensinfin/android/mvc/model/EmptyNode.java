//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */

public class EmptyNode implements ICollaboration {
	private static Logger logger = LoggerFactory.getLogger(EmptyNode.class);

	// - F I E L D - S E C T I O N
	private final String name;

	// - C O N S T R U C T O R - S E C T I O N
	public EmptyNode(final String name) {
		super();
		this.name = name;
	}
	// - G E T T E R S   &   S E T T E R S

	public String getName() {
		return name;
	}

	// - M E T H O D - S E C T I O N
	@Override
	public String toString() {
		return new StringBuilder("EmptyNode [")
				.append("name: ").append(0)
				.append("]")
				.append("->").append(super.toString())
				.toString();
	}

	@Override
	public List<ICollaboration> collaborate2Model(final String variation) {
		return new ArrayList<>();
	}
}
