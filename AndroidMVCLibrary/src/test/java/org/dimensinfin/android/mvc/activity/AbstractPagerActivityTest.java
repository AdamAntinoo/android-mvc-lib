package org.dimensinfin.android.mvc.activity;

import android.support.annotation.NonNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AbstractPagerActivity.class, LoggerFactory.class})
public class AbstractPagerActivityTest {
	private class PagerActivityTest extends AbstractPagerActivity {
//		@Override
//		public void addPage( @NonNull AbstractPagerFragment newFrag ) {
//		}
	}
	@Test
	public void addPage_null(){
		// Given
		mockStatic(LoggerFactory.class);
		Logger logger = mock(Logger.class);
		when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);

//		final Logger logger = mock(Logger.class);
//		final LoggerFactory loggerFactory = mock(LoggerFactory.class);
//		final AbstractPagerActivity abstractPagerActivity = mock  ( AbstractPagerActivity.class);

		// Tests
		final PagerActivityTest pagerActivityTest = new PagerActivityTest();
		pagerActivityTest.addPage(null);

	}
	@Test
	public void addPage_page(){
		// Given
		final Logger logger = mock(Logger.class);
		final LoggerFactory loggerFactory = mock(LoggerFactory.class);
//		final AbstractPagerActivity abstractPagerActivity = mock  ( AbstractPagerActivity.class);
		final AbstractPagerFragment abstractPagerFragment = mock(AbstractPagerFragment.class);

		// Tests
		final PagerActivityTest pagerActivityTest = new PagerActivityTest();
		pagerActivityTest.addPage(abstractPagerFragment);

	}
}
