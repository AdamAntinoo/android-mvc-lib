package org.dimensinfin.mvc.demo.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.dimensinfin.android.mvc.datasource.MVCDataSource;
import org.dimensinfin.mvc.demo.domain.ColorfulFigure;
import org.dimensinfin.mvc.demo.domain.Colours;
import org.dimensinfin.mvc.demo.domain.Figures;

public abstract class FigureColorDataSource extends MVCDataSource {
	protected List<ColorfulFigure> elements = new ArrayList<>();
	// - I D A T A S O U R C E

	/**
	 * This sample shows how a single content generator can be used to render the data in different presentations. The Figure Color will generate a
	 * list of figures of different colours. There are two renders. By colour and by figure but both use the same data model.
	 */
	@Override
	public void prepareModel() throws Exception {
		for (int i = 0; i < 10; i++)
			this.elements.add( new ColorfulFigure.Builder()
					                   .withColour( Colours.decode( new Random().nextInt( 4 ) + 1 ) )
					                   .withFigure( Figures.decode( new Random().nextInt( 4 ) + 1 ) )
					                   .build()
			);
	}
}
