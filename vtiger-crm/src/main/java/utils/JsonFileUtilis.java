package utils;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonFileUtilis {

    /**
     * Fetch value from JSON file using key
     */
    public String fetchDataFromJsonFile(String key) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("./src/test/resources/VtigerData.json"));

        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj.get(key).toString();
    }
}