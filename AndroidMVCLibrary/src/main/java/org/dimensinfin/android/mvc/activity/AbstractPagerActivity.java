//  PROJECT:     Android.MVC (A.MVC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Android API16.
//  DESCRIPTION: Library that defines a generic Model View Controller core classes to be used
//               on Android projects. Defines the Part factory and the Part core methods to manage
//               a generic converter from a Graph Model to a hierarchical Part model that finally will
//               be converted to a Part list to be used on a BaseAdapter tied to a ListView.
//               The new implementation performs the model to list transformation on the fly each time
//               a model change is detected so the population of the displayed view should be done in
//               real time while processing the model sources. This should allow for search and filtering.
package org.dimensinfin.android.mvc.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.AbstractFragmentPagerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class extends the bare android Activity. Defines the ActionBar and instantiates the layout. The generic layout
 * for any of the MVC activities contains 3 key elements. A <b>Background</b> image container that defines the full
 * background of the activity, the <b>ActionBar</b> that is defined on the applications styles and that it is made
 * visible by default and the <b>ViewPager</b> container that will hold the fragments. This allows for a generic
 * activity that will be able to contain different pages and with the feature to allow to swipe to them without changing
 * the activity. The different pages will be shown by a circle page indicator if more that one page is present. Only two
 * of them are accessible to other implementations, the Background and the ActionBar.
 * <p>
 * So at the creation step we only should have to generate the Fragments and add them to the pager. This is the
 * functionality for the <code>{@link addPage(ImageView)}</code> public method. Fragments share some characteristics to
 * use the possibilities offered by the ActionBar like the <b>Title</b> and the <b>SubTitle</b>.
 * @author Adam Antinoo
 * @since 1.0.0
 */
// - CLASS IMPLEMENTATION ...................................................................................
public abstract class AbstractPagerActivity extends Activity {
	public enum EExtrasMVC {
		EXTRA_EXCEPTIONMESSAGE, EXTRA_VARIANT
	}

	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = LoggerFactory.getLogger("AbstractPagerActivity");

	// - F I E L D - S E C T I O N ............................................................................
	protected Bundle extras = null;
	protected ActionBar _actionBar = null;
	protected ViewPager _pageContainer = null;
	protected AbstractFragmentPagerAdapter _pageAdapter = null;
	/** Image reference to the background layout item that can be replaced by the application implementation. */
	protected ImageView background = null;
	protected CirclePageIndicator _indicator = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	// - M E T H O D - S E C T I O N ..........................................................................
	public Activity getActivity() {
		return this;
	}

	/**
	 * Allows to change the activity background that covers the full size of the display
	 * @param newbackground the new background image.
	 */
	public void setBackground( final ImageView newbackground ) {
		this.background = newbackground;
	}

