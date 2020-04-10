package org.dimensinfin.core.interfaces;

public interface Converter<F, T> {
	T convert(F input);
}
