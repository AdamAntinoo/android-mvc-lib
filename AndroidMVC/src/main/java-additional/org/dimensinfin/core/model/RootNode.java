//	PROJECT:        corebase.model (CORE.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.6.
//	DESCRIPTION:		Library that defines the model classes to implement the core for a GEF based
//									Model-View-Controller. Code is as neutral as possible and made to be reused
//									on all Java development projects.
//                  Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IExpandable;

import java.util.List;
import java.util.Vector;

// - CLASS IMPLEMENTATION ...................................................................................

/**
 * This is an special implementation of an Expandable Node that is used to contain the first and root element
 * of all Model graph. The <code>RootNode</code> will be expanded by default and will render to an special
 * <code>RootPart</code>. The contents are a list of children that are the first level of data of the model to
 * be used on the MVC.
 * 
 * @author Adam Antinoo
 */
public class RootNode implements IExpandable {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 297128593703416475L;

	// - F I E L D - S E C T I O N ............................................................................
	//	private boolean _expanded = false;
	@JsonIgnore
	private Vector<ICollaboration> children = new Vector<ICollaboration>();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public RootNode () {
		// Change the default expansion state to always expanded.
		expand();
	}

	public void addChild (final ICollaboration child) {
		if (null != child) {
			children.add(child);
		}
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void addChildren (final List<ICollaboration> modelList) {
		for (ICollaboration node : modelList)
			this.addChild(node);
	}

	public void clean () {
		getChildren().removeAllElements();
	}

	/**
	 * This special node collaborates with their children but not itself.
	 */
	public List<ICollaboration> collaborate2Model (final String variant) {
		final Vector<ICollaboration> results = new Vector<ICollaboration>();
		for (ICollaboration node : this.getChildren())
			results.add(node);
		return results;
	}

	public boolean collapse () {
		return true;
	}

	public boolean expand () {
		return true;
	}

	/**
	 * Check for pointer transient fields that can be loaded from object read operations.
	 */
	@JsonIgnore
	public Vector<ICollaboration> getChildren () {
		if (null == children) children = new Vector<ICollaboration>();
		return children;
	}

	public boolean isEmpty () {
		return false;
	}

	public boolean isExpanded () {
		return true;
	}

	public boolean isRenderWhenEmpty () {
		return true;
	}

	public IExpandable setRenderWhenEmpty (final boolean renderWhenEmpty) {
		return this;
	}

	@Override
	public String toString () {
		StringBuffer buffer = new StringBuffer("RootNode [");
		buffer.append("count: ").append(this.getChildren().size()).append("\t,");
		buffer.append("[").append(this.getChildren()).append("]");
		buffer.append(" ").append(super.toString()).append(" ]");
		return buffer.toString();
	}

}

// - UNUSED CODE ............................................................................................
