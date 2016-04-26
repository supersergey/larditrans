# LardiTrans Test Assignment #

# Configuring Environment #

## jqGrid ##

Web UI uses the jqGrid control that runs upon JQuery.
Before you run the application:
1. Open http://www.trirand.com/blog/?page_id=6
2. Select all checkboxes and click download.
3. Unzip the downloaded files to src\main\resources\static\trirand\

## Configuration file ##

You must specify the configuration file in the command line, like
'mvn spring-boot:run -Dlardi.conf=file.properties'

Edit 'file.properties' according to the comments inside the file

## Database setup ##

By editing the configuration file, you  can select db or file system to store your files. If you decide to use the database (), you must configure the database schema.
To do so, execute the 'sqlinit.sql' file in the MySQL Console.
If you wish to run the application with the test data, execute the 'datainit.sql' file (you do not need to execute the 'sqlinit.sql' before)
Currently, the only supported database is MySQL.
Do not forget to specify your database URL, user name and password in the configuration file.

## Run the application ##
'mvn spring-boot:run -Dlardi.conf=file.properties'

## Open the application ##
Open localhost:8080 in your Web-browser.
If you have configured the database with test data, you can login with the following credentials: 
* login: test
* password: test
If you have selected to use file storage, these credentials are available by default.

Enjoy ;-)