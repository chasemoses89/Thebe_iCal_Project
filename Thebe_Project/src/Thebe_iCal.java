import java.time.LocalDateTime;
import java.io.*;
import javax.swing.*;

/**
 * This program creates an ics file that is compatible with MS Outlook and Gmail.
 * @author Lee, Chase; Hsu, Jon; Igeta, David
 * Thebe_iCal Team Project
 */
public class Thebe_iCal {
	
	/**
	 * This is the main() method of the program
	 * @param args
	 */
	public static void main(String[] args) {
		//Event subject line
		String sSubject = "";
		//Event location
		String sLocation = "";
		//Event description which will be placed in the body
		String sBody = "";
		//Beginning month event starts
		String sStartMonth = "";
		//Beginning day event starts
		String sStartDay = "";
		//Beginning year event starts
		String sStartYear = "";
		//Beginning time event starts
		String sStartTime = "";
		//End month event ends
		String sEndMonth = "";
		//End day event ends
		String sEndDay = "";
		//End year event ends
		String sEndYear = "";
		//End time event ends
		String sEndTime = "";
		//Event class
		String sClass = "";
		//system current date
		String Sysdate = LocalDateTime.now().toString();
		//current date and time
		String sCurrentTime = Thebe_iCal.sSystemDate(Sysdate);
		//longitude of location
		Float GeoLat = 21.4667f;
		//latitude of location
		Float GeoLong = 157.9833f;
		//User's choice for window 1
		int iChoice = -1;
		//sentinel value for loops
		boolean bValid = false;
		//indicates if a valid geographic position was entered
		boolean bGeoPos = false;
		//states if end date/time is less than the start date/time
		boolean bValidTimeFrame;
		//states if day is valid based on month an leap year
		boolean bValidDate;

		//instantiates the drop down menu lists
		String[] sTime = {"0000", "0100", "0200", "0300", "0400", "0500", "0600", "0700", "0800", "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
		String[] sDay = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] sMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String[] sYear = {"2015", "2016", "2017", "2018", "2019", "2020"};
		String[] sClasses = {"PUBLIC", "PRIVATE", "CONFIDENTIAL"};
		
		//instantiates all the drop down menu items
		JComboBox jStartTime = new JComboBox(sTime);
		JComboBox jStartDay = new JComboBox(sDay);
		JComboBox jStartMonth = new JComboBox(sMonth);
		JComboBox jStartYear = new JComboBox(sYear);
		JComboBox jEndTime = new JComboBox(sTime);
		JComboBox jEndDay = new JComboBox(sDay);
		JComboBox jEndMonth = new JComboBox(sMonth);
		JComboBox jEndYear = new JComboBox(sYear);
		JComboBox jClasses = new JComboBox(sClasses);
		
		//instantiates all the text fields
		JTextField fSubject = new JTextField();
		JTextField fLocation = new JTextField();
		JTextField fDescription = new JTextField();
		JTextField fGeoLat = new JTextField();
		JTextField fGeoLong = new JTextField();
		
		//instantiates an array of fields
		Object[] fields = {
			"Subject", fSubject,
			"Location", fLocation,
			"Description", fDescription,
			"Event Class", jClasses,
			"Start Year:", jStartYear,
			"Month", jStartMonth,
			"Day", jStartDay,
			"Time", jStartTime,
			"End Year:", jEndYear,
			"Month", jEndMonth,
			"Day", jEndDay,
			"Time", jEndTime,
		};
		
		//displays the iCal Event Creator window to the user
		iChoice = JOptionPane.showConfirmDialog(null, fields, "iCal Event Creator", JOptionPane.OK_CANCEL_OPTION);
		
		//terminates program if user does not click 'OK'
		if (iChoice != 0) {
			JOptionPane.showMessageDialog(null, "iCal Event has not been created.");
			System.exit(1);
		}
		
		//retrieves input from user and assigns to variables
		sSubject = fSubject.getText();
		sLocation = fLocation.getText();
		sBody = fDescription.getText();
		sStartYear = jStartYear.getSelectedItem().toString();
		sStartMonth = jStartMonth.getSelectedItem().toString();
		sStartDay = jStartDay.getSelectedItem().toString();
		sStartTime = jStartTime.getSelectedItem().toString();
		sEndYear = jEndYear.getSelectedItem().toString();
		sEndMonth = jEndMonth.getSelectedItem().toString();
		sEndDay = jEndDay.getSelectedItem().toString();
		sEndTime = jEndTime.getSelectedItem().toString();
		sClass = jClasses.getSelectedItem().toString();
		
		//instantiates boolean variable by called methods to be used in the while statement below
		bValidTimeFrame = Thebe_iCal.CheckBacktrack(sStartYear, sEndYear, sStartMonth, sEndMonth, sStartDay, sEndDay, sStartTime, sEndTime);
		bValidDate = Thebe_iCal.CheckDate(sStartYear, sEndYear, sStartMonth, sEndMonth, sStartDay, sEndDay, sStartTime, sEndTime);
		
		//shows error messages if either CheckBacktrack()method or CheckDate() method returns false
		while (!bValidTimeFrame || !bValidDate) {
			//shows this message if CheckBacktrack method returns false
			if(bValidTimeFrame == false) {
				JOptionPane.showMessageDialog(null, "The end date you entered occurs before the start date.");
			}
			//shows this message if CheckDate method returns false
			else if(bValidDate == false) {
				JOptionPane.showMessageDialog(null, "Please enter a valid day for the selected month.");
			}
			//displays the iCal Event Creator window to the user
			iChoice = JOptionPane.showConfirmDialog(null, fields, "iCal Event Creator", JOptionPane.OK_CANCEL_OPTION);
			
			//terminates program if user does not click 'OK'
			if (iChoice != 0) {
				JOptionPane.showMessageDialog(null, "iCal Event has not been created.");
				System.exit(1);
			}
			//retrieves input from user and assigns to variables
			sSubject = fSubject.getText();
			sLocation = fLocation.getText();
			sBody = fDescription.getText();
			sStartYear = jStartYear.getSelectedItem().toString();
			sStartMonth = jStartMonth.getSelectedItem().toString();
			sStartDay = jStartDay.getSelectedItem().toString();
			sStartTime = jStartTime.getSelectedItem().toString();
			sEndYear = jEndYear.getSelectedItem().toString();
			sEndMonth = jEndMonth.getSelectedItem().toString();
			sEndDay = jEndDay.getSelectedItem().toString();
			sEndTime = jEndTime.getSelectedItem().toString();
			sClass = jClasses.getSelectedItem().toString();
			
			//call methods again to see if they return true or false
			bValidTimeFrame = Thebe_iCal.CheckBacktrack(sStartYear, sEndYear, sStartMonth, sEndMonth, sStartDay, sEndDay, sStartTime, sEndTime);
			bValidDate = Thebe_iCal.CheckDate(sStartYear, sEndYear, sStartMonth, sEndMonth, sStartDay, sEndDay, sStartTime, sEndTime);
		}
		
		//instantiates another array of object to display geographic position fields
		Object[] GeoFields = {
				"Geo Latitude", fGeoLat,
				"Geo Longitude", fGeoLong
		};
		
		//asks user if they wish to enter a geographic position for their event
		iChoice = JOptionPane.showConfirmDialog(null, "Do you want to enter a geographic position?");
		
		//display input window for geographic position if the user selects 'OK'
		if(iChoice == 0) {	
			
			//continues to display window until valid coordinates are entered
			while(!bValid) {
				try {
					//displays geographic position entry window
					iChoice = JOptionPane.showConfirmDialog(null, GeoFields, "Geographic Position", JOptionPane.OK_CANCEL_OPTION);
					//if user selects 'OK' then attempt to parse input to a float
					if(iChoice == 0) {
						GeoLat = Float.parseFloat(fGeoLat.getText());
						GeoLong = Float.parseFloat(fGeoLong.getText());
						//exits loop
						bValid = true;
						//indicates that valid coordinates have been entered
						bGeoPos = true;
					}
					//if user attempts to cancel or exit window
					if(iChoice == 2 || iChoice == -1) {
						//exits while loop
						bValid = true;
					}
				}
				//exeception thrown if input cannot be parsed into a float data type
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Invalid coordinates. Click 'Cancel' or close window \nto continue without geographic position.");
					//resets value of coordinates
					GeoLat = null;
					GeoLong = null;
				}
			}//end while
		}//end if
		
