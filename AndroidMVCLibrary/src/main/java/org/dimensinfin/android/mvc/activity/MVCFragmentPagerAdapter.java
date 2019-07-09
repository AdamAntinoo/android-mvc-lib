package org.dimensinfin.android.mvc.activity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.dimensinfin.android.mvc.R;

public class MVCFragmentPagerAdapter extends FragmentPagerAdapter {
	// - F I E L D - S E C T I O N
	private final List<IPagerFragment> _fragments = new ArrayList<>();
	private int _pagerid = R.id.pager; // This is the resource id for the pager layout viewer that should be set.

	// - C O N S T R U C T O R - S E C T I O N
	public MVCFragmentPagerAdapter( final FragmentManager fm ) {
		super(fm);
	}

	// - F R A G M E N T P A G E R A D A P T E R
	@Override
	public int getCount() {
		return _fragments.size();
	}

	/**
	 * This Adapter method goes to the underlying data structure and gets the item identified by the unique id received as
	 * a parameter. In out case of a simple list we should check that this identifier is not out of bounds of the data
	 * array. In such a case we create an empty Fragment to replace the missing item.
	 *
	 * @param position the array position (base 0) for the item to get.
	 * @return the stored fragment or an empty new fragment if the position is out of boundaries.
	 */
	@Override
	public Fragment getItem( final int position ) {
		// Check if the requested position is available. If the position requested is outdide the limit return empty element.
		if (position >= this.getCount()) return new Fragment();
		if (position < 0) return new Fragment();
		return (Fragment) _fragments.get(position);
	}

	// - M E T H O D - S E C T I O N
	/**
	 * Add a new fragment at the end of the list of already managed fragments. After the addition we notify the Adapter
	 * that the source data has changed so it can update any UI element affected by this change like the indicator is the
	 * number of pages is greater than one.
	 *
	 * @param fragNew new fragment to all to the list of pages.
	 */
	public void addPage( final IPagerFragment fragNew ) {
		if (null != fragNew) {
			_fragments.add(fragNew);
		}
		this.notifyDataSetChanged();
	}

	public int getNextFreePosition() {
		return _fragments.size();
	}


	/**
	 * Return the fragment internal identifier generated by the FragmentPager. This is a composed string from some fields
	 * that have to be informed to this adapter.
	 *
	 * @param position the page position identifier we are going to use for this Fragment. This has not to match the real
	 *                 position of the instance on the Fragment internal list, it is only a numeric unique identifier.
	 * @return internal Fragment Manager fragment identifier string.
	 */
	public String getFragmentId( final int position ) {
		return "android:switcher:" + _pagerid + ":" + this.getItemId(position);
	}

	public Fragment getInitialPage() {
		return this.getItem(0);
	}
}
