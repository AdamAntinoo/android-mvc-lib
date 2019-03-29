package org.dimensinfin.android.mvc.demo.activity;

import android.app.Application;
import android.os.Bundle;

import org.dimensinfin.android.mvc.activity.AbstractPagerFragment;
import org.dimensinfin.android.mvc.controller.GroupViewModel;
import org.dimensinfin.android.mvc.core.AppCompatibilityUtils;
import org.dimensinfin.android.mvc.core.EEvents;
import org.dimensinfin.android.mvc.datasource.AMVCDataSource;
import org.dimensinfin.android.mvc.datasource.DataSourceLocator;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.demo.database.Group;
import org.dimensinfin.android.mvc.demo.factory.DemoControllerFactory;
import org.dimensinfin.android.mvc.interfaces.ICollaboration;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.datasource.IDataSource;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * @author Adam Antinoo
 */
public class DatabaseDemoFragment extends AbstractPagerFragment {
    /**
     * This is the link from the Fragment to the LiveView model that should be connected to the database.
     */
    private GroupViewModel mWordViewModel;
    private DatabaseRoomDataSource ds; // Store for the data source so it can be accessed and connected to the live view.

    // - L I F E C Y C L E
//	@Override
//	public void onCreate(@Nullable final Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		mWordViewModel = of(this).get(GroupViewModel.class);
//
//		mWordViewModel.getAllWords().observe(this, new Observer<List<Group>>() {
//			@Override
//			public void onChanged(@Nullable final List<Group> words) {
//				// Update the cached copy of the words in the adapter.
//				ds.setGroups(words);
//			}
//		});
//	}

    // - O V E R R I D E   A B S T R A C T P A G E R F R A G M E N T
    @Override
    public String getSubtitle() {
        return getAppContext().getResources().getString(R.string.activity_subtitle_DemoFragment_Database);
    }

    @Override
    public String getTitle() {
        return getAppContext().getResources().getString(R.string.activity_title_DemoFragment_Database);
    }

    @Override
    public IControllerFactory createFactory() {
        return new DemoControllerFactory(this.getVariant());
    }

    @Override
    protected List<ICollaboration> registerHeaderSource() {
        return new ArrayList<>();
    }

    @Override
    protected IDataSource registerDataSource() {
        AbstractPagerFragment.logger.info(">> [DatabaseDemoFragment.registerDataSource]");
        this.ds = null;
        try {
//			final DataSourceLocator identifier = new DataSourceLocator()
//					.addIdentifier(this.getVariant())
//					.addIdentifier("DATABASE");
            // Connect the model view.
            mWordViewModel = new GroupViewModel((Application) this.getAppContext());
            mWordViewModel.getAllWords().observe(this, new Observer<List<Group>>() {
                @Override
                public void onChanged(@Nullable final List<Group> words) {
                    // Update the cached copy of the words in the adapter.
                    ds.setGroups(words);
                }
            });
            ds = new DatabaseRoomDataSource.Builder()
                    .addIdentifier(this.getVariant())
                    .addIdentifier("DATABASE")
                    .withFactory(this.getFactory())
                    .withViewModel(this.mWordViewModel)
                    .withVariant(getVariant())
                    .withExtras(getExtras())
                    .withCacheStatus(false)
                    .build();
            return ds;
        } finally {
            AbstractPagerFragment.logger.info("<< [DatabaseDemoFragment.registerDataSource]");
        }
    }

    public static class DatabaseRoomDataSource extends AMVCDataSource {
        // - F I E L D - S E C T I O N
        private GroupViewModel viewModel;

        // - C O N S T R U C T O R - S E C T I O N
        private DatabaseRoomDataSource() {
            super();
        }

        private DatabaseRoomDataSource(final DataSourceLocator locator, final IControllerFactory controllerFactory) {
            super(locator, controllerFactory);

            // Do simple test initialization after 10 seconds.

        }

//		public DatabaseRoomDataSource setViewModel(final GroupViewModel model) {
//			this.viewModel = model;
//			return this;
//		}

        public void setGroups(final List<Group> newGroups) {
            this.cleanModel();
            for (Group node : newGroups) {
                this.addModelContents(node);
            }
            this.sendChangeEvent(EEvents.EVENTCONTENTS_ACTIONMODIFYDATA.name());
        }

        @Override
        public void collaborate2Model() {
            final List<Group> initial = new ArrayList<>();
            final Group node = new Group.Builder()
                    .withName("Initial")
                    .build();
            this.viewModel.storeGroup(node);
            initial.add(node);
            this.setGroups(initial);
        }

        // - B U I L D E R
        public static class Builder {
            private final DatabaseRoomDataSource object;
            private final DataSourceLocator identifier;

            public Builder() {
                this.identifier = new DataSourceLocator();
                this.object = new DatabaseRoomDataSource();
            }

            public Builder addIdentifier(final int identifier) {
                this.identifier.addIdentifier(Integer.valueOf(identifier).toString());
                return this;
            }

            public Builder addIdentifier(final long identifier) {
                this.identifier.addIdentifier(Long.valueOf(identifier).toString());
                return this;
            }

            public Builder addIdentifier(final String identifier) {
                if (null != identifier) this.identifier.addIdentifier(identifier);
                return this;
            }

            public Builder withFactory(final IControllerFactory factory) {
                if (null != factory) this.object.controllerFactory = factory;
                return this;
            }

            public Builder withVariant(final String variant) {
                if (null != variant) this.object.setVariant(variant);
                return this;
            }

            public Builder withExtras(final Bundle extras) {
                if (null != extras) this.object.setExtras(extras);
                return this;
            }

            public Builder withCacheStatus(final boolean cacheStatus) {
                this.object.shouldBeCached(cacheStatus);
                return this;
            }

            public Builder withViewModel(final GroupViewModel viewModel) {
                this.object.viewModel = viewModel;
                return this;
            }

            public DatabaseRoomDataSource build() {
                // Register the identifier and create the data source.
                this.object.locator = this.identifier;
                // Do any other validations. If failed then launch an exception.
                AppCompatibilityUtils.assertNotNull(this.object.locator);
                AppCompatibilityUtils.assertNotNull(this.object.viewModel);
                AppCompatibilityUtils.assertNotNull(this.object.controllerFactory);

                return object;
            }
        }
    }
}
