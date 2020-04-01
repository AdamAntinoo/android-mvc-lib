package org.dimensinfin.mvc.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabelGenerator {
	private LabelGenerator() {}

	public List<String> generateLabels( final int labelCount ) {
		final List<String> results = new ArrayList<>();
		for (int index = 0; index < labelCount; index++)
			results.add( this.generateRandomLabel() );
		return results;
	}

	private String generateRandomLabel() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder( targetStringLength );
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int)
					                                   (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append( (char) randomLimitedInt );
		}
		String generatedString = buffer.toString();

		return generatedString;
	}

	// - B U I L D E R
	public static class Builder {
		private LabelGenerator onConstruction;

		public Builder() {
			this.onConstruction = new LabelGenerator();
		}

		public LabelGenerator build() {
			return this.onConstruction;
		}
	}
}
