package org.dimensinfin.android.mvc.datasource;

import android.widget.BaseAdapter;

import org.dimensinfin.android.mvc.activity.MVCFragment;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.support.EmptyNode;
import org.dimensinfin.android.mvc.support.MockController;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Antinoo
 */
public class DataSourceAdapterTest {
	private static final ControllerFactory factory = Mockito.mock(ControllerFactory.class);
	private static final List<IAndroidController> controllers = new ArrayList<>();
	private static  EmptyNode item1;

	@Before
	public void setUp() {
		// Initialize the list of controllers
		 item1 = new EmptyNode("Test 1");
		controllers.add(new MockController(item1, factory));
		controllers.add(new MockController(new EmptyNode("Test 2"), factory));
		controllers.add(new MockController(new EmptyNode("Test 3"), factory));
	}

//	@Test
	public void propertyChange() {
		// Given
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource datasource = Mockito.mock(MVCDataSource.class);
		final BaseAdapter adapter = Mockito.mock(BaseAdapter.class);

		// When
//		Mockito.when( fragment.getActivityContext()).thenReturn(context);

		// Test
		final DataSourceAdapter dsadapter = new DataSourceAdapter(fragment, datasource);
		dsadapter.propertyChange(new PropertyChangeEvent(this, "Test", null, null));

		// Asserts
		Assert.assertNotNull("The controllers should have been loaded.", dsadapter.getCount());
	}

	@Test
	public void notifyDataSetChanged() {
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource datasource = Mockito.mock(MVCDataSource.class);
		Mockito.when(datasource.getDataSectionContents()).thenReturn(controllers);
		final DataSourceAdapter dsadapter = new DataSourceAdapter(fragment, datasource);
		dsadapter.notifyDataSetChanged();
		final IAndroidController expected = controllers.get(1);
		final Object obtained = dsadapter.getItem(1);
		Assert.assertEquals("The selected element should match.", expected, obtained);
		Assert.assertEquals("The contents should have 3 elements.", 3, dsadapter.getCount());
		Assert.assertEquals("The selected item should match the identifier.", item1.hashCode(), dsadapter.getItemId(0));
	}

	@Test
	public void baseAdapter() {
		// Given
		final MVCFragment fragment = Mockito.mock(MVCPagerFragment.class);
		final MVCDataSource datasource = Mockito.mock(MVCDataSource.class);
		final BaseAdapter adapter = Mockito.mock(BaseAdapter.class);

		// When
		Mockito.when(datasource.getDataSectionContents()).thenReturn(controllers);

		// Test
		final DataSourceAdapter dsadapter = new DataSourceAdapter(fragment, datasource);
		dsadapter.notifyDataSetChanged();

		// Asserts
		final IAndroidController expected = controllers.get(1);
		final Object obtained = dsadapter.getItem(1);
		Assert.assertTrue("Check the adapter configuration.", dsadapter.areAllItemsEnabled());
		Assert.assertTrue("Check the adapter configuration.", dsadapter.isEnabled(1));
		Assert.assertTrue("Check the adapter configuration.", dsadapter.hasStableIds());
		Assert.assertEquals("Check the adapter configuration.", 1, dsadapter.getViewTypeCount());
		Assert.assertEquals("Check the adapter configuration.", 0, dsadapter.getItemViewType(1));
	}
}
