package org.dimensinfin.core.interfaces;

import java.io.Serializable;

/**
 * The interface defines a method to be used when nodes of this type get transferred to Angular projects when
 * they get serialized to Json strings. This class identification helps to reconstruct the right object class
 * at the client environment.
 */
public interface IJsonAngular extends Serializable {
    String getJsonClass();
}
