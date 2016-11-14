//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2015 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.evedroid.fragment;

// - IMPORT SECTION .........................................................................................
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.dimensinfin.android.mvc.core.AbstractCorePart;
import org.dimensinfin.android.mvc.core.AbstractHolder;
import org.dimensinfin.android.mvc.core.IEditPart;
import org.dimensinfin.android.mvc.core.IPartFactory;
import org.dimensinfin.core.model.AbstractComplexNode;
import org.dimensinfin.core.model.AbstractGEFNode;
import org.dimensinfin.core.model.IGEFNode;
import org.dimensinfin.evedroid.EVEDroidApp;
import org.dimensinfin.evedroid.activity.FittingActivity;
import org.dimensinfin.evedroid.constant.AppWideConstants;
import org.dimensinfin.evedroid.constant.AppWideConstants.EFragment;
import org.dimensinfin.evedroid.core.EveAbstractPart;
import org.dimensinfin.evedroid.datasource.DataSourceLocator;
import org.dimensinfin.evedroid.datasource.SpecialDataSource;
import org.dimensinfin.evedroid.factory.PartFactory;
import org.dimensinfin.evedroid.fragment.core.AbstractNewPagerFragment;
import org.dimensinfin.evedroid.model.APIKey;
import org.dimensinfin.evedroid.model.EveChar;
import org.dimensinfin.evedroid.model.EveItem;
import org.dimensinfin.evedroid.part.APIKeyPart;
import org.dimensinfin.evedroid.part.PilotInfoPart;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

// - CLASS IMPLEMENTATION ...................................................................................
/**
 * This is a test implementation that will run a testing configuration. The sources for fittings maybe already
 * fitted ships or XML fitting configuration files but there is no code now to import from such sources. <br>
 * <br>
 * Fragment implementation that will get some input form the user to select a fitting and a count of copies to
 * calculate the item requirements to cover that request. By default fittings are matched against the GARAGE
 * function Location. The GARAGE function may not be unique. If that case the matching should be against each
 * of the GARAGE locations.
 * 
 * @author Adam Antinoo
 */
