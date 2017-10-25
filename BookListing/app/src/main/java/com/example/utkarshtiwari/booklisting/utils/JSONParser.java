package com.example.utkarshtiwari.booklisting.utils;

/**
 * Created by utkarshtiwari on 10/10/17.
 */

import com.example.utkarshtiwari.booklisting.models.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public ArrayList<Book> getResponseData() {
        ArrayList<Book> itemList = new ArrayList<Book>();
        String responseJSON = HttpGetRequest.getResponseString(this.dataSource);
        if (responseJSON == null) {
            return null;
        }

        // Return empty list if null response
        if (responseJSON == null) {
            return itemList;
        }

        try {
            JSONObject jsonObject = new JSONObject(responseJSON);

            if (!jsonObject.has("items")) {
                return new ArrayList<Book>();
            }

            JSONArray jsonArray = jsonObject.getJSONArray("items");

            // Generate product arraylist from json data
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);

                String name = bookObject.getJSONObject("volumeInfo").getString("title");
                String photoURL = "";
                double price = 0.0;
                ArrayList<String> authors = new ArrayList<String>();
                String currency = "";

                if (bookObject.getJSONObject("volumeInfo").has("authors")) {
                    JSONArray jArray = bookObject.getJSONObject("volumeInfo").getJSONArray("authors");
                    for (int j = 0; j < jArray.length(); j++) {
                        authors.add(jArray.getString(j));
                    }

                }
                String language = bookObject.getJSONObject("volumeInfo").getString("language");
                String infoLink = bookObject.getJSONObject("volumeInfo").getString("infoLink");
                if (bookObject.has("saleInfo") && bookObject.getJSONObject("saleInfo").has("listPrice")) {
                    price = bookObject.getJSONObject("saleInfo").getJSONObject("listPrice").getDouble("amount");
                    currency = bookObject.getJSONObject("saleInfo").getJSONObject("listPrice").getString("currencyCode");
                }
                if (bookObject.getJSONObject("volumeInfo").has("imageLinks")) {
                    photoURL = bookObject.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail");
                }
                Book book = new Book(name, authors, language, price, currency, photoURL, infoLink);
                itemList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Book>();
        }
        return itemList;
    }
}
