//  PROJECT:     ShopList.Android (SHOP.A)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Android application to collect the list of things to buy. It will store the item name,
//               the preferred place to buy it and the last payed price. There can be some lists by shop
//               or by location but all ot them share the same list of already registered items.
//               The application uses the MVC library and also the latest Android database development
//               called Room.
package org.dimensinfin.android.mvc.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.dimensinfin.android.mvc.R;

/**
 * @author Adam Antinoo
 */

// - CLASS IMPLEMENTATION ...................................................................................
public class AbstractLinearActivity extends AbstractPagerActivity {
	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	//	protected Bundle _extras = null;
	protected ViewGroup _fragmentContainer = null;
	protected FragmentDataContainer _linearAdapter = null;
	/** Image reference to the background layout item that can be replaced by the application implementation. */
	protected ImageView background = null;

	// - M E T H O D - S E C T I O N ..........................................................................
	public void addLinear( AbstractPagerFragment newFrag ) {
		logger.info(">> [AbstractLinearActivity.addLinear]");
		// Before checking if we have already this fragment we should get its unique identifier.
		Fragment frag = this.getFragmentManager().findFragmentByTag(_linearAdapter.getFragmentId(_linearAdapter.getNextPosition()));
		if ( null == frag ) {
			_linearAdapter.addLinear(newFrag);
			// Now add the configured fragment to the activity container layout.
			// Begin the transaction
			logger.info("-- [AbstractLinearActivity.addLinear]> Fragment: {}", newFrag.getClass().getSimpleName());
			FragmentTransaction ft = this.getFragmentManager().beginTransaction();
			// Add the fragment to the container with the new fragment.
			ft.add(R.id.fragmentContainer, newFrag);
			ft.commit();
		} else {
			if ( frag instanceof AbstractPagerFragment ) {
				// Reuse a previous created Fragment. But watch some of the fields have been removed.
				((AbstractPagerFragment) frag).setVariant(newFrag.getVariant());
				newFrag = (AbstractPagerFragment) frag;
			} else
				throw new RuntimeException("RTEX [AbstractLinearActivity.addLinear]> The fragment located does not inherit the required functionality. Does not extend AbstractPagerFragment.");
		}
		// Be sure the Fragment activity points to a valid activity.
		newFrag.setAppContext(this);
		// Copy the Activity extras to the Fragment. This avoids forgetting to set this by the developer.
		newFrag.setExtras(extras);
		logger.info("<< [AbstractLinearActivity.addLinear]");
	}

	// --- A C T I V I T Y   L I F E C Y C L E
	@Override
	public void onCreate( @Nullable final Bundle savedInstanceState ) {
		logger.info(">> [AbstractLinearActivity.onCreate]");
		super.onCreate(savedInstanceState);
		// Set the layout to the core activity that defines the background and the fragment container, this time one on top of another and
		// not in different pages.
		try {
			this.setContentView(R.layout.activity_linear);

			// Locate the elements of the page and store in global data.
			_fragmentContainer = (ViewGroup) this.findViewById(R.id.fragmentContainer);
			background = (ImageView) this.findViewById(R.id.backgroundFrame);
			//		_indicator = (CirclePageIndicator) this.findViewById(org.dimensinfin.android.mvc.R.id.indicator);
			// Check page structure.
			if ( null == _fragmentContainer ) {
				throw new RuntimeException("RTEX [AbstractLinearActivity.onCreate]> Expected UI element not found.");
			}
			if ( null == background ) {
				throw new RuntimeException("RTEX [AbstractLinearActivity.onCreate]> Expected UI element not found.");
			}

			// Add the adapter for the page switching.
			_linearAdapter = new FragmentDataContainer(_fragmentContainer.getId());
		} catch (RuntimeException rtex) {
			//			AndroidGlobalDataManager.registerRuntimeException("ALinearActivity.onCreate"
			//					, rtex, AndroidGlobalDataManager.EExceptionSeverity.UNEXPECTED);
			rtex.printStackTrace();
		}
		logger.info("<< [AbstractLinearActivity.onCreate]");
	}

	// - CLASS IMPLEMENTATION ...................................................................................
	public static class FragmentDataContainer {
		// - F I E L D - S E C T I O N ............................................................................
		private int _pagerid = R.id.fragmentContainer;
		protected List<Fragment> _fragments = new ArrayList();
		//		private Iterable<? extends Fragment> fragments;

		// - C O N S T R U C T O R - S E C T I O N ................................................................
		public FragmentDataContainer( final int layoutId ) {
			this._pagerid = layoutId;
		}

		// - M E T H O D - S E C T I O N ..........................................................................

		/**
		 * Return the fragment internal identifier generated by the FragmentPager. This is a composed string from
		 * some fields that have to be informed to this adapter.
		 * @param position the page position identifier we are going to use for this Fragment. This has not to match the real
		 *                 position of the instance on the Fragment internal list, it is only a numeric unique identifier.
		 * @return internal Fragment Manager fragment identifier string.
		 */
		public String getFragmentId( final int position ) {
			return "android:switcher:" + _pagerid + ":" + position;
		}

		public int getNextPosition() {
			return _fragments.size();
		}

		/**
		 * Add a new fragment at the end of the list of already managed fragments.
		 * @param fragNew new fragment to all to the list of pages.
		 */
		public void addLinear( final Fragment fragNew ) {
			if ( null != fragNew ) {
				_fragments.add(fragNew);
			}
		}

		public List<Fragment> getFragments() {
			return _fragments;
		}
	}
}
// - UNUSED CODE ............................................................................................
//[01]
