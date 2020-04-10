//  PROJECT:        ProofAssesProcessing
//  FILE NAME:      $Id: ProcessorApp.java,v $
//  LAST UPDATE:    $Date: 2000/06/28 11:52:36 $
//  RELEASE:        $Revision: 1.4 $
//  AUTHORS:        Lima Delta Delta (LDD) - boneymen@netscape.com
//  COPYRIGHT:      (c) 2009 by LDD Game Development Spain, all rights reserved.

package org.dimensinfin.core.model;

// - IMPORT SECTION .........................................................................................
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

// - CLASS IMPLEMENTATION ...................................................................................
public class AbstractPropertyChanger implements Serializable {

	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -1694932541665379792L;
	private static Logger logger = Logger.getLogger("model");

	// - F I E L D - S E C T I O N ............................................................................
	@JsonIgnore
	private transient HashMap<PropertyChangeListener, PropertyChangeListener> _listeners = null;
	@JsonIgnore
	private transient AbstractPropertyChanger _parentChanger = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractPropertyChanger () {
	}

	@Deprecated
	public AbstractPropertyChanger (final AbstractPropertyChanger parent) {
		_parentChanger = parent;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public void addPropertyChangeListener (final PropertyChangeListener newListener) {
		if (null != newListener) getListeners().put(newListener, newListener);
	}

	public void firePropertyChange (final PropertyChangeEvent event) {
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
		if (!consumed) {
			// Now send the event to the parent to fire it also to its listeners.
			if (null != _parentChanger) _parentChanger.firePropertyChange(event);
		}
	}

	public void firePropertyChange (final String propertyName, final Object oldValue, final Object newValue) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		this.firePropertyChange(event);
	}

	public void fireStructureChange (PropertyChangeEvent event) {
		this.firePropertyChange(event);
	}

	public void fireStructureChange (final String propertyName, final Object oldState, final Object newState) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldState, newState);
		this.firePropertyChange(event);
	}

	public boolean hasListeners (final String propertyName) {
		if (getListeners().size() > 0)
			return true;
		else
			return false;
	}

	public void removePropertyChangeListener (final PropertyChangeListener listener) {
		if (null != listener) getListeners().remove(listener);
	}

	protected void setParentChanger (final AbstractPropertyChanger newParent) {
		_parentChanger = newParent;
	}

	private HashMap<PropertyChangeListener, PropertyChangeListener> getListeners () {
		if (null == _listeners) _listeners = new HashMap<PropertyChangeListener, PropertyChangeListener>();
		return _listeners;
	}
}

// - UNUSED CODE ............................................................................................
