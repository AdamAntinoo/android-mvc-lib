package org.dimensinfin.android.mvc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.dimensinfin.android.mvc.activity.MVCFragmentPagerAdapter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MVCFragmentPagerAdapterTest {
	@Test
	public void getCurrentPosition_empty() {
		// Requesting the position for an empty list of the first page should return 0.
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final MVCFragmentPagerAdapter adapter = new MVCFragmentPagerAdapter(fragmentManager);

		// Assert
		assertEquals(0, adapter.getNextFreePosition());
	}

	@Test
	public void getCurrentPosition_next() {
		// Get a new position after adding a page.
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final Fragment fragment = mock(Fragment.class);
		final MVCFragmentPagerAdapter adapter = new MVCFragmentPagerAdapter(fragmentManager) {
			public void notifyDataSetChanged() {
			}
		};

		// Test
		adapter.addPage(fragment);
		// Assert
		assertEquals(1, adapter.getNextFreePosition());
	}

	@Test
	public void getFragmentId_default() {
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final int pagerId = R.id.pager;
		final MVCFragmentPagerAdapter adapter = new MVCFragmentPagerAdapter(fragmentManager);

		// Test
		final String obtained = adapter.getFragmentId(adapter.getNextFreePosition());
		// Assert
		final String expected = "android:switcher:" + pagerId + ":" + adapter.getNextFreePosition();
		assertEquals(expected, obtained);
	}

	@Test
	public void addPage_insertion() {
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final int pagerId = 10;
		final Fragment fragment = mock(Fragment.class);
		final MVCFragmentPagerAdapter adapter = new MVCFragmentPagerAdapter(fragmentManager) {
			public void notifyDataSetChanged() {
			}
		};

		// Assert initial conditions
		assertEquals(0, adapter.getNextFreePosition());
		// Test
		adapter.addPage(fragment);
		// Assert final conditions
		assertEquals(1, adapter.getNextFreePosition());
	}

	@Test
	public void addPage_insertnull() {
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final int pagerId = 10;
		final Fragment fragment = mock(Fragment.class);
		final MVCFragmentPagerAdapter adapter = new MVCFragmentPagerAdapter(fragmentManager) {
			public void notifyDataSetChanged() {
			}
		};

		// Assert initial conditions
		assertEquals(0, adapter.getNextFreePosition());
		// Test
		adapter.addPage(fragment);
		adapter.addPage(fragment);
		// Assert final conditions
		assertEquals(2, adapter.getNextFreePosition());
		// Test
		adapter.addPage(null);
		// Assert final conditions
		assertEquals(2, adapter.getNextFreePosition());
	}

	//	@Test
	public void addPage_notify() {
		// Given
		final Fragment fragment = mock(Fragment.class);
		final MVCFragmentPagerAdapter adapter = mock(MVCFragmentPagerAdapter.class);

		// Test
		adapter.addPage(fragment);
		// Assert final conditions
		verify(adapter, times(1)).notifyDataSetChanged();
	}

}
