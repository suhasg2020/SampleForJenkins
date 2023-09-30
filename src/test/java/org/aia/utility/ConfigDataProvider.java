package org.aia.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Reporter;

public class ConfigDataProvider {
	
	Properties pro;

	public ConfigDataProvider() {
		
		
		pro=new Properties();
		
		try 
		{
			//pro.load(new FileInputStream(new File(System.getProperty("user.dir")+"\\Config\\config.properties")));
			pro.load(new FileInputStream(new File("./Config/config.properties")));
			Reporter.log("LOG : INFO -Config File loaded", true);

		} catch (IOException e) {
			
			Reporter.log("LOG : FAIL-Failed to load Config files", true);

		}
		
	}
	
	/**
	 * @return Properties
	 * 
	 */
	public Properties testDataProvider() {
		pro=new Properties();
		try {
			FileInputStream ip = new FileInputStream("./testData/testData.properties");
		pro.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}
	
	
	public String getStagingURL()
	{
		return pro.getProperty("stagingurl");
	}
	
	public String getUATURL()
	{
		return pro.getProperty("uaturl");
	}
	
	public String getProdURL()
	{
		return pro.getProperty("productionurl");
	}
	
	
	public String getValue(String key)
	{
		return pro.getProperty(key);
	}
	
}
