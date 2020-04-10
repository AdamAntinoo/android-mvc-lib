package org.dimensinfin.core.domain;

import java.util.ArrayList;
import java.util.List;

import org.dimensinfin.core.interfaces.ICollaboration;

public class Node implements ICollaboration {
	protected Node() {}

	public String getJsonClass() {
		return this.getClass().getSimpleName();
	}

	// - I C O L L A B O R A T I O N
	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	/**
	 * MVC nodes always compare as equals to them and to any other instance because they have no content at all. The single data that can be get
	 * from them is calculated for its class name.
	 */
	@Override
	public int compareTo( final Object target ) {
		return 0;
	}

	// - B U I L D E R
	public static class Builder {
		private Node onConstruction;

		public Builder() {
			this.onConstruction = new Node();
		}

		public Node build() {
			return this.onConstruction;
		}
	}
}
