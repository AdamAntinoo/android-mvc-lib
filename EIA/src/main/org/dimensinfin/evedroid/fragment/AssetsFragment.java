//	PROJECT:        EVEIndustrialist (EVEI)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2014 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API11.
//	DESCRIPTION:		Application helper for Eve Online Industrialists. Will help on Industry and Manufacture.

package org.dimensinfin.evedroid.fragment;

// - IMPORT SECTION .........................................................................................
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.dimensinfin.android.mvc.core.AbstractAndroidPart;
import org.dimensinfin.core.model.AbstractGEFNode;
import org.dimensinfin.evedroid.EVEDroidApp;
import org.dimensinfin.evedroid.R;
import org.dimensinfin.evedroid.connector.AppConnector;
import org.dimensinfin.evedroid.constant.AppWideConstants;
import org.dimensinfin.evedroid.core.EveAbstractPart;
import org.dimensinfin.evedroid.factory.AbstractIndustryDataSource;
import org.dimensinfin.evedroid.factory.AssetsMaterialsDataSource;
import org.dimensinfin.evedroid.factory.DataSourceFactory;
import org.dimensinfin.evedroid.fragment.core.AbstractPagerFragment;
import org.dimensinfin.evedroid.manager.AssetsManager;
import org.dimensinfin.evedroid.model.Asset;
import org.dimensinfin.evedroid.model.EveLocation;
import org.dimensinfin.evedroid.model.RegionGroup;
import org.dimensinfin.evedroid.model.Separator;
import org.dimensinfin.evedroid.part.AssetGroupPart;
import org.dimensinfin.evedroid.part.LocationAssetsPart;
import org.dimensinfin.evedroid.part.RegionPart;
import org.dimensinfin.evedroid.part.ShipPart;
import org.dimensinfin.evedroid.storage.AppModelStore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// - CLASS IMPLEMENTATION ...................................................................................
public class AssetsFragment extends AbstractPagerFragment {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private int	_filter	= AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		Log.i("NEOCOM", ">> AssetsFragment.onCreateView");
		final View theView = super.onCreateView(inflater, container, savedInstanceState);
		try {
			setIdentifier(_filter);
		} catch (final RuntimeException rtex) {
			Log.e("NEOCOM", "RTEX> AssetsFragment.onCreateView - " + rtex.getMessage());
			rtex.printStackTrace();
			stopActivity(new RuntimeException("RTEX> AssetsFragment.onCreateView - " + rtex.getMessage()));
		}
		Log.i("NEOCOM", "<< AssetsFragment.onCreateView");
		return theView;
	}

	@Override
	public void onStart() {
		Log.i("NEOCOM", ">> AssetsFragment.onStart");
		AppModelStore store = EVEDroidApp.getAppStore();
		// If the fragment is already initialized then skip this initialization
		if (!_alreadyInitialized) {
			try {
				if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION) {
					setDataSource(new AssetsByLocationDataSource(store));
				}
				if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSARESHIPS) {
					setDataSource(new AssetsShipsDataSource(store));
				}
				if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSMATERIALS) {
					setDataSource(new AssetsMaterialsDataSource(store));
				}
			} catch (final RuntimeException rtex) {
				Log.e("NEOCOM", "RTEX> AssetsFragment.onStart - " + rtex.getMessage());
				rtex.printStackTrace();
				stopActivity(new RuntimeException("RTEX> AssetsFragment.onStart - " + rtex.getMessage()));
			}
		}
		super.onStart();
		Log.i("NEOCOM", "<< AssetsFragment.onStart");
	}

	@Override
	public String getTitle() {
		return getPilotName();
	}

	@Override
	public String getSubtitle() {
		String st = "";
		if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION) {
			st = "ASSETS - By Location";
		}
		if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSARESHIPS) {
			st = "ASSETS - Ships";
		}
		if (_filter == AppWideConstants.fragment.FRAGMENT_ASSETSMATERIALS) {
			st = "ASSETS - Materials";
		}
		return st;
	}

	public AbstractPagerFragment setFilter(final int filter) {
		_filter = filter;
		return this;
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
/**
 * This DataSource will manage the elements to shown on the assets list for a character in the page that lists
 * those assets by Region - Location - Container order. If the number of locations is greater than a
 * predefined setting (that will be modifiable on the UI on the settings page) then the locations will be
 * grouped into Regions. If the number is lower the locations will be the first display level. <br>
 * The second and next levels will be composed with Containers, Ships and Assets. Those levels will only be
 * read from the database only if the container or location is expanded so this will use a lazy evaluation
 * pattern do read and generate the momory structures that contain the assets.<br>
 * To manage this asset information the DataSource will heavily interface with the model AssetsManager that
 * will have stored all the downloaded information (so a change on an Activity will not clear that data) and
 * all the functionalities to manage assets when they are stored on the database and also if they are stored
 * in local memory.
 * 
 * @author Adam Antinoo
 */
final class AssetsByLocationDataSource extends AbstractIndustryDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long														serialVersionUID	= -9118872719574627171L;

	// - F I E L D - S E C T I O N ............................................................................
	private boolean																			state							= false;
	private final ArrayList<AbstractGEFNode>						modelSource				= new ArrayList<AbstractGEFNode>();
	private final HashMap<String, AbstractAndroidPart>	regions						= new HashMap<String, AbstractAndroidPart>();
	private ArrayList<EveLocation>											locations					= null;

	private final HashMap<String, RegionGroup>					regionModel				= new HashMap<String, RegionGroup>();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AssetsByLocationDataSource(final AppModelStore store) {
		super(store);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	/**
	 * This method will initialize the Part hierarchy on the base root part element that will be accessed from
	 * the Adapter through the <code>IDataSource</code> interface when the Adapter is created. The data
	 * generated represents the model hierarchy as it should be represented on memory and the IDataSource call
	 * will instantiate that model to the UI rendering model on a determinate precise instant.<br>
	 * On this particular implementation we should instantiate the lazy parts for the locations from a database
	 * query to get all locations for the selected Pilot.<br>
	 * The new implementation will check the Settings to test if we should group the location into regions or
	 * not. By default we group them but for some small characters this will not be required. We can also make
	 * it automatic so over a predetermined number of locations it will group them or not. This can also be
	 * defined as a setting.
	 */
	@Override
	public void createContentHierarchy() {
		logger.info(">> AssetsByLocationDataSource.createHierarchy");
		// Clear the current list of elements.
		state = true;
		super.createContentHierarchy();
		createContentModel();
		try {
			// Get the list of Locations for this Pilot.
			final AssetsManager manager = DataSourceFactory.getPilot().getAssetsManager();

			// Get access to the location data for this character.
			locations = manager.getLocations();
			// Depending on the Setting group Locations into Regions
			if (groupLocations()) {
				for (final EveLocation location : locations) {
					final EveAbstractPart part = (EveAbstractPart) new LocationAssetsPart(location)
							.setRenderMode(AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION);
					final String regionName = location.getRegion();
					AbstractAndroidPart hitRegion = this.regions.get(regionName);
					if (null == hitRegion) {
						hitRegion = (AbstractAndroidPart) new RegionPart(new Separator(regionName))
								.setRenderMode(AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION);
						this.regions.put(regionName, hitRegion);
						hitRegion.addChild(part);
						this._root.add(hitRegion);
					} else {
						hitRegion.addChild(part);
					}
				}
			} else {
				// The number of locations is not enough to group them. Use the locations as the first level.
				for (final EveLocation location : locations) {
					this._root.add((AbstractAndroidPart) new LocationAssetsPart(location)
							.setRenderMode(AppWideConstants.fragment.FRAGMENT_ASSETSBYLOCATION));
				}
			}
		} catch (final RuntimeException rte) {
			rte.printStackTrace();
			logger.severe("E> There is a problem at: AssetsByLocationDataSource.createHierarchy.");
		}
		logger.info("<< AssetsByLocationDataSource.createHierarchy [" + this._root.size() + "]");
	}

	public void createContentModel() {
		Log.i("NEOCOM", ">> AssetsByLocationDataSource.createContentModel");
		// Clear the current list of elements.
		modelSource.clear();
		try {
			// Get the list of Locations for this Pilot.
			final AssetsManager manager = DataSourceFactory.getPilot().getAssetsManager();

			// Get access to the location data for this character.
			locations = manager.getLocations();
			// Depending on the Setting group Locations into Regions
			if (groupLocations()) {
				for (final EveLocation location : locations) {
					final String regionName = location.getRegion();
					RegionGroup hitRegion = this.regionModel.get(regionName);
					if (null == hitRegion) {
						hitRegion = new RegionGroup(regionName);
						this.regionModel.put(regionName, hitRegion);
						hitRegion.addChild(location);
						this.modelSource.add(hitRegion);
					} else {
						hitRegion.addChild(location);
					}
				}
			} else {
				// The number of locations is not enough to group them. Use the locations as the first level.
				for (final EveLocation location : locations) {
					this.modelSource.add(location);
				}
			}
		} catch (final RuntimeException rtex) {
			rtex.printStackTrace();
			Log.e("NEOCOM", "RTEX> AssetsByLocationDataSource.createContentModel - " + rtex.getMessage());
		}
		logger.info("<< AssetsByLocationDataSource.createContentModel [" + this.modelSource.size() + "]");
	}

	private boolean groupLocations() {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EVEDroidApp.getAppStore()
				.getActivity());
		final String locLimitString = prefs.getString(AppWideConstants.preference.PREF_LOCATIONSLIMIT,
				AppConnector.getResourceString(R.string.pref_numberOfLocations_default));
		// Check for the special value of unlimited.
		if (locLimitString.equalsIgnoreCase("Unlimited")) return false;
		// Convert the stored preference value to a number.
		int locLimit = 10;
		try {
			locLimit = Integer.parseInt(locLimitString);
		} catch (NumberFormatException nex) {
			locLimit = 10;
		}
		if (locations.size() > locLimit)
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<AbstractAndroidPart> getPartHierarchy() {
		logger.info(">> AssetsFragment.AssetsByLocationDataSource.getPartHierarchy");
		Collections.sort(this._root, EVEDroidApp.createComparator(AppWideConstants.comparators.COMPARATOR_NAME));
		//		ArrayList<AbstractAndroidPart> result = new ArrayList<AbstractAndroidPart>();
		//		for (AbstractAndroidPart node : _root) {
		//			// Check if the node is expanded. Then add its children.
		//			if (node.isExpanded()) {
		//				result.add(node);
		//				result.add(new TerminatorPart(new Separator("")));
		//				ArrayList<AbstractAndroidPart> grand = node.getPartChildren();
		//				// Order the list of blueprints by their profit
		//				Collections.sort(grand, EVEDroidApp.createComparator(AppWideConstants.comparators.COMPARATOR_NAME));
		//				result.addAll(grand);
		//				result.add(new TerminatorPart(new Separator("")));
		//			} else {
		//				result.add(node);
		//			}
		//		}
		//		_adapterData = result;
		logger.info("<< AssetsDirectorActivity.AssetsByLocationDataSource.getPartHierarchy");
		return super.getPartHierarchy();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer("AssetsFragment.AssetsByLocationDataSource [");
		//		buffer.append(getWeight()).append(" ");
		buffer.append("state:").append(state);
		buffer.append("count:").append(_root.size());
		buffer.append("model count:").append(modelSource.size());
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getPropertyName().equalsIgnoreCase(SystemWideConstants.events.EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE)) {
			fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES, event.getOldValue(),
					event.getNewValue());
		}
	}
}

