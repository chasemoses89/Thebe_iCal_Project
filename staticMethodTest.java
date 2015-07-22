import static org.junit.Assert.*;

import org.junit.Test;


public class staticMethodTest {
	
	@Test
	//This is to test the CheckDate method
	public void test_static_CheckDate() {
		String sYearStart = "2015";
		String sMonthStart = "70";
		String sMonthEnd = "7";
		String sDayStart = "21";
		String sDayEnd = "21";
		String sTimeStart = "1200";
		String sTimeEnd = "1300";
		boolean expected_value = true;
		boolean actual_value = Thebe_iCal.CheckDate(sYearStart, sYearStart, sMonthStart, sMonthEnd, sDayStart, sDayEnd, sTimeStart, sTimeEnd);
		assertEquals("CheckDate method failed" , expected_value, actual_value);
		
	}//end method for CheckDate
	
	@Test
	//This is to test the CheckBackTrack method
	public void test_static_CheckBackTrack() {
		String sYearStart = "2015";
		String sMonthStart = "70";
		String sMonthEnd = "7";
		String sDayStart = "21";
		String sDayEnd = "21";
		String sTimeStart = "1200";
		String sTimeEnd = "1300";
		boolean expected_value = false;
		boolean actual_value = Thebe_iCal.CheckBacktrack(sYearStart, sYearStart, sMonthStart, sMonthEnd, sDayStart, sDayEnd, sTimeStart, sTimeEnd);
		assertEquals("CheckBackTrack method failed", expected_value, actual_value);
		
	}//end test for CheckBackTrack method
	
	
	@Test
	//This is to test the calculateDistance method
	public void test_static_calculateDistance() {
		float latitude1 = 10000;
		float longitude1 = 10000;
		float latitude2 = 10000;
		float longitude2 = 10000;
		double expected_value = 0.0;
		double actual_value = Thebe_iCal.calculateDistance(latitude1, longitude1, latitude2, longitude2);
		assertEquals(expected_value, expected_value, actual_value);
		
	}//end test for calculateDistance method
	
}//end of staticMethodTest class
