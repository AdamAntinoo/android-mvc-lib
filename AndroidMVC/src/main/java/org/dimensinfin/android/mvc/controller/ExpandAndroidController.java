package org.dimensinfin.android.mvc.controller;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.dimensinfin.android.mvc.factory.IControllerFactory;
import org.dimensinfin.core.domain.EEvents;
import org.dimensinfin.core.interfaces.IExpandable;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

/**
 * @author Adam Antinoo
 * @since 2.0.0
 */
public abstract class ExpandAndroidController<M extends IExpandable> extends AndroidController<M> implements View.OnClickListener {
	protected static final Handler _handler = new Handler(Looper.getMainLooper());

	private ClickSupporter clickSupporter = new ClickSupporter(false);

	public ExpandAndroidController( @NonNull final M model, @NonNull final IControllerFactory factory ) {
		super(model, factory);
	}

	public boolean clickRunning () {
		return this.clickSupporter.isClickRunning();
	}
	// - V I E W . O N C L I C K L I S T E N E R
	@Override
	public void onClick(final View view) {
		logger.info(">> [ExpandAndroidController.onClick]");
		// Signal the action may take some time and launch it on the background.
		this.clickSupporter.activateClick();
		this.notifyDataModelChange();  // Signal the view needs update

		_handler.postDelayed(() -> {
			logger.info("-- [ExpandAndroidController.onClick.run]");
//			final Instant chrono = Instant.now();
			this.getModel().toggleExpand();
			this.notifyDataModelChange(EEvents.EVENT_ACTIONEXPANDCOLLAPSE);
			this.clickSupporter.completeClick();
//			logger.info("<< [ExpandAndroidController.onClick.run]> Time Elapsed: {}ms",
//					new Duration(chrono, Instant.now()).getMillis());
		}, TimeUnit.MILLISECONDS.toMillis(1000));
		logger.info("<< [NeoComExpandablePart.onClick]");
	}

	// - C L I C K S U P P O R T E R   C L A S S
	public static class ClickSupporter {
		// - F I E L D - S E C T I O N
		private boolean clickRunning = false;

		// - C O N S T R U C T O R - S E C T I O N
		public ClickSupporter(final boolean clickRunning) {
			this.clickRunning = clickRunning;
		}

		// - M E T H O D - S E C T I O N
		public boolean isClickRunning() {
			return clickRunning;
		}

		public void setClickRunning(final boolean clickRunning) {
			this.clickRunning = clickRunning;
		}

		public boolean activateClick() {
			this.setClickRunning(true);
			return this.isClickRunning();
		}

		public boolean completeClick() {
			this.setClickRunning(false);
			return this.isClickRunning();
		}

		// -  C O R E

		@Override
		public boolean equals(final Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			final ClickSupporter that = (ClickSupporter) o;
			return new EqualsBuilder()
					.append(clickRunning, that.clickRunning)
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37)
					.append(clickRunning)
					.toHashCode();
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this)
					.append("clickRunning", clickRunning)
					.toString();
		}
	}
}
