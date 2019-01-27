//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Adam Antinoo
 */

public class ClickSupporter {
	private static Logger logger = LoggerFactory.getLogger(ClickSupporter.class);

	public static ClickSupporter newSupporter(final boolean initialState) {
		return new ClickSupporter.Builder(initialState)
				.build();
	}

	// - F I E L D - S E C T I O N
	private boolean clickRunning = false;

	// - C O N S T R U C T O R - S E C T I O N
	private ClickSupporter(Builder builder) {
		clickRunning = builder.clickRunning;
	}

	// - M E T H O D - S E C T I O N
	public boolean isClickRunning() {
		return clickRunning;
	}

	public void setClickRunning(final boolean clickRunning) {
		this.clickRunning = clickRunning;
	}

	@Override
	public String toString() {
		return new StringBuilder("ClickSupporter [")
				.append("clickRunning: ").append(this.clickRunning)
				.append("]")
				.toString();
	}

	// -  B U I L D E R
	public static class Builder {
		// Required parameters
		private final boolean clickRunning;

		// Optional parameters - initialized to default values
		public Builder(final boolean clickRunning) {
			this.clickRunning = clickRunning;
		}

		public ClickSupporter build() {
			return new ClickSupporter(this);
		}
	}
}
