package org.dimensinfin.android.mvc.parts;

final class TestModel {
	private String test;

	private TestModel(final Builder builder) {
		this.test = builder.test;
	}

	public static class Builder {
		private String test;

		public TestModel.Builder withTestField(final String test) {
			this.test = test;
			return this;
		}

		public TestModel build() {
			return new TestModel(this);
		}
	}
}
