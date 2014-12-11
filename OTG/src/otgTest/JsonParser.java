package otgTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class JsonParser {
	
	private boolean DEBUG;  //used for dev testing
	
	/**
	 * contstructor
	 * @param debug
	 */
	public JsonParser(boolean debug) {
		DEBUG = debug;
	}
	
	/**
	 * parse a json object
	 * @param jsonObject the incoming JSONObject
	 * @return a map of key,value pairs
	 */
    private Map<String, String> parseObject(JSONObject jsonObject) {
    	if (DEBUG) {System.out.println("\n");}
        Map<String, String> flatJson = new LinkedHashMap<String, String>();
        parseObject(jsonObject, flatJson, "");
        return flatJson;
    }

    /**
     * parse a json array
     * @param jsonArray the incoming JSONArray
     * @return a map of key,value pairs
     */
    private List<Map<String, String>> parseArray(JSONArray jsonArray) {
        List<Map<String, String>> flatJson = new ArrayList<Map<String, String>>();
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, String> stringMap = parseObject(jsonObject);
            flatJson.add(stringMap);
        }
        return flatJson;
    }

    /**
     * parse a valid json input into a map of key,value pairs
     * @param json incoming json string
     * @return map of key,value pairs
     * @throws Exception
     */
    public List<Map<String, String>> parseJson(String json) throws Exception {
        List<Map<String, String>> flatJson = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            flatJson = new ArrayList<Map<String, String>>();
            flatJson.add(parseObject(jsonObject));
        } catch (JSONException je) {
            flatJson = handleAsArray(json);
        }
        return flatJson;
    }

    /**
     * process a JSONArray
     * @param json the incoming json string
     * @return map of key,value pairs
     * @throws Exception
     */
    private List<Map<String, String>> handleAsArray(String json) throws Exception {
        List<Map<String, String>> flatJson = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            flatJson = parseArray(jsonArray);
        } catch (Exception e) {
            throw new Exception("Json might be malformed e=" + e);
        }
        return flatJson;
    }

    /**
     * parse a JSONArray
     * @param obj the incoming JSONArray
     * @param flatJson the map of key,value pairs
     * @param prefix used for inner array/objects
     */
    private void parseArray(JSONArray obj, Map<String, String> flatJson, String prefix) {    	
        int length = obj.length();
        if (DEBUG) {System.out.println("length=" + length + " prefix=" + prefix);}
        
        for (int i = 0; i < length; i++) {
        	
            if (obj.get(i).getClass() == JSONArray.class) {
            	if (DEBUG) {System.out.println("array:key is a JSONArray");}
                JSONArray jsonArray = (JSONArray) obj.get(i);
                if (jsonArray.length() < 1) continue;
                parseArray(jsonArray, flatJson, prefix + i);
            } 
            else if (obj.get(i).getClass() == JSONObject.class) {
            	if (DEBUG) {System.out.println("array:key is a JSONObject");}
                JSONObject jsonObject = (JSONObject) obj.get(i);
                parseObject(jsonObject, flatJson, prefix + (i + 1));
            } 
            else if (obj.get(i).getClass() == Integer.class) {
            	int x = obj.getInt(i);
            	if (DEBUG) {System.out.println("array:value=" + x);}
            	flatJson.put(prefix + (i + 1), String.valueOf(x));
            } 
            else if (obj.get(i).getClass() == Double.class) {	
            	double d = obj.getDouble(i);
            	if (DEBUG) {System.out.println("array:value=" + d);}
            	flatJson.put(prefix + (i + 1), String.valueOf(d));
            } 
            else {
                String value = obj.getString(i);
                if (DEBUG) {System.out.println("array:value=" + value);}
                if (value != null)
                    flatJson.put(prefix + (i + 1), value);
            }
            
        }//for
    }
    
    /**
     * parse a JSONObject
     * @param obj the incoming JSONObject
     * @param flatJson a map of key,value pair
     * @param prefix used for inner array/objects
     */
    private void parseObject(JSONObject obj, Map<String, String> flatJson, String prefix) {
        @SuppressWarnings("rawtypes")
		Iterator iterator = obj.keys();
        if (DEBUG) {System.out.println("length=" + obj.length() + " prefix=" + prefix);}
        
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            if (DEBUG) {System.out.println("key=" + key);}
            
            if (obj.get(key).getClass() == JSONObject.class) {
            	if (DEBUG) {System.out.println("Object:key is a JSONObject");}
                JSONObject jsonObject = (JSONObject) obj.get(key);
                parseObject(jsonObject, flatJson, prefix);
            } 
            else if (obj.get(key).getClass() == JSONArray.class) {
            	if (DEBUG) {System.out.println("Object:key is a JSONArray");}
                JSONArray jsonArray = (JSONArray) obj.get(key);
                if (jsonArray.length() < 1) continue;
                parseArray(jsonArray, flatJson, key);
            } 
            else if (obj.get(key).getClass() == Integer.class) {
            	int i = obj.getInt(key);
            	if (DEBUG) {System.out.println("Object:value=" + i);}
            	flatJson.put(prefix + key, String.valueOf(i));
            } 
            else if (obj.get(key).getClass() == Double.class) {
            	double d = obj.getDouble(key);
            	if (DEBUG) {System.out.println("Object:value=" + d);}
            	flatJson.put(prefix + key, String.valueOf(d));
            } 
            else {
                String value = obj.getString(key);
                if (DEBUG) {System.out.println("Object:value=" + value);}
                if (value != null && !value.equals("null"))
                    flatJson.put(prefix + key, value);
            }
            
        }//while
    }
    
}
