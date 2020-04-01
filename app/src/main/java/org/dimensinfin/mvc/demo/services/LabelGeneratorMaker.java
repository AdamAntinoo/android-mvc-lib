package org.dimensinfin.mvc.demo.services;

import dagger.Component;

@Component(modules = LabelGeneratorModule.class)
public interface LabelGeneratorMaker {
	LabelGenerator maker();
}
