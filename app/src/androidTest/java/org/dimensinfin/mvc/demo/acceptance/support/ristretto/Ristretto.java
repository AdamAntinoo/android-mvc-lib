package org.dimensinfin.mvc.demo.acceptance.support.ristretto;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.awaitility.Awaitility;

import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.core.MVCScheduler;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.mvc.demo.acceptance.support.core.World;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Ristretto {
	private static final int WAIT_TIMEOUT = 70;

	// - W O R L D
	private static World world;

	public static void setWorld( final World newworld ) {
		world = newworld;
	}

	// - A C T I V I T Y   M A N A G E M E N T
	public static int activityPageCount() {
		return ((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getCount();
	}

	public static int setActivePage( final int targetPage ) {
		((MVCMultiPageActivity) world.getActiveActivity()).setPage( targetPage );
		return targetPage;
	}

	@Deprecated
	public static ViewGroup accessHeaderContainer( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessHeaderContainer();
	}

	@Deprecated
	public static ViewGroup accessDataContainer( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessDataSectionContainer();
	}

	public static List<IAndroidController> accessHeaderPanels( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessHeaderContents();
	}

	public static List<IAndroidController> accessDataPanels( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessDataContents();
	}

	public static int headerContentsCount( final int page ) {
		return Ristretto.accessHeaderPanels( page ).size();
	}

	public static int dataContentsCount( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessDataContents().size();
	}

	public static List<IAndroidController> withType( final List<IAndroidController> controllers, final Class type ) {
		final List<IAndroidController> results = new ArrayList<>();
		for (IAndroidController controller : controllers)
			if (type.isInstance( controller ))
				results.add( controller );
		return results;
	}

	public static List<?> withTypex( final List<IAndroidController> controllers, final TypeMatcher matcher ) {
		return matcher.match( controllers );
	}

	// - S Y N C H R O N I Z A T I O N
	public static void waitForCompletion( final Runnable callback ) {
		LogWrapper.enter();
		// Add the callback behind the latest scheduler run and ui run.
		final Monitor monitor = new Monitor();
		world.getActiveActivity().runOnUiThread( callback );
		world.getActiveActivity().runOnUiThread( monitor::activateTrigger );
		Awaitility.await().atMost( WAIT_TIMEOUT, SECONDS ).until( monitor::isTriggered );
		LogWrapper.exit();
	}

	public static void waitForBackground( final Runnable callback ) {
		LogWrapper.enter();
		// Add the callback behind the latest scheduler run and ui run.
		final Monitor monitor = new Monitor();
		MVCScheduler.backgroundExecutor.submit( callback );
		MVCScheduler.backgroundExecutor.submit( monitor::activateTrigger );
		Awaitility.await().atMost( WAIT_TIMEOUT, SECONDS ).until( monitor::isTriggered );
		LogWrapper.exit();
	}

	@Deprecated
	public static void waitForCompletion( final World newworld, final Runnable callback ) {
		world = newworld;
		waitForCompletion( callback );
	}

	public static void updateDisplay() {
		final MVCMultiPageActivity activity = (MVCMultiPageActivity) world.getActiveActivity();
		final MVCPagerFragment fragment = (MVCPagerFragment) activity.accessPageAdapter().getItem( 0 );
		fragment.updateDisplay();
	}

	// - M O N I T O R
	private static final class Monitor {
		private boolean trigger = false;

		public void activateTrigger() {
			LogWrapper.enter();
			this.trigger = true;
		}

		public Boolean isTriggered() {
			return this.trigger;
		}
	}
}
