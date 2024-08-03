Documentation:
--------------

Java openJDK should be Installed.

Latest version of Chrome browser should be Installed, currently the Chromedriver is set to Chrome v127.

Latest version of Git should be Installed.

Latest version of Maven should be Installed.

User Guide to Execute the test:
1.Download the workspace from GIT and Keep in local directories.

2.Open the workspace in intellij, configure JDK to the project, and run 'mvn install' to build the project.

3.To start execution

                 - In left panel open the AutoAssignment project 
                 
                 - Go to src->main->resources->config.properties
	                    - mention the applicationUrl, the website URL.
                        - mention the browser to be executed.

                 -  In testSuite folder under src folder open the Testng.xml file.
                 
                 -  In opened Testng file ,right click in the inside panel of file.
                 
                 -  And click Run.
                 
                 -  After execution go to target folder - right click and open cucumber-report.html to see the results.