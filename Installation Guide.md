# Installation Instructions #

All necessary libraries are kept in the libraries module.

Depending on your java SDK, it may not come with JavaFX included. 

* Step 1: Ensure that Project language level is set to 8

* Step 2: Add Java MySQL Connector

* Step 3: Add Java FX library

  * In IntelliJ open up 'Project Structure' (CTRL + ALT + SHIFT + S)
  
  * Then under 'Platform Settings' select the 'Global Libraries' tab
  
  * Select the + sign in the upper left. 
  
  * Then select 'Java' from the dropdown menu.
  
  * Select all the jar files under Final_Project/libraries/javafx-sdk-11.0.2/lib 
  
  * When prompted "Library <YOUR LIBRARY NAME> will be added to the selected modules." make sure that this project is currently selected, and the click ok.
  
* Step 4: In the Database_Utils class edit the following 4 fields so that it satisfies the requirements of your MySQL server
  * userName
  * password
  * serverName
  * portNumber
  * dbName

* Ensure that the Database has been restored from the dump file
  * /Data Files/nbafantasy.sql
  
* Run the program by running Main.main()
  * Either set up the appropriate run configuration or right click on the Main class and select 'Run Main.main()'


