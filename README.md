# aia-fonteva-automation

### AIA UI_API automation

This framework supports the API and UI automation through Selenium and Rest Assured library. Written in Java programming language and supports Extent Reporting. It is expected to supports running the test on Jenkins OR BrowserStack cloud. 

###### Built With
1. Selenium - Browser automation framework
2. Rest Assured - API automation Library
3. Java - Programming language
4. Maven - Dependency management
5. TestNG - Testing framework
6. Extent Report - Reporting framework

###### Prerequisite 
1. Selenium - Version 4 or higher
2. Rest Assured - Version 4.4.0 or higher
3. Java - Version 1.7 or higher
4. TestNG Plugin - Latest
5. Maven
6. Jenkins
7. Chrome/Edge browser installed - Any Version
8. JAVA_HOME and MAVEN_HOME Environment variable configured
9. Jenkins OR BrowserStack User ID and Access Key - Optional (If needs to run on BrowserStack)

###### Installation and Framework Set-Up
1. Download the zip or clone the Git repository.
2. Unzip the zip file (if you downloaded one).
3. Open the Eclipse and Import the Maven project
4. Navigate to the .\resources folder and open the Config.properties file, change the details like User credentials, Environment name, and check the application details like URL, application credentials (Username, Password) and application test data.
5. Keep the "platFormName" as "local" if you want to test on a local machine.
6. In the same TestData.properties file, check the API details like Base URI, credentials like User ID, Token etc and change accordingly.
8. Open the Jenkins UI and configure the project (if needs to be executed from Jenkins)

###### Running Example

###### A. Using TestNG
Right-click on the testNG.xml file and Run as TestNG Suite

###### B. Using CI/CD
Right-click on the Project name and Run as Maven Test

###### C. Using Maven
Access the Jenkins project and click on Build Now

######D. Using Browser Stack


######Execution Report
Extent report HTML file 'ExtentReportResults.html' can be accessed from the test-output folder.

###### Email Extent report
User can get the extent reports via email
