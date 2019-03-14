//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
package org.dimensinfin.android.mvc.demo.support;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class PojoTestUtilsDemo {

	private static final Validator ACCESSOR_VALIDATOR = ValidatorBuilder.create()
			.with(new GetterTester())
			.with(new SetterTester())
			.build();

	public static void validateAccessors(final Class<?> clazz) {
		validate(clazz, ACCESSOR_VALIDATOR);
	}

	private static void validate(final Class<?> clazz, Validator validator) {
		validator.validate(PojoClassFactory.getPojoClass(clazz));
	}

}
