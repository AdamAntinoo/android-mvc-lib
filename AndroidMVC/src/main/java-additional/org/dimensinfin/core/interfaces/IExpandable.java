//	PROJECT:     corebase.model (CORE.M)
//	AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:   (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT: Java 1.6.
//	DESCRIPTION: Library that defines the model classes to implement the core for a GEF based
//               Model-View-Controller. Code is as neutral as possible and made to be reused
//               on all Java development projects.
//               Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.core.interfaces;

/**
 * This interface defines the nodes that can contain or reference other nodes. Node structure is not expected
 * to be hierarchical, this is a classic parent-children structure, but allows to use a graph. Nodes can
 * collaborate to the global model with other elements that interconnect the pieces in ways different from
 * parent-child relationships. Anyway of the structure it should have signals to tell model view constructors
 * about the expandability and availability of contents.
 */
// - INTERFACE IMPLEMENTATION ...............................................................................
public interface IExpandable extends ICollaboration {
	public boolean collapse ();

	public boolean expand ();

	public boolean isEmpty ();

	public boolean isExpanded ();

	public boolean isRenderWhenEmpty ();

	public IExpandable setRenderWhenEmpty (final boolean renderWhenEmpty);
}

// - UNUSED CODE ............................................................................................
