package org.dimensinfin.mvc.demo.controller;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.AndroidController;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.mvc.demo.domain.ApplicationHeaderTitle;
import org.dimensinfin.mvc.demo.render.ApplicationHeaderTitleRender;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.interfaces.IMenuActionTarget;

import androidx.annotation.NonNull;

/**
 * @author Adam Antinoo
 */
public class ApplicationHeaderTitleController extends AndroidController<ApplicationHeaderTitle> implements IMenuActionTarget, Comparable {
    private int iconReference = R.drawable.defaulticonplaceholder;
    private Context context;

    public ApplicationHeaderTitleController( @NonNull final ApplicationHeaderTitle model, @NonNull final IControllerFactory factory ) {
        super( model, factory );
    }

    // - A N D R O I D C O N T R O L L E R
    @Override
    public IRender buildRender( final Context context) {
        this.context = context;
        return new ApplicationHeaderTitleRender(this, context);
    }

    // - G E T T E R S   &   S E T T E R S
//    public int getIconReference() {
//        return iconReference;
//    }
//
//    public ApplicationHeaderTitleController setIconReference( final int resourceIdentifier) {
//        logger.info("-- [DemoHeaderTitleController.setIconReference]> setting icon ref: " + resourceIdentifier);
//        iconReference = resourceIdentifier;
//        return this;
//    }

    // - C O M P A R A B L E   I N T E R F A C E
    @Override
    public int compareTo(@NonNull final Object o) {
        if (o instanceof ApplicationHeaderTitleController) {
            final ApplicationHeaderTitleController target = (ApplicationHeaderTitleController) o;
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
}
