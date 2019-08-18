package org.dimensinfin.android.mvc.controller;

import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IEventEmitter;

public interface IModelContainer<M extends ICollaboration> extends IEventEmitter {
	M getModel();
}
