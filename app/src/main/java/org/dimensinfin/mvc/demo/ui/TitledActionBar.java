package org.dimensinfin.mvc.demo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.core.interfaces.ICollaboration;

public class TitledActionBar implements ICollaboration {
	private String title = "-TITLE-";

	private TitledActionBar() {}

	public String getTitle() {
		return this.title;
	}

	// - I C O L L A B O R A T I O N
	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object target ) {
		if (target instanceof TitledActionBar)
			return this.title.compareTo( ((TitledActionBar) target).title );
		else return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private TitledActionBar onConstruction;

		public Builder() {
			this.onConstruction = new TitledActionBar();
		}

		public TitledActionBar.Builder withTitle( final String title ) {
			this.onConstruction.title = Objects.requireNonNull( title );
			return this;
		}

		public TitledActionBar build() {
			Objects.requireNonNull( this.onConstruction.title );
			return this.onConstruction;
		}
	}
}
