package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.events.EventEmitter;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IEventEmitter;

import java.beans.PropertyChangeListener;

/**
 * @author Adam Antinoo
 */

public class GenericController<M> implements IModelContainer<M> {
	// - F I E L D - S E C T I O N
	/** Reference to the Model node. */
	private final M model; // Holds the model node.
	/** This field caches the factory that is set during the construction. */
	private final IControllerFactory factory;
	/** This is the delegate instance to be used to handle the events and the listener chain. */
	private IEventEmitter eventController = new EventEmitter();

	// - C O N S T R U C T O R - S E C T I O N
	public GenericController(final M model, final IControllerFactory factory) {
		this.model = model;
		this.factory = factory;
	}

	// - D E L E G A T E - I E V E N T E M I T T E R
	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		eventController.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		eventController.removePropertyChangeListener(listener);
	}

	@Override
	public boolean sendChangeEvent(final String eventName) {
		return eventController.sendChangeEvent(eventName);
	}

	// - C O M P A R A B L E   I N T E R F A C E
//	@Override
//	public int compareTo(@NonNull final M target) {
//		return this.getModel().compareTo(target)
//	}

	// - G E T T E R S   &   S E T T E R S
	@Override
	public M getModel() {
		return model;
	}

	/**
	 * The factory is set on all the Controllers during the creation time by the factory itself. This allows to construct
	 * any Model supported by the factory from any Controller created by that Factory.
	 */
	public IControllerFactory getControllerFactory() {
		return this.factory;
	}

	// - M E T H O D - S E C T I O N
}
