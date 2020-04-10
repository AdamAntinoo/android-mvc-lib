//	PROJECT:     corebase.model (CORE.M)
//	AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:   (c) 2013-2017 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT: Java 1.6.
//	DESCRIPTION: Library that defines the model classes to implement the core for a GEF based
//               Model-View-Controller. Code is as neutral as possible and made to be reused
//               on all Java development projects.
//               Added more generic code to develop other Model-View-Controller patterns.
package org.dimensinfin.core.interfaces;

import java.beans.PropertyChangeListener;

/**
 * @author Adam Antinoo
 */

// - INTERFACE IMPLEMENTATION ...............................................................................
public interface IEventProjector {
	public void addPropertyChangeListener( final PropertyChangeListener newListener );

	public void removePropertyChangeListener( final PropertyChangeListener newListener );
}
