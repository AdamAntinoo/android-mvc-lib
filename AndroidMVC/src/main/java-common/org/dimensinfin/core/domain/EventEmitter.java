package org.dimensinfin.core.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dimensinfin.core.interfaces.IEventEmitter;
import org.dimensinfin.core.interfaces.IEventReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements a helper instance that is able to encode and pass events to event listeners. This is the
 * recommended way to transmit events asynchronously between instances at runtime.
 *
 * @author Adam Antinoo
 */
public class EventEmitter implements Serializable, IEventEmitter {
	private static final long serialVersionUID = -1694932541665379792L;
	private static Logger logger = LoggerFactory.getLogger(EventEmitter.class);

	private transient Map<IEventReceiver, IEventReceiver> listeners;

	private boolean fireEvent( final IntercommunicationEvent event ) {
		boolean consumed = false;
		// Get all the listeners and send them this change
		for (IEventReceiver listener : this.getListeners().values()) {
			logger.info("-- [EventEmitter.fireEvent]-> Event name: {} on target: {}",
					event.getEventName(), this.getClass().getSimpleName());
			listener.receiveEvent(event);
			consumed = true;
		}
		return consumed;
	}

	private Map<IEventReceiver, IEventReceiver> getListeners() {
		if (null == this.listeners) this.listeners = new ConcurrentHashMap<>();
		return this.listeners;
	}


	// - I E V E N T E M I T T E R   I N T E R F A C E
	@Override
	public void addEventListener( final IEventReceiver listener ) {
		if (null != listener) this.getListeners().put(listener, listener);
	}

	@Override
	public void removeEventListener( final IEventReceiver listener ) {
		if (null != listener) this.getListeners().remove(listener);
	}

	@Override
	public boolean sendChangeEvent( final IntercommunicationEvent event ) {
		return this.fireEvent(event);
	}

	@Override
	public boolean sendChangeEvent( final String eventName ) {
		return this.fireEvent(new IntercommunicationEvent(this, eventName, null, null));
	}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin ) {
		return this.fireEvent(new IntercommunicationEvent(origin, eventName, origin, origin));
	}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin, final Object oldValue, final Object newValue ) {
		return this.fireEvent(new IntercommunicationEvent(this, eventName, oldValue, newValue));
	}
}