	/**
	 * Adds a new <code>Fragment</code> to the list of fragments managed by the pager. The new fragment is added at the
	 * last position of the list of fragments, positions are then relative and not set by the developer.
	 * @param newFrag the new fragment to add to the pager. When the application is brought back to life we can have
	 *                already the fragments created and initialized at the <code>FragmentManager</code>. In such a case we
	 *                discard the new received fragment and use the already instance at the <code>FragmentManager</code>.
	 */
	public void addPage( @NonNull AbstractPagerFragment newFrag ) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.addPage]");
		// Before checking if we have already this fragment we should get its unique identifier.
		Fragment frag = this.getFragmentManager().findFragmentByTag(_pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
		if ( null == frag ) {
			_pageAdapter.addPage(newFrag);
		} else {
			// We need to update the fragment cached on the Fragment Manager
			if ( frag instanceof AbstractPagerFragment ) {
				AbstractPagerActivity.logger.info("-- [AbstractPagerActivity.addPage]> Reusing available fragment. {}"
						,_pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
				// Reuse a previous created Fragment. Copy all fields accesible.
				((AbstractPagerFragment) frag)
						.setVariant(newFrag.getVariant())
						.setExtras(newFrag.getExtras())
						.setAppContext(newFrag.getAppContext())
						.setListCallback(newFrag.getListCallback());
				_pageAdapter.addPage(newFrag);
			} else
				throw new RuntimeException("RTEX [AbstractPagerActivity.addPage]> The fragment located does not inherit the required functionality. Does not extend AbstractPagerFragment.");
		}
		// Be sure the Fragment activity points to a valid activity.
		newFrag.setAppContext(getActivity());
		// Copy the Activity extras to the Fragment. This avoids forgetting to set this by the developer.
		newFrag.setExtras(this.getExtras());
		// Check the number of pages to activate the indicator when more the one.
		if ( _pageAdapter.getCount() > 1 ) {
			this.activateIndicator();
		}
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.addPage]"); //$NON-NLS-1$
	}

	protected void activateIndicator() {
		// If the Indicator is active then set the listener.
		if ( null != _indicator ) {
			_indicator.setVisibility(View.VISIBLE);
			_indicator.setViewPager(_pageContainer);
			_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				public void onPageScrolled( final int arg0, final float arg1, final int arg2 ) {
				}

				public void onPageScrollStateChanged( final int arg0 ) {
				}

				public void onPageSelected( final int position ) {
					if ( null != _actionBar ) {
						_actionBar.setTitle(_pageAdapter.getTitle(position));
						// Clear empty subtitles.
						if ( "" == _pageAdapter.getSubTitle(position) ) {
							_actionBar.setSubtitle(null);
						} else {
							_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
						}
					}
				}
			});
		} else {
			_pageContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				public void onPageScrolled( final int arg0, final float arg1, final int arg2 ) {
				}

				public void onPageScrollStateChanged( final int arg0 ) {
				}

				public void onPageSelected( final int position ) {
					if ( null != _actionBar ) {
						_actionBar.setTitle(_pageAdapter.getTitle(position));
						// Clear empty subtitles.
						if ( "" == _pageAdapter.getSubTitle(position) ) {
							_actionBar.setSubtitle(null);
						} else {
							_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
						}
					}
				}
			});
		}
	}

	private void disableIndicator() {
		if ( null != _indicator ) {
			_indicator.setVisibility(View.GONE);
		}
	}

	private Bundle getExtras() {
		return extras;
	}

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new MVCExceptionHandler(this));
		// Process the extras received by the intent so they can be shared to all the Fragments
		try {
			// Get the parameters and save them on local fields to be stored on destruction and passed to Fragments.
			if ( null != savedInstanceState ) {
				extras = savedInstanceState;
			} else {
				extras = this.getIntent().getExtras();
			}
		} catch (RuntimeException rtex) {
			logger.warn("RTEX [AbstractPagerActivity.onCreate]> " + rtex.getMessage());
		}


		// TODO This section is back the core ActionBar that can be configured until a new configuration is tested.
		// Set the layout to the core activity that defines the background, the indicator and the fragment container.
		this.setContentView(R.layout.activity_pager);
		// Gets the activity's default ActionBar
		_actionBar = this.getActionBar();
		_actionBar.show();
		_actionBar.setDisplayHomeAsUpEnabled(true);

		// Locate the elements of the page and store in global data.
		_pageContainer = (ViewPager) this.findViewById(R.id.pager);
		background = (ImageView) this.findViewById(R.id.backgroundFrame);
		_indicator = (CirclePageIndicator) this.findViewById(R.id.indicator);
		// Check page structure.
		if ( null == _pageContainer ) {
			throw new RuntimeException("RTEX [AbstractPagerActivity.onCreate]> Expected UI element not found.");
		}
		if ( null == background ) {
			throw new RuntimeException("RTEX [AbstractPagerActivity.onCreate]> Expected UI element not found.");
		}

		// Add the adapter for the page switching.
		_pageAdapter = new AbstractFragmentPagerAdapter(this.getFragmentManager(), _pageContainer.getId());
		_pageContainer.setAdapter(_pageAdapter);
		// Cleat the indicator from the view until more than one page is added.
		this.disableIndicator();
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
	}

	private void updateInitialTitle() {
		if ( null != _actionBar ) {
			Fragment firstFragment = _pageAdapter.getInitialPage();
			// REFACTOR This IF can be removed once this code works.
			if ( firstFragment instanceof AbstractPagerFragment ) {
				_actionBar.setTitle(((AbstractPagerFragment) firstFragment).getTitle());
				_actionBar.setSubtitle(((AbstractPagerFragment) firstFragment).getSubtitle());
			}
		}
	}

	//--- A C T I V I T Y   L I F E C Y C L E

	@Override
	protected void onStart() {
		super.onStart();
		// Update the menu for the first page.
		updateInitialTitle();
	}
}
// - UNUSED CODE ............................................................................................
