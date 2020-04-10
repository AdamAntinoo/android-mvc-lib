package org.dimensinfin.core.domain;

import java.beans.PropertyChangeEvent;

public class IntercommunicationEvent extends PropertyChangeEvent {
	public IntercommunicationEvent( final Object source, final String eventName, final Object oldValue, final Object newValue ) {
		super(source, eventName, oldValue, newValue);
	}

	public String getEventName() {
		return this.getPropertyName();
	}
}