public class FittingFragment extends AbstractNewPagerFragment {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static Logger logger = Logger.getLogger("FittingFragment");

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * This code is identical on all Fragment implementations so can be moved to the super class.
	 */
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		Log.i("NEOCOM", ">> FittingFragment.onCreateView");
		final View theView = super.onCreateView(inflater, container, savedInstanceState);
		try {
			setIdentifier(_variant.hashCode());
			registerDataSource();
		} catch (final RuntimeException rtex) {
			Log.e("EVEI", "RTEX> FittingFragment.onCreateView - " + rtex.getMessage());
			rtex.printStackTrace();
			stopActivity(new RuntimeException("RTEX> FittingFragment.onCreateView - " + rtex.getMessage()));
		}
		Log.i("NEOCOM", "<< FittingFragment.onCreateView");
		return theView;
	}

	@Override
	public String getTitle() {
		return "Fitting - Under Test";
	}

	@Override
	public String getSubtitle() {
		return "";
	}

	/**
	 * This code is identical on all Fragment implementations so can be moved to the super class.
	 */
	@Override
	public void onStart() {
		Log.i("NEOCOM", ">> FittingFragment.onStart");
		try {
			// Check the datasource status and create a new one if still does not exists.
			if (checkDSState()) {
				registerDataSource();
			}
		} catch (final RuntimeException rtex) {
			Log.e("EVEI", "RTEX> FittingFragment.onCreateView - " + rtex.getMessage());
			rtex.printStackTrace();
			stopActivity(new RuntimeException("RTEX> FittingFragment.onCreateView - " + rtex.getMessage()));
		}
		super.onStart();
		Log.i("NEOCOM", "<< FittingFragment.onStart");
	}

	/**
	 * This is the single piece f code specific for this fragment. It should create the right class DataSource
	 * and connect it to the Fragment for their initialization during the <b>start</b> phase. <br>
	 * Current implementation is a test code to initialize the DataSorue with a predefined and testing fitting.
	 */
	private void registerDataSource() {
		Log.i("NEOCOM", ">> FittingFragment.registerDataSource");
		long capsuleerid = 100;
		String fittingid = "Purifier";
		DataSourceLocator locator = new DataSourceLocator().addIdentifier(_variant.name());
		// Register the datasource. If this same datasource is already at the manager we get it instead creating a new one.
		SpecialDataSource ds = new FittingDataSource(locator, new FittingPartFactory(_variant));
		ds.setVariant(_variant);
		// ds.setExtras(getExtras();
		ds.addParameter(AppWideConstants.EExtras.CAPSULEERID.name(), capsuleerid);
		ds.addParameter(AppWideConstants.EExtras.FITTINGID.name(), fittingid);
		ds = (SpecialDataSource) EVEDroidApp.getAppStore().getDataSourceConector().registerDataSource(ds);
		setDataSource(ds);
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class FittingPartFactory extends PartFactory implements IPartFactory {
	// - S T A T I C - S E C T I O N ..........................................................................
	// - F I E L D - S E C T I O N ............................................................................
	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public FittingPartFactory(final EFragment _variant) {
		super(_variant);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * The method should create the matching part for the model received but there is no other place where we
	 * should create the next levels of the hierarchy. So we will create the part trasnformationes here.
	 */
	@Override
	public IEditPart createPart(final IGEFNode node) {
		if (node instanceof APIKey) {
			AbstractCorePart part = new APIKeyPart((AbstractComplexNode) node).setFactory(this);
			return part;
		}
		if (node instanceof EveChar) {
			AbstractCorePart part = new PilotInfoPart((AbstractComplexNode) node).setFactory(this);
			return part;
		}
		return null;
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class FittingHeaderHolder extends AbstractHolder {
	// - S T A T I C - S E C T I O N ..........................................................................
	// - F I E L D - S E C T I O N ............................................................................
	//	private ITheme	_theme					= null;

	private Spinner fittingsSpinner = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public FittingHeaderHolder(final FittingHeaderPart target, final Activity context) {
		super(target, context);
		//		_theme = new RubiconRedTheme(context);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public FittingHeaderPart getPart() {
		return (FittingHeaderPart) super.getPart();
	}

	@Override
	public void initializeViews() {
		super.initializeViews();
		// Create spinner contents from part.
		fittingsSpinner = (Spinner) _convertView.findViewById(R.id.fittingspinner);
		if (null != fittingsSpinner) {
			fittingsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(final AdapterView<?> parentView, final View selectedItemView, final int position,
						final long id) {
					Object item = fittingsSpinner.getSelectedItem();
					if (item instanceof String) {
						String itemName = (String) item;
						Toast toast = Toast.makeText(getPart().getActivity(), "Item selected: " + itemName, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
						toast.show();
					}
				}

				public void onNothingSelected(final AdapterView<?> parentView) {
					// your code here
				}

			});
			List<String> ships = getPart().getFittings();
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, ships);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			fittingsSpinner.setAdapter(dataAdapter);
		}
	}

	public void setView(final View newView) {
		_convertView = newView;
	}

	@Override
	public void updateContent() {
		super.updateContent();
	}

	@Override
	protected void createView() {
		final LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		_convertView = mInflater.inflate(R.layout.fitting_4header, null);
		_convertView.setTag(this);
	}

	protected void loadEveIcon(final ImageView targetIcon, final int typeID) {
		if (null != targetIcon) {
			final String link = EVEDroidApp.getTheCacheConnector().getURLForItem(typeID);
			final Drawable draw = EVEDroidApp.getTheCacheConnector().getCacheDrawable(link, targetIcon);
			targetIcon.setImageDrawable(draw);
		}
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class FittingHeaderPart extends EveAbstractPart {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long serialVersionUID = -4642153502498052929L;

	// - F I E L D - S E C T I O N ............................................................................

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public FittingHeaderPart(final AbstractGEFNode item) {
		super(item);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public EveItem getCastedModel() {
		return (EveItem) getModel();
	}

	public List<String> getFittings() {
		List<String> fittings = new ArrayList<String>();

		// Get a reference to the special DataSource.
		if (getActivity() instanceof FittingActivity) {
			FittingActivity act = (FittingActivity) getActivity();
			FittingsDataSource ds = (FittingsDataSource) act.getDataSource(AppWideConstants.fragment.FRAGMENT_FITTINGS);
			fittings.addAll(ds.getFittings());
		}
		return fittings;
	}

	@Override
	public long getModelID() {
		return 0;
	}

	@Override
	protected AbstractHolder selectHolder() {
		// Get the proper holder from the render mode.
		return new FittingHeaderHolder(this, _activity);
	}
}

// - UNUSED CODE ............................................................................................
