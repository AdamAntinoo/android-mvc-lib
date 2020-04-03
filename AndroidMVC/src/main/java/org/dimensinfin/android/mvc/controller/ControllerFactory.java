package org.dimensinfin.android.mvc.controller;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dimensinfin.android.mvc.domain.IControllerFactory;
import org.dimensinfin.android.mvc.domain.MVCNode;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.core.interfaces.ICollaboration;

public class ControllerFactory implements IControllerFactory {
	/**
	 * This field contains the list of pages that can be launched from a panel interaction. Each page destination should be registered on the
	 * factory before it can be accessed from the <code>onClick()</code> interaction to jump to another page.
	 * The list is shared by all the factories so a registration with the same page name will replace any previous registration.
	 */
	private static final Map<String, Class> activityRegistry = new HashMap<>();

	// - F I E L D - S E C T I O N
	private final String variant;

	// - C O N S T R U C T O R - S E C T I O N
	public ControllerFactory( final String selectedVariant ) {
		this.variant = Objects.requireNonNull( selectedVariant );
	}

	// - M E T H O D - S E C T I O N
	public IAndroidController createController( final ICollaboration node ) {
		// Associate the default classes defined at the MVC.
		if (node instanceof Spacer) {
			return new SpacerController( (Spacer) node, this )
					       .setRenderMode( this.getVariant() );
		}
		if (node instanceof ExceptionReport) {
			return new ExceptionReportController( (ExceptionReport) node, this );
		}
		if (node instanceof MVCNode) { // This is used solely during page creation to show the spinner while the model is calculated.
			return new ProgressSpinnerController( (MVCNode) node, this );
		}

		// If no model class is trapped then result a NOT FOUND mark
		final String message = MessageFormat.format( "-No Model-Controller match-[{0}]-", node.getClass().getSimpleName() );
		return new SpacerController( new Spacer.Builder()
				                             .withLabel( message )
				                             .build(),
				this );
	}

	public IControllerFactory registerActivity( @NonNull final String activityCode, @NonNull final Class activityClass ) {
		activityRegistry.put( Objects.requireNonNull( activityCode ), Objects.requireNonNull( activityClass ) );
		return this;
	}

	public boolean isRegistered( final String activityCode ) {
		if (null != activityCode) return activityRegistry.containsKey( activityCode );
		else return false;
	}

	public Intent prepareActivity( @NonNull final String activityCode, @NonNull final Context context ) {
		Objects.requireNonNull( activityCode );
		return new Intent( Objects.requireNonNull( context ), Objects.requireNonNull(activityRegistry.get( activityCode )) );
	}

	public void cleanRegistry() {
		activityRegistry.clear();
	}

	// - G E T T E R S   &   S E T T E R S
	public String getVariant() {
		return variant;
	}

}
