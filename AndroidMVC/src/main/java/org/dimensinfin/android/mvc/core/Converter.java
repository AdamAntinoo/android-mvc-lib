package org.dimensinfin.android.mvc.core;

public interface Converter<F, T> {
	T convert(F input);
}
