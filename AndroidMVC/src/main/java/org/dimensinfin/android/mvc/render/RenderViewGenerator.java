package org.dimensinfin.android.mvc.render;

import android.content.Context;
import android.view.View;

import java.util.Objects;

import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.domain.IRender;
import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.core.interfaces.ICollaboration;

public class RenderViewGenerator<M extends ICollaboration> {
	private IAndroidController<M> controller;
	private IRender render;
	// - C O M P O N E N T S
	private Context context;
	private M model;
	private IControllerFactory factory;

	private RenderViewGenerator() {}

	// - B U I L D E R
	public static class Builder<M extends ICollaboration> {
		private RenderViewGenerator<M> onConstruction;

		public Builder() {
			this.onConstruction = new RenderViewGenerator<>();
		}

		public Builder<M> withContext( final Context context ) {
			Objects.requireNonNull( context );
			this.onConstruction.context = context;
			return this;
		}

		public Builder<M> withModel( final M model ) {
			Objects.requireNonNull( model );
			this.onConstruction.model = model;
			return this;
		}

		public Builder<M> withFactory( final IControllerFactory factory ) {
			Objects.requireNonNull( factory );
			this.onConstruction.factory = factory;
			return this;
		}

		public View generateView() {
			Objects.requireNonNull( this.onConstruction.context );
			Objects.requireNonNull( this.onConstruction.model );
			Objects.requireNonNull( this.onConstruction.factory );
			this.onConstruction.controller = this.onConstruction.factory.createController( this.onConstruction.model );
			this.onConstruction.render = this.onConstruction.controller.buildRender(
					this.onConstruction.context );
			final View createdView = this.onConstruction.render.getView();
			this.onConstruction.render.updateContent(); // This update is not performed during the view creation.
			return createdView;
		}
	}
}
