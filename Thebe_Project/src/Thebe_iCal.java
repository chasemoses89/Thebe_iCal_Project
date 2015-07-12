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
		String [] sSubjectArry = new String[5];
		//Event location
		String sLocation = "";
		String [] sLocationArry = new String[5];
		//Event description which will be placed in the body
		String sBody = "";
		String [] sBodyArry = new String[5];
		//Beginning month event starts
		String sStartMonth = "";
		String [] sStartMonthArry = new String[5];
		//Beginning day event starts
		String sStartDay = "";
		String [] sStartDayArry = new String[5];
		//Beginning year event starts
		String sStartYear = "";
		String [] sStartYearArry = new String[5];
		//Beginning time event starts
		String sStartTime = "";
		String [] sStartTimeArry = new String[5];
		//End month event ends
		String sEndMonth = "";
		String [] sEndMonthArry = new String[5];
		//End day event ends
		String sEndDay = "";
		String [] sEndDayArry = new String[5];
		//End year event ends
		String sEndYear = "";
		String [] sEndYearArry = new String[5];
		//End time event ends
		String sEndTime = "";
		String [] sEndTimeArry = new String[5];
		//Event class
		String sClass = "";
		String [] sClassArry = new String[5];
		//system current date
		String Sysdate = LocalDateTime.now().toString();
		//current date and time
		String sCurrentTime = Thebe_iCal.sSystemDate(Sysdate);
		//name of the output file
		String sFileName = "";
		//String [] sFileNameArry = new String[5];
		//longitude of location
		Float GeoLat = 21.4667f;
		Float[] fLatArray= new Float[5];
		//latitude of location
		Float GeoLong = 157.9833f;
		Float[] fLongArray = new Float[5];
		//User's choice for window 1
		int iChoice = -1;
		//sentinel value for loops
		boolean bValidCoordinates = false;
		//states if end date/time is less than the start date/time
		boolean bValidTimeFrame;
		//states if day is valid based on month an leap year
		boolean bValidDate;
		//array counter
		int i = 0;
		int iContinueProgram;
		//loop through program again
		boolean bContinue = true;
		//initializes fileWriter
		PrintWriter fileWriter = null;

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
		
		//instantiates another array of object to display geographic position fields
		Object[] GeoFields = {
			"Geo Latitude", fGeoLat,
			"Geo Longitude", fGeoLong
		};
				
		while (bContinue == true) {
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
		
			//this while loop will ensure valid dates are inputed before proceeding further
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
			}//end while loop for !bValidTimeFrame || !bValidDate methods
			
			//continues to display window until valid coordinates are entered
			while(!bValidCoordinates) {
				try {
					//displays geographic position entry window
					iChoice = JOptionPane.showConfirmDialog(null, GeoFields, "Geographic Position", JOptionPane.OK_CANCEL_OPTION);
					//if user selects 'OK' then attempt to parse input to a float
					if(iChoice == 0) {
						GeoLat = Float.parseFloat(fGeoLat.getText());
						GeoLong = Float.parseFloat(fGeoLong.getText());
						
						//if input has been parsed to a float then coordinates are valid and exits loop
						bValidCoordinates = true;
					}
					//if user attempts to cancel or exit window
					if(iChoice == 2 || iChoice == -1) {
						JOptionPane.showMessageDialog(null, "iCal Event has not been created.");
						System.exit(1);
					}
				}
				//exeception thrown if input cannot be parsed into a float data type
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Please enter valid coordinates to continue.");
					//resets value of coordinates
					GeoLat = null;
					GeoLong = null;
				}
				catch (ArrayIndexOutOfBoundsException aout) {
					JOptionPane.showMessageDialog(null, "No more coordinates can be entered.");
				}
			}//end !bValidCoordinates while loop
			
			//if the code has reached this point then all input is valid and are added to array variables
			sSubjectArry [i] = sSubject;
			sLocationArry [i] = sLocation;
			sBodyArry [i] = sBody;
			sStartMonthArry [i] = sStartMonth;
			sStartDayArry [i] = sStartDay;
			sStartYearArry [i] = sStartYear;
			sStartTimeArry [i] = sStartTime;
			sEndMonthArry [i] = sEndMonth;
			sEndDayArry [i] = sEndDay;
			sEndYearArry [i] = sEndYear;
			sEndTimeArry [i] = sEndTime;
			sClassArry [i] = sClass;
			fLatArray [i] = GeoLat;
			fLongArray [i] = GeoLong;
			
			//asks if user wants to create another event file
			iContinueProgram = JOptionPane.showConfirmDialog(null, "Do you want to enter another event file?");
			
			//if 'Yes' then reset all variable back to original values and increment i counter
			if (iContinueProgram == 0) {
				bContinue = true;
				iChoice = -1;
				bValidCoordinates = false;
				sSubject = "";
				sLocation = "";
				sBody = "";
				sStartMonth = "";
				sStartDay = "";
				sStartYear = "";
				sStartTime = "";
				sEndMonth = "";
				sEndDay = "";
				sEndYear = "";
				sEndTime = "";
				sClass = "";
				GeoLat = null;
				GeoLong = null;
				//states if end date/time is less than the start date/time
				bValidTimeFrame = false;
				//states if day is valid based on month an leap year
				bValidDate = false;
				i++;
			}//end if bContinue statement
			//exits the program without writing files if otherwise
			else {
				bContinue = false;
			}
		}//end bContinue while loop
		/*
		for (int b = 0; b < i; b++) {
			
			int a = 0;
			
			if (((Integer.parseInt(sStartTimeArry [a]) < Integer.parseInt(sStartTimeArry [a+1]))
					) {
			}
			//System.out.println("Distance = " + Thebe_iCal.calculateDistance(fLatArray[b], fLongArray[b], fLatArray[b+1], fLongArray[b+1]));
		}
		*/
		//once user is done entering event files prompt user for name of file to be saved
		sFileName = JOptionPane.showInputDialog("Please enter a name to save your file.");
		
		try {
			//c is the counter which loops through each event file's data
			for (int c = 0; c <= i; c++) {
				
				//makes connection to fileWriter and output file
				fileWriter = new PrintWriter(sFileName + "_" + c + ".ics");
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
				if(sClassArry[c].trim().length() > 0) {
					fileWriter.println("CLASS:" + sClassArry[c]);
				}
				
				//only writes the created syntax if the field has been entered
				fileWriter.println("CREATED:" + sCurrentTime + "\n"
					+ "DESCRIPTION:" + sBodyArry[c] + "\\n\n"
					+ "DTEND;TZID=\"Hawaiian Standard Time\":" + sEndYearArry[c] + sEndMonthArry[c] + sStartDayArry[c] + "T" + sEndTimeArry[c] + "00\n"
					+ "DTSTAMP:" + sCurrentTime + "\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":" + sStartYearArry[c] + sStartMonthArry[c] + sEndDayArry[c] + "T" + sStartTimeArry[c] + "00\n"
					+ "LAST-MODIFIED:" + sCurrentTime
					+ "GEO:" + fLatArray[c] + ";" + fLongArray[c]);
				//only writes the location syntax if the field has been entered
				
				if(sLocationArry[c].trim().length() > 0) {
					fileWriter.println("LOCATION:" + sLocationArry[c]);
				}
				
				fileWriter.println("PRIORITY:5\n"
					+ "SEQUENCE:0");
				
				//only writes the subject syntax if the field has been entered
				if(sSubjectArry[c].trim().length() > 0) {
					fileWriter.println("SUMMARY;LANGUAGE=en-us:" + sSubjectArry[c]);
				}
				
				fileWriter.println("TRANSP:OPAQUE\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000407663BC33B0D001000000000000000\n"
					+ "\t010000000109752F585EB3B448B59159DFD3CF4D2\n"
					+ "END:VEVENT\n"
					+ "END:VCALENDAR");
			
				//closes fileWriter
				fileWriter.close();
				
				//displays message if file was written successfully
				JOptionPane.showMessageDialog(null,"Your event file " + sFileName + "_" + c + ".ics" + " has been successfully created!");
			}//end for loop which prints each event file entered
		}//end try
		
		//this exception must be caught in order to use the fileWriter
		catch (FileNotFoundException fnf) {
			JOptionPane.showMessageDialog(null, "The file could not be found!");
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
		|| (Integer.parseInt(sYearStart) >= Integer.parseInt(sYearEnd)) && (Integer.parseInt(sMonthStart) > Integer.parseInt(sMonthEnd))
		|| (Integer.parseInt(sYearStart) >= Integer.parseInt(sYearEnd)) && (Integer.parseInt(sMonthStart) >= Integer.parseInt(sMonthEnd)) && (Integer.parseInt(sDayStart) > Integer.parseInt(sDayEnd))
		|| (Integer.parseInt(sYearStart) >= Integer.parseInt(sYearEnd)) && (Integer.parseInt(sMonthStart) >= Integer.parseInt(sMonthEnd)) && (Integer.parseInt(sDayStart) >= Integer.parseInt(sDayEnd)) && (Integer.parseInt(sTimeStart) > Integer.parseInt(sTimeEnd))) {
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
		int iShort = 28; //case where month is 29 days
		int iLong = 30; //case where month is 30 days
		
		//checks if user enters a day greater than 28 for the month of Feb 
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
	}//end CheckDate method
	
	/**
	 * Computers the great circle distance between two points
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return
	 */
	public static double calculateDistance(float latitude1, float longitude1, float latitude2, float longitude2)
	  {
		double radLat1 = Math.toRadians(latitude1);
		double radLon1 = Math.toRadians(longitude1);
		double radLat2 = Math.toRadians(latitude2);
		double radLon2 = Math.toRadians(longitude2);
		
		//great circle distance in radians
		double distanceRadians = Math.acos((Math.cos(radLat1) * Math.cos(radLat2)) + (Math.sin(radLat1) * Math.sin(radLat2))
	        * (Math.cos(radLon1 - radLon2)));
		
		distanceRadians = Math.toDegrees(distanceRadians);
		
		double distanceDeg = 60 * distanceRadians;

	    return distanceDeg;
	  }

}//ends Public Class Thebe_iCal
