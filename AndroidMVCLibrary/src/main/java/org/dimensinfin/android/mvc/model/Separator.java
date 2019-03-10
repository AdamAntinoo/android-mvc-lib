//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import java.util.Hashtable;
import java.util.Hashtable;
import java.util.Hashtable;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class Separator {
	private static Logger logger = LoggerFactory.getLogger(Separator.class);

	// - F I E L D - S E C T I O N

	// - C O N S T R U C T O R - S E C T I O N
	public Separator() {
		super();
	}

	// - M E T H O D - S E C T I O N
	@Override
	public String toString() {
		return new StringBuilder("Separator [")
				.append("name: ").append(0)
				.append("]")
				.append("->").append(super.toString())
				.toString();
	}
}
