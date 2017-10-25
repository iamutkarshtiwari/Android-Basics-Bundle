package iamutkarshtiwari.github.io.noos.utils;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iamutkarshtiwari.github.io.noos.models.News;

public class JSONParser {

    private String dataSource;

    public JSONParser(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Parses the JSON string and generates list of products
     *
     * @return ArrayList of Products
     */
    public ArrayList<News> getResponseData() {
        ArrayList<News> newsList = new ArrayList<News>();
        String responseJSON = HttpGetRequest.getResponseString(this.dataSource);
        if (responseJSON == null) {
            return null;
        }
        try {
            JSONObject reader = new JSONObject(responseJSON);
            JSONObject response = reader.getJSONObject("response");
            JSONArray resultArray = response.getJSONArray("results");
            for(int i=0; i< resultArray.length(); i++) {
                JSONObject news = resultArray.getJSONObject(i);
                String title = news.getString("webTitle");
                String type = news.getString("type");
                String date = news.getString("webPublicationDate");
                String category = news.getString("sectionName");
                String url =  news.getString("webUrl");
                News n = new News(category, title, type, date, url);
                newsList.add(n);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<News>();
        }
        return newsList;
    }
}
