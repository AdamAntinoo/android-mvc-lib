package org.dimensinfin.android.mvc.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.viewpagerindicator.CirclePageIndicator;
import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.core.MVCException;
import org.dimensinfin.android.mvc.core.MVCExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import androidx.annotation.NonNull;

/**
 * This class extends the bare android Activity. Defines the ActionBar and instantiates the layout. The generic layout
 * for any of the MVC activities contains 3 key elements. A <b>Background</b> image container that defines the full
 * background of the context, the <b>ActionBar</b> that is defined on the applications styles and that it is made
 * visible by default and the <b>ViewPager</b> container that will hold the fragments. This allows for a generic context
 * that will be able to contain different pages and with the feature to allow to swipe to them without changing the
 * context. The different pages will be shown by a circle page indicator if more that one page is present. Only two of
 * them are accessible to other implementations, the Background and the ActionBar.
 *
 * So at the creation step we only should have to generate the Fragments and add them to the pager. This is the
 * functionality for the <code>addPage(ImageView)</code> public method. Fragments share some characteristics to use the
 * possibilities offered by the ActionBar like the <b>Title</b> and the <b>SubTitle</b>.
 * @author Adam Antinoo
 * @since 1.0.0
 */
public abstract class AbstractPagerActivity extends FragmentActivity {
	public enum EExtrasMVC {
		EXTRA_EXCEPTIONMESSAGE, EXTRA_VARIANT
	}

	protected static Logger logger = LoggerFactory.getLogger(AbstractPagerActivity.class);

	// - F I E L D - S E C T I O N
	protected Bundle extras = null;
	protected ActionBar _actionBar = null;
	protected ViewPager _pageContainer = null;
	private final AbstractFragmentPagerAdapter _pageAdapter = new AbstractFragmentPagerAdapter(this.getSupportFragmentManager());

	/** Image reference to the background layout item that can be replaced by the application implementation. */
	protected ImageView background = null;
	protected CirclePageIndicator _indicator = null;

	// - C O N S T R U C T O R - S E C T I O N

	// - M E T H O D - S E C T I O N
	/**
	 * Allows to change the context background that covers the full size of the display
	 * @param newBackground the new background image.
	 */
	public void setBackground(final ImageView newBackground) {
		this.background = newBackground;
	}

	/**
	 * Adds a new <code>Fragment</code> to the list of fragments managed by the pager. The new fragment is added at the
	 * last position of the list of fragments, positions are then relative and not set by the developer.
	 * @param newFrag the new fragment to add to the pager. When the application is brought back to life we can have
	 *                already the fragments created and initialized at the <code>FragmentManager</code>. In such a case we
	 *                discard the new received fragment and use the already instance at the <code>FragmentManager</code>.
	 */
	public void addPage(@NonNull final AbstractPagerFragment newFrag) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.addPage]");
		// Connect to the application context of not already done.
		newFrag.setAppContext(this.getApplicationContext());
		// Before checking if we have already this fragment we should get its unique identifier.
		final Fragment frag = this.getSupportFragmentManager().findFragmentByTag(_pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
		if (null == frag) {
			_pageAdapter.addPage(newFrag);
		} else {
			if (null == newFrag)
				throw new RuntimeException("RTEX [AbstractPagerActivity.addPage]> The fragment defined is null and cannot be used.");
			// We need to update the fragment cached on the Fragment Manager
			if (frag instanceof AbstractPagerFragment) {
				AbstractPagerActivity.logger.info("-- [AbstractPagerActivity.addPage]> Reusing available fragment. {}"
						, _pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
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
		// Be sure the Fragment context points to a valid context.
		newFrag.setAppContext(this.getApplicationContext());
		// Copy the Activity extras to the Fragment. This avoids forgetting to set this by the developer.
		newFrag.setExtras(this.getExtras());
		// Check the number of pages to activate the indicator when more the one.
		if (_pageAdapter.getCount() > 1) {
			this.activateIndicator();
		}
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.addPage]"); //$NON-NLS-1$
	}

	protected void activateIndicator() {
		// If the Indicator is active then set the listener.
		if (null != _indicator) {
			_indicator.setVisibility(View.VISIBLE);
			_indicator.setViewPager(_pageContainer);
			_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
					// Do nothing on scroll detection.
				}

				public void onPageScrollStateChanged(final int arg0) {
					// Do nothing on scroll detection.
				}

				public void onPageSelected(final int position) {
					if (null != _actionBar) {
						_actionBar.setTitle(_pageAdapter.getTitle(position));
						// Clear empty subtitles.
						if ("" == _pageAdapter.getSubTitle(position)) {
							_actionBar.setSubtitle(null);
						} else {
							_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
						}
					}
				}
			});
		}
//		else {
//			_pageContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//				public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
//					// Do nothing on scroll detection.
//				}
//
//				public void onPageScrollStateChanged(final int arg0) {
//					// Do nothing on scroll detection.
//				}
//
//				public void onPageSelected(final int position) {
//					if (null != _actionBar) {
//						_actionBar.setTitle(_pageAdapter.getTitle(position));
//						// Clear empty subtitles.
//						if ("" == _pageAdapter.getSubTitle(position)) {
//							_actionBar.setSubtitle(null);
//						} else {
//							_actionBar.setSubtitle(_pageAdapter.getSubTitle(position));
//						}
//					}
//				}
//			});
//		}
	}

	private void disableIndicator() {
		if (null != _indicator) {
			_indicator.setVisibility(View.GONE);
		}
	}

	private Bundle getExtras() {
		return extras;
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		AbstractPagerActivity.logger.info(">> [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
		super.onCreate(savedInstanceState);
		// Install the default library exception interceptor to show lib exceptions.
		Thread.setDefaultUncaughtExceptionHandler(new MVCExceptionHandler(this));
		// Process the extras received by the intent so they can be shared to all the Fragments
		try {
			// Get the parameters and save them on local fields to be stored on destruction and passed to Fragments.
			if (null != savedInstanceState) {
				extras = savedInstanceState;
			} else {
				extras = this.getIntent().getExtras();
			}
		} catch (RuntimeException rtex) {
			logger.warn("RTEX [AbstractPagerActivity.onCreate]> {}", rtex.getMessage());
		}


		// TODO This section is back the core ActionBar that can be configured until a new configuration is tested.
		// Set the layout to the core context that defines the background, the indicator and the fragment container.
		this.setContentView(R.layout.activity_pager);
		// Gets the context's default ActionBar
		_actionBar = this.getActionBar();
		_actionBar.show();
		_actionBar.setDisplayHomeAsUpEnabled(true);

		// Locate the elements of the page and store in global data.
		_pageContainer = (ViewPager) this.findViewById(R.id.pager);
		background = (ImageView) this.findViewById(R.id.backgroundFrame);
		_indicator = (CirclePageIndicator) this.findViewById(R.id.indicator);
		// Check page structure.
		if (null == _pageContainer) {
			throw new MVCException("RTEX [AbstractPagerActivity.onCreate]> Expected UI element not found.");
		}
		if (null == background) {
			throw new MVCException("RTEX [AbstractPagerActivity.onCreate]> Expected UI element not found.");
		}

		// Add the adapter for the page switching.
//		_pageAdapter = new AbstractFragmentPagerAdapter(this.getFragmentManager(), _pageContainer.getId());
		_pageContainer.setAdapter(_pageAdapter);
		// Cleat the indicator from the view until more than one page is added.
		this.disableIndicator();
		AbstractPagerActivity.logger.info("<< [AbstractPagerActivity.onCreate]"); //$NON-NLS-1$
	}
	private void updateInitialTitle() {
		if (null != _actionBar) {
			Fragment firstFragment = _pageAdapter.getInitialPage();
			// REFACTOR This IF can be removed once this code works.
			if (firstFragment instanceof AbstractPagerFragment) {
				_actionBar.setTitle(((AbstractPagerFragment) firstFragment).getTitle());
				_actionBar.setSubtitle(((AbstractPagerFragment) firstFragment).getSubtitle());
			}
		}
	}

	// - A C T I V I T Y   L I F E C Y C L E

	@Override
	protected void onStart() {
		super.onStart();
		// Update the menu for the first page.
		updateInitialTitle();
	}
}
