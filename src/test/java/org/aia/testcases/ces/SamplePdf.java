package org.aia.testcases.ces;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;

import org.aia.pages.BaseClass;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.utility.BrowserSetup;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.github.dockerjava.api.model.Driver;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class SamplePdf extends BaseClass {

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("@BeforeMethod");
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		util = new Utility(driver, 30);
	}

	public static String takeScreenSot() {
		driver.get("https://fonteva-io.herokuapp.com/generateMultiplePDF/dev/join?doc=https%3A%2F%2Faia--upgradestg.sandbox.my.site.com%2Fecommerce%2Fs%2Freceipt%3FgeneratePDF%3Dtrue%26language%3Den_US%26id%3DvoL1jRhW5%2BuPRPWS1nMTBMZ1lBtmmQhJWwKz1tpeChs%3D&doc=https%3A%2F%2Faia--upgradestg.sandbox.my.site.com%2Fecommerce%2Fs%2Fsales-order%3FgeneratePDF%3Dtrue%26language%3Den_US%26id%3DoXJMPJqJM5JnTdRLWB3sI3ZWnq9TIjcJHcrEPkpBHGQ%3D");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return util.captureScreenshot(driver);
	}
	
	@Test
	public static void extractPDF()  
    {  
        // creating an object of class Tesseract  
        Tesseract tesseract = new Tesseract( ) ;  
        try {  
            // this includes the path of tessdata inside the extracted folder  
            tesseract.setDatapath("C:\\Users\\sghodake\\Desktop\\Sample\\SampleForJenkins\\tessdata" ) ;  
            // specifying the image that has to be read  
            String text = tesseract.doOCR( new File(takeScreenSot() ) ) ;    
            // printing the text corresponding to the image interpreted  
            System.out.print( text ) ;  
        }  
        catch ( TesseractException e ) {  
            e.printStackTrace( ) ;  
        }  
    }  
}
