/**
 * @author Pallavi
 */

/***************************************************/

package org.aia.java_mail_API;

import org.aia.utility.Constants;

/**
 * Data for Sending EMail after execution
 */
public class EmailConfig {

	public static final String SERVER = "smtp.gmail.com";
	public static final String PORT = "465";

	public static final String FROM = "aia500test@gmail.com";
	public static final String login_PASSWORD = "Login_123";
	public static final String PASSWORD = "dpnlfpivjzpfozak";

	/* "**********@gmail.com", */
	public static final String[] TO = { "sghodake@innominds.com", "smurala@innominds.com",
			"sgopisetty@innominds.com" };
	public static final String SUBJECT = Constants.getProjectName();
}
