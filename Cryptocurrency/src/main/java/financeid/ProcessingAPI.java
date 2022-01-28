package financeid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class ProcessingAPI {
    private static JSONObject getJSONObject(URL url) throws IOException, ParseException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuffer informationString = new StringBuffer();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) informationString.append(scanner.nextLine());
            scanner.close();
            JSONParser parser = new JSONParser();
            JSONArray dataObject = (JSONArray) parser.parse(String.valueOf(informationString));
            return (JSONObject) dataObject.get(0);
        }
    }
    protected static String getName(URL url) throws IOException, ParseException {
        return String.valueOf(getJSONObject(url).get("name"));
    }
    protected static String getSymbol(URL url) throws IOException, ParseException {
        return String.valueOf(getJSONObject(url).get("symbol"));
    }
    protected static Double getPrice(URL url) throws IOException, ParseException {
        return parseDouble(String.valueOf(getJSONObject(url).get("price_usd")));
    }
}
