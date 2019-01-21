//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.interfaces.IPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Vector;

/**
 * @author Adam Antinoo
 */

public class BasePart<T> {
	private static Logger logger = LoggerFactory.getLogger(BasePart.class);

	// - F I E L D - S E C T I O N
	/** List of children of the hierarchy. */
	private List<IPart> children = new Vector<>();
	/** The parent element on the hierarchy chain. Null if this is the Parent and we are a root node. */
	private IPart parent;
	/** Reference to the Model node. */
	private T model;

	// - C O N S T R U C T O R - S E C T I O N
	public BasePart(final T model) {
//		super();
		if ( null== model)throw new MVCException(ErrorInfo)
		this.model = model;
	}

	// - M E T H O D - S E C T I O N

	/**
	 * Used to detect if the node is the root of a hierarchy because there is no more parent upwards.
	 * @return true if the parent is null and then I am the root.
	 */
	public boolean isRoot() {
		return (null == this.parent) ? true : false;
	}

	public List<IPart> getChildren() {
		if (children == null) return new Vector<IPart>(2);
		return children;
	}

	public IPart getParentPart() {
		return parent;
	}

	public T getModel() {
		return model;
	}

//	/**
//	 * This can lead to invalid code because the root part can be any part just without a parent. So more tests should be
//	 * run to check that we are not returning an invalid conversion. See the instanceOf.
//	 * @return the root part of the hierarchy.
//	 */
//	public IPart getRoot() {
//		if (null == this.getParentPart()) return (IPart) this;
//		return this.getParentPart().getRoot();
//	}

	/**
	 * Set the primary model object that this EditPart represents. This method is used by an
	 * <code>EditPartFactory</code> when creating an EditPart.
	 */
	public void setModel(final T model) {
		this.model = model;
	}

	/**
	 * Sets the parent EditPart. There is no reason to override this method.
	 */
	public void setParent(final IPart parent) {
		this.parent = parent;
	}

	/**
	 * Parts are special elements. The root element that is a AbstractPropertyChanger is not responsible to store the
	 * model but needs it as reference to set a parent for notifications. So do not forget to pass the reference up and
	 * store the model at the same time.
	 * @param model the Data model linked to this part.
	 */
//	public AbstractPart(final RootNode model, final IPartFactory factory) {
//		this(model);
//		//		this.model = model;
//		_factory = factory;
//		//		setParentChanger(this);
//	}

	// - M E T H O D - S E C T I O N

	public void addChild(final IPart child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return new StringBuilder("BasePart [")
				.append("content count: ").append(this.children.size())
				.append("hasParent: ").append(this.isRoot())
				.append("[model-> ").append(this.getModel().toString())
				.append("] ]")
//				.append("->").append(super.toString())
				.toString();
	}
}
