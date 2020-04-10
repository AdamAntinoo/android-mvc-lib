//  PROJECT:        or.dimensinfin.core
//  AUTHORS:        Adam Antinoo - haddockgit@gmail.com
//  COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.

package org.dimensinfin.core.model;

import java.util.logging.Logger;

import org.dimensinfin.core.exceptions.ModelManagementException;
import org.dimensinfin.core.interfaces.IModelStore;
import org.dimensinfin.core.parser.IPersistentHandler;
import org.dimensinfin.gef.core.AbstractGEFNode;

//- IMPORT SECTION .........................................................................................

public abstract class AbstractModelStore extends AbstractGEFNode implements IModelStore {

	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -3441364184625849530L;
	protected static Logger logger = Logger.getLogger("AbstractModelStore");

	// - F I E L D - S E C T I O N ............................................................................
	//	/** Path to the persistent file that is the model repository. */
	//	private static String				persistentModelStoragePath	= "DefaultModelStore.xml";
	/** Reference to the class that will control the input/output of the model data structures. */
	protected transient IPersistentHandler persistentStorage = null;
	/** Stores a flag to signal if the model has changed with respect to the persistent stored data. */
	private boolean dirty = false;
	/**
	 * This field marks this store as automatic persistent update or not. By default the data is NOT
	 * automatically updated on the matching store persistent structures. If set to true any change on the model
	 * data should trigger an update on the persistent structures. This is performed by the <code>dirty</code>
	 * flag.
	 */
	private boolean automaticUpdate = false;
	/**
	 * Reference to the last exception occurred during processing. This allows graphical displays to show this
	 * message at a later moment.
	 */
	private Exception lastException;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AbstractModelStore () {
		super();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public boolean getDirty (final boolean dirtyState) {
		return dirty;
	}

	public IPersistentHandler getPersistentStorage () {
		return persistentStorage;
	}

	public boolean isAutomaticUpdate () {
		return automaticUpdate;
	}

	public boolean isDirty () {
		return dirty;
	}

	public boolean restore () {
		if (null != persistentStorage)
			synchronized (persistentStorage) {
				return persistentStorage.restore();
			}
		else
			return false;
	}

	public boolean save () {
		if (null != persistentStorage)
			synchronized (persistentStorage) {
				return persistentStorage.save();
			}
		else
			return false;
	}

	public void setAutomaticUpdate (final boolean automaticUpdate) {
		this.automaticUpdate = automaticUpdate;
	}

	@Override
	public synchronized void setDirty (final boolean dirtyState) {
		dirty = dirtyState;
		if ((dirty) && (automaticUpdate)) {
			// The automatic update signals that the persistent structures should be updated.
			if (null != persistentStorage) persistentStorage.save();
			dirty = false;
		}
	}

	/**
	 * Sets the InputHelper to the instance receives on the parameter. The state of the model is modified to the
	 * final state depending on the state of the InputHelper. The changes on the states has to be reviewed from
	 * the state model documented.
	 * 
	 * @throws Exception
	 *           Throws this exception in the case we receive an invalid handler.
	 */
	public void setPersistentStorage (final IPersistentHandler newPersistence) throws Exception {
		lastException = null;
		if (null != newPersistence) {
			persistentStorage = newPersistence;
		} else {
			// This is an exception because we cannot have default handlers.
			// TODO Add a default serialized object writer handlers as the default.
			lastException = new ModelManagementException("Received an invalid Persistenthandler: null");
			throw lastException;
		}
		persistentStorage.setStore(this);
	}

	@Override
	public String toString () {
		final StringBuffer buffer = new StringBuffer("AbstractModelStore [");
		if (null != persistentStorage) buffer.append("persistentHandler=").append(persistentStorage).append(" ");
		buffer.append("lastException=").append(lastException).append(" ");
		buffer.append("]\n");
		return buffer.toString();
	}

}
// - UNUSED CODE ............................................................................................
