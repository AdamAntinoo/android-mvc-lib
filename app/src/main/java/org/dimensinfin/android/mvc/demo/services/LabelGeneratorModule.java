package org.dimensinfin.android.mvc.demo.services;

import dagger.Module;
import dagger.Provides;

@Module
public class LabelGeneratorModule {
	@Provides
	public  LabelGenerator provideLabelGenerator() {
		return new LabelGenerator.Builder().build();
	}
}
