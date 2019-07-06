package org.dimensinfin.android.mvc.interfaces;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Describes the api for property and structural changes event emitters. Any change to the node data will trigger the
 * emission of a typed event that can be intercepted by the listeners connected to emitters.
 */
public interface IEventEmitter {
	void addPropertyChangeListener(final PropertyChangeListener listener);
	void removePropertyChangeListener(final PropertyChangeListener listener);
	boolean sendChangeEvent(final String eventName);
	boolean sendChangeEvent(final PropertyChangeEvent eventName);
//	void addEventListener( final IEventReceiver listener );
//
//	void removeEventListener( final IEventReceiver listener );
//
//	boolean sendChangeEvent( final String eventName );

//	boolean sendChangeEvent( final NeoComEvent eventName );
}
