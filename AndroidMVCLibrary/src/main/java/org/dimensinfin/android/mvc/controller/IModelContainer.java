package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.interfaces.IEventEmitter;
import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * @author Adam Antinoo
 */

public interface IModelContainer<M extends ICollaboration> extends IEventEmitter {
	M getModel();
}
