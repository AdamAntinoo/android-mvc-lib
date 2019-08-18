package org.dimensinfin.android.mvc.controller;

import androidx.annotation.NonNull;

import org.dimensinfin.core.domain.EventEmitter;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IEventEmitter;
import org.dimensinfin.core.interfaces.IEventReceiver;

import java.util.Objects;

/**
 * Delegate to be used on the controllers to isolate from the model class to be used on the controller. With this
 * delegate we are able to isolate the model and be able to use inheritance for development of controllers.
 *
 * @author Adam Antinoo
 */

public class ControllerAdapter<M extends ICollaboration> implements IEventEmitter, Comparable<M> {
	// - F I E L D - S E C T I O N
	/** Reference to the Model node. */
	private final M model; // Holds the model node.
	/** This is the delegate instance to be used to handle the events and the listener chain. */
	private IEventEmitter eventController = new EventEmitter();

	// - C O N S T R U C T O R - S E C T I O N
	public ControllerAdapter( @NonNull final M model ) {
		Objects.requireNonNull(model);
		this.model = model;
	}

	// - D E L E G A T E - I E V E N T E M I T T E R

	@Override
	public void addEventListener( final IEventReceiver listener ) {this.eventController.addEventListener(listener);}

	@Override
	public void removeEventListener( final IEventReceiver listener ) {this.eventController.removeEventListener(listener);}

	@Override
	public boolean sendChangeEvent( final String eventName ) {return this.eventController.sendChangeEvent(eventName);}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin ) {
		return this.eventController.sendChangeEvent(eventName, origin);
	}

	@Override
	public boolean sendChangeEvent( final String eventName, final Object origin, final Object oldValue, final Object newValue ) {
		return this.eventController.sendChangeEvent(eventName, origin, oldValue, newValue);
	}

	// - C O M P A R A B L E   I N T E R F A C E
	@Override
	public int compareTo( @NonNull final M target ) {
		return this.getModel().compareTo(target);
	}

	// - G E T T E R S   &   S E T T E R S
//	@Override
	public M getModel() {
		return model;
	}
}
