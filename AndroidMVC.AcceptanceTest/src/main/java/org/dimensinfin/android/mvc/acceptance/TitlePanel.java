package org.dimensinfin.android.mvc.acceptance;

import org.dimensinfin.core.interfaces.ICollaboration;

import java.util.ArrayList;
import java.util.List;

public class TitlePanel implements ICollaboration {
	private String title;

	public String getTitle() {
		return this.title;
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object o ) {
		return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private TitlePanel onConstruction;

		public Builder() {
			this.onConstruction = new TitlePanel();
		}

		public TitlePanel.Builder withTitle( final String title ) {
			this.onConstruction.title = title;
			return this;
		}

		public TitlePanel build() {
			return this.onConstruction;
		}
	}
}
