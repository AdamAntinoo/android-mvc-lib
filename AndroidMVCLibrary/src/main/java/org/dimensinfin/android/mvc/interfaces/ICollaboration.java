package org.dimensinfin.android.mvc.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * This is the interface of nodes that are able to collaborate more items to a Model-View-Controller pattern. This kind
 * of implementation frees model nodes from the single parent-children hierarchy that was the common behavior for GEF
 * node trees. New MVC will convert graphs to trees and then to lists for model render on list views.
 */
public interface ICollaboration extends Serializable, Comparable {
	List<ICollaboration> collaborate2Model(String variation);
}
