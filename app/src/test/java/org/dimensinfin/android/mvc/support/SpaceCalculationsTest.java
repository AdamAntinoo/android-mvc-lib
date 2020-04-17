package org.dimensinfin.android.mvc.support;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import org.dimensinfin.core.domain.MassUnit;
import org.dimensinfin.core.domain.SpeedUnit;
import org.dimensinfin.logging.LogWrapper;

public class SpaceCalculationsTest {
	private static double earthOrbitRadius_km = 149598261.0;
	private static double marsOrbitRadius_km = 249200000.0;
	private static double shipMass_kg = new Double( MassUnit.TONS.toKilograms( 100.0 ) );
	private static double gasSpeed_kms = new Double( SpeedUnit.KMH.toKMS( 180000.0 ) );

	private double accelerationSegment_km;

	@Test
	public void orbitDistance() {
		// Add for additional travel by not using synchronization
		final double earthMarsDistance_km = (marsOrbitRadius_km - earthOrbitRadius_km) * 1.16;
		LogWrapper.info( "earthMarsDistance_km={}", Double.toString( earthMarsDistance_km ) );
		// Only 4 of 5 segments are done with acceleration (two for acceleration and two for deceleration)
		this.accelerationSegment_km = earthMarsDistance_km / 5.0;
		LogWrapper.info( "accelerationSegment_km={}", Double.toString( this.accelerationSegment_km ) );
	}

	@Test
	public void mass2Burn() {
		final double requiredShipSpeed_cms = new Double( SpeedUnit.CMS.toCMS( 10.0 ) );
		// mass * speed == gas mass * gas speed;
		final double gasMass_g = MassUnit.KG.toGrams( shipMass_kg ) * requiredShipSpeed_cms / SpeedUnit.KMS.toCMS( gasSpeed_kms );
		LogWrapper.info( "gasMass_g={}", Double.toString( gasMass_g ) );
		Assert.assertEquals( 100.0, gasMass_g, 0.1 );
	}

	@Test
	public void timeToTravel() {
		this.orbitDistance();
		final double escapeSpeed_kms = SpeedUnit.KMS.toKMS( 11.0 );
		// s = s0 + v0.t + 1/2.a.t2
		// segment = 11kms.t + 1/2 10cms.t2
		// (1/2 10cms) t2 + (11kms) T - segment = 0
		// t = -11 +- SQR(11² - 4.a.c)/2.a
		final double intermediate = escapeSpeed_kms * escapeSpeed_kms -
				                            (4.0 * SpeedUnit.CMS.toKMS( 10.0 / 2.0 ) * (-1 * this.accelerationSegment_km));
		final Double timeToTravel_s1 = (escapeSpeed_kms + Math.sqrt(intermediate))/(2.0*SpeedUnit.CMS.toKMS( 10.0 / 2.0 ));
//		Assert.assertEquals( 220000.0, timeToTravel_s1, 0.1 );
		Assert.assertEquals( 10.0, TimeUnit.SECONDS.toHours( timeToTravel_s1.longValue() ), 0.1);
	}
}
