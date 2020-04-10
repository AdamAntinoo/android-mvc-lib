package org.dimensinfin.android.mvc.datasource;

import android.os.Bundle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.factory.ControllerFactory;
import org.dimensinfin.android.mvc.support.Test4MVCDataSource;

public class MVCDataSourceTest {
	@Test
	void buildSuccess() {
		final Bundle extras = Mockito.mock( Bundle.class );
		final ControllerFactory factory = Mockito.mock( ControllerFactory.class );
		final String variant = "-TAG-";
		final MVCDataSource dataSource = new Test4MVCDataSource.Builder()
				                                 .addIdentifier( 123 )
				                                 .addIdentifier( 54321L )
				                                 .addIdentifier( "-TEST-IDENTIFIER-" )
				                                 .withExtras( extras )
				                                 .withFactory( factory )
				                                 .withVariant( variant )
				                                 .build();
		Assertions.assertNotNull( dataSource );
	}

	@Test
	void buildFailure() {
		// Given
		final Bundle extras = Mockito.mock( Bundle.class );
		final ControllerFactory factory = Mockito.mock( ControllerFactory.class );
		final String variant = "-TAG-";
		// Exceptions
		Assertions.assertThrows( NullPointerException.class, () -> {
			final MVCDataSource dataSource = new Test4MVCDataSource.Builder()
					                                 .addIdentifier( "-TEST-IDENTIFIER-" )
					                                 .withExtras( extras )
					                                 .withFactory( null )
					                                 .withVariant( variant )
					                                 .build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final MVCDataSource dataSource = new Test4MVCDataSource.Builder()
					                                 .addIdentifier( "-TEST-IDENTIFIER-" )
					                                 .withExtras( extras )
					                                 .withFactory( factory )
					                                 .withVariant( null )
					                                 .build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final MVCDataSource dataSource = new Test4MVCDataSource.Builder()
					                                 .addIdentifier( "-TEST-IDENTIFIER-" )
					                                 .withExtras( extras )
					                                 .withVariant( variant )
					                                 .build();
		} );
	}
}