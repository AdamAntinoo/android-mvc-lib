//	PROJECT:        corebase.model (CORE.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.6.
//	DESCRIPTION:		Library that defines the model classes to implement the core for a GEF based
//									Model-View-Controller. Code is as neutral as possible and made to be reused
//									on all Java development projects.
//                  Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.android.mvc.domain;

import java.util.List;
import java.util.Vector;

import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IJsonAngular;

// - CLASS IMPLEMENTATION ...................................................................................
public class Separator implements ICollaboration, IJsonAngular {
	public enum ESeparatorType {
		DEFAULT, LINE_WHITE, LINE_RED, LINE_ROSE, LINE_ORANGE, LINE_YELLOW, LINE_GREEN, LINE_LIGHTBLUE, LINE_DARKBLUE,
		LINE_PURPLE, LINE_GREY, LINE_BLACK, EMPTY_SIGNAL
	}

	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -6265859021549690154L;

	// - F I E L D - S E C T I O N ............................................................................
	protected String jsonClass = "Separator";
	private String title = "-TITLE-";
	private ESeparatorType type = ESeparatorType.DEFAULT;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public Separator () {
		// This is an special case because the rendering should be a white line.
		this.type = ESeparatorType.DEFAULT;
		jsonClass = "Separator";
	}

	public Separator (final String title) {
		super();
		this.title = title;
	}

	//- M E T H O D - S E C T I O N ..........................................................................
	/**
	 * Separator has no constants but the Title. Not expandable and then has no needs for contents testing.
	 */
	public List<ICollaboration> collaborate2Model (final String variant) {
		return new Vector<ICollaboration>();
	}

	public String getJsonClass () {
		return jsonClass;
	}

	public String getTitle () {
		return this.title;
	}

	public ESeparatorType getType () {
		return this.type;
	}

	public void setTitle (final String title) {
		this.title = title;
	}

	public Separator setType (final ESeparatorType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("Separator [");
		buffer.append(this.title).append(" ");
		buffer.append(" ]");
		return buffer.toString();
	}
}

// - UNUSED CODE ............................................................................................
