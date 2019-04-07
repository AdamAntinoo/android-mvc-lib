package org.dimensinfin.android.mvc.core;

public interface IConverter<F, T> {
	T convert(F input);
}
