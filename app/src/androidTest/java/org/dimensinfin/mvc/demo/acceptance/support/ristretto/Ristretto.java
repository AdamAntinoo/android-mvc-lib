package org.dimensinfin.mvc.demo.acceptance.support.ristretto;

import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.awaitility.Awaitility;

import org.dimensinfin.mvc.demo.acceptance.support.core.AcceptanceNeoComLogger;
import org.dimensinfin.mvc.demo.acceptance.support.core.World;
import org.dimensinfin.android.mvc.activity.MVCMultiPageActivity;
import org.dimensinfin.android.mvc.activity.MVCPagerFragment;
import org.dimensinfin.android.mvc.controller.IAndroidController;
import org.dimensinfin.android.mvc.core.MVCScheduler;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Ristretto {
	private static final int WAIT_TIMEOUT = 70;

	// - W O R L D
	private static World world;

	public static void setWorld( final World newworld ) {
		world = newworld;
	}

	// - F I L T E R I N G
//	public static ViewContainer onContainer( final LinearLayout linear ) {
//		final ViewContainer container = new ViewContainer();
//		for (int i = 0; i < linear.getChildCount(); i++)
//			container.add( linear.getChildAt( i ) );
//		return container;
//	}
//
//	public static ViewContainer onContainer( final ViewGroup linear ) {
//		final ViewContainer container = new ViewContainer();
//		for (int i = 0; i < linear.getChildCount(); i++)
//			container.add( linear.getChildAt( i ) );
//		return container;
//	}

	// - A C T I V I T Y   M A N A G E M E N T
	public static int activityPageCount() {
		return ((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getCount();
	}

	public static ViewGroup accessHeaderContainer( final int page ) {
		final Fragment fragment = Objects.requireNonNull(
				((MVCMultiPageActivity) world.getActiveActivity()).accessPageAdapter().getItem( page )
		);
		return ((MVCPagerFragment) fragment).accessHeaderContainer();
	}

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
	public static List<IAndroidController> withTypex<T>( final List<IAndroidController> controllers, final Class type ) {
		final List<IAndroidController> results = new ArrayList<>();
		for (IAndroidController controller : controllers)
			if (type.isInstance( controller ))
				results.add( controller );
		return results;
	}

	// - S Y N C H R O N I Z A T I O N
	public static void waitForCompletion( final Runnable callback ) {
		AcceptanceNeoComLogger.enter();
		// Add the callback behind the latest scheduler run and ui run.
		final Monitor monitor = new Monitor();
		world.getActiveActivity().runOnUiThread( callback );
		world.getActiveActivity().runOnUiThread( monitor::activateTrigger );
		Awaitility.await().atMost( WAIT_TIMEOUT, SECONDS ).until( () -> monitor.isTriggered() );
		AcceptanceNeoComLogger.exit();
	}

	public static void waitForBackground( final Runnable callback ) {
		AcceptanceNeoComLogger.enter();
		// Add the callback behind the latest scheduler run and ui run.
		final Monitor monitor = new Monitor();
		MVCScheduler.backgroundExecutor.submit( callback );
		MVCScheduler.backgroundExecutor.submit( monitor::activateTrigger );
		Awaitility.await().atMost( WAIT_TIMEOUT, SECONDS ).until( () -> monitor.isTriggered() );
		AcceptanceNeoComLogger.exit();
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
			AcceptanceNeoComLogger.enter();
			this.trigger = true;
		}

		public Boolean isTriggered() {
			return this.trigger;
		}
	}

//	public static Matcher<View> withPanelType( final Matcher<View> matcher, final Class type ) {
//		return new TypeSafeMatcher<View>() {
//			@Override
//			public void describeTo( Description description ) {
//				description.appendText( "with panel type: " );
//				description.appendValue( type.getSimpleName() );
//				matcher.describeTo( description );
//			}
//
//			/**
//			 * Find views that have attached a controller of the specified type.
//			 * @param view
//			 * @return
//			 */
//			@Override
//			public boolean matchesSafely( View view ) {
//				if (matcher.matches( view )) {
//					final Object controller = view.getTag();
//					if (null != controller)
//						if (type.isInstance( controller ))
//							return true;
//				}
//				return false;
//			}
//		};
//	}

//	private static final int EMPTY = -1;
//	private static final int ANY = -2;
//
//	public static boolean withDrawable( final View target, final int resourceId ) {
//		if (!(target instanceof ImageView)) {
//			return false;
//		}
//		ImageView imageView = (ImageView) target;
//		if (resourceId == EMPTY) {
//			return imageView.getDrawable() == null;
//		}
//		if (resourceId == ANY) {
//			return imageView.getDrawable() != null;
//		}
//		Resources resources = target.getContext().getResources();
//		Drawable expectedDrawable = resources.getDrawable( resourceId );
////		resourceName = resources.getResourceEntryName( resourceId );
//
//		if (expectedDrawable == null) {
//			return false;
//		}
//
//		Bitmap bitmap = getBitmap( imageView.getDrawable() );
//		Bitmap otherBitmap = getBitmap( expectedDrawable );
//		return bitmap.sameAs( otherBitmap );
//	}
//
//	private static Bitmap getBitmap( Drawable drawable ) {
//		Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );
//		Canvas canvas = new Canvas( bitmap );
//		drawable.setBounds( 0, 0, canvas.getWidth(), canvas.getHeight() );
//		drawable.draw( canvas );
//		return bitmap;
//	}

//	public static Matcher<View> withDrawable( final int resourceId ) {
//		return new DrawableMatcher( resourceId );
//	}

//	public static Matcher<View> noDrawable() {
//		return new DrawableMatcher( -1 );
//	}
}
