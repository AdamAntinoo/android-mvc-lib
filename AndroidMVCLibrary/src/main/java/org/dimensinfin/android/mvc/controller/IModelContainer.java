package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.android.mvc.interfaces.IEventEmitter;

/**
 * @author Adam Antinoo
 */

public interface IModelContainer<M> extends IEventEmitter {
	M getModel();
}
