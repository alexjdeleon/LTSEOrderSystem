# LTSEOrderSystem
This application processes orders and determines which ones are valid and which ones are invalid. 

## Description
By default the application will generate two CSV files and two corresponding JSON files with the results; 

The JSON format includes a user friendsly "rejection message" for each rejected trade. The JSON files can be disabled via the application properties as shown below in the Setup and Configuration section.

This application takes in a comma separated input file containing the trade orders in the following format:

The first line contains the header -
**Time stamp,broker,sequence id,type,Symbol,Quantity,Price,Side**

The subsequent lines contain the data for each order - 
**10/5/2017 10:00:00,Fidelity,1,2,BARK,100,1.195,Buy**

The application will process the orders and determine if each individual order is valid or invalid according to the following rules:

1. Only orders that have values for the fields of ‘broker’, ‘symbol’, ‘type’, ‘quantity’, ‘sequence id’, ‘side’, and ‘price’ should be accepted.
2. Only orders for symbols actually traded on the exchange should be accepted
3. Only order from known brokers should be accepted
3. Each broker may only submit three orders per minute: any additional orders in should be rejected
4. Within a single broker’s trades ids must be unique. If ids repeat for the same broker, only the first message with a given id should be accepted.

The application will output two files, one containing broker and id of each accepted order and the other containing the broker and id of each rejected order.

**Please refer to Instructions.pdf for more details.**

**Please note: Rule number 3 was not explicitly mentioned in the Instructions.pdf but was assummed because the firms.txt file was included but not mentioned.**

## Setup and Configuration

**Please make sure the Java 8 JDK is installed on your system**

Please follow these steps to download and configure this application:
1. Clone this repository either via the command line or by downloading the ZIP file
2. If you wish to add, modify, remove any of the broker names and symbol names used in the processing rule, 
do so by modifiying the txt files in the **src/main/resources/data/** directory
3. The following configurations are also available in **src/main/resources/application.properties** file
		Property | Description
	------------ | -------------
	 data.output.directory=output | Output directory
	data.output.include.header=false | If true, the named header is included in the output file
	data.output.generate.json=true | If true, two additional files in JSON format are generated with the results. 

4. Once the application is correctly configured
In the command line, navigate the to cloned repository (or extracted zip directory) and run the appropriate build script

**Linux/OS X:**
* sh build.sh

**Windows:**
* build.bat

## Usage
Once the application has been correctly configured and built, run it by executing the following command from the project root directory:

*java -jar LTSEOrderSystem.jar [input_file]*

where, [input_file] is the csv you wish to process; if none is provided, it will look for file named "trades.csv" in the project root directory.

## Testing
The application includes the following JUnit test cases:
1. **PropertyManagerTest** - Verifies that the application's properties load properly
2. **TradeInputMapperTest** - Tests that the functionality for mapping the comma separated representation of an order to an object
3. **LTSEDataRepositoryTest** - Verifies that the application's reference data loads propely
4. **LTSEOrderProcessorTest** - 
Runs the following use cases and verifies the results:
* An input file for which all of the trade orders are accepted
* An input file for which one trade is rejected for each of the rejection scenarios in the requirements
* Verifies that the JSON file (if enabled) is being generated correctly




