package org.dimensinfin.android.mvc.core;

import android.app.Fragment;
import android.app.FragmentManager;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AbstractFragmentPagerAdapterTest {
	@Test
	public void getCurrentPosition_empty() {
		// Requesting the position for an empty list of the first page should return 0.
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final AbstractFragmentPagerAdapter adapter = new AbstractFragmentPagerAdapter(fragmentManager, 0);

		// Assert
		assertEquals(0, adapter.getNextFreePosition());
	}

	@Test
	public void getCurrentPosition_next() {
		// Get a new position after adding a page.
		// Given
		final FragmentManager fragmentManager = mock(FragmentManager.class);
		final Fragment fragment = mock(Fragment.class);
		final AbstractFragmentPagerAdapter adapter = new AbstractFragmentPagerAdapter(fragmentManager, 0) {
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
		final int pagerId = 10;
		final AbstractFragmentPagerAdapter adapter = new AbstractFragmentPagerAdapter(fragmentManager, pagerId);

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
		final AbstractFragmentPagerAdapter adapter = new AbstractFragmentPagerAdapter(fragmentManager, pagerId) {
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
		final AbstractFragmentPagerAdapter adapter = new AbstractFragmentPagerAdapter(fragmentManager, pagerId) {
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
		final AbstractFragmentPagerAdapter adapter = mock(AbstractFragmentPagerAdapter.class);

		// Test
		adapter.addPage(fragment);
		// Assert final conditions
		verify(adapter, times(1)).notifyDataSetChanged();
	}

}
