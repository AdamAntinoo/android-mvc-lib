package org.dimensinfin.android.mvc.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.exception.MVCException;
import org.dimensinfin.android.mvc.exception.MVCExceptionHandler;


import me.relex.circleindicator.CircleIndicator;

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
 *
 * @author Adam Antinoo
 * @since 1.0.0
 */
public abstract class MVCMultiPageActivity extends FragmentActivity {
	public enum EMVCExtras {
		EXTRA_EXCEPTIONMESSAGE, EXTRA_VARIANT
	}

	protected static Logger logger = LoggerFactory.getLogger(MVCMultiPageActivity.class);
	private final MVCFragmentPagerAdapter _pageAdapter = new MVCFragmentPagerAdapter(this.getSupportFragmentManager());
	// - F I E L D - S E C T I O N
	protected Bundle extras;
	protected ActionBar _actionBar;
	protected ViewPager _pageContainer;
	/** Image reference to the background layout item that can be replaced by the application implementation. */
	protected ImageView background;
	protected CircleIndicator _indicator;
	private Exception lastException;

	// - A C C E P T A N C E
	public MVCFragmentPagerAdapter accessPageAdapter() {
		return this._pageAdapter;
	}

	// - M E T H O D - S E C T I O N

	/**
	 * Allows to change the context background that covers the full size of the display
	 *
	 * @param newBackground the new background image.
	 */
	public void setBackground( final ImageView newBackground ) {
		this.background = newBackground;
	}

