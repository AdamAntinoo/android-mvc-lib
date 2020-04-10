package org.dimensinfin.core.interfaces;

import org.dimensinfin.core.domain.IntercommunicationEvent;

/**
 * Describes the api for property and structural changes event emitters. Any change to the node data will trigger the
 * emission of a typed event that can be intercepted by the listeners connected to emitters.
 */
public interface IEventEmitter {
	void addEventListener( final IEventReceiver listener );

	void removeEventListener( final IEventReceiver listener );

	boolean sendChangeEvent( final IntercommunicationEvent event );

	boolean sendChangeEvent( final String eventName );

	boolean sendChangeEvent( final String eventName, final Object origin );

	boolean sendChangeEvent( final String eventName, final Object origin, final Object oldValue, final Object newValue );
}
