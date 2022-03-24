Title: Desktop Scheduling Application.

Purpose: This application is designed to be used in conjunction with an SQL Database ("client_schedule").
         The client_schedule database contains several tables of information; an 'appointment table', a 'contacts'
         table, a 'countries' table, a 'customers' table, a 'first_level_divisions' table, and a 'users' table.
         At its heart, this is a scheduling application. After initially logging in, the user is greeted by a Main
         Menu Screen. Here the user has the option of viewing all customer records in the database. Customer
         records are viewed in table form. Each customer is identified by its own unique customer ID number.
         Other customer attributes displayed in the table are Name, Address, Postal Code, Phone #, and Division ID.
         The user also has the option of adding new customers to the database, or deleting customers from the DB.
         In addition to customer data, the user of this application can view all appointments located in the database.
         Similar to customer information, appointment info is displayed in table form as well. Each appointment has
         its own Appointment ID, Title, Description, Location, Contact, Type, Start Time/Date, End Time/Date,
         Customer ID, and User ID. The user has the ability to create/delete appointments as well. A unique feature
         of this application is that the times and dates displayed use the user's local time on his/her machine.
         Any local time entered by the user (creating a new appointment, for instance) will automatically be converted
         to UTC time when it's added into the database. Meaning that no matter where this database is accessed,
         all time zone conversions are automatically done for each user, correct for that individual's location.
         Appointments can be viewed by Customer, by User, or by Contact - making retrieval easy.

This application was authored by: Brandon Rumzis as a final project for SoftwareII - Advanced Java Concepts - C195.
                                  brumzis@my.wgu.edu. ID# 001283408. Build was completed using IntelliJ IDEA 2021.2.2
                                  (Community Edition) Runtime version: 11.0.12+7-b1504.28 amd64.
                                  VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o. Windows 10 10.0
                                  Project SDK 17 (17.0.1). This project was completed on 3/24/22.

To run: This application can be run from the 'Main' method. VM Configurations must be set to allow javafx to run
        properly. VM options include: --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics

Additional report chosen: I chose to include the ability to view all appointments by User. I noticed this particular
                          functionality was not part of the original requirements for the project. I thought the
                          ability for the user to filter all appointments belonging to him/her made sense to include.
                          A combobox with all users in the database can be selected - filtering all appointments
                          for the user selected.

My SQL Connector: mysql-connector-java-8.0.28