	/**
	 * Adds a new <code>Fragment</code> to the list of fragments managed by the pager. The new fragment is added at the
	 * last position of the list of fragments, positions are then relative and not set by the developer.
	 *
	 * @param newFrag the new fragment to add to the pager. When the application is brought back to life we can have
	 *                already the fragments created and initialized at the <code>FragmentManager</code>. In such a case we
	 *                discard the new received fragment and use the already instance at the <code>FragmentManager</code>.
	 */
	public void addPage( @NonNull final IPagerFragment newFrag ) {
		logger.info(">> [MVCMultiPageActivity.addPage]");
		Objects.requireNonNull(newFrag);
		// Before checking if we have already this fragment we should get its unique identifier.
		final Fragment frag = this.getSupportFragmentManager().findFragmentByTag(
				_pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
		if (null == frag) {
			_pageAdapter.addPage(newFrag);
		} else {
			// We need to update the fragment cached on the Fragment Manager
			if (frag instanceof MVCPagerFragment) {
				logger.info("-- [MVCMultiPageActivity.addPage]> Reusing available fragment. {}"
						, _pageAdapter.getFragmentId(_pageAdapter.getNextFreePosition()));
				// Reuse a previous created Fragment. Copy all fields accessible.
				((MVCFragment) frag)
						.setVariant(newFrag.getVariant())
						.setExtras(newFrag.getExtras())
						.setActivityContext(newFrag.getActivityContext())
						.setListCallback(newFrag.getListCallback());
				_pageAdapter.addPage(newFrag);
			} else
				throw new RuntimeException(
						"The fragment located does not inherit the required functionality. Does not extend MVCPagerFragment.");
		}
		// Be sure the Fragment context points to a valid context.
		newFrag.setActivityContext(this);
		// Copy the Activity extras to the Fragment. This avoids forgetting to set this by the developer.
		newFrag.setExtras(this.getExtras());
		// Check the number of pages to activate the indicator when more the one.
		if (_pageAdapter.getCount() > 1) {
			this.activateIndicator();
		}
		((Fragment) newFrag).onAttach(this);
		logger.info("<< [MVCMultiPageActivity.addPage]"); //$NON-NLS-1$
	}

	public IPagerFragment setPage( final int pageNumber ) {
		if ((pageNumber < 0) || (pageNumber > this._pageAdapter.getCount() - 1))
			return (IPagerFragment) this._pageAdapter.getItem(this._pageContainer.getCurrentItem());
		this._pageContainer.setCurrentItem(pageNumber, true);
		return (IPagerFragment) this._pageAdapter.getItem(this._pageContainer.getCurrentItem());
	}

	protected void activateIndicator() {
		// If the Indicator is active then set the listener.
		if (null != _indicator) {
			_indicator.setVisibility(View.VISIBLE);
			_indicator.setViewPager(_pageContainer);
		}
		_pageContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageScrolled( final int arg0, final float arg1, final int arg2 ) {}

			public void onPageSelected( final int position ) {
				activateActionBar(((IPagerFragment) _pageAdapter.getItem(position)).generateActionBarView());
			}

			public void onPageScrollStateChanged( final int arg0 ) {}
		});
	}

	private void disableIndicator() {
		if (null != _indicator) {
			_indicator.setVisibility(View.GONE);
		}
	}

	protected Bundle getExtras() {
		return this.extras;
	}

	/**
	 * This creates the place where to render the components. During the construction it will connect the view with the adapter. Any exception durint
	 * this state should show the exception rendering are where to render the list of exceptions detected.
	 *
	 * @param savedInstanceState the last saved activity state.
	 */
	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		logger.info(">> [MVCMultiPageActivity.onCreate]");
		super.onCreate(savedInstanceState);
		try {
			this.extractExtras(savedInstanceState);
			this.setContentView(R.layout.activity_pager); // Set the layout to the core context
			this.deactivateActionBar();
			// Locate the elements of the page and store in global data.
			_pageContainer = this.findViewById(R.id.pager);
			background = this.findViewById(R.id.backgroundFrame);
			_indicator = this.findViewById(R.id.indicator);
			// Check page structure.
			if (null == _pageContainer)
				throw new MVCException("Expected UI element not found.");
			if (null == background)
				throw new MVCException("Expected UI element not found.");
			_pageContainer.setAdapter(_pageAdapter);
			// Cleat the indicator from the view until more than one page is added.
			this.disableIndicator();
		} catch (final Exception ex) {
			this.lastException = ex;
			this.showException(ex); // Show any exception data on the empty page.
		} finally {
			logger.info("<< [MVCMultiPageActivity.onCreate]");
		}
	}

	// - A C T I V I T Y   L I F E C Y C L E

	@Override
	protected void onStart() {
		super.onStart();
		// Update the menu for the first page.
		this.updateInitialTitle();
	}

	@Override
	public void onAttachFragment( final Fragment fragment ) {
		super.onAttachFragment(fragment);
	}

	protected void showException( final Exception exception ) {
		final ViewGroup exceptionContainer = this.findViewById(R.id.exceptionContainer);
		exceptionContainer.removeAllViews();
		exceptionContainer.addView(new MVCExceptionHandler(this).getExceptionView(exception));
		exceptionContainer.setVisibility(View.VISIBLE);
	}

	protected void extractExtras( final Bundle savedInstanceState ) {
		if (null != savedInstanceState) this.extras = savedInstanceState;
		else this.extras = this.getIntent().getExtras();
		if (null == this.extras)
			this.extras = new Bundle(); // If the extras are not defined then create an empty container.
	}

	protected void activateActionBar( final View actionBarView ) {
		logger.info(">> [MVCMultiPageActivity.activateActionBar]");
		try {
			if (null != actionBarView) {
				ActionBar actionbar = this.getActionBar();
				if (null != actionbar) {
					// Activate the custom ActionBar
					actionbar.setDisplayShowCustomEnabled(true);
					actionbar.setDisplayShowTitleEnabled(false);
					this.getActionBar().setCustomView(actionBarView);
					this.getActionBar().show();
				}
			} else this.activateDefaultActionbar();
		} catch (RuntimeException rtex) {
			logger.info(
					"EX [MVCMultiPageActivity.activateActionBar]> Exception changing Android ActionBar: {}. Returning to default setup.");
			this.activateDefaultActionbar();
		}
		logger.info("<< [MVCMultiPageActivity.activateActionBar]");
	}

	protected void deactivateActionBar() {
		if (null != this.getActionBar()) {
			_actionBar = this.getActionBar();
			_actionBar.hide();
			_actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	protected void activateDefaultActionbar() {
		if (null != this.getActionBar()) {
			_actionBar = this.getActionBar();
			_actionBar.setDisplayShowCustomEnabled(false);
			_actionBar.setDisplayShowTitleEnabled(true);
			_actionBar.show();
			_actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	private void updateInitialTitle() {
		Fragment firstFragment = _pageAdapter.getInitialPage();
		// REFACTOR This IF can be removed once this code works.
		if (firstFragment instanceof IPagerFragment)
			this.activateActionBar(((IPagerFragment) firstFragment).generateActionBarView());
		else this.activateDefaultActionbar();
	}
}
