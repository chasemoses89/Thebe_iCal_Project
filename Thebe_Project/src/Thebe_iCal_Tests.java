import static org.junit.Assert.*;

import org.junit.Test;

public class Thebe_iCal_Tests {

	@Test
	public void HawaiiToAlaskaDistance() {
		double testDistance = Thebe_iCal.calculateDistance(21.3114f, 157.7964f, 64f, 150f);
		assertEquals(2580.4, testDistance, 1);
	}
	
	@Test
	public void OneHourBacktrack() {
		boolean testBacktrack = Thebe_iCal.CheckBacktrack("2015", "2015", "2", "2", "28", "28", "0100", "0000");
		assertEquals(false, testBacktrack);
	}
	
	@Test
	public void OneDayBacktrack() {
		boolean testBacktrack = Thebe_iCal.CheckBacktrack("2015", "2015", "2", "2", "28", "26", "0000", "0000");
		assertEquals(false, testBacktrack);
	}
	
	@Test
	public void OneMonthBacktrack() {
		boolean testBacktrack = Thebe_iCal.CheckBacktrack("2015", "2015", "2", "1", "28", "28", "0000", "0000");
		assertEquals(false, testBacktrack);
	}
	
	@Test
	public void OneYearBacktrack() {
		boolean testBacktrack = Thebe_iCal.CheckBacktrack("2015", "2014", "2", "2", "28", "28", "0000", "0000");
		assertEquals(false, testBacktrack);
	}
	
	public void NoBackTrack() {
		boolean testBacktrack = Thebe_iCal.CheckBacktrack("2015", "2015", "2", "2", "28", "28", "0000", "0000");
		assertTrue(testBacktrack);
	}
	
	@Test
	public void CheckFeb29() {
		boolean testDate = Thebe_iCal.CheckDate("2015", "2015", "2", "2", "29", "29", "0000", "0100");
		assertFalse(testDate);
	}

	@Test
	public void CheckJuly31() {
		boolean testDate = Thebe_iCal.CheckDate("2015", "2015", "7", "7", "31", "31", "0000", "0100");
		assertTrue(testDate);
	}
	
	@Test
	public void CheckSept31() {
		boolean testDate = Thebe_iCal.CheckDate("2015", "2015", "9", "9", "31", "31", "0000", "0100");
		assertFalse(testDate);
	}
}
