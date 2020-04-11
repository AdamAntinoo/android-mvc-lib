package org.dimensinfin.mvc.demo.render;

import android.content.Context;
import android.widget.ImageView;

import java.util.Objects;

import org.dimensinfin.mvc.demo.R;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureController$Base;
import org.dimensinfin.mvc.demo.domain.ColorfulFigureRender$Base;

public class ColorfulFigureRender extends ColorfulFigureRender$Base {
	private ImageView figure;

	public ColorfulFigureRender( final ColorfulFigureController$Base controller, final Context context ) {
		super( controller, context );
	}

	@Override
	public void initializeViews() {
		super.initializeViews();
		this.figure = Objects.requireNonNull( this.getView().findViewById( R.id.figure ) );
	}

	@Override
	public void updateContent() {
		this.label.setText( this.getController().getModel().getLabel() );
		switch (this.getController().getModel().getFigure()) {
			case TRIANGLE:
				this.figure.setImageDrawable( this.getContext().getResources().getDrawable( R.drawable.triangle ) );
				break;
		}
	}
}
