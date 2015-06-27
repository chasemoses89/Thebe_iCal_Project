import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * 
 * @author Igeta, David
 * Thebe_iCal Team Project
 */
public class Thebe_iCal {

	public static void main(String[] args) {
		//initializes fileWriter
		PrintWriter fileWriter = null;
		try {
			//makes connection to fileWriter and output file
			fileWriter = new PrintWriter("iCal_Event.ics");
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
					+ "BEGIN:VEVENT\n"
					+ "CLASS:PUBLIC\n"
					+ "CREATED:20150627T031544Z\n"
					+ "DESCRIPTION:This is an example of sample text in the body of an appointment. \\n\n"
					+ "DTEND;TZID=\"Hawaiian Standard Time\":20150817T140000\n"
					+ "DTSTAMP:20150627T031544Z\n"
					+ "DTSTART;TZID=\"Hawaiian Standard Time\":20150817T130000\n"
					+ "LAST-MODIFIED:20150627T031544Z\n"
					+ "LOCATION:Hamilton Library\n"
					+ "PRIORITY:5\n"
					+ "SEQUENCE:0\n"
					+ "SUMMARY;LANGUAGE=en-us:Study for ICS 314 Exam\n"
					+ "TRANSP:OPAQUE\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000407663BC33B0D001000000000000000\n"
					+ "\t010000000109752F585EB3B448B59159DFD3CF4D2\n"
					+ "X-ALT-DESC;FMTTYPE=text/html:<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//E\n"
					+ "\tN\">\\n<HTML>\\n<HEAD>\\n<META NAME=\"Generator\" CONTENT=\"MS Exchange Server ve\n"
					+ "\trsion 14.02.5004.000\">\\n<TITLE></TITLE>\\n</HEAD>\\n<BODY>\\n<!-- Converted f\n"
					+ "\trom text/rtf format -->\\n\\n<P DIR=LTR><SPAN LANG=\"en-us\"><FONT FACE=\"Calib\n"
					+ "\tri\">This is an example of sample text in the body of an appointment.</FONT\n"
					+ "\t></SPAN><SPAN LANG=\"en-us\"> </SPAN></P>\\n\\n</BODY>\\n</HTML>\n"
					+ "X-MICROSOFT-CDO-BUSYSTATUS:BUSY\n"
					+ "X-MICROSOFT-CDO-IMPORTANCE:1\n"
					+ "X-MICROSOFT-DISALLOW-COUNTER:FALSE\n"
					+ "X-MS-OLK-AUTOFILLLOCATION:FALSE\n"
					+ "X-MS-OLK-CONFTYPE:0\n"
					+ "BEGIN:VALARM\n"
					+ "TRIGGER:-PT15M\n"
					+ "ACTION:DISPLAY\n"
					+ "DESCRIPTION:Reminder\n"
					+ "END:VALARM\n"
					+ "END:VEVENT\n"
					+ "END:VCALENDAR");
			
			//closes fileWriter
			fileWriter.close();
			
		}
		catch (FileNotFoundException fnf) {
			JOptionPane.showMessageDialog(null, "The file could not be found!");
		}
		JOptionPane.showMessageDialog(null,"Content has been successfully written to file!");

	}

}
