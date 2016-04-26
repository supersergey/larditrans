# LardiTrans Test Assignment #

## Configuring Environment ##

### Configuration file ###

When running the application, you must specify the configuration file in the command line, like

`mvn spring-boot:run -Dlardi.conf=file.properties`

Edit `file.properties` according to the comments inside the file.
You can find the default properties file at `src/main/resources/application.properties`

### Database setup ###

By editing the configuration file, you  can select db or file system to store your files. If you decide to use the database, you must configure the database schema.
To do so, execute the `sqlinit.sql` file in the MySQL Console.
If you wish to run the application with the test data, execute the `datainit.sql` file (you do not need to execute the `sqlinit.sql` before)
Currently, the only supported database is MySQL.
Do not forget to specify your database URL, user name and password in the configuration file.

## Build ##
`mvn clean compile`

## Run the application ##
`mvn spring-boot:run -Dlardi.conf=file.properties`

## Open the UI ##
Open `localhost:8080` in your Web-browser.

If you have configured the database with test data, you can login with the following credentials: 

* login: `test`

* password: `123456`

If you have selected to use file storage, these credentials are available by default.

Enjoy ;-)