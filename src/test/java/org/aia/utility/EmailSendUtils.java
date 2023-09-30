/**
 * @author Pallavi
 */

/***************************************************/

package org.aia.utility;

import org.aia.utility.Constants;
import org.aia.java_mail_API.*;
import org.aia.java_mail_API.*;

import static org.aia.java_mail_API.EmailConfig.*;
import javax.mail.MessagingException;

public class EmailSendUtils {

	public static void sendEmail(int count_totalTCs, int count_passedTCs, int count_failedTCs, int count_skippedTCs) {

		if (DataProviderFactory.getConfig().getValue("sendmail").equalsIgnoreCase("YES")) {
			System.out.println("****************************************");
			System.out.println("Send Email - START");
			System.out.println("****************************************");

			System.out.println("File name: " + Constants.EXTENT_REPORT_NAME);

			String messageBody = getTestCasesCountInFormat(count_totalTCs, count_passedTCs, count_failedTCs,
					count_skippedTCs);
			System.out.println(messageBody);

			/*
			 * String attachmentFile_ExtentReport = Constants.REPORTS_Folder +
			 * "report.html";
			 */

			String attachmentFile_ExtentReport = Constants.EXTENT_REPORT_NAME;

			try {
				EmailAttachmentsSender.sendEmailWithAttachments(SERVER, PORT, FROM, PASSWORD, TO, SUBJECT, messageBody,
						attachmentFile_ExtentReport);

				System.out.println("****************************************");
				System.out.println("Email sent successfully.");
				System.out.println("Send Email - END");
				System.out.println("****************************************");
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		}

	}

	private static String getTestCasesCountInFormat(int count_totalTCs, int count_passedTCs, int count_failedTCs,
			int count_skippedTCs) {
		System.out.println("count_totalTCs: " + count_totalTCs);
		System.out.println("count_passedTCs: " + count_passedTCs);
		System.out.println("count_failedTCs: " + count_failedTCs);
		System.out.println("count_skippedTCs: " + count_skippedTCs);

		return "<html>\r\n" + "\r\n" + " \r\n" + "\r\n"
				+ "<head>\"Hello, "
				+ ""
				+ "We just got a new build to test."
				+ "We have run a quick test to verify the Application is smooth and up. Please check the reports. <b>Here is a quick summary :</b> \"</head>"
				+ "        <body> \r\n<table class=\"container\" align=\"center\" style=\"padding-top:20px\">\r\n<tr align=\"center\"><td colspan=\"4\"><h2>"
				+ Constants.getProjectName() + "</h2></td></tr>\r\n<tr><td>\r\n\r\n"
				+ "       <table style=\"background:#67c2ef;width:120px\" >\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_totalTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Total</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
				+ "               \r\n" + "                 <table style=\"background:#79c447;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_passedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Passed</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                </td>\r\n" + "                <td>\r\n"
				+ "                <table style=\"background:#ff5454;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_failedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Failed</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
				+ "                <td>\r\n" + "                <table style=\"background:#fabb3d;width:120px\">\r\n"
				+ "                     <tr><td style=\"font-size: 36px\" class=\"value\" align=\"center\">"
				+ count_skippedTCs + "</td></tr>\r\n"
				+ "                     <tr><td align=\"center\">Skipped</td></tr>\r\n" + "       \r\n"
				+ "                </table>\r\n" + "                \r\n" + "                </td>\r\n"
				+ "                </tr>\r\n" + "               \r\n" + "                \r\n"
				+ "            </table>\r\n" + "       \r\n" + "    </body>\r\n" + "</html>";
	}

}
