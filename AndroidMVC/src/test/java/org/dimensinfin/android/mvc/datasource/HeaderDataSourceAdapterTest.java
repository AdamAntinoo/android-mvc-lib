package org.dimensinfin.android.mvc.datasource;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.support.SpacerController;

import static org.mockito.Mockito.times;

public class HeaderDataSourceAdapterTest {
	private final List<IAndroidController> controllers = new ArrayList<>();
	private MVCPagerFragment fragment;
	private ControllerFactory factory;
	private Spacer item1;

	@Test
	public void constructorContract() {
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final HeaderDataSourceAdapter adapter = new HeaderDataSourceAdapter( fragment, dataSource );
		Assertions.assertNotNull( adapter );
	}

	@Test
	public void accessContents() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getHeaderSectionContents() ).thenReturn( this.controllers );
		// Test
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter( fragment, dataSource );
		// Assertions
		// After initialization. Empty
		List<IAndroidController> obtained = dsAdapter.accessContents();
		Assertions.assertEquals( 0, obtained.size() );
		// Generate and process model. 3 items
		dsAdapter.notifyDataSetChanged();
		obtained = dsAdapter.accessContents();
		Assertions.assertEquals( 3, obtained.size() );
		// Clear model. Generation not touched. 3 items
		dsAdapter.clean();
		obtained = dsAdapter.accessContents();
		Assertions.assertEquals( 3, obtained.size() );
		// Clean and regenerate contents. Empty
		dsAdapter.clean();
		Mockito.when( dataSource.getHeaderSectionContents() ).thenReturn( new ArrayList<>() );
		dsAdapter.notifyDataSetChanged();
		obtained = dsAdapter.accessContents();
		Assertions.assertEquals( 0, obtained.size() );
	}

//	public void builderIncomplete() {
//		Assertions.assertThrows( NullPointerException.class, () -> {
//			final DataSourceAdapter adapter = new DataSourceAdapter( null, null );
//		} );
//	}

	//	@Test
	public void collaborateData() {
		final MVCPagerFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final HeaderDataSourceAdapter adapter = new HeaderDataSourceAdapter( fragment, dataSource );
		Assertions.assertNotNull( adapter );
		adapter.requestDataModel();
		Mockito.verify( dataSource, times( 1 ) ).collaborate2Model();
	}

	@Test
	public void notifyDataSetChanged() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getHeaderSectionContents() ).thenReturn( this.controllers );
		// Test
		final HeaderDataSourceAdapter dsAdapter = new HeaderDataSourceAdapter( fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		// Assertions
		Assertions.assertEquals( 3, dsAdapter.getCount(), "The contents should have 3 elements." );
		final IAndroidController expected = this.controllers.get( 1 );
		final Object obtained = dsAdapter.getItem( 1 );
		Assertions.assertEquals( expected, obtained, "The selected element should match." );
		Assertions.assertEquals( item1.hashCode(), dsAdapter.getItemId( 0 ), "The selected item should match the identifier." );
	}

	@BeforeEach
	public void beforeEach() {
		this.fragment = Mockito.mock( MVCPagerFragment.class );
		this.factory = Mockito.mock( ControllerFactory.class );
		// Initialize the list of controllers
		this.item1 = new Spacer.Builder().withLabel( "Test 1" ).build();
		this.controllers.add( new SpacerController( this.item1, this.factory ) );
		this.controllers.add( new SpacerController( new Spacer.Builder().withLabel( "Test 2" ).build(), this.factory ) );
		this.controllers.add( new SpacerController( new Spacer.Builder().withLabel( "Test 3" ).build(), this.factory ) );
	}
}
