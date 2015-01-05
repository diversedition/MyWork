package otgTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class OtgTest {		
	
	private static final boolean DEBUG=false;

	
	/**
	 * main program
	 * @param args
	 */
	public static void main(String[] args) {		
		String FILE="otg-datasample";
		
		//Can override the default json file with a file from the data subdirectory, or an internal testcase
		if (args.length > 0 && args[0] != null && args[0].length() > 0)
		{
			FILE = args[0];
		}
		
		System.out.println("\nConverting a JSon File: [" + FILE + "] to CSV format\n");
		
		try {
			String json = null;
			
			if (FILE.equals("jsonValue")) {
				json = jsonValue();   //internal testcase			
			} else {
				File inFile = new File("data/" + FILE + ".json");
				BufferedReader br = new BufferedReader(new FileReader(inFile));
				json = IOUtils.toString(br);
				br.close();
			}
			
			if (DEBUG) {System.out.println("json file=" + json);}
						
			File outFile = new File("data/" + FILE + ".csv");
		    parseJson(json, outFile);
		}
		catch (Exception e) {
			System.out.println("Exception in main e=" + e);
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * parse a json string into the outFile
	 * @param json a valid json string
	 * @param outFile
	 * @throws Exception
	 */
	public static void parseJson(String json, File outFile) throws Exception {
        JsonParser parser = new JsonParser(DEBUG);
        CsvWriter writer = new CsvWriter();

        List<Map<String, String>> flatJson = parser.parseJson(json);
        writer.writeAsCSV(flatJson, outFile);
        
        System.out.println("Success...");
    }
	
	
	/**
	 * an internal testcase
	 * @return a valid json string
	 */
	private static String jsonValue() {
        return "[\n" +
                "    {\n" +
                "        \"studentName\": \"Foo\",\n" +
                "        \"Age\": \"12\",\n" +
                "        \"subjects\": [\n" +
                "            {\n" +
                "                \"name\": \"English\",\n" +
                "                \"marks\": \"40\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"History\",\n" +
                "                \"marks\": \"50\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"studentName\": \"Bar\",\n" +
                "        \"Age\": \"12\",\n" +
                "        \"subjects\": [\n" +
                "            {\n" +
                "                \"name\": \"English\",\n" +
                "                \"marks\": \"40\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"History\",\n" +
                "                \"marks\": \"50\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Science\",\n" +
                "                \"marks\": \"40\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"studentName\": \"Baz\",\n" +
                "        \"Age\": \"12\",\n" +
                "        \"subjects\": []\n" +
                "    }\n" +
                "]";
    }

}


