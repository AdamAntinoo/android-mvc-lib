package org.dimensinfin.android.mvc.interfaces;

import org.dimensinfin.android.mvc.core.AbstractAndroidController;

import java.beans.PropertyChangeListener;

/**
 * Describes the api for property and structural changes event emitters. Any change to the node data will trigger the emission
 * of a typed event that can be intercepted by the listeners connected to emitters.
 */
public interface IEventEmitter {
	void addPropertyChangeListener(PropertyChangeListener listener);
}
