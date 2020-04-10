package org.dimensinfin.android.mvc.core;

import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.core.interfaces.ICollaboration;
import org.dimensinfin.core.interfaces.IEventReceiver;

import java.util.List;

public interface IDataSource extends IEventReceiver {
	DataSourceLocator getDataSourceLocator();

	boolean isCacheable();

	/**
	 * This is the action to signal the data source to start searching for its data to process it and generate
	 * the contents for the root model list. The model generated is still not processed and no controllers are created until
	 * the viewer requires them. This call is to create any internal data source data structures that are to be used when extracting the
	 * header and data section models.
	 */
	void prepareModel() throws Exception;

	void collaborate2Model();

	IDataSource addHeaderContents( final ICollaboration newModel );

	/**
	 * This method will use the data source models to generate the contents for the header section. The model will be transformed to the list of
	 * Controllers so the final header view will have the list of render views to be used on the display.
	 *
	 * @return the list of header controllers to be rendered.
	 */
	List<IAndroidController> getHeaderSectionContents();

	/**
	 * This is the method to generate the list of controllers to be used on the list rendering. The list returned is only for the controllers that are
	 * visible and then that collaborate to the view.
	 *
	 * @return the list of data section controllers to be rendered.
	 */
	List<IAndroidController> getDataSectionContents();
}