		//prints file if user selects 'NO'
		if(iChoice == 1) {
			bValid = true;
		}
		
		if (bValid == true) {
		//initializes fileWriter
		PrintWriter fileWriter = null;
		try {
			//makes connection to fileWriter and output file
			fileWriter = new PrintWriter("iCal_Event.ics");
			//begin writing to file
			//every line that is commented out is optional and can still be read/processed by MS Outlook 2010
			fileWriter.println("BEGIN:VCALENDAR\n"
					+ "PRODID:-//Microsoft Corporation//Outlook 14.0 MIMEDIR//EN\n"
					+ "VERSION:2.0\n"
					+ "METHOD:PUBLISH\n"
					+ "X-MS-OLK-FORCEINSPECTOROPEN:TRUE\n"
					+ "BEGIN:VTIMEZONE\n"
					+ "TZID:Hawaiian Standard Time\n"
					+ "BEGIN:STANDARD\n"
					+ "DTSTART:16010101T000000\n"
					+ "TZOFFSETFROM:-1000\n"
					+ "TZOFFSETTO:-1000\n"
					+ "END:STANDARD\n"
					+ "END:VTIMEZONE\n"
					+ "BEGIN:VEVENT");
			//only writes the class syntax if the field has been entered
			if(sClass.trim().length() > 0) {
				fileWriter.println("CLASS:" + sClass);
			}
			//only writes the created syntax if the field has been entered
			fileWriter.println("CREATED:" + sCurrentTime + "\n"
					+ "DESCRIPTION:" + sBody + "\\n\n"
					+ "DTEND;TZID=\"Hawaiian Standard Time\":" + sEndYear + sEndMonth + sStartDay + "T" + sEndTime + "00\n"
					+ "DTSTAMP:" + sCurrentTime + "\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":" + sStartYear + sStartMonth + sEndDay + "T" + sStartTime + "00\n"
					+ "LAST-MODIFIED:" + sCurrentTime);
			//prints the geographic position line only if valid coordinates were entered
			if(bGeoPos == true) {
				fileWriter.println("GEO:" + GeoLat + ";" + GeoLong);
			}
			//only writes the location syntax if the field has been entered
			if(sLocation.trim().length() > 0) {
				fileWriter.println("LOCATION:" + sLocation);
			}
			fileWriter.println("PRIORITY:5\n"
					+ "SEQUENCE:0");
			//only writes the subject syntax if the field has been entered
			if(sSubject.trim().length() > 0) {
				fileWriter.println("SUMMARY;LANGUAGE=en-us:" + sSubject);
			}
			fileWriter.println("TRANSP:OPAQUE\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000407663BC33B0D001000000000000000\n"
					+ "\t010000000109752F585EB3B448B59159DFD3CF4D2\n"
					+ "END:VEVENT\n"
					+ "END:VCALENDAR");
			
			//closes fileWriter
			fileWriter.close();
			
		}//end try
		//this exception must be caught in order to use the fileWriter
		catch (FileNotFoundException fnf) {
			JOptionPane.showMessageDialog(null, "The file could not be found!");
		}
		
		//displays message if file was written successfully check if changes were made
		JOptionPane.showMessageDialog(null,"Your event has been successfully created!");
		}//end if statement
		else {
			JOptionPane.showMessageDialog(null,"iCal Event has not been created.");
		}
		
	}//end main() arg
	
	/**
	 * method to format the current system date
	 * @param sSysdate
	 * @return sSysdate the system's current date formatted for the .ics file
	 */
	public static String sSystemDate (String SystemDate) {
		//instantiates current time variable to be returned
		String sCurrentTime = "";
		//instantiates length of the system date
		int iLen = SystemDate.length();
		//instantiates variable to hold each char of system date
		String sChar = "";
		
		//loops through the system date to remove any special characters
		for (int i = 0; i < iLen; i++) {
			sChar = SystemDate.substring(i, i + 1);
			//removes special characters
			if(sChar.equals("-") || sChar.equals(":") || sChar.equals(".")) {
				//do nothing
			}
			//concatentates string if not special character
			else {
				sCurrentTime = sCurrentTime + sChar;
			}
		}
		//concatenates the "Z" at the end of the time
		//sCurrentTime = sCurrentTime + "Z";
			
		return sCurrentTime;
	}//end sSystemDate method
	
	/**
	 * This method checks if the end date is less than the start date.
	 * @param sValidStart
	 * @param sValidEnd
	 * @return
	 */
	public static boolean CheckBacktrack (String sYearStart, String sYearEnd, String sMonthStart, String sMonthEnd, String sDayStart, String sDayEnd, String sTimeStart, String sTimeEnd) {
		if((Integer.parseInt(sYearStart) > Integer.parseInt(sYearEnd))
		|| (Integer.parseInt(sYearStart) > Integer.parseInt(sYearEnd)) && (Integer.parseInt(sMonthStart) > Integer.parseInt(sMonthEnd))
		|| (Integer.parseInt(sMonthStart) > Integer.parseInt(sMonthEnd)) && (Integer.parseInt(sDayStart) > Integer.parseInt(sDayEnd))
		|| (Integer.parseInt(sYearStart) > Integer.parseInt(sYearEnd)) && (Integer.parseInt(sMonthStart) > Integer.parseInt(sMonthEnd)) && (Integer.parseInt(sDayStart) > Integer.parseInt(sDayEnd)) && (Integer.parseInt(sTimeStart) > Integer.parseInt(sTimeEnd))) {
			return false;
		}
		else {
			return true;
		}
	}//end CheckBacktrack method
	
	/**
	 * Checks if a valid day is entered for a particular month
	 * @param sYearStart
	 * @param sYearEnd
	 * @param sMonthStart
	 * @param sMonthEnd
	 * @param sDayStart
	 * @param sDayEnd
	 * @param sTimeStart
	 * @param sTimeEnd
	 * @return
	 */
	public static boolean CheckDate (String sYearStart, String sYearEnd, String sMonthStart, String sMonthEnd, String sDayStart, String sDayEnd, String sTimeStart, String sTimeEnd) {
		int iFeb = 2; //29 days
		int iApr = 4; //30 days
		int iJun = 6; //30 days
		int iSep = 9; //30 days
		int iNov = 11; //30 days
		int iShort = 29; //case where month is 29 days
		int iLong = 30; //case where month is 30 days
		
		//checks if user enters a day greater than 29 or 30 for the month of Feb 
		if((Integer.parseInt(sMonthStart) == iFeb && Integer.parseInt(sDayStart) > iShort)
				|| (Integer.parseInt(sMonthEnd) == iFeb && Integer.parseInt(sDayEnd) > iShort)
				|| (Integer.parseInt(sMonthStart) == iFeb && Integer.parseInt(sDayStart) > iShort)
				|| (Integer.parseInt(sMonthEnd) == iFeb && Integer.parseInt(sDayEnd) > iShort)) {	
			return false;
		}
		//checks if user enters a day greater than 30 for the months of Apr, Jun, Sep, Nov 
				if((Integer.parseInt(sMonthStart) == iApr && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iApr && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iApr && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iApr && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iJun && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iJun && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iJun && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iJun && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iSep && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iSep && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iSep && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iSep && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iNov && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iNov && Integer.parseInt(sDayEnd) > iLong)
						|| (Integer.parseInt(sMonthStart) == iNov && Integer.parseInt(sDayStart) > iLong)
						|| (Integer.parseInt(sMonthEnd) == iNov && Integer.parseInt(sDayEnd) > iLong)) {	
					return false;
				}
		//returns true if no if statements are executed
		return true;
	}
}//ends Public Class Thebe_iCal
