//	PROJECT:        NeoCom.Android (NEOC.A)
//	AUTHORS:        Adam Antinoo - adamantinoo.git@gmail.com
//	COPYRIGHT:      (c) 2013-2016 by Dimensinfin Industries, all rights reserved.
//	ENVIRONMENT:		Android API16.
//	DESCRIPTION:		Application to get access to CCP api information and help manage industrial activities
//									for characters and corporations at Eve Online. The set is composed of some projects
//									with implementation for Android and for an AngularJS web interface based on REST
//									services on Sprint Boot Cloud.
package org.dimensinfin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.viewpagerindicator.CirclePageIndicator;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.connector.MVCAppConnector;
import org.dimensinfin.android.mvc.enumerated.EExtrasMVC;

import java.util.logging.Logger;

//- CLASS IMPLEMENTATION ...................................................................................

/**
 * This abstract Activity will collect all the common code that is being used on the new Activity pattern.
 * Most of the new activities change minor actions on some methods while sharing all the rest of the code.<br>
 * This class implements a generic Activity with a swipe gesture multi page layout and Titled pages that will
 * show names only if the number of pages is more than 1. Current implementation ises a cicle indicator but
 * will be transistioned to a Titled indicator. The base code will take care of the menu and the Action tool
 * bar.
 *
 * @author Adam Antinoo
 */
public abstract class AbstractPagerActivity extends AppCompatActivity {
	// - S T A T I C - S E C T I O N ..........................................................................
	protected static Logger logger = Logger.getLogger("AbstractPagerActivity");

	// - F I E L D - S E C T I O N ............................................................................
	protected Toolbar _actionBar = null;
	protected ViewPager _pageContainer = null;
	protected AbstractFragmentPagerAdapter _pageAdapter = null;
	protected ImageView _back = null;
	protected CirclePageIndicator _indicator = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................

	public Activity getActivity () {
		return this;
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	protected void activateIndicator () {
		// If the Indicator is active then set the listener.
		if ( null != _indicator ) {
			_indicator.setVisibility(View.VISIBLE);
			_indicator.setViewPager(_pageContainer);
			_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				public void onPageScrolled (final int arg0, final float arg1, final int arg2) {
				}

				public void onPageScrollStateChanged (final int arg0) {
				}

				public void onPageSelected (final int position) {
					_actionBar.setTitle(_pageAdapter.getTitle(position));
					// Clear empty subtitles.
					if ( "" == _pageAdapter.getSubTitle(position) ) {
						_actionBar.setSubtitle(null);
					} else {
						_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
					}
				}
			});
		} else {
			_pageContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				public void onPageScrolled (final int arg0, final float arg1, final int arg2) {
				}

				public void onPageScrollStateChanged (final int arg0) {
				}

				public void onPageSelected (final int position) {
					_actionBar.setTitle(_pageAdapter.getTitle(position));
					// Clear empty subtitles.
					if ( "" == _pageAdapter.getSubTitle(position) ) {
						_actionBar.setSubtitle(null);
					} else {
						_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
					}
				}
			});
		}
	}

	protected void addPage (final AbstractPagerFragment newFrag, final int position) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.addPage]"); //$NON-NLS-1$
		final Fragment frag = this.getSupportFragmentManager().findFragmentByTag(_pageAdapter.getFragmentId(position));
		if ( null == frag ) {
			_pageAdapter.addPage(newFrag);
		} else {
			// Reuse a previous created Fragment. But watch some of the fields have been removed.
			((AbstractPagerFragment) frag).setVariant(newFrag.getVariant());
			_pageAdapter.addPage(frag);
		}
		// Check the number of pages to activate the indicator when more the
		// one.
		if ( _pageAdapter.getCount() > 1 ) {
			this.activateIndicator();
		}
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.addPage]"); //$NON-NLS-1$
	}

	protected void disableIndicator () {
		if ( null != _indicator ) {
			_indicator.setVisibility(View.GONE);
		}
	}

	protected AbstractFragmentPagerAdapter getPageAdapter () {
		return _pageAdapter;
	}

	@Override
	protected void onCreate (final Bundle savedInstanceState) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_pager);
		try {
			// Gets the activity's default ActionBar
			Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
			_actionBar = toolbar;
			try {
				setSupportActionBar(toolbar);
			} catch (final Exception rtex) {
				logger.severe("RTEX> [AbstractPagerActivity.onCreate]> " + rtex.getMessage());
	//			rtex.printStackTrace();
				//			this.stopActivity(new RuntimeException("RTEX> AbstractPagerActivity.onCreate - " + rtex.getMessage()));
			}
			//		_acti
			//	_actionBar = this.getActionBar();
			//	_actionBar.show();
			//	_actionBar.setDisplayHomeAsUpEnabled(true);

			// Locate the elements of the page and store in global data.
			_pageContainer = (ViewPager) this.findViewById(R.id.pager);
			_back = (ImageView) this.findViewById(R.id.backgroundFrame);
			_indicator = (CirclePageIndicator) this.findViewById(R.id.indicator);
			// Check page structure.
			if ( null == _pageContainer ) {
				this.stopActivity(new RuntimeException("RTEX> Expected UI element not found."));
			}
			if ( null == _back ) {
				this.stopActivity(new RuntimeException("RTEX> Expected UI element not found."));
			}

			// Add the adapter for the page switching.
			_pageAdapter = new AbstractFragmentPagerAdapter(this.getSupportFragmentManager(), _pageContainer.getId());
			_pageContainer.setAdapter(_pageAdapter);
			this.disableIndicator();
		} catch (final Exception rtex) {
			Log.e("EVEI", "RTEX> AbstractPagerActivity.onCreate - " + rtex.getMessage());
			rtex.printStackTrace();
			this.stopActivity(new RuntimeException("RTEX> AbstractPagerActivity.onCreate - " + rtex.getMessage()));
		}
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
	}

	/**
	 * For really unrecoverable or undefined exceptions the application should go to a safe spot. That spot is
	 * defined by the application so this is another abstract method.
	 *
	 * @param exception
	 */
	protected void stopActivity (final Exception exception) {
		final Intent intent = new Intent(this, MVCAppConnector.getSingleton().getFirstActivity());
		// Pass the user message to the activity for display.
		intent.putExtra(EExtrasMVC.EXTRA_EXCEPTIONMESSAGE.name(), exception.getMessage());
		this.startActivity(intent);
	}

	protected void updateInitialTitle () {
		Fragment firstFragment = this.getPageAdapter().getInitialPage();
		// REFACTOR This IF can be removed once this code works.
		if ( firstFragment instanceof AbstractPagerFragment ) {
			_actionBar.setTitle(((AbstractPagerFragment) firstFragment).getTitle());
			_actionBar.setSubtitle(((AbstractPagerFragment) firstFragment).getSubtitle());
		}
	}
}
// - UNUSED CODE
// ............................................................................................
