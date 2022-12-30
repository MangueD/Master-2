package analizer.parser;

import org.json.simple.JSONObject;

/**
 * Represent a log with his message, level and loggerName for now
 */
public class Log {
	private JSONObject message;
	private String level;
	private String className;

	public Log(JSONObject message, String level, String className){
		this.className = className;
		this.message = message;
		this.level = level;
	}

	public JSONObject getMessage() {
		return message;
	}

	public String getLevel() {
		return level;
	}

	public String getClassName() {
		return className;
	}
}
