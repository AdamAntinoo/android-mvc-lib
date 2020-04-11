package org.dimensinfin.mvc.demo.render;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.render.MVCExpandableRender;
import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.controller.FigureGroupController;

public class FigureGroupRender extends MVCExpandableRender {
	private TextView title;

	public FigureGroupRender( @NonNull final FigureGroupController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	@Override
	public FigureGroupController getController() {
		return (FigureGroupController) super.getController();
	}

	// - I R E N D E R
	@Override
	public void initializeViews() {
		super.initializeViews();
		this.title = Objects.requireNonNull( this.getView().findViewById( R.id.title ) );
		this.title.setVisibility( View.VISIBLE );
	}

	@Override
	public void updateContent() {
		super.updateContent();
		this.title.setText( this.getController().getModel().getLabel() );
	}

	@Override
	public int accessLayoutReference() {
		return R.layout.figuregroup4list;
	}
}