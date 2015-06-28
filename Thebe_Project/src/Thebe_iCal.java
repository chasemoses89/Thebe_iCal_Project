import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

import javax.swing.*;

/**
 * 6/27/2015
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
		//Current time and date
		String sCurrentTime = "";
		//sentinel value
		boolean bContinue = false;
		
		String[] sTime = {"0000", "0100", "0200", "0300", "0400", "0500", "0600", "0700", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000", "2100", "2200", "2300"};
		String[] sMonth = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String[] sYear = {"2015", "2016", "2017", "2018", "2019", "2020"};
		
		JComboBox jStartTime = new JComboBox(sTime);
		JComboBox jStartMonth = new JComboBox(sMonth);
		JComboBox jStartYear = new JComboBox(sYear);
		JComboBox jEndTime = new JComboBox(sTime);
		JComboBox jEndMonth = new JComboBox(sMonth);
		JComboBox jEndYear = new JComboBox(sYear);
		
		JTextField fSubject = new JTextField();
		JTextField fLocation = new JTextField();
		JTextField fDescription = new JTextField();
		
		Object[] fields = {
			"Subject", fSubject,
			"Location", fLocation,
			"Description", fDescription,
			"Start Years", jStartYear,
			"Start Months", jStartMonth,
			"Start Time", jStartTime,
			"End Years", jEndYear,
			"End Months", jEndMonth,
			"End Time", jEndTime
		};
		
		JOptionPane.showConfirmDialog(null, fields, "iCal Event Creator", JOptionPane.OK_CANCEL_OPTION);
		
		sSubject = fSubject.getText();
		sLocation = fLocation.getText();
		sBody = fDescription.getText();
		sStartYear = jStartYear.getSelectedItem().toString();
		sStartMonth = jStartMonth.getSelectedItem().toString();
		sStartTime = jStartTime.getSelectedItem().toString();
		sEndYear = jEndYear.getSelectedItem().toString();
		sEndMonth = jEndMonth.getSelectedItem().toString();
		sEndTime = jEndTime.getSelectedItem().toString();
		
		sCurrentTime = LocalDateTime.now().toString();
		
		System.out.println(sCurrentTime);
		
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
					+ "CLASS:PUBLIC\n"
					+ "CREATED:20150627T031544Z\n"
					+ "DESCRIPTION:" + sBody + "\\n\n"
					+ "DTEND;TZID=\"Hawaiian Standard Time\":" + sEndYear + sEndMonth + "T" + sEndTime + "00\n"
					+ "DTSTAMP:20150627T031544Z\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":" + sStartYear + sStartMonth + "T" + sStartTime + "00\n"
					+ "LAST-MODIFIED:20150627T031544Z\n"
					+ "LOCATION:" + sLocation + "\n"
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
		JOptionPane.showMessageDialog(null,"Content has been successfully written to file!");

	}//end main() arg

}//ends Public Class Thebe_iCal
