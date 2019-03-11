//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */

public class Separator implements ICollaboration {
	private static Logger logger = LoggerFactory.getLogger(Separator.class);

	public enum ESeparatorType {
		DEFAULT, LINE_WHITE, LINE_RED, LINE_ORANGE, LINE_YELLOW, LINE_GREEN, LINE_DARKBLUE, EMPTY_SIGNAL,
		LINE_ROSE, LINE_LIGHTBLUE, LINE_PURPLE, LINE_GREY, LINE_BLACK
	}

	// - F I E L D - S E C T I O N
	private String title = "";
	private ESeparatorType type = ESeparatorType.DEFAULT;

	// - C O N S T R U C T O R - S E C T I O N
	public Separator() {
		super();
	}

	public Separator(final String title) {
		super();
		this.title = title;
	}

	// - G E T T E R S   &   S E T T E R S
	public String getTitle() {
		return title;
	}

	public ESeparatorType getType() {
		return type;
	}

	public Separator setType(final ESeparatorType type) {
		this.type = type;
		return this;
	}

	// - M E T H O D - S E C T I O N
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		final Separator separator = (Separator) o;

		return new EqualsBuilder()
				.append(title, separator.title)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(title)
				.toHashCode();
	}

	@Override
	public String toString() {
		return "Separator{" +
				"title='" + title + '\'' +
				'}';
	}

	@Override
	public List<ICollaboration> collaborate2Model(final String variation) {
		return new ArrayList<>();
	}
}
