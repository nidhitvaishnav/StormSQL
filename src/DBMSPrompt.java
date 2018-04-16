import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.System.out;

/**
 *  @author Nidhi Vaishnav
 *  @version 1.0
 *  <b>
 *  <p>This is the main file which creates interactive prompt</p>
 *  </b>
 *
 */
public class DBMSPrompt {

	/* This can be changed to whatever you like */
	static String prompt = "stormsql> ";
	static String version = "v1.01(example)";
	static String copyright = "©2018 Nidhi Vaishnav";
	static boolean isExit = false;
	static String currentPath = System.getProperty("user.dir")+"\\database\\";
	static String currentDatabasePath = "";
	/*
	 * Page size for alll files is 512 bytes by default.
	 * You may choose to make it user modifiable
	 */
	static long pageSize = 512; 

	/* 
	 *  The Scanner class is used to collect user commands from the prompt
	 *  There are many ways to do this. This is just one.
	 *
	 *  Each time the semicolon (;) delimiter is entered, the userCommand 
	 *  String is re-populated.
	 */
	static Scanner scanner = new Scanner(System.in).useDelimiter(";");
	
	/** ***********************************************************************
	 *  Main method
	 */
    public static void main(String[] args) {

		/* Display the welcome screen */
		splashScreen();

		/* Variable to collect user input from the prompt */
		String userCommand = ""; 

		while(!isExit) {
			System.out.print(prompt);
			/* toLowerCase() renders command case insensitive */
			userCommand = scanner.next().replace("\n", " ").replace("\r", "").trim().toLowerCase();
			// userCommand = userCommand.replace("\n", "").replace("\r", "");
			parseUserCommand(userCommand);
		}
		System.out.println("Exiting...");


	}

	/** ***********************************************************************
	 *  Static method definitions
	 */

	/**
	 *  Display the splash screen
	 */
	public static void splashScreen() {
		System.out.println(line("-",80));
        System.out.println("Welcome to stormBaseLite"); // Display the string.
		System.out.println("stormBaseLite Version " + getVersion());
		System.out.println(getCopyright());
		System.out.println("\nType \"help;\" to display supported commands.");
		System.out.println(line("-",80));
	}
	
	/**
	 * @param s The String to be repeated
	 * @param num The number of time to repeat String s.
	 * @return String A String object, which is the String s appended to itself num times.
	 */
	public static String line(String s,int num) {
		String a = "";
		for(int i=0;i<num;i++) {
			a += s;
		}
		return a;
	}
	
	public static void printCmd(String s) {
		System.out.println("\n\t" + s + "\n");
	}
	public static void printDef(String s) {
		System.out.println("\t\t" + s);
	}
	
		/**
		 *  Help: Display supported commands
		 */
		public static void help() {
			out.println(line("*",80));
			out.println("SUPPORTED COMMANDS\n");
			out.println("All commands below are case insensitive\n");
			out.println("SHOW TABLES;");
			out.println("\tDisplay the names of all tables.\n");
			//printCmd("SELECT * FROM <table_name>;");
			//printDef("Display all records in the table <table_name>.");
			out.println("SELECT <column_list> FROM <table_name> [WHERE <condition>];");
			out.println("\tDisplay table records whose optional <condition>");
			out.println("\tis <column_name> = <value>.\n");
			out.println("DROP TABLE <table_name>;");
			out.println("\tRemove table data (i.e. all records) and its schema.\n");
			out.println("UPDATE TABLE <table_name> SET <column_name> = <value> [WHERE <condition>];");
			out.println("\tModify records data whose optional <condition> is\n");
			out.println("VERSION;");
			out.println("\tDisplay the program version.\n");
			out.println("HELP;");
			out.println("\tDisplay this help information.\n");
			out.println("EXIT;");
			out.println("\tExit the program.\n");
			out.println(line("*",80));
		}

	/** return the DavisBase version */
	public static String getVersion() {
		return version;
	}
	
	public static String getCopyright() {
		return copyright;
	}
	
	public static void displayVersion() {
		System.out.println("stormBaseLite Version " + getVersion());
		System.out.println(getCopyright());
	}
		
