//	PROJECT:        corebase.model (CORE.M)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Java 1.6.
//	DESCRIPTION:		Library that defines the model classes to implement the core for a GEF based
//									Model-View-Controller. Code is as neutral as possible and made to be reused
//									on all Java development projects.
package org.dimensinfin.core.util;

// - CLASS IMPLEMENTATION ...................................................................................
public abstract class OneParameterTask<T> implements Runnable {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = 7601587036153405892L;

	// - F I E L D - S E C T I O N ............................................................................
	private T target = null;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public OneParameterTask (T source) {
		target = source;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public abstract void run ();

	public T getTarget () {
		return target;
	}
}

// - UNUSED CODE ............................................................................................
