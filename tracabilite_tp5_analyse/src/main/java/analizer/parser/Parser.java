package analizer.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The parser parse a logging JSON file, and extract the values
 */
public class Parser {

    /*
     * read a json logging file and
     * return a list of Log which contain the message, loggername and level of every
     * loggins inside the file
     */
    public List<Log> parse(String logPath) throws IOException, ParseException {
        ArrayList<Log> res = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(logPath);

        //getting a log
        Object o = jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray)o;

        for(Object object : jsonArray){//create our object Log
            JSONObject logObject = (JSONObject)object;

            String logMessage = (String) logObject.get("message");
            String className = (String) logObject.get("loggerName");
            String level = (String) logObject.get("level");

            res.add(new Log((JSONObject)jsonParser.parse(logMessage), level, className));
        }
        return res;
    }
}
