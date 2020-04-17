package org.dimensinfin.mvc.demo.acceptance.support.core;

import android.view.View;
import android.widget.TextView;

import java.util.Map;
import java.util.Objects;

import org.junit.Assert;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.acceptance.steps.SupportStepParent;

public class ValidationSupport extends SupportStepParent {

	public ValidationSupport( final MVCWorld world ) {
		super( world );
	}

	protected boolean validatePanelFieldContents( final Map<String, String> row, final View targetView,
	                                              final int fieldIdentifier, final String fieldName ) {
		final TextView viewField = Objects.requireNonNull( targetView.findViewById( fieldIdentifier ) );
		final String fieldValue = viewField.getText().toString();
		LogWrapper.info( "[THEN] {}: {}", fieldName, fieldValue );
		Assert.assertEquals( this.decodePredefinedValue( row.get( fieldName ) ), fieldValue );
		return this.decodePredefinedValue( row.get( fieldName ) ).equals( fieldValue );
	}

	protected boolean validatePanelFieldContentsNoCaps( final Map<String, String> row, final View targetView,
	                                                    final int fieldIdentifier, final String fieldName ) {
		final TextView viewField = Objects.requireNonNull( targetView.findViewById( fieldIdentifier ) );
		final String fieldValue = viewField.getText().toString();
		LogWrapper.info( "[THEN] {}: {}", fieldName, fieldValue );
		Assert.assertEquals( this.decodePredefinedValue( row.get( fieldName ) ).toUpperCase(), fieldValue.toUpperCase() );
		return this.decodePredefinedValue( row.get( fieldName ) ).toUpperCase().equals( fieldValue.toUpperCase() );
	}

	protected String decodePredefinedValue( final String value ) {
		Objects.requireNonNull( value );
		if (value.contains( "<" )) {
			switch (value) {
				case "<version>":
					return this.world.getActiveActivity().getResources().getString( R.string.appversion );
			}
		}
		return value;
	}
}
