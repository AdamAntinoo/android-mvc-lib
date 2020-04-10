//  PROJECT:     corebase.model (CORE.M)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2013-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Java 1.7.
//  DESCRIPTION: Library that defines the model classes to implement the core for a GEF based
//               Model-View-Controller. Code is as neutral as possible and made to be reused
//               on all Java development projects.
package org.dimensinfin.core.util;

import java.util.concurrent.TimeUnit;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

// - CLASS IMPLEMENTATION ...................................................................................
public class Chrono {
	public enum ChronoOptions {
		DEFAULT, STANDARD, SHOWMILLIS, SHOWSECONDS
	}

	// - S T A T I C - S E C T I O N ..........................................................................

	// - F I E L D - S E C T I O N ............................................................................
	private Instant _startPoint = null;

	// - C O N S T R U C T O R - S E C T I O N ................................................................
	public Chrono() {
		_startPoint = Instant.now();
	}

	// - M E T H O D - S E C T I O N ..........................................................................
	public Chrono setMillis( final long newmillis ) {
		if (null != _startPoint) _startPoint = Instant.now().plus(newmillis);
		return this;
	}

	public String printElapsed() {
		return printElapsed(ChronoOptions.DEFAULT);
	}

	public String printElapsed( final ChronoOptions flag ) {
		if (flag == ChronoOptions.STANDARD)
			return generateTimeStringStandard(Instant.now().getMillis() - _startPoint.getMillis());
		return generateDurationString(Instant.now().getMillis() - _startPoint.getMillis(), flag);
	}

	protected String generateTimeStringStandard( final long millis ) {
		try {
			final long elapsed = millis;
			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
			if (elapsed > TimeUnit.DAYS.toMillis(1)) {
				timeFormatter.appendDayOfMonth(2).appendLiteral("d ");
			}
			if (elapsed > TimeUnit.HOURS.toMillis(1)) {
				timeFormatter.appendHourOfDay(2).appendLiteral(":");
			}
			if (elapsed > TimeUnit.MINUTES.toMillis(1)) {
				timeFormatter.appendMinuteOfHour(2).appendLiteral(":");
			}
			if (elapsed > TimeUnit.SECONDS.toMillis(1)) {
				timeFormatter.appendSecondOfMinute(2).appendLiteral(".").appendMillisOfSecond(3);
			} else timeFormatter.appendLiteral("0.").appendMillisOfSecond(3);
			return timeFormatter.toFormatter().print(new Instant(elapsed));
		} catch (final RuntimeException rtex) {
			return "0:00";
		}
	}

	protected String generateTimeString( final long millis ) {
		try {
			final long elapsed = millis;
			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
			if (elapsed > TimeUnit.DAYS.toMillis(1)) {
				timeFormatter.appendDayOfMonth(2).appendLiteral("d ");
			}
			if (elapsed > TimeUnit.HOURS.toMillis(1)) {
				timeFormatter.appendHourOfDay(2).appendLiteral("h ");
			}
			if (elapsed > TimeUnit.MINUTES.toMillis(1)) {
				timeFormatter.appendMinuteOfHour(2).appendLiteral("m ").appendSecondOfMinute(2).appendLiteral("s");
			} else timeFormatter.appendSecondOfMinute(1).appendLiteral("s");
			return timeFormatter.toFormatter().print(new Instant(elapsed));
		} catch (final RuntimeException rtex) {
			return "0m 00s";
		}
	}

	protected String generateTimeStringMillis( final long millis ) {
		try {
			final long elapsed = millis;
			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
			if (elapsed > TimeUnit.DAYS.toMillis(1)) {
				timeFormatter.appendDayOfMonth(2).appendLiteral("d ");
			}
			if (elapsed >TimeUnit.HOURS.toMillis(1)) {
				timeFormatter.appendHourOfDay(2).appendLiteral("h ");
			}
			if (elapsed > TimeUnit.MINUTES.toMillis(1)) {
				timeFormatter.appendMinuteOfHour(2).appendLiteral("m ");
			}
			if (elapsed > TimeUnit.SECONDS.toMillis(1)) {
				timeFormatter.appendSecondOfMinute(1).appendLiteral("s ").appendMillisOfSecond(3).appendLiteral("ms");
			} else timeFormatter.appendLiteral("0s ").appendMillisOfSecond(3).appendLiteral("ms");
			return timeFormatter.toFormatter().print(new Instant(elapsed));
		} catch (final RuntimeException rtex) {
			return "0m 00s";
		}
	}

	protected String generateDurationString( final long millis, final ChronoOptions format ) {
		Duration duration = new Duration(millis);
		PeriodFormatter formatter = null;
		if (format == ChronoOptions.SHOWMILLIS) {
			formatter = new PeriodFormatterBuilder()
					.appendDays()
					.appendSuffix("d ")
					.appendHours()
					.appendSuffix("h ")
					.appendMinutes()
					.appendSuffix("m ")
					.appendSeconds()
					.appendSuffix("s ")
					.appendMillis()
					.appendSuffix("ms")
					.toFormatter();
		} else {
			if (millis <= TimeUnit.SECONDS.toMillis(1)) return "0s";
			formatter = new PeriodFormatterBuilder()
					.appendDays()
					.appendSuffix("d ")
					.appendHours()
					.appendSuffix("h ")
					.appendMinutes()
					.appendSuffix("m ")
					.appendSeconds()
					.appendSuffix("s")
					.toFormatter();
		}
		return formatter.print(duration.toPeriod());
	}
}

// - UNUSED CODE ............................................................................................
