package org.dimensinfin.android.mvc.domain;

import org.dimensinfin.core.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

public class Spacer implements ICollaboration {
	private String label;
	private SpacerType type = SpacerType.LINE_WHITE;

	public Spacer() { }

	public String getLabel() {
		return this.label;
	}

	public SpacerType getType() {
		return this.type;
	}

	public Spacer setLabel( final String label ) {
		this.label = label;
		return this;
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object o ) {
		final Spacer target = (Spacer) o;
		return this.label.compareTo(target.label);
	}

	// - B U I L D E R
	public static class Builder {
		private Spacer onConstruction;

		public Builder() {
			this.onConstruction = new Spacer();
		}

		public Spacer.Builder withLabel( final String label ) {
			this.onConstruction.label = label;
			return this;
		}

		public Spacer.Builder withType( final SpacerType type ) {
			this.onConstruction.type = type;
			return this;
		}

		public Spacer build() {
			return this.onConstruction;
		}
	}
}
