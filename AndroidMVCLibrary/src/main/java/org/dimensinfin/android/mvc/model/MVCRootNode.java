//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.model;

import lombok.EqualsAndHashCode;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Vector;

/**
 * This is an special node that is set on the root of a graph or hierarchy. It will collaborate its contents that are
 * being managed by the data source itself. So when the data source wants to show something it adds an element to this
 * root node. The root node does not set any filtering on its contents and will be neutral about the type of its
 * children.
 * @author Adam Antinoo
 */
@EqualsAndHashCode
public class MVCRootNode implements ICollaboration {
	private static final long serialVersionUID = 297128593703416475L;
	private static Logger logger = LoggerFactory.getLogger(MVCRootNode.class);

	// - F I E L D - S E C T I O N
	private List<ICollaboration> children = new Vector<ICollaboration>();

	// - C O N S T R U C T O R - S E C T I O N
	public MVCRootNode() {
		super();
	}

	// - I C O L L A B O R A T I O N   I N T E R F A C E
	/**
	 * This special node collaborates with their children but not itself.
	 */
	public List<ICollaboration> collaborate2Model(final String variant) {
		final Vector<ICollaboration> results = new Vector<ICollaboration>();
		for (ICollaboration node : this.getChildren())
			results.add(node);
		return results;
	}

	// - G E T T E R S   &   S E T T E R S

	public List<ICollaboration> getChildren() {
		return children;
	}

	// - M E T H O D - S E C T I O N
	public void addChild(final ICollaboration child) {
		if (null != child) {
			children.add(child);
		}
	}
	public void addChildren(final List<ICollaboration> modelList) {
		for (ICollaboration node : modelList)
			this.addChild(node);
	}
	public void clean() {
		this.getChildren().clear();
	}

	@Override
	public String toString() {
		return new StringBuilder("MVCRootNode [")
				.append("count: ").append(this.getChildren().size()).append(" ")
				.append(" ]")
				.toString();
	}

}
