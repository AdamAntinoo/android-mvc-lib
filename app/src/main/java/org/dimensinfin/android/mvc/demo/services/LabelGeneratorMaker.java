package org.dimensinfin.android.mvc.demo.services;

import dagger.Component;

@Component(modules = LabelGeneratorModule.class)
public interface LabelGeneratorMaker {
	LabelGenerator maker();
}
