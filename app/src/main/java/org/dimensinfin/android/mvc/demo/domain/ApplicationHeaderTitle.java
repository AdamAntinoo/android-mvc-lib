package org.dimensinfin.android.mvc.demo.domain;

import java.util.Objects;

import org.dimensinfin.android.mvc.core.domain.MVCNode;

public class ApplicationHeaderTitle extends MVCNode {
//	public static ApplicationHeaderTitle provideApplicationHeaderTitle() {
//		return new ApplicationHeaderTitle.Builder().build();
//	}

	private String name;
	private String version;

	private ApplicationHeaderTitle() {}

	public String getName() {
		return this.name;
	}

	public String getVersion() {
		return this.version;
	}

	// - B U I L D E R
	public static class Builder {
		private ApplicationHeaderTitle onConstruction;

		public Builder() {
			this.onConstruction = new ApplicationHeaderTitle();
		}

		public ApplicationHeaderTitle.Builder withApplicationName( final String applicationName ) {
			Objects.requireNonNull( applicationName );
			this.onConstruction.name = applicationName;
			return this;
		}

		public ApplicationHeaderTitle.Builder withApplicationVersion( final String applicationVersion ) {
			Objects.requireNonNull( applicationVersion );
			this.onConstruction.version = applicationVersion;
			return this;
		}

		public ApplicationHeaderTitle build() {
			return this.onConstruction;
		}
	}
}
