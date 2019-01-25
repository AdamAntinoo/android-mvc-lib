//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the AndroidController factory and the AndroidController core methods to manage
//               a generic converter from a Graph Model to a hierarchical AndroidController model that finally will
//               be converted to a AndroidController list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.controller;

import lombok.Builder;
import org.dimensinfin.android.mvc.interfaces.IAndroidController;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Vector;

/**
 * This class will implement the core Android interaction controller on the classic pattern Model-View-Controller special and
 * generic implementation into a library for Android native projects. This controller pattern will be the intermediate connection
 * nodes between the free graph composition of the Model and the final Visual Controller list that will connect the Model and the Render inside the
 * View containers used in the Android interface.
 *
 * On version 4 I have replaced the old GEF AndroidController concept by the traditional Controller and started to add unit test code and replaced old
 * style java code coding by the new advanced code techniques and refactorings.
 *
 * @since 4.0.0
 * @author Adam Antinoo
 */

@Builder
public abstract class AAndroidController<T extends ICollaboration,Z extends IRender> {
	/** This is the public logger that should be used by all the Controllers. */
	public static Logger logger = LoggerFactory.getLogger(AAndroidController.class);

	// - F I E L D - S E C T I O N
	/** List of children of the hierarchy. */
	private List<IAndroidController> children = new Vector<>();
//	/** The parent element on the hierarchy chain. Null only if tis is the root of the hierarchy. */
//	private IAndroidController parent;
////	/** The root part of the hierarchy. Calculated when the parent is set. If null the parentship has not created. */
//	private IRootPart root;
	/** Reference to the Model node. */
	/** This field caches the factory that is set during the construction. */
	private IControllerFactory factory = null;
	private T model; // Holds the model node.
	private Z render; // Holds the main render for the visible component of the model.

	// - C O N S T R U C T O R - S E C T I O N
	// - G E T T E R S   &   S E T T E R S
	/**
	 * The factory is set on all the Parts during the creation time by the factory itself. This allows to construct any
	 * Model supported by the factory from any AndroidController created by that Factory.
	 */
	public IControllerFactory getControllerFactory() {
		return this.factory;
	}

	// - M E T H O D - S E C T I O N
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AAndroidController)) return false;
		AAndroidController that = (AAndroidController) o;
		return new EqualsBuilder()
//				.append(this.root, that.root)
//				.append(this.parent, that.parent)
				.append(this.model, that.model)
				.append(this.render, that.render)
				.append(this.children, that.children)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 41)
//				.append(this.root)
				.append(this.model)
				.append(this.render)
				.append(this.children)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder("AAndroidController [")
				.append("content count: ").append(this.getChildren().size())
				.append("[ model-> ").append(this.getModel().toString())
				.append("] [ render.type -> ").append(this.getRender().toString())
				.append("] ]")
				.toString();
	}
}
