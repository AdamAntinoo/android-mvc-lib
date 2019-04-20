package org.dimensinfin.android.mvc.activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.ITitledFragment;

import androidx.fragment.app.Fragment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AMVCFragment extends Fragment implements ITitledFragment, IPagerFragment {
	protected static Logger logger = LoggerFactory.getLogger(AMVCFragment.class);
	/**
	 * Task handler to manage execution of code that should be done on the main loop thread.
	 */
	protected static final Handler handler = new Handler(Looper.getMainLooper());

	// - A P I   F I E L D S
	/**
	 * Factory that will generate the specific <b>Parts</b> for this Fragment/Activity/Application.
	 */
	protected IControllerFactory _factory = null;
	/**
	 * The variant is an optional field that should be set by the developer. Because it will choose not to fill it should
	 * have a valid default value.
	 */
	private String _variant = "-DEFAULT-";
	/**
	 * Copy of the extras bundle received by the Activity.
	 */
	private Bundle _extras = new Bundle();
	/**
	 * The library will require access to a valid application context at any time. Usually the activity is not connected
	 * to the Fragment until the fragment is going to be used and then the life cycle is started. But if the developed
	 * likes to use fragments not connected to real Activities we should be sure we can still have access to a valid
	 * context. We get a reference to the long term singleton for the Application context.
	 */
	private Context _appContext;

	// - G E T T E R S   &   S E T T E R S
	/**
	 * Sets the variant code to differentiate this instance form any other Fragment instances. This field should be set on
	 * the instantiation process of the Fragment and also should be recovered from persistence when the fragment is
	 * reconstructed.
	 *
	 * @return the variant name assigned to this fragment instance.
	 */
	public String getVariant() {
	    return _variant;
	}

	/**
	 * Sets the variant name. Variant names should come from a limited list of strings, usually implemented as an
	 * enumerated and that is set for each fragment instance that should have any differentiation. The value is restored
	 * on fragment reconstruction and can help to add specific and differential funtionalities.
	 *
	 * @param selectedVariant the new name to assign to this fragment instance.
	 * @return
	 */
	public AMVCFragment setVariant( final String selectedVariant) {
	    _variant = selectedVariant;
	    return this;
	}

	/**
	 * Gets a reference to the extras received by the Activity. They can come from the intent of from the persistence
	 * stored data upon Application reactivation.
	 *
	 * @return extras bundle.
	 */
	public Bundle getExtras() {
	    return _extras;
	}

	/**
	 * Sets the extras to be associated to the Fragment. This is usually automatically setup by the Activity but the
	 * developer can change the set of extras at any time. Contents are transparent to the library and are nor user by
	 * it.
	 *
	 * @param extras new bundle of extrax to be tied to this Fragment instance.
	 * @return this instance to allow for functional constructive statements.
	 */
	public AMVCFragment setExtras( final Bundle extras) {
	    _extras = extras;
	    return this;
	}

	public Context getAppContext() {
	    return this._appContext;
	}

	/**
	 * During initialization of the Fragment we install the long term singleton for the Application context This is done
	 * from the owner activity that connects the Fragment but also added when the fragment is reconnected..
	 *
	 * @param appContext the Application singleton context.
	 * @return this instance to allow for functional constructive statements.
	 */
	public AMVCFragment setAppContext( final Context appContext) {
	    this._appContext = appContext;
	    return this;
	}

	/**
	 * Returns the <b>ControllerFactory</b> associated with this Fragment instance. If the factory is still undefined then
	 * the method calls the creation method to get a fresh instance.
	 *
	 * @return
	 */
	public IControllerFactory getFactory() {
	    // Check if we have already a factory.
	    if (null == _factory) {
	        _factory = this.createFactory();
	    }
	    return _factory;
	}
	/**
	 * This method should be implemented by all the application Fragments to set the <b>ControllerFactory</b> that will be
	 * used during the model transformation processing to generate the <b>Parts</b> of the model to be used on this
	 * Fragment.
	 */
	public abstract IControllerFactory createFactory();

	// - I T I T L E D F R A G M E N T   I N T E R F A C E
	/**
	 * Gets the text to set set at the subtitle slot on the <b>ActionBar</b>. This should be implemented by each new
	 * Fragment.
	 *
	 * @return subtitle string.
	 */
	public abstract String getSubtitle();

	/**
	 * Gets the text to set set at the title slot on the <b>ActionBar</b>. This should be implemented by each new
	 * Fragment.
	 *
	 * @return title string.
	 */
	public abstract String getTitle();
}