//- CLASS IMPLEMENTATION ...................................................................................
final class AssetsShipsDataSource extends AbstractIndustryDataSource {
	// - S T A T I C - S E C T I O N ..........................................................................
	private static final long											serialVersionUID	= 7810087592108417570L;

	// - F I E L D - S E C T I O N ............................................................................
	private final HashMap<String, AssetGroupPart>	categories				= new HashMap<String, AssetGroupPart>();

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public AssetsShipsDataSource(final AppModelStore store) {
		super(store);
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	@Override
	public void createContentHierarchy() {
		logger.info(">> AssetsDirectorActivity.AssetsShipsDataSource.createHierarchy");
		// Clear the current list of elements.
		this._root.clear();

		// Get the list of Locations for this Pilot.
		try {
			final AssetsManager manager = DataSourceFactory.getPilot().getAssetsManager();
			// Depending on the Setting group Locations into Regions
			final ArrayList<Asset> assetsShips = manager.searchAsset4Category("Ship");
			for (final Asset asset : assetsShips) {
				// Check if there an entry for this asset name.
				//		CategoryGroupPart hit = categories.get(asset.getName());
				//					if (null == hit) {
				//						hit = new CategoryGroupPart(new Separator(asset.getName()));
				//						categories.put(asset.getName(), hit);
				this._root.add((AbstractAndroidPart) new ShipPart(asset)
						.setRenderMode(AppWideConstants.fragment.FRAGMENT_ASSETSARESHIPS));
				//					}
				//					hit.addChild(new Asset4CategoryPart(asset));
			}
		} catch (final RuntimeException sqle) {
			sqle.printStackTrace();
			logger.severe("E> There is a problem with the access to the Assets database when getting the Manager.");
		}
		logger.info("<< AssetsDirectorActivity.AssetsShipsDataSource.createHierarchy [" + this._root.size() + "]");
	}

	@Override
	public ArrayList<AbstractAndroidPart> getPartHierarchy() {
		logger.info(">> AssetsDirectorActivity.AssetsShipsDataSource.getPartHierarchy");
		Collections.sort(this._root, EVEDroidApp.createComparator(AppWideConstants.comparators.COMPARATOR_NAME));
		final ArrayList<AbstractAndroidPart> result = new ArrayList<AbstractAndroidPart>();
		for (final AbstractAndroidPart node : this._root) {
			result.add(node);
			// Check if the node is expanded. Then add its children.
			if (node.isExpanded()) {
				final ArrayList<AbstractAndroidPart> grand = node.getPartChildren();
				result.addAll(grand);
			}
		}
		this._adapterData = result;
		logger.info("<< AssetsDirectorActivity.AssetsShipsDataSource.getPartHierarchy");
		return result;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		// Intercept the object changing state and store a reference on a persistent map.
		if (event.getPropertyName().equalsIgnoreCase(SystemWideConstants.events.EVENTSTRUCTURE_ACTIONEXPANDCOLLAPSE)) {
			fireStructureChange(SystemWideConstants.events.EVENTADAPTER_REQUESTNOTIFYCHANGES, event.getOldValue(),
					event.getNewValue());
		}
	}
}

// - UNUSED CODE ............................................................................................
