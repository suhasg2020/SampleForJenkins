package org.aia.utility;

public class Constants {
	public static final  String USER_DIR = System.getProperty("user.dir");
	public static final  String APPLICATION_PROPERTIES_PATH = USER_DIR+"/config.properties";
	public static final String SCREENSHOT_PATH = USER_DIR+"/ScreenShots/";
	public static final String GENERATE_REPORT_PATH = USER_DIR+"/Reports/report.html";
	public static final String GENERATE_Template_REPORT_PATH = USER_DIR+"/test-output/custom_report_with_chart.html";
	public static final String EXTENT_REPORT_FOLDER_PATH = USER_DIR + "/Reports/";
	public static final String EXTENT_REPORT_NAME = "report.html";
	public static final String YES = "yes";
	public static final String NO = "no";
	
	private static final String PROJECT_NAME = "Fonteva GUI Automation Report";
	public static String getProjectName() {
		return PROJECT_NAME;
	}
}
