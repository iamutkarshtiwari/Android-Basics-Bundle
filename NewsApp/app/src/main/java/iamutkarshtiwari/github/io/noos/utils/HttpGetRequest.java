package iamutkarshtiwari.github.io.noos.utils;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpGetRequest {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECTION_TIMEOUT = 10000;


    public static String getResponseString(String params) {
        String stringUrl = params;
        String result;

        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Connect to our url
            connection.connect();
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
            Log.e("ERROR :", "Error connecting to network");
            return "";
        }
        return result;
    }

}
