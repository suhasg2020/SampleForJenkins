package org.aia.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class helps in the configuration of Log4j
 * @author Pallavi A
 */

public class Logging {

	// The object logger is a static reference to the Logger class used to perform
	// Log4j operations
	public static Logger logger = Logger.getLogger(Logging.class);

	public static void configure() throws FileNotFoundException {

		// PropertiesConfigurator is used to configure Logger from a .properties file
		FileInputStream inputProp = new FileInputStream("./properties/log4j.properties");
		PropertyConfigurator.configure(inputProp);

	}

}
