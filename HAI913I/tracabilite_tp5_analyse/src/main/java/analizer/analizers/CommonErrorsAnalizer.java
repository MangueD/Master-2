package analizer.analizers;

import analizer.parser.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * the class which extract informations according to question 2.3
 */
public class CommonErrorsAnalizer {
	private List<Log> logs;
	private Map<String, Integer> errorsOccurencesThrowned;
	private Map<String, Integer> errorsOccurencesCaught;//associate the exception and how many times it has appeared

	public CommonErrorsAnalizer(List<Log> logs) throws IOException {
		this.logs = logs;
		errorsOccurencesThrowned = new Hashtable<>();
		errorsOccurencesCaught = new Hashtable<>();

		/*
		 * creating the initialize the errorsOccurencesThrowned attribute
		 */
		for(Log l : logs){
			if(!l.getLevel().equals("DEBUG")){
				continue;
			}
			JSONObject j = l.getMessage();
			String where = (String) j.get("where");

			if(!where.equals("throw")){
				continue;
			}

			String errorName = (String) j.get("exception");
			if(errorsOccurencesThrowned.containsKey(errorName)){
				errorsOccurencesThrowned.put(errorName, errorsOccurencesThrowned.get(errorName) + 1);
			}
			else{
				errorsOccurencesThrowned.put(errorName, 1);
			}
		}

		/*
		 * creating the initialize the errorsOccurencesCaught attribute
		 */
		for(Log l : logs){
			if(!l.getLevel().equals("DEBUG")){
				continue;
			}
			JSONObject j = l.getMessage();
			String where = (String) j.get("where");

			if(!where.equals("catch")){
				continue;
			}

			String errorName = (String) j.get("exception");
			if(errorsOccurencesCaught.containsKey(errorName)){
				errorsOccurencesCaught.put(errorName, errorsOccurencesCaught.get(errorName) + 1);
			}
			else{
				errorsOccurencesCaught.put(errorName, 1);
			}
		}
	}

	public Map<String, Integer> getErrorsOccurencesThrown(){
		return errorsOccurencesThrowned;
	}

	public Map<String, Integer> getErrorsOccurencesCaught(){
		return errorsOccurencesCaught;
	}

	/**
	 * @return A list of string containing information every time the exception err appeared,
	 */
	public List<String> errorDatasStringThrown(String err){
		List<String> res = new ArrayList<>();

		for(int i = 0; i < logs.size(); i++){
			//filter INFO log
			if(!logs.get(i).getLevel().equals("DEBUG")){
				continue;
			}

			//filter catch
			JSONObject j = logs.get(i).getMessage();
			String where = (String) j.get("where");
			if(!where.equals("throw")){
				continue;
			}

			//filter exception name
			String errorName = (String) j.get("exception");
			if(!errorName.equals(err)){
				continue;
			}

			//creating the string information about this log
			String string = "user:" + (String)j.get("user");
			if(logs.get(i).getClassName().contains("ProductRepository")){//We can extract parameters informations
				JSONObject prevJ = logs.get(i - 1).getMessage();
				string += " parameter(s)" + ((JSONArray)prevJ.get("parameters")).toString();
			}

			res.add(string);
		}
		return res;
	}

	/**
	 * @return A list of string containing information every time the exception err appeared,
	 */
	public List<String> errorDatasStringCaught(String err){
		List<String> res = new ArrayList<>();

		for(int i = 0; i < logs.size(); i++){
			//filter INFO log
			if(!logs.get(i).getLevel().equals("DEBUG")){
				continue;
			}

			//filter throw
			JSONObject j = logs.get(i).getMessage();
			String where = (String) j.get("where");
			if(!where.equals("catch")){
				continue;
			}

			//filter exception name
			String errorName = (String) j.get("exception");
			if(!errorName.equals(err)){
				continue;
			}

			//creating the string information about this log
			String string = "user:" + (String)j.get("user");

			res.add(string);
		}
		return res;
	}

	/**
	 * @return an ordered list of exception name, from the most occurring to the less
	 */
	public List<String> orderedExceptionThrown(){
		List<String> rankErrors = new ArrayList<>();

		//Sort by insertion
		for(String s : errorsOccurencesThrowned.keySet()){
			if(rankErrors.size() == 0){
				rankErrors.add(s);
				continue;
			}
			for(int i = 0; i < rankErrors.size(); i++){
				int value = errorsOccurencesThrowned.get(rankErrors.get(i));
				if(value < errorsOccurencesThrowned.get(s)){
					rankErrors.add(i, s);
					break;
				}
				else if(i == rankErrors.size() - 1){
					rankErrors.add(s);
					break;
				}
			}
		}
		return rankErrors;
	}

	/**
	 * @return an ordered list of exception name, from the most occurring to the less
	 */
	public List<String> orderedExceptionCaught(){
		List<String> rankErrors = new ArrayList<>();

		//Sort by insertion
		for(String s : errorsOccurencesCaught.keySet()){
			if(rankErrors.size() == 0){
				rankErrors.add(s);
				continue;
			}
			for(int i = 0; i < rankErrors.size(); i++){
				int value = errorsOccurencesCaught.get(rankErrors.get(i));
				if(value < errorsOccurencesCaught.get(s)){
					rankErrors.add(i, s);
					break;
				}
				else if(i == rankErrors.size() - 1){
					rankErrors.add(s);
					break;
				}
			}
		}
		return rankErrors;
	}




}
