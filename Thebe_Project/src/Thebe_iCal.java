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
		//controls how many event files a user can enter in the program
		final int SIZE = 5;
		//this is the minimum array size required to perform sorting and calcuation methods
		final int MIN_SIZE = 1;
		//Event subject line
		String sSubject = "";
		String [] sSubjectArry = new String[SIZE];
		//Event location
		String sLocation = "";
		String [] sLocationArry = new String[SIZE];
		//Event description which will be placed in the body
		String sBody = "";
		String [] sBodyArry = new String[SIZE];
		//Beginning month event starts
		String sStartMonth = "";
		String [] sStartMonthArry = new String[SIZE];
		//Beginning day event starts
		String sStartDay = "";
		String [] sStartDayArry = new String[SIZE];
		//Beginning year event starts
		String sStartYear = "";
		String [] sStartYearArry = new String[SIZE];
		//Beginning time event starts
		String sStartTime = "";
		String [] sStartTimeArry = new String[SIZE];
		//End month event ends
		String sEndMonth = "";
		String [] sEndMonthArry = new String[SIZE];
		//End day event ends
		String sEndDay = "";
		String [] sEndDayArry = new String[SIZE];
		//End year event ends
		String sEndYear = "";
		String [] sEndYearArry = new String[SIZE];
		//End time event ends
		String sEndTime = "";
		String [] sEndTimeArry = new String[SIZE];
		//Event class
		String sClass = "";
		String [] sClassArry = new String[SIZE];
		double dNauticleMiles;
		Double [] dStatuteMiles = new Double[SIZE];
		Double [] dKilometers = new Double[SIZE];
		//number to convert nautical miles to kilometers
		double dNautToKilo = 1.852;
		//number to convert nautical miles to statute miles
        double dNautToStat = 1.15077944802;
		//system current date
		String Sysdate = LocalDateTime.now().toString();
		//current date and time
		String sCurrentTime = Thebe_iCal.sSystemDate(Sysdate);
		//name of the output file
		String sFileName = "";
		//longitude of location
		Float GeoLat = 21.4667f;
		Float[] fLatArray= new Float[SIZE];
		//latitude of location
		Float GeoLong = 157.9833f;
		Float[] fLongArray = new Float[SIZE];
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
		//valid time
		boolean bValidTime = false;
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
				
		while (bContinue == true && i < SIZE) {
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
			iContinueProgram = JOptionPane.showConfirmDialog(null, "Do you want to create another same-day event?");

			//user wants to enter another event and the array still has room
			if (iContinueProgram == 0 && i < (SIZE - 1)) {
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
				//System.out.println("i = " + i);
			}//end if bContinue statement
			//user wants to enter another event but the array is full
			else if(iContinueProgram == 0 && i == (SIZE - 1)) {
				JOptionPane.showMessageDialog(null, "You have entered the max number of events.");
				bContinue = false;
			}
			//user does not want to enter another event
			else if(iContinueProgram == 1) {
				bContinue = false;	
			}
			//if user selects cancel or closes dialog box quit program
			else if(iContinueProgram == 2 || iContinueProgram == -1) {
				JOptionPane.showMessageDialog(null, "iCal Event has not been created.");
				System.exit(1);
			}
		}//end bContinue while loop
		
		/*Sort the event files based on their times before you calculate the great circle distances*/
		String sNowTime = "";
		String sPastTime = "";
		String sNowTimeEnd = "";
		String sPastTimeEnd = "";
		String sNowSubject = "";
		String sPastSubject = "";
		String sNowLocation = "";
		String sPastLocation = "";
		String sNowClass = "";
		String sPastClass = "";
		String sNowBody = "";
		String sPastBody = "";
		Float fNowLat;
		Float fPastLat;
		Float fNowLong;
		Float fPastLong;
		
		/*
		System.out.print("sStartTimeArry = ");
		for(int x = 0; x <= i; x++) {
			System.out.print(sStartTimeArry[x] + ", ");
		}
		System.out.println("");
		*/
		//bubble sort for loop which sorts event files in ascending order based on the sStartTime
		for(int p = 0; p < i && i >= MIN_SIZE; p++) {
			System.out.println("bublesort outer loop executed " + p + " times");
			
			//inner loop to compare one position with the rest of the array
			for(int n = p + 1; n <= i; n++) {
				System.out.println("bublesort inner loop executed " + n + " times");
				//if the sStartTime is > the subsequent event file's sStartTime then switch all variables
				if(Integer.parseInt(sStartTimeArry [p]) > Integer.parseInt(sStartTimeArry [n])) {
					//container variable which holds each variable temporarily
					sNowTime = sStartTimeArry [n];
					sPastTime = sStartTimeArry [p];
					sNowTimeEnd = sEndTimeArry [n];
					sPastTimeEnd = sEndTimeArry [p];
					sNowLocation = sLocationArry [n];
					sPastLocation = sLocationArry [p];
					sNowSubject = sSubjectArry [n];
					sPastSubject = sSubjectArry [p];
					sNowBody = sBodyArry[n];
					sPastBody = sBodyArry[p];
					sNowClass = sClassArry[n];
					sPastClass = sClassArry[p];
					fNowLat = fLatArray[n];
					fPastLat = fLatArray[p];
					fNowLong = fLongArray[n];
					fPastLong = fLongArray[p];
					
					//switch variables
					sStartTimeArry [n] = sPastTime;
					sStartTimeArry [p] = sNowTime;
					sEndTimeArry [n] = sPastTimeEnd;
					sEndTimeArry [p] = sNowTimeEnd;
					sSubjectArry [n] = sPastSubject;
					sSubjectArry [p] = sNowSubject;
					sLocationArry [n] = sPastLocation;
					sLocationArry [p] = sNowLocation;
					sClassArry [n] = sPastClass;
					sClassArry [p] = sNowClass;
					sBodyArry [n] = sPastBody;
					sBodyArry [p] = sNowBody;
					fLatArray[n] = fPastLat;
					fLatArray[p] = fNowLat;
					fLongArray[n] = fPastLong;
					fLongArray[p] = fNowLong;
					
					/*
					System.out.print("sStartTimeArry = ");
					for(int x = 0; x <= i; x++) {
						System.out.print(sStartTimeArry[x] + ", ");
					}
					System.out.println("");
					*/
				}//end if statement
			}//end inner for loop
		}//end outer for loop
		/*
		System.out.print("Results after exited from loop: \nsStartTimeArry = ");
		for(int x = 0; x <= i; x++) {
			System.out.print(sStartTimeArry[x] + ", ");
		}
		System.out.println("");
		*/
		//ensure to stop loop at (i - 1) since we add 1 to i inside the loop
		for (int b = 0; b <= (i - 1) && i >= MIN_SIZE; b++) {
			System.out.println("calculatdistance executed " + b + " times");
			//calculates the great circle distance between two events at a time until i is reached
			dNauticleMiles = Thebe_iCal.calculateDistance(fLatArray[b], fLongArray[b], fLatArray[b+1], fLongArray[b+1]);
		    dStatuteMiles[b] = dNauticleMiles * dNautToStat;
		    dKilometers[b] = dNauticleMiles * dNautToKilo;
		}
		
		try {
			//once user is done entering event files prompt user for name of file to be saved
			sFileName = JOptionPane.showInputDialog("Please enter a name to save your file.");
			
			//if the user does not enter a file name then give generic name
			if(sFileName.trim().length() < 1) {
				sFileName = "Untitled";
			}
			//c is the counter which loops through each event file's data
			for (int c = 0; c <= i; c++) {
				
				//makes connection to fileWriter and output file
				fileWriter = new PrintWriter(sFileName + "_" + (c + 1) + ".ics");
				//begin writing to file
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
					+ "DTEND;TZID=\"Hawaiian Standard Time\":" + sEndYearArry[c] + sEndMonthArry[c] + sEndDayArry[c] + "T" + sEndTimeArry[c] + "00\n"
					+ "DTSTAMP:" + sCurrentTime + "\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":" + sStartYearArry[c] + sStartMonthArry[c] + sStartDayArry[c] + "T" + sStartTimeArry[c] + "00\n"
					+ "LAST-MODIFIED:" + sCurrentTime + "\n"
					+ "GEO:" + fLatArray[c] + ";" + fLongArray[c]);
                if(dStatuteMiles[c] != null && dKilometers[c] != null) {
                	fileWriter.println("COMMENT: Distance to Event " + (c + 2) + " is " + dStatuteMiles[c] + " SM or " + dKilometers[c] + " km");
                }
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
		catch (NullPointerException npe) {
			JOptionPane.showMessageDialog(null, "iCal Event has not been created.");
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
		int iShort = 29; //case where month is 29 days
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
      public static double calculateDistance(float latitude1, float longitude1, float latitude2, float longitude2) { 
        double x1 = Math.toRadians(latitude1);
        double y1 = Math.toRadians(longitude1);
        double x2 = Math.toRadians(latitude2);
        double y2 = Math.toRadians(longitude2);

        // great circle distance in radians
        double angle1 = Math.acos(Math.sin(x1) * Math.sin(x2)
                      + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));

        // convert back to degrees
        angle1 = Math.toDegrees(angle1);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance1 = 60 * angle1;
        
        return distance1;
      }  

}//ends Public Class Thebe_iCal