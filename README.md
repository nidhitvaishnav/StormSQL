# StormSQL
Given project is a command line Database management system which supports DDL, DML, and VDL commands. It supports DDL commands like create database, create table, drop table, DML commands like insert, delete and update, VDL commands like select.
<h2>Installation:</h2>
  • Download and extract ntv170030_cs6360.rar.<br>
  •	Code files: <br>
      o	DBMSPrompt.java<br>
      o	FileUtils.java<br>
      o	Records.java<br>
  •	Go to MyDBMS/src directory and Compile all java files.<br>
  •	Run DBMSPrompt.<br>
  
<h2>  SQL Commands:</h2>
<h4>Assumptions:</h4>
•	In the project all the code files must be in the MyDBMS/src and the databases will get created in MyDBMS/database. These both directories must exist in the project as per given structure.<br>
•	End Every SQL commands with semicolon.<br>
•	All commands are case insensitive.<br>
•	Our primary key is row_id which is auto incremental, and it is by default added in every table.<br>
•	We are not allowing the user to add any other Primary key.<br>

<h4>1.	Create database<h4>
•	create database <database_name>;<br>
•	Create database with name <database_name>.<br>
•	It creates 2 directories, user_data and catalog.<br>
•	Inside catalog, 2 .tbl files (table files) metadata_columns.tbl and metadata_tables.tbl are created.<br>

<h4>2.	Use database</h4>
•	use <database_name>;<br>
•	To perform any operation within database it is crucial command.<br>
•	To perform operations on table, use database first.<br>
•	If you create new database than from that point by default it will use that database.<br>

<h4>3.	Drop database</h4>
•	drop database <database_name>;<br>
•	It drops the database and all tables within that database<br>

<h4>4.	Show databases</h4>
•	Show databases;<br>
•	It displays all the databases exist in the system.<br>

<h4>5.	Create table</h4>
•	create table <table_name>(<column_name> <datatype> [is null],….);<br>
•	To create a table, a database must be selected with use command.<br>
•	Table should not already exist in the database.<br>
•	To create a table at least one column_name and its datatype is necessary<br>
•	Here, table file has been created with extension .tbl<br>
•	The name of table is stored in the metadata_tables.tbl file.<br>
•	The details of column with its row_id, table name, column name, datatype, position, IsNull are stored in the netadata_columns.tbl file.<br>

<h4>6.	Show table</h4>
•	Show tables;<br>
•	To show tables, a database must be selected with use command.<br>
•	It displays all the tables within that database.<br>

<h4>7.	Show columns</h4>
•	Show columns;<br>
•	To show columns, a database must be selected with use command.<br>
•	It displays all the columns within that database.<br>

<h4>8.	Drop table</h4>
•	Drop table <table_name>;<br>
•	To drop the table a database must be selected.<br>
•	Table must exist in the database.<br>
•	All the relevant entries of given table is removed from metadata_table.tbl and metadata_columns.tbl<br>
 

<h4>9.	Insert data</h4>
•	Insert into <table_name> values(<value_1>, <value_2>,…);<br>
•	Insert into <table_name>(<column1_name>, <column2_name>,…) values(<value_1>, <value_2>,…);<br>
•	To insert values in table, a database must be selected with use command.<br>
•	Table name must be provided.<br>
•	Table should already exist in the database.<br>
•	If no column names are provided then number of values must be equal to total number of columns in the table.<br>
•	If the name of columns is provided then they must be equal to the name of columns in the database.<br>
•	Input datatype should be similar to the datatype provided in the table definition.<br>

<h4>10.	Select data</h4>
•	Select * from <table_name>;<br>
•	Select <column1_name>,… from  <table_name>;<br>
•	Select * from <table_name> where <column_name>=<value>;<br>
•	Select <column1_name>,… from  <table_name> where <column_name>=<value>;<br>
•	To select values from table, a database must be selected with use command.<br>
•	Table name must be provided.<br>
•	Table should already exist in the database.<br>
•	If where key word is written, then the condition must be provided.<br>
•	Condition columns and select columns must match to the columns in the tables.<br>
•	Only one condition after where clause is supported.<br>

<h4>11.	Update table values</h4>
•	Update <table_name> set <column_name>=<value>;<br>
•	Update <table_name> set <column_name>=<value> where <column_name>=<value>;<br>
•	To select values from table, a database must be selected with use command.<br>
•	Table name must be provided.<br>
•	Table should already exist in the database.<br>
•	If where key word is written, then the condition must be provided.<br>
•	Condition columns and select columns must match to the columns in the tables.<br>
•	Only one condition after where clause is supported.<br>
•	You can edit only one column at a time. (Multiple column updates are not supported).<br>
 

<h4>12.	Delete data</h4>
•	delete from <table_name>;<br>
•	delete from <table_name> where <column_name>=<value>;<br>
•	To delete value from table, a database must be selected with use command.<br>
•	Table name must be provided.<br>
•	Table should already exist in the database.<br>
•	If where key word is written, then the condition must be provided.<br>
•	Condition columns and select columns must match to the columns in the tables.<br>
•	All the rows for which condition is satisfied are deleted.<br>
•	If no where clause is provided, then all the rows from the table are deleted.<br>

<h4>13.	Help</h4>
•	Help;<br>
•	Basic syntax of the above queries are provided as guideline.<br>

<h4>14.	Version</h4>
•	Version;<br>
•	Database name, version and name of author is provided.<br>

<h4>15.	Exit</h4>
•	Exit;<br>
•	To exit the database StormSQL exit is used.<br>

16.	Quit
•	Quit;
•	To exit the database StormSQL quit command is used.


