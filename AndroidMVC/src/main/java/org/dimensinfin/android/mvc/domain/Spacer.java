package org.dimensinfin.android.mvc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.core.interfaces.ICollaboration;

/**
 * The spacer is a default node type that will represent a separation between elements. Separators are rendered like the <b>HR</b> HTML tags and
 * are available in different colours. The can contain an optional label. If the label exists it is displayed inside the spacer area.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 2.0.0
 */
public class Spacer implements ICollaboration {
	private String label;
	private SpacerType type = SpacerType.LINE_WHITE;

	public Spacer() { }

	public String getLabel() {
		return this.label;
	}

	@Deprecated
	public Spacer setLabel( final String label ) {
		this.label = label;
		return this;
	}

	public SpacerType getType() {
		return this.type;
	}

	@Override
	public List<ICollaboration> collaborate2Model( final String variation ) {
		return new ArrayList<>();
	}

	@Override
	public int compareTo( final Object o ) {
		final Spacer target = (Spacer) o;
		return this.label.compareTo( target.label );
	}

	// - B U I L D E R
	public static class Builder {
		private Spacer onConstruction;

		public Builder() {
			this.onConstruction = new Spacer();
		}

		public Spacer.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Spacer.Builder withType( final SpacerType type ) {
			this.onConstruction.type = Objects.requireNonNull( type );
			return this;
		}

		public Spacer build() {
			return this.onConstruction;
		}
	}
}
