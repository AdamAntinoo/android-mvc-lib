package org.dimensinfin.android.mvc.events;

import org.dimensinfin.android.mvc.interfaces.IEventEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Adam Antinoo
 */

public class EventEmitter implements Serializable, IEventEmitter {
	private static final long serialVersionUID = -1694932541665379792L;
	private static Logger logger = LoggerFactory.getLogger(EventEmitter.class);

	// - F I E L D - S E C T I O N
	private transient HashMap<PropertyChangeListener, PropertyChangeListener> _listeners = null;

	// - C O N S T R U C T O R - S E C T I O N
	public EventEmitter() {
		super();
	}

	// - G E T T E R S   &   S E T T E R S
	// - M E T H O D - S E C T I O N
	protected boolean firePropertyChange(final PropertyChangeEvent event) {
		boolean consumed = false;
		// Get all the listeners and send them this change
		for (PropertyChangeListener listener : getListeners().values()) {
			logger.info("-- [AbstractPropertyChanger.firePropertyChange]-> Property: " + event.getPropertyName()
					+ " on object: " + this.getClass().getName());
			listener.propertyChange(event);
			consumed = true;
		}
		// If the event was consumed by any listener of this object then do not send it to the parent. This is the
		// root cause of event duplication when also the parent has listeners.
//		if (!consumed) {
//			// Now send the event to the parent to fire it also to its listeners.
//			if (null != _parentChanger) _parentChanger.firePropertyChange(event);
//		}
		return consumed;
	}

	private HashMap<PropertyChangeListener, PropertyChangeListener> getListeners() {
		if (null == _listeners) _listeners = new HashMap<PropertyChangeListener, PropertyChangeListener>();
		return _listeners;
	}

	// - I E V E N T E M I T T E R   I N T E R F A C E
	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (null != listener) getListeners().put(listener, listener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (null != listener) getListeners().remove(listener);
	}

	@Override
	public boolean sendChangeEvent(final String eventName) {
		return this.firePropertyChange(new PropertyChangeEvent(this, eventName, null, null));
	}
}
