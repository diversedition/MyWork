package otgTest;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class CsvWriter {

	/**
	 * create the csv file from the json map object
	 * @param flatJson
	 * @param fileName
	 * @throws FileNotFoundException
	 */
    public void writeAsCSV(List<Map<String, String>> flatJson, File fileName) throws FileNotFoundException {
        Set<String> headers = collectHeaders(flatJson);
        String output = StringUtils.join(headers.toArray(), ",") + "\n";
        for (Map<String, String> map : flatJson) {
            output = output + getCommaSeperatedRow(headers, map) + "\n";
        }
        writeToFile(output, fileName);
    }

    /**
     * write the csv string to a file
     * @param output
     * @param fileName
     * @throws FileNotFoundException
     */
    private void writeToFile(String output, File fileName) throws FileNotFoundException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(writer);
        }
    }

    /**
     * close the writer
     * @param writer
     */
    private void close(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create a comma separated row
     * @param headers
     * @param map
     * @return
     */
    private String getCommaSeperatedRow(Set<String> headers, Map<String, String> map) {
        List<String> items = new ArrayList<String>();
        for (String header : headers) {
            String value = map.get(header) == null ? "" : map.get(header).replace(",", "");
            items.add(value);
        }
        return StringUtils.join(items.toArray(), ",");
    }

    /**
     * collect the headers from the json key,value pairs
     * @param flatJson
     * @return
     */
    private Set<String> collectHeaders(List<Map<String, String>> flatJson) {
        Set<String> headers = new TreeSet<String>();
        for (Map<String, String> map : flatJson) {
            headers.addAll(map.keySet());
        }
        return headers;
    }
}