	public static void parseUserCommand (String userCommand) {
		
		/* commandTokens is an array of Strings that contains one token per array element 
		 * The first token can be used to determine the type of command 
		 * The other tokens can be used to pass relevant parameters to each command-specific
		 * method inside each case statement */
		// String[] commandTokens = userCommand.split(" ");
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(userCommand.split(" ")));
		

		/*
		*  This switch handles a very small list of hardcoded commands of known syntax.
		*  You will want to rewrite this method to interpret more complex commands. 
		*/
		switch (commandTokens.get(0)) {
			case "create":
//				System.out.println("CASE: CREATE");
				if (commandTokens.get(1).equalsIgnoreCase("database")){
					parseCreateDatabase(userCommand);
				}
				else if(commandTokens.get(1).equalsIgnoreCase("table")) {
					parseCreateTable(userCommand);
				}
				else {
					System.out.println("I didn't understand the command: \"" + userCommand + "\"");
				}
				break;
			case "show":
//				System.out.println("CASE: SHOW");
				if (commandTokens.get(1).equalsIgnoreCase("databases")) {
					parseShowDatabase(userCommand);
				}
				else if(commandTokens.get(1).equalsIgnoreCase("tables")) {
					parseShowTable(userCommand);
				}
				else if (commandTokens.get(1).equalsIgnoreCase("columns")) {
					parseShowColumn(userCommand);
				}
				else {
					System.out.println("I didn't understand the command: \"" + userCommand + "\"");					
				}
				break;
			case "drop":
//				System.out.println("CASE: DROP");
				if (commandTokens.get(1).equalsIgnoreCase("database")) {
					dropDatabase(userCommand);
				}
				else if(commandTokens.get(1).equalsIgnoreCase("table")) {
					dropTable(userCommand);
				}
				else {
					System.out.println("I didn't understand the command: \"" + userCommand + "\"");					
				}
				break;
			case "use":
//				System.out.println("CASE: USE");
				useDatabase(userCommand);
				break;
			case "insert":
				System.out.println("CASE: INSERT");
				insertQuery(userCommand);
				break;
			case "delete":
				System.out.println("CASE: DELETE");
				deleteQuery(userCommand);
				break;
			case "update":
				System.out.println("CASE: UPDATE");
				parseUpdate(userCommand);
				break;

			case "select":
				System.out.println("CASE: SELECT");
				parseQuery(userCommand);
				break;
			
			case "help":
				help();
				break;
			case "version":
				displayVersion();
				break;
			case "exit":
				isExit = true;
				break;
			case "quit":
				isExit = true;
			default:
				System.out.println("I didn't understand the command: \"" + userCommand + "\"");
				break;
		}
	}
	
	/**
	 *  Stub method for creating database
	 *  @param createDBString is a String of the user input
	 */
	public static void parseCreateDatabase(String createDBString) {
//		System.out.println("STUB: This is the createDatabase method.");
//		System.out.println("\tParsing the string:\"" + createDBString + "\"");
		
		
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(createDBString.split(" ")));
		if (commandTokens.size()==3) {
			try {
				String dbName = commandTokens.get(2);
				File dbFile = new File(currentPath+dbName);
				boolean dbExist=dbFile.exists();
				
				if (!dbExist) {
					Boolean dbSuccessFlag = dbFile.mkdir();
					if (!dbSuccessFlag) {
						System.out.println("database "+dbName+" has not been created;");
					}
					else {
						File metadataFile = new File(currentPath+ dbName+"\\catalog");
						File dataFile = new File(currentPath+ dbName+"\\user_data");
						
						Boolean mdSuccessFlag = metadataFile.mkdir();
						Boolean dataSuccessFlag = dataFile.mkdir();
						setCurrentDatabase(dbName);
						try {
							RandomAccessFile tablesCatalog = new RandomAccessFile(currentDatabasePath+"catalog\\metadata_tables.tbl", "rw");
							createTableFile(tablesCatalog, 0);
							tablesCatalog.close();
						}
						catch (Exception e) {
							out.println("Unable to create the metadata_tables file");
							e.printStackTrace();
						}

						/** Create davisbase_columns systems catalog */
						try {
							RandomAccessFile columnsCatalog = new RandomAccessFile(currentDatabasePath+"catalog\\metadata_columns.tbl", "rw");
							createTableFile(columnsCatalog, 0);
							columnsCatalog.close();
						}
						catch (Exception e) {
							out.println("Unable to create the metadata_columns file");
							e.printStackTrace();
						}
						if (mdSuccessFlag && dataSuccessFlag) {
							System.out.println("database "+dbName+" is successfully created;");
						}
						else {
							System.out.println("matadata or data is not created;");
						}
					}
				}
				else {
					System.out.println("Can't create database "+dbName+"; database already exists;");
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}


	
	/**
	 *  Stub method for creating database
	 *  @param createDBString is a String of the user input
	 */
	public static void parseShowDatabase(String createDBString) {
//		System.out.println("STUB: This is the parseShowDatabase method.");
//		System.out.println("\tParsing the string:\"" + createDBString + "\"");
		
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(createDBString.split(" ")));
		if (commandTokens.size()==2) {
			try {
				File[] directories = new File(currentPath).listFiles(File::isDirectory);
				System.out.println("---------------------");
				System.out.println("Databases");
				System.out.println("---------------------");

				for (int x=0; x<directories.length; x++)
				{
					System.out.println(directories[x].getName());
				}
				System.out.println("---------------------");

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("You have an error in your syntex right next to databases;");
		}
	}
		
	
	/**
	 *  Stub method for dropping database
	 *  @param dropDBString is a String of the user input
	 */
	public static void dropDatabase(String dropDBString) {
//		System.out.println("STUB: This is the dropDatabase method.");
//		System.out.println("\tParsing the string:\"" + dropDBString + "\"");

		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(dropDBString.split(" ")));
		String dbName = commandTokens.get(2);
		if (commandTokens.size()==3) {
			File dbFile = new File(currentPath+dbName);
			boolean dbExist=dbFile.exists();
			if (dbExist) {
				FileUtils fu = new FileUtils();
					try {
					Boolean deleteFlag = fu.deleteRecursive(dbFile);
						if (deleteFlag){
							setCurrentDatabase("");
							System.out.println("database "+dbName+" has been deleted successfully;");
						}
						else {
							System.out.println("Cannot delete database "+dbName+";");
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
			}
			else {
				System.out.println("can't drop database '"+dbName+"', database does not exist;");
			}
		}
		else {
			System.out.println("You have an error in your syntex right next to databases;");
		}
	}
	/**
	 *  Stub method for dropping database
	 *  @param useDBString is a String of the user input
	 */
	public static void useDatabase(String useDBString) {
//		System.out.println("STUB: This is the useDatabase method.");
//		System.out.println("\tParsing the string:\"" + useDBString + "\"");
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(useDBString.split(" ")));
		if (commandTokens.size()==2) {
			String dbName = commandTokens.get(1);
			File dbFile = new File(currentPath+dbName);
			boolean dbExist=dbFile.exists();
			if (dbExist) {
				setCurrentDatabase(dbName);
			}
			else {
				System.out.println("Unknown database "+dbName+";");
			}
		}
	}
	
	/**
	 *  Stub method for setting current database as working database
	 *  @param dbName is a String of the user input
	 */
	public static void setCurrentDatabase(String dbName) {
		if (dbName.equals("")) {
			currentDatabasePath = "";
		}
		else {
			currentDatabasePath = currentPath + dbName+"\\";
		}
	}

	/**
	 *  Stub method for creating new tables
	 *  @param file is a RandomAccessFile of the user input
	 */

	public static void createTableFile(RandomAccessFile file, long currentFileSize) {
		try {
    
			/* Initially, the file is one page in length */
			file.setLength(pageSize+currentFileSize);
			/* Set file pointer to the beginnning of the file */
			file.seek(0+currentFileSize);
			/*Page is a leaf page*/
			file.writeByte(13);
			/*Number of cells*/
			file.writeByte(0);
			/*start writing at location from bottom*/
			int startWriteLoc = (int) ((pageSize+currentFileSize)-1);
			file.writeShort(startWriteLoc);
			/*Page pointer for the next page; no next page : -1*/
			file.writeInt(-1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Stub method for creating new tables
	 *  @param queryString is a String of the user input
	 */
	public static void parseCreateTable(String createTableString) {
		
//		System.out.println("STUB: Calling your method to create a table");
//		System.out.println("Parsing the string:\"" + createTableString + "\"");
		
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
		ArrayList<String> createTableTokens = new ArrayList<String>(Arrays.asList(createTableString.split(" ")));

		System.out.println(createTableTokens.toString());
		/* Define table file name */

		String tableName = "";
		
		String tempTableName = createTableTokens.get(2);
		if (createTableTokens.get(2).contains("(")) {
			tableName = tempTableName.substring(0, tempTableName.indexOf("("));		
		}
		else {
			tableName = createTableTokens.get(2);
		}
		String tableFileName = tableName+".tbl";
		String columnStr = "";
		try {
		columnStr = createTableString.substring(createTableString.indexOf("(")+1, createTableString.indexOf(")"));
		}
		catch(Exception e) {
			System.out.println("Error: A table must have atleast 1 column;");
			return;
		}
		System.out.println(columnStr);
		
		/*Tokenizing string*/
		ArrayList<String> ColumnTokens = new ArrayList<String>(Arrays.asList(columnStr.split(",")));
		for (int i=0; i<ColumnTokens.size(); i++) {
			System.out.println(ColumnTokens.get(i));			
		}
		if (ColumnTokens.size()<=1) {
			System.out.println("--Error: A table must have atleast 1 column;--");
			return;
		}
		
		String tableFilePath = currentDatabasePath+"user_data\\"+tableFileName;
		FileUtils fu = new FileUtils();
		if (fu.tableExists(tableFilePath)) {
			System.out.println("Table "+tableFileName+" already exist;");
			return;
		}
				
		/*  Code to insert a row in the davisbase_tables table 
		 *  i.e. database catalog meta-data 
		 */
		 //make an entry in catalog table file
		String catalogTableFilePath = currentDatabasePath+"catalog\\metadata_tables.tbl";
		
		try {
	        RandomAccessFile catalogTableFile =  new RandomAccessFile(catalogTableFilePath, "rw");
	        int nCols = 2;
	        
	        /*Page pointer for the next page; no next page : -1*/
			int row_id = fu.getRow_id(catalogTableFile);
	        
	        /*getting size of current data*/
	        Records[] tableData= new Records[nCols];
	        tableData[0] =	new Records(Integer.toString(row_id), "int");
	        tableData[1] = new Records(tableName, "text");
	        
	        writeRecordsInFile(catalogTableFile ,tableData, nCols);
	        catalogTableFile.close();
//	        System.out.println("-------------------Catalog table is updated---------------------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*  Code to insert rows in the davisbase_columns table  
		 *  for each column in the new table 
		 *  i.e. database catalog meta-data 
		 */
		
		
		String catalogColumnFilePath = currentDatabasePath+"catalog\\metadata_columns.tbl";
		/*  Code to create a .tbl file to contain column data */
		try {
			/*  Create RandomAccessFile tableFile in read-write mode.
			 *  Note that this doesn't create the table file in the correct directory structure
			 */
			RandomAccessFile catalogColumnFile = new RandomAccessFile(catalogColumnFilePath, "rw");
			String columnData="";
			
			/*Writing row_id column by default*/
			
			int nCols = 6;
	        /*Page pointer for the next page; no next page : -1*/
			int row_id = fu.getRow_id(catalogColumnFile);
	        /*getting size of current data*/
	        Records[] tableData= new Records[nCols];

	        tableData[0] =	new Records(Integer.toString(row_id), "int");
	        tableData[1] = new Records(tableName, "text");
	        //Column name
	        tableData[2] = new Records("row_id", "text");
	        //data type
	        tableData[3] = new Records("int", "text");
	        //ordinal position
	        tableData[4] = new Records("0", "tinyint");
	        /*Not null: 
	         * 1 -> True; 
	         * 0 -> False*/
	        tableData[5] = new Records("1", "tinyint");
	       
	        
	        
	        writeRecordsInFile(catalogColumnFile ,tableData, nCols);
			
			
			for (int i=0; i<ColumnTokens.size(); i++) {
				columnData = ColumnTokens.get(i);
				/*Tokenizing string*/
				ArrayList<String> colParameters = new ArrayList<String>(Arrays.asList(columnData.replaceAll("^[,\\s]+", "").split("[,\\s]+")));
//				System.out.println(colParameters.toString());
				if (colParameters.size()<2) {
					System.out.println("Error: column name or datatype is not defined");
					return;
				}

				int ordinal_position=i+1;

				int not_null=0;
				String columnName = colParameters.get(0);
				String datatype = colParameters.get(1);
//				System.out.println("colParameters.size: "+colParameters.size());
				if (colParameters.size()==4 && colParameters.get(2).equals("not") && colParameters.get(3).equals("null")) {
					not_null=1;
				}
				
				nCols = 6;
		        /*Page pointer for the next page; no next page : -1*/
				row_id = fu.getRow_id(catalogColumnFile);
		        /*getting size of current data*/
		        tableData= new Records[nCols];

		        tableData[0] = new Records(Integer.toString(row_id), "int");
		        tableData[1] = new Records(tableName, "text");
		        tableData[2] = new Records(columnName, "text");
		        tableData[3] = new Records(datatype, "text");
		        tableData[4] = new Records(Integer.toString(ordinal_position), "tinyint");
		        tableData[5] = new Records(Integer.toString(not_null), "tinyint");
		        
//		        for (int j=0; j<nCols; j++) {
//		        	System.out.println(tableData[j].data+"; "+tableData[j].dataType+ "; "+tableData[j].nByte+"; "+tableData[j].serialCode);
//		        }
		        
		        
		        writeRecordsInFile(catalogColumnFile ,tableData, nCols);
//		        System.out.println("");
			}
			
			catalogColumnFile.close();
//	        System.out.println("Catalog column is updated");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		try {
			/*  Create RandomAccessFile tableFile in read-write mode.
			 *  Note that this doesn't create the table file in the correct directory structure
			 */
			RandomAccessFile tableFile = new RandomAccessFile(tableFilePath, "rw");
			createTableFile(tableFile, 0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		System.out.println("Table "+tableName+" has been created successfully;");
		
			
	}
	
	public static void writeRecordsInFile(RandomAccessFile file ,Records[] tableData, int nCols) {
        /*1 to define number of columns;
         * nCols contains serial codes of all the columns
         * */
		FileUtils fu = new FileUtils();
		try {
			/*Page pointer for the next page; no next page : -1*/
			long pagePointer = fu.getPagePointer(file);
			
	        /*get the total cell in the page*/
			int nCellInPage = fu.getnCellInFile(file, pagePointer);
	        
//	        System.out.println("nCellInPage: "+nCellInPage);
	       
	        /*Get the offset of last element*/
	        int lastRecordLoc = fu.getoffset(file, pagePointer);
	        if (lastRecordLoc==-99 ) {
	        	System.out.println("Error in table creation");
	        }
	        
//	        System.out.println("last record loc: "+lastRecordLoc);
	        
	        /*get offset of this index*/
	        long indexWriteLoc = fu.getIndexOffset(nCellInPage, pagePointer);
	        
//	        System.out.println("index write loc: "+indexWriteLoc);
			
	        int totalBytes=1+nCols;
	        for (int i = 0; i<nCols; i++) {
	        	totalBytes+=tableData[i].nByte;
	        }
	        
	        long writeLoc = (lastRecordLoc-totalBytes);
	        
	        if ((indexWriteLoc+2) > writeLoc) {
        		/* updating page pointer from -1 to next page pointer*/
				file.seek(pagePointer+4);
				int newPagePointer =  (int) (pagePointer+pageSize);
				file.writeInt(newPagePointer);
				/* adding a new page*/
				long fileSize = file.length();
				createTableFile(file, fileSize);
				/*updating page pointer*/
				pagePointer = newPagePointer;
//				System.out.println(pagePointer);
				nCellInPage = fu.getnCellInFile(file, pagePointer);
				lastRecordLoc = fu.getoffset(file, pagePointer);
				indexWriteLoc = fu.getIndexOffset(nCellInPage, pagePointer);
				writeLoc = (lastRecordLoc-totalBytes);
	        }
	        
	        /*Updating nCellInPage*/
	        fu.setnCellInFile(file, pagePointer, nCellInPage);
	        fu.setStartOfContent(file, pagePointer, (int)writeLoc);
	        fu.setIndexOffset(file, indexWriteLoc, (int)writeLoc);
	        
			/*Writing record data in file*/
	        file.seek(writeLoc);
	        file.writeByte(nCols);
	        /*writing the serialCode of all the records */
	        for (int i=0; i<tableData.length; i++) {
	        	file.writeByte(tableData[i].serialCode);
	        }
	        for (int i=0; i<tableData.length; i++) {
	        	if (tableData[i].serialCode==0x00 || tableData[i].serialCode==0x04) {
	        		file.writeByte((int)tableData[i].data);
	        	}
	        	else if (tableData[i].serialCode==0x01 || tableData[i].serialCode==0x05) {
	        		file.writeShort((int)tableData[i].data);
	        	}
	        	else if (tableData[i].serialCode==0x02 || tableData[i].serialCode==0x06 || tableData[i].serialCode==0x08) {
	        		file.writeInt((int)tableData[i].data);
	        	}
	        	else if (tableData[i].serialCode==0x03 || tableData[i].serialCode==0x07 || tableData[i].serialCode==0x09 || tableData[i].serialCode==0x0A || tableData[i].serialCode==0x0B) {
	        		file.writeLong((int)tableData[i].data);	
	        	}
	        	else{
	        		file.writeBytes((String) tableData[i].data);
	        	}
	        }
	        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getDatabaseName() {
		String dbName = "";
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return dbName;
		}
		dbName = new File(currentDatabasePath).getName();
		return dbName;
	}
	
	/**
	 *  Stub method for displaying table
	 *  @param showTableStr is a String of the user input
	 */
	public static void parseShowTable(String showTableStr) {
//		System.out.println("STUB: This is the showTable method.");
//		System.out.println("\tParsing the string:\"" + showTableStr + "\"");
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(showTableStr.split(" ")));
		
//		int pos = currentDatabasePath.lastIndexOf("/") + 1;
//		currentDatabasePath.substring(pos, path.length()-pos);
		
		String dbName = getDatabaseName();
		
		if (commandTokens.size()==2) {
			try {
				File[] tblFiles = new File(currentDatabasePath+"user_data\\" ).listFiles(File :: isFile);
				System.out.println("---------------------");
				System.out.println("Tables in "+dbName);
				System.out.println("---------------------");

				for (int x=0; x<tblFiles.length; x++)
				{	
					int dotPos = tblFiles[x].getName().indexOf(".");
					String tableName = tblFiles[x].getName().substring(0, dotPos);
					System.out.println(tableName);
				}
				System.out.println("---------------------");

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("You have an error in your syntex right next to tables;");
		}
		
	}

	public static void parseShowColumn(String showColumnStr) {
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
		int nCols = 6;
		String dbName = getDatabaseName();
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(showColumnStr.split(" ")));
		try {
			if (commandTokens.size()==2) {
				System.out.println("---------------------");
				System.out.println("Columns in "+dbName);
				System.out.println("---------------------");

				RandomAccessFile columnFile = new RandomAccessFile(currentDatabasePath+"catalog\\metadata_columns.tbl", "r");
				
				ArrayList <Records[]> columnList = new ArrayList<Records[]>();
				FileUtils fu = new FileUtils();
				
				long pagePointer = -512;
				/*reading metadata table*/

				do {
					pagePointer+=pageSize;
					int nCellInPage=fu.getnCellInFile(columnFile, pagePointer);
					long indexOffset = pagePointer+8;
					/*read all items in the given page*/
//					System.out.println("   Inside While: IndexOffset: "+indexOffset);
					for (int i=0; i<nCellInPage; i++) {

						columnFile.seek(indexOffset);
						int dataOffset = columnFile.readShort();
//						System.out.println("   Inside for: dataOffset"+dataOffset);
						Records[] recordRow = readRecord(columnFile, dataOffset);
						
						System.out.println(String.valueOf(recordRow[0].data)+" "+(String)recordRow[1].data+" "+(String)recordRow[2].data+" "+(String)recordRow[3].data+" "+String.valueOf(recordRow[4].data)+" "+String.valueOf(recordRow[5].data));

						indexOffset+=2;
					}
				}while(fu.nextPageExist(columnFile, pagePointer));
				System.out.println("---------------------");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	
	/**
	 *  Stub method for dropping tables
	 *  @param dropTableString is a String of the user input
	 */
	public static void dropTable(String dropTableString) {
		System.out.println("STUB: This is the dropTable method.");
		System.out.println("\tParsing the string:\"" + dropTableString + "\"");
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
	}
	
	/**
	 *  Stub method for executing queries
	 *  @param queryString is a String of the user input
	 */
	public static void parseQuery(String queryString) {
		System.out.println("STUB: This is the parseQuery method");
		System.out.println("\tParsing the string:\"" + queryString + "\"");
	}

	/**
	 *  Stub method for updating records
	 *  @param updateString is a String of the user input
	 */
	public static void parseUpdate(String updateString) {
		System.out.println("STUB: This is the dropTable method");
		System.out.println("Parsing the string:\"" + updateString + "\"");
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
	}
	/**
	 *  Stub method for inserting records
	 *  @param insertQuery is a String of the user input
	 */
	public static void insertQuery(String insertQueryString) {
//		System.out.println("STUB: This is the insert method");
//		System.out.println("Parsing the string:\"" + queryString + "\"");
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
		ArrayList<String> insertQueryTokens = new ArrayList<String>(Arrays.asList(insertQueryString.split("values")));
		ArrayList<String> beforeValuesTokens = new ArrayList<String>(Arrays.asList(insertQueryTokens.get(0).split(" ")));
		
		System.out.println(insertQueryTokens.toString());
		System.out.println(beforeValuesTokens.toString());
		if (beforeValuesTokens.get(0).equalsIgnoreCase("insert")==false || beforeValuesTokens.get(1).equalsIgnoreCase("into")==false) {
			System.out.println("Error: Insert command is not correct. Please check syntex");
			return;
		}
		
		String tableName = beforeValuesTokens.get(2).substring(0, beforeValuesTokens.get(2).indexOf("("));
		String tableFileName = tableName+".tbl";
		String dbName = getDatabaseName();
		
		String tablePath = currentDatabasePath+"user_data\\"+tableFileName;
		String metadataColumnPath = currentDatabasePath+"catalog\\metadata_columns.tbl";
		
		FileUtils fu = new FileUtils();
		if (fu.tableExists(tablePath)==false) {
			System.out.println("Error: Table "+dbName+"."+tableName+" does not exist");
			return;
		}
		
		String columnStr = "";
		boolean useDefaultColsFlag = false;
		try {
			int startIndex = insertQueryTokens.get(0).indexOf("(");
			int endIndex = insertQueryTokens.get(0).indexOf(")");
			if (startIndex==-1 || endIndex==-1) {
				useDefaultColsFlag=true;
			}
			else {
				columnStr = insertQueryTokens.get(0).substring(startIndex+1, endIndex);
				useDefaultColsFlag = false;
				System.out.println("columnStr: "+columnStr);
			}
		}
		catch(Exception e) {
			System.out.println("Error: Syntex error");
			return;
		}
		String valueStr = "";
		try {
			int startIndex = insertQueryTokens.get(1).indexOf("(");
			int endIndex = insertQueryTokens.get(1).indexOf(")");
			if (startIndex!=-1 || endIndex!=-1) {
				valueStr = insertQueryTokens.get(1).substring(startIndex+1, endIndex);
				System.out.println("valueStr: "+valueStr);
			}
		}
		catch(Exception e) {
			System.out.println("Error: Syntex error");
			return;
		}
		
		

		
//		readColumns(RandomAccessFile columnFile, String tableName);
	}
	/**
	 *  Stub method for deleting records
	 *  @param queryString is a String of the user input
	 */
	public static void deleteQuery(String queryString) {
//		System.out.println("STUB: This is the delete method");
//		System.out.println("Parsing the string:\"" + queryString + "\"");
		if (currentDatabasePath.equals("")) {
			System.out.println("No database selected. Select the default DB to be used by USE databseName;");
			return;
		}
	}
	
	public static ArrayList <Records[]> readColumns(RandomAccessFile columnFile, String tableName) {
		/* metadata table has 6 columns:
		 * row_id, table_name, column_name, data_type, ordinal_position, is_nullable*/
		int nCols = 6;
		ArrayList <Records[]> columnList = new ArrayList<Records[]>();
		FileUtils fu = new FileUtils();
		
		/*reading metadata table*/
		try {
			long pagePointer = -512;
			/*reading metadata table*/

			do {
				pagePointer+=pageSize;
				int nCellInPage=fu.getnCellInFile(columnFile, pagePointer);
				long indexOffset = pagePointer+8;
				/*read all items in the given page*/
//				System.out.println("   Inside While: IndexOffset: "+indexOffset);
				for (int i=0; i<nCellInPage; i++) {

					columnFile.seek(indexOffset);
					int dataOffset = columnFile.readShort();
//					System.out.println("   Inside for: dataOffset"+dataOffset);
					Records[] recordRow = readRecord(columnFile, dataOffset);
					
					System.out.println(String.valueOf(recordRow[0].data)+" "+(String)recordRow[1].data+" "+(String)recordRow[2].data+" "+(String)recordRow[3].data+" "+String.valueOf(recordRow[4].data)+" "+String.valueOf(recordRow[5].data));

					indexOffset+=2;
				}
			}while(fu.nextPageExist(columnFile, pagePointer));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return columnList;
		
	}
	
	public static Records[] readRecord(RandomAccessFile columnFile, long dataOffset) {
		Records[] columnData = null;
		try {
			
			if (dataOffset!=-1) {

				columnFile.seek(dataOffset);
				/*read number of column*/
				int tempNCols = columnFile.readByte();
				columnData = new Records[tempNCols];
				
				for (int j=0; j<tempNCols; j++) {
					int serialCode = columnFile.readByte();
					columnData[j]=new Records(serialCode);
//					System.out.println("serialCode: "+columnData[j].serialCode + " nByte: "+columnData[j].nByte);
				}
				for (int j=0; j<tempNCols; j++) {
					if (columnData[j].serialCode==0x00 || columnData[j].serialCode==0x04) {
						columnData[j].data = (int) columnFile.readByte();
//						System.out.println("data: "+columnData[j].data);
		        	}
		        	else if (columnData[j].serialCode==0x01 || columnData[j].serialCode==0x05) {
	        			columnData[j].data= (int) columnFile.readShort();
//						System.out.println("data: "+columnData[j].data);

		        	}
		        	else if (columnData[j].serialCode==0x02 || columnData[j].serialCode==0x06 || columnData[j].serialCode==0x08) {
		        		columnData[j].data = (int) columnFile.readInt();
//  						System.out.println("data: "+columnData[j].data);

		        	}
		        	else if (columnData[j].serialCode==0x03 || columnData[j].serialCode==0x07 || columnData[j].serialCode==0x09 ) {
		        		columnData[j].data = (float) columnFile.readLong();	
//						System.out.println("data: "+columnData[j].data);

		        	}
		        	else if (columnData[j].serialCode==0x0A) {
		        		Object date = columnFile.readLong();
		        		columnData[j].data = columnData[j].getDate((String) date);
//						System.out.println("data: "+columnData[j].data);
		        	}
		        	else if (columnData[j].serialCode==0x0B) {
		        		Object dateTime = columnFile.readLong();
		        		columnData[j].data = columnData[j].getDateTime((String)dateTime);
//						System.out.println("data: "+columnData[j].data);
		        	}
		        	else
		        	{
		        		int nReadLen = columnData[j].nByte;
		        		char[] data = new char[nReadLen];
//		        		System.out.println("currentPointer: "+currentPointer+" nReadLen: "+nReadLen+" currentPointer+nReadLen: "+(currentPointer+nReadLen));
//		        		columnFile.read
		        		for (int i=0; i<nReadLen; i++) {
		        			data[i]=(char)columnFile.readByte();
		        		}
		        		String str = String.valueOf(data);
//		        		System.out.println("read data:"+str);
		        		columnData[j].data=str;		        			
	        		}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return columnData;
	}
}