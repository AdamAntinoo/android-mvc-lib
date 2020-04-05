package org.dimensinfin.android.mvc.datasource;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.android.mvc.activity.MVCFragment;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.ControllerFactory;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.Spacer;
import org.dimensinfin.android.mvc.support.SpacerController;
import org.dimensinfin.android.mvc.support.SpacerRender;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.domain.IntercommunicationEvent;

import static org.mockito.Mockito.times;

public class DataSourceAdapterTest {
	private final List<IAndroidController> controllers = new ArrayList<>();
	private MVCPagerFragment fragment;
	private ControllerFactory factory;
	private Spacer item1;

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

	@Test
	public void constructorContract() {
		// Given
		final DataSourceAdapter baseAdapter = new DataSourceAdapter();
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final DataSourceAdapter adapter = new DataSourceAdapter( this.fragment, dataSource );
		// Assertions
		Assertions.assertNotNull( baseAdapter );
		Assertions.assertNotNull( adapter );
	}

	@Test
	public void constructorFailure() {
		final MVCPagerFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final DataSourceAdapter adapter = new DataSourceAdapter( null, dataSource );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final DataSourceAdapter adapter = new DataSourceAdapter( this.fragment, null );
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			final DataSourceAdapter adapter = new DataSourceAdapter( null, null );
		} );
	}

	@Test
	public void requestDataModel() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// Test
		final DataSourceAdapter adapter = new DataSourceAdapter( this.fragment, dataSource );
		adapter.requestDataModel();
		// Assertions
		Mockito.verify( dataSource, Mockito.times( 1 ) ).collaborate2Model();
	}
//Assertions.assertEquals( expected, adapter.get );

	// TODO - Need to test indirectly the contents of the 'context' field

	//	public void builderIncomplete() {
//		Assertions.assertThrows( NullPointerException.class, () -> {
//			final DataSourceAdapter adapter = new DataSourceAdapter( null, null );
//		} );
//	}
	@Test
	public void getCountEmpty() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final List<IAndroidController> dataSectionContents = new ArrayList<>();
		// When
//		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( dataSectionContents );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( this.fragment, dataSource );
		final int obtained = dsAdapter.getCount();
		// Assertions
		Assertions.assertEquals( 0, obtained );
	}

	@Test
	public void getCountLoaded() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( this.fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		final int obtained = dsAdapter.getCount();
		// Assertions
		Assertions.assertEquals( 3, obtained );
	}

	@Test
	public void accessContents() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( this.fragment, dataSource );
		// Assertions
		Assertions.assertEquals( 0, dsAdapter.accessContents().size() );
		dsAdapter.notifyDataSetChanged();
		final List<IAndroidController> obtained = dsAdapter.accessContents();
		// Assertions
		Assertions.assertEquals( this.controllers.size(), obtained.size() );
	}

	@Test
	public void notifyDataSetChanged() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		// Assertions
		Assertions.assertEquals( 3, dsAdapter.getCount(), "The contents should have 3 elements." );
		final IAndroidController expected = this.controllers.get( 1 );
		final Object obtained = dsAdapter.getItem( 1 );
		Assertions.assertEquals( expected, obtained, "The selected element should match." );
		Assertions.assertEquals( item1.hashCode(), dsAdapter.getItemId( 0 ), "The selected item should match the identifier." );
	}

	@Test
	public void collaborateData() {
		final MVCPagerFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final DataSourceAdapter adapter = new DataSourceAdapter( fragment, dataSource );
		Assertions.assertNotNull( adapter );
		adapter.requestDataModel();
		Mockito.verify( dataSource, times( 1 ) ).collaborate2Model();
	}


	@Test
	public void getItem() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		// Assertions
		Assertions.assertNotNull( dsAdapter.getItem( 0 ) );
		Assertions.assertNotNull( dsAdapter.getItem( 2 ) );
		Assertions.assertTrue( this.controllers.get( 0 ).equals( dsAdapter.getItem( 0 ) ) );
	}

	@Test
	public void getItemId() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		// Assertions
		Assertions.assertTrue( this.controllers.get( 0 ).getModelId() == dsAdapter.getItemId( 0 ) );
		Assertions.assertTrue( this.controllers.get( 2 ).getModelId() == dsAdapter.getItemId( 2 ) );
	}

	@Test
	public void getView() {
		// Given
		final MVCFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		final Activity context = Mockito.mock( Activity.class );
		final List<IAndroidController> controllersList = new ArrayList<>(  );
		final SpacerController controller =Mockito.mock(SpacerController.class);
		controllersList.add(controller);
		final SpacerRender render = Mockito.mock(SpacerRender.class);
		final View view =Mockito.mock(View.class);
		// When
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( controllersList );
		Mockito.when( fragment.getActivityContext() ).thenReturn( context );
		Mockito.when( controller.buildRender( context ) ).thenReturn( render );
		Mockito.when( render.getView() ).thenReturn( view );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		dsAdapter.notifyDataSetChanged();
		// Assertions
		final View obtained = dsAdapter.getView( 0, null, null );
		Assertions.assertNotNull( obtained );
	}
// TODO - Tests cached views and also the exception generation
	@Test
	public void hasStableIds() {
		// Given
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		// Test
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		// Assertions
		Assertions.assertTrue( dsAdapter.hasStableIds() );
	}

	@Test
	public void adapterMethods() {
		final MVCFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		Assertions.assertTrue( dsAdapter.areAllItemsEnabled() );
		Assertions.assertTrue( dsAdapter.isEnabled( 0 ) );
		Assertions.assertEquals( 0, dsAdapter.getItemViewType( 0 ) );
		Assertions.assertEquals( 1, dsAdapter.getViewTypeCount() );
	}

	@Test
	public void receiveEvent() throws InterruptedException {
		final MVCFragment fragment = Mockito.mock( MVCPagerFragment.class );
		final MVCDataSource dataSource = Mockito.mock( MVCDataSource.class );
		Mockito.when( dataSource.getDataSectionContents() ).thenReturn( this.controllers );
		final DataSourceAdapter dsAdapter = new DataSourceAdapter( fragment, dataSource );
		final DataSourceAdapter spyAdapter = Mockito.spy( dsAdapter );
		dsAdapter.receiveEvent( new IntercommunicationEvent( this, EEvents.EVENT_NEWDATA.name(),
				this, this ) );
		dsAdapter.receiveEvent( new IntercommunicationEvent( this, EEvents.EVENT_ACTIONEXPANDCOLLAPSE.name(),
				this, this ) );
		dsAdapter.receiveEvent( new IntercommunicationEvent( this, EEvents.EVENT_ADAPTER_REQUESTNOTIFYCHANGES.name(),
				this, this ) );
	}
}
