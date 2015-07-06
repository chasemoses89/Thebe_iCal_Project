import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

import javax.swing.*;

/**
 * 
 * @author Igeta, David
 * Thebe_iCal Team Project
 */
public class Thebe_iCal {

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
		Float GeoLat = 37.386013f;
		//latitude of location
		Float GeoLong = 122.082932f;
		//User's choice for window 1
		int iChoice = -1;
		//sentinel value
		boolean bValid = false;
		//indicates if a valid geographic position was entered
		boolean bGeoPos = false;
		
		//instantiates the drop down menu lists
		String[] sTime = {"0000", "0100", "0200", "0300", "0400", "0500", "0600", "0700", "0800", "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
		String[] sDay = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
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
			JOptionPane.showMessageDialog(null, "File has not been written.");
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
						bValid = true;
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
					JOptionPane.showMessageDialog(null, "Please enter valid coordinates.");
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
					+ "BEGIN:VEVENT\n"
					+ "CLASS:" + sClass + "\n"
					+ "CREATED:" + sCurrentTime + "\n"
					+ "DESCRIPTION:" + sBody + "\\n\n"
					+ "DTEND;TZID=\"Hawaiian Standard Time\":" + sEndYear + sEndMonth + sStartDay + "T" + sEndTime + "00\n"
					+ "DTSTAMP:" + sCurrentTime + "\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":" + sStartYear + sStartMonth + sEndDay + "T" + sStartTime + "00\n"
					+ "LAST-MODIFIED:" + sCurrentTime);
			//prints the geographic position line only if valid coordinates were entered
			if(bGeoPos == true) {
				fileWriter.println("GEO:" + GeoLat + ";" + GeoLong);
			}
			fileWriter.println("LOCATION:" + sLocation + "\n"
					+ "PRIORITY:5\n"
					+ "SEQUENCE:0\n"
					+ "SUMMARY;LANGUAGE=en-us:" + sSubject + "\n"
					+ "TRANSP:OPAQUE\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000407663BC33B0D001000000000000000\n"
					+ "\t010000000109752F585EB3B448B59159DFD3CF4D2\n"
					//+ "X-ALT-DESC;FMTTYPE=text/html:<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//E\n"
					//+ "\tN\">\\n<HTML>\\n<HEAD>\\n<META NAME=\"Generator\" CONTENT=\"MS Exchange Server ve\n"
					//+ "\trsion 14.02.5004.000\">\\n<TITLE></TITLE>\\n</HEAD>\\n<BODY>\\n<!-- Converted f\n"
					//+ "\trom text/rtf format -->\\n\\n<P DIR=LTR><SPAN LANG=\"en-us\"><FONT FACE=\"Calib\n"
					//+ "\tri\">This is an example of sample text in the body of an appointment.</FONT\n"
					//+ "\t></SPAN><SPAN LANG=\"en-us\"> </SPAN></P>\\n\\n</BODY>\\n</HTML>\n"
					//+ "X-MICROSOFT-CDO-BUSYSTATUS:BUSY\n"
					//+ "X-MICROSOFT-CDO-IMPORTANCE:1\n"
					//+ "X-MICROSOFT-DISALLOW-COUNTER:FALSE\n"
					//+ "X-MS-OLK-AUTOFILLLOCATION:FALSE\n"
					//+ "X-MS-OLK-CONFTYPE:0\n"
					//+ "BEGIN:VALARM\n"
					//+ "TRIGGER:-PT15M\n"
					//+ "ACTION:DISPLAY\n"
					//+ "DESCRIPTION:Reminder\n"
					//+ "END:VALARM\n"
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
		JOptionPane.showMessageDialog(null,"Your .ics file has been successfully created!");
		}//end if statement
		else {
			JOptionPane.showMessageDialog(null,"File has not been written.");
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
		System.out.println(SystemDate);
		//loops through the system date to remove any special characters
		for (int i = 0; i < iLen; i++) {
			sChar = SystemDate.substring(i, i + 1);
			//removes special characters
			if(sChar.equals("-") || sChar.equals(":") || sChar.equals(".")) {
				i++;
			}
			//concatentates string if not special character
			else {
				sCurrentTime = sCurrentTime + sChar;
			}
		}
		//concatenates the "Z" at the end of the time
		sCurrentTime = sCurrentTime + "Z";
			
		return sCurrentTime;
	}//end sSystemDate method

}//ends Public Class Thebe_iCal
