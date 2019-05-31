package org.dimensinfin.android.mvc.support;

import android.content.Context;
import androidx.annotation.NonNull;

import org.dimensinfin.android.mvc.controller.AAndroidController;
import org.dimensinfin.android.mvc.interfaces.IControllerFactory;
import org.dimensinfin.android.mvc.interfaces.IRender;

public class MockController extends AAndroidController<EmptyNode> {
	//	private boolean visible = true;

	public MockController( @NonNull final EmptyNode model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	//	public MockController(@NonNull final ControllerAdapter<EmptyNode> delegate, @NonNull final IControllerFactory factory) {
	//		super(delegate, factory);
	//	}

	// - C O N S T R U C T O R - S E C T I O N
	//	public MockController(@NonNull final IControllerFactory factory) {
	//		super(factory);
	//	}

	//	public MockController(@NonNull final EmptyNode model, @NonNull final IControllerFactory factory) {
	//		super(new ControllerAdapter<EmptyNode>(model), factory);
	//		// Connect the delegate.
	//		//		this.delegatedController = ;
	//	}

	// - D E L E G A T E D - A A N D R O I D C O N T R O L L E R
	//	public EmptyNode getModel() {
	//		return delegatedController.getModel();
	//	}

	// - O V E R R I D E - A A N D R O I D C O N T R O L L E R
	//	@Override
	//	public long getModelId() {
	//		return 0;
	//	}

	@Override
	public IRender buildRender( final Context context ) {
		return new MockRender(this, context);
	}

	//	@Override
	//	public boolean isVisible() {
	//		return visible;
	//	}

	//	public MockController setVisible(final boolean visible) {
	//		this.visible = visible;
	//		return this;
	//	}

	// - C O R E
	//	@Override
	//	public boolean equals(final Object o) {
	//		if (this == o) return true;
	//		if (o == null || getClass() != o.getClass()) return false;
	//		final MockController that = (MockController) o;
	//		if (super.equals(o))
	//			return new EqualsBuilder()
	////					.append(visible, that.visible)
	////					.append(delegatedController, that.delegatedController)
	//					.isEquals();
	//		else return false;
	//	}
	//
	//	@Override
	//	public int hashCode() {
	//		return new HashCodeBuilder(17, 37)
	////				.append(delegatedController)
	//				.append(visible)
	//				.append(this.getModel().getName())
	//				.toHashCode();
	//	}

	//	@Override
	//	public int compareTo(@NonNull final Object o) {
	////		if (o instanceof MockController) {
	////			final MockController target = (MockController) o;
	//		return this.getModel().compareTo(((MockController) o).getModel());
	////		} else return -1;
	//	}
}
