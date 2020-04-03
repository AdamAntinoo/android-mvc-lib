package org.dimensinfin.android.mvc.support;

import java.util.List;

import org.dimensinfin.core.interfaces.ICollaboration;

public class Test4ModelFailure implements ICollaboration {
	private Test4ModelFailure() {}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return null;
	}

	@Override
	public int compareTo( final Object o ) {
		return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private Test4ModelFailure onConstruction;

		public Builder() {
			this.onConstruction = new Test4ModelFailure();
		}

		public Test4ModelFailure build() {
			return this.onConstruction;
		}
	}
}
