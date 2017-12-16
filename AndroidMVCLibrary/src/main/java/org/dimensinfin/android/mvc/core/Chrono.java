package org.dimensinfin.android.mvc.core;


import org.dimensinfin.android.mvc.constants.SystemWideConstants;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * Created by Adam on 16/12/2017.
 */

public class Chrono {
	private Instant _startPoint=null;
	public Chrono(){
		_startPoint= Instant.now();
	}
	public String printElapsed(){
		return generateTimeString(Instant.now().getMillis()-_startPoint.getMillis());
	}
	protected String generateTimeString (final long millis) {
		try {
			final long elapsed = Instant.now().getMillis() - millis;
			final DateTimeFormatterBuilder timeFormatter = new DateTimeFormatterBuilder();
			if ( elapsed > SystemWideConstants.ONEHOUR ) {
				timeFormatter.appendHourOfDay(2).appendLiteral("h ");
			}
			if ( elapsed > SystemWideConstants.ONEMINUTE ) {
				timeFormatter.appendMinuteOfHour(2).appendLiteral("m ").appendSecondOfMinute(2).appendLiteral("s");
			}
			return timeFormatter.toFormatter().print(new Instant(elapsed));
		} catch (final RuntimeException rtex) {
			return "0m 00s";
		}
	}
}
