package org.dimensinfin.android.mvc.factory;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.dimensinfin.android.mvc.controller.ExceptionReportController;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.controller.ProgressSpinnerController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.domain.Spinner;
import org.dimensinfin.android.mvc.exception.ExceptionReport;
import org.dimensinfin.android.mvc.exception.MVCErrorInfo;
import org.dimensinfin.android.mvc.exception.MVCException;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.core.domain.Node;
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
		if (node instanceof Spinner) { // This is used solely during page creation to show the spinner while the model is calculated.
			return new ProgressSpinnerController( (Spinner) node, this );
		}

		// If no model class is trapped then result a NOT FOUND mark
		final String message = MessageFormat.format( "-No Model-Controller match-[{0}]-", node.getClass().getSimpleName() );
		return new SpacerController( new Spacer.Builder()
				                             .withLabel( message )
				                             .build(),
				this );
	}

	// - G E T T E R S   &   S E T T E R S
	@Override
	public String getVariant() {
		return variant;
	}

	@Override
	public IControllerFactory registerActivity( @NonNull final String activityCode, @NonNull final Class activityClass ) {
		activityRegistry.put( Objects.requireNonNull( activityCode ), Objects.requireNonNull( activityClass ) );
		return this;
	}

	@Override
	public Intent prepareActivity( @NonNull final String activityCode, @NonNull final Context context ) throws MVCException {
		if (null == activityCode) throw MVCErrorInfo.NULL_PARAMETER_EXCEPTION.generateException( "activityCode" );
		if (null == context) throw MVCErrorInfo.NULL_PARAMETER_EXCEPTION.generateException( "context" );
		// Check if target exists
		final Class target = activityRegistry.get( activityCode );
		if (null == target) throw MVCErrorInfo.UNREGISTERED_TARGET_ACTIVITY.generateException( activityCode );
		return new Intent( context, target );
	}

	public boolean isRegistered( final String activityCode ) {
		if (null != activityCode) return activityRegistry.containsKey( activityCode );
		else return false;
	}

	public void cleanRegistry() {
		activityRegistry.clear();
	}

}
