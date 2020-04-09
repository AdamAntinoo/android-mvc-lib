package org.dimensinfin.android.mvc.render;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

import org.dimensinfin.android.mvc.R;
import org.dimensinfin.android.mvc.controller.ProgressSpinnerController;

public class ProgressSpinnerRender extends TypedRender<ProgressSpinnerController> {
	private TextView taskName;
	private Chronometer elapsedTime;
	private ImageView spinner;

	public ProgressSpinnerRender( @NonNull final ProgressSpinnerController controller, @NonNull final Context context ) {
		super( controller, context );
	}

	// - I R E N D E R
	@Override
	public int accessLayoutReference() {
		return R.layout.progressspinner4header;
	}

	@Override
	public void initializeViews() {
		this.taskName = Objects.requireNonNull( this.getView().findViewById( R.id.taskName ) );
		this.elapsedTime = Objects.requireNonNull( this.getView().findViewById( R.id.elapsedTime ) );
		this.spinner = Objects.requireNonNull( this.getView().findViewById( R.id.spinner ) );
		this.taskName.setVisibility( View.INVISIBLE );
		this.elapsedTime.setVisibility( View.VISIBLE );
		this.spinner.setVisibility( View.VISIBLE );
	}

	@Override
	public void updateContent() {
//		this.taskName.setText(this.getController().getModel().getTaskName());
//		this.taskName.setText(this.getController().getModel().getTaskName());
//		final InitialisationTask.ETaskStatus status = this.getController().getModel().getStatus();
//		if ( status == InitialisationTask.ETaskStatus.SCHEDULED)
//			spinner.setVisibility(View.VISIBLE);
//		if ( status == InitialisationTask.ETaskStatus.EXECUTING) {
//			spinner.setVisibility(View.VISIBLE);
		Animation rotation = AnimationUtils.loadAnimation( getContext(), org.dimensinfin.android.mvc.R.anim.clockwise_rotation );
		rotation.setRepeatCount( Animation.INFINITE );
		spinner.startAnimation( rotation );
		this.elapsedTime.start();
//		final Instant elapsedTimer = Instant.now();
//		new CountDownTimer( TimeUnit.DAYS.toMillis( 1 ), TimeUnit.MILLISECONDS.toMillis( 10 ) ) {
//			@Override
//			public void onTick( final long millisUntilFinished ) {
//				elapsedTime.setText( generateTimeString( elapsedTimer.getMillis() ) );
////				_progressElapsedCounter.invalidate();
//			}
//
//			@Override
//			public void onFinish() {
//				elapsedTime.setText( generateTimeString( elapsedTimer.getMillis() ) );
////				_progressElapsedCounter.invalidate();
//			}
//		}.start();
//		}
	}

//	private String generateTimeString( final long millis ) {
//		try {
//			final long elapsed = Instant.now().getMillis() - millis;
//			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
//			if (elapsed > TimeUnit.HOURS.toMillis( 1 )) {
//				timeFormatter.appendHourOfDay( 2 ).appendLiteral( "h " );
//			}
//			if (elapsed > TimeUnit.MINUTES.toMillis( 1 )) {
//				timeFormatter.appendMinuteOfHour( 2 ).appendLiteral( "m " ).appendSecondOfMinute( 2 ).appendLiteral( "s" );
//			} else timeFormatter.appendSecondOfMinute( 2 ).appendLiteral( "s" );
//			return timeFormatter.toFormatter().print( new Instant( elapsed ) );
//		} catch (final RuntimeException rtex) {
//			return "0m 00s";
//		}
//	}
}
