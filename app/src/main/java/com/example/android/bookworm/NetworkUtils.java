package com.example.android.bookworm;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    //Base URL for the Books API
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";// Parameter for the search term
    private static final String MAX_RESULT = "maxResults";//Parameter that limits search results
    private static final String PRINT_TYPE = "printType";//Parameter to filter by print type

    static String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            //Building up the query uri
            Uri builtUri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULT, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestUrl = new URL(builtUri.toString());

            urlConnection =(HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
            if(builder.length() == 0) {
                //Stream was empty, no point in parsing
                Log.d(LOG_TAG, "Stream is empty");
                return null;
            }

            bookJSONString = builder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
               try{
                   reader.close();
               }catch (IOException e){
                   e.printStackTrace();
               }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }
}

//            String builtUri1 = builtUri.toString();
//            Log.d(LOG_TAG,builtUri1);
//            URL requestUrl = createUrlObject(builtUri1);
//
//            //Perform HTTP request to the URL and receive a JSON response back
//            try {
//                 bookJSONString = makeHttpRequest(requestUrl);
//            }
//            catch(IOException e) {
//                Log.e(LOG_TAG, "Problem making HttpRequest");
//            }
//        Log.d(LOG_TAG,bookJSONString);
//            return bookJSONString;


//    private static String makeHttpRequest(URL requestUrl) throws IOException {
//        String jsonResponse = "";
//
//        if(requestUrl==null){
//            return jsonResponse;
//        }
//        HttpURLConnection urlConnection = null;
//        InputStream inputStream = null;
//        try{
//            urlConnection = (HttpURLConnection) requestUrl.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            // If the request was successful (response code 200),
//            // then read the input stream and parse the response.
//            if(urlConnection.getResponseCode()==200) {
//                inputStream = urlConnection.getInputStream();
//                jsonResponse = readFromStream(inputStream);
//            }
//            else{
//                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
//            }
//        }
//        catch (IOException e){
//            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.",e);
//        }
//        finally{
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (inputStream != null) {
//                // Closing the input stream could throw an IOException, which is why
//                // the makeHttpRequest(URL url) method signature specifies than an IOException
//                // could be thrown.
//                inputStream.close();
//            }
//        }
//        return jsonResponse;
//    }
//
//    private static String readFromStream(InputStream inputStream) throws IOException {
////        StringBuilder output = new StringBuilder();
////        if(inputStream!=null){
////            InputStreamReader inputStreamReader = new  InputStreamReader(inputStream, Charset.forName("UTF-8"));
////            BufferedReader reader = new BufferedReader(inputStreamReader);
////            String line = reader.readLine();
////            while(line!=null){
////                output.append(line);
////                line = reader.readLine();
////            }
////        }
//        StringBuffer buffer = new StringBuffer();
//        if(inputStream==null){
//            return null;
//        }
//        BufferedReader reader = null;
//        reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        while((line = reader.readLine()) != null){
//            buffer.append(line + "\n");
//        }
//        if(buffer.length() == 0){
//            //Stream was empty, no point in parsing
//            Log.d(LOG_TAG, "Stream is empty");
//            return null;
//        }
//        return buffer.toString();
//    }
//
//    private static URL createUrlObject(String builtUri) {
//        URL url = null;
//        try{
//            url = new URL(builtUri);
//        }
//        catch(MalformedURLException e) {
//            Log.e(LOG_TAG, "Problem building the url");
//        }
//        return url;
//    }

