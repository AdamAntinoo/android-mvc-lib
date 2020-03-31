package org.dimensinfin.android.mvc.demo;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.dimensinfin.android.mvc.core.AbstractRender;
import org.dimensinfin.android.mvc.demo.R;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;
import org.dimensinfin.android.mvc.interfaces.IRender;
import org.dimensinfin.android.mvc.model.DemoHeaderTitle;

import androidx.annotation.NonNull;

/**
 * @author Adam Antinoo
 */
public class DemoHeaderTitleController extends AndroidController<DemoHeaderTitle> implements IMenuActionTarget, Comparable {
    // - F I E L D - S E C T I O N
//	private GenericController<DemoHeaderTitle> delegatedController;
    private int iconReference = R.drawable.defaulticonplaceholder;
    private Context context;

    // - C O N S T R U C T O R - S E C T I O N
    public DemoHeaderTitleController(@NonNull final DemoHeaderTitle model, @NonNull final IControllerFactory factory) {
        super(new ControllerAdapter<DemoHeaderTitle>(model), factory);
        // Connect the delegate.
//		this.delegatedController = new GenericController<>(model);
    }

    // - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
//	public DemoHeaderTitle getModel() {
//		return delegatedController.getModel();
//	}

    // - O V E R R I D E - A A N D R O I D C O N T R O L L E R
    @Override
    public long getModelId() {
        return this.getModel().hashCode();
    }

    @Override
    public IRender buildRender(final Context context) {
        this.context = context;
        return new DemoHeaderTitleRender(this, context);
    }

    // - G E T T E R S   &   S E T T E R S
    public int getIconReference() {
        return iconReference;
    }

    public DemoHeaderTitleController setIconReference(final int resourceIdentifier) {
        logger.info("-- [DemoHeaderTitleController.setIconReference]> setting icon ref: " + resourceIdentifier);
        iconReference = resourceIdentifier;
        return this;
    }

    // - C O M P A R A B L E   I N T E R F A C E
    @Override
    public int compareTo(@NonNull final Object o) {
        if (o instanceof DemoHeaderTitleController) {
            final DemoHeaderTitleController target = (DemoHeaderTitleController) o;
            return this.getModel().compareTo(target.getModel());
        } else return -1;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    // - I M E N U A C T I O N   I N T E R F A C E
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        logger.info(">< [DemoHeaderTitleController.onContextItemSelected]");
        Toast.makeText(this.context
                , item.getTitle()
                , Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        logger.info(">> [DemoHeaderTitleController.onCreateContextMenu]");
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");
        menu.add(0, v.getId(), 0, "Action 2");
        logger.info("<< [DemoHeaderTitleController.onCreateContextMenu]");
    }

    public class DemoHeaderTitleRender extends AbstractRender {
        // - F I E L D - S E C T I O N
        private TextView applicationName;
        private TextView applicationVersion;

        // - C O N S T R U C T O R - S E C T I O N
        public DemoHeaderTitleRender(final DemoHeaderTitleController controller, final Context context) {
            super(controller, context);
        }

        // - I R E N D E R   I N T E R F A C E
        @Override
        public DemoHeaderTitleController getController() {
            return (DemoHeaderTitleController) super.getController();
        }

        @Override
        public void initializeViews() {
            applicationName = this.getView().findViewById(R.id.applicationName);
            applicationVersion = this.getView().findViewById(R.id.applicationVersion);
            assert (applicationName != null);
            assert (applicationVersion != null);
        }

        @Override
        public void updateContent() {
            applicationName.setText(this.getController().getModel().getName());
            applicationName.setVisibility(View.VISIBLE);
            applicationVersion.setText(getController().getModel().getVersion());
            applicationVersion.setVisibility(View.VISIBLE);
        }

        @Override
        public int accessLayoutReference() {
            return R.layout.demoheadertitle4header;
        }
    }
}
