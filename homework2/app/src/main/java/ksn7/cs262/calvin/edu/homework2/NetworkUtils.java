package ksn7.cs262.calvin.edu.homework2;

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
    private static final String PLAYER_BASE_URL = "https://calvincs262-monopoly.appspot.com/monopoly/v1/"; // Base URI for player data

    /**
     * Currently malfunctioning for an unknown reason. Supposed to getPlayerInfo based on input
     * Issue possibly with the base URI or URL construction?
     *
     * @param queryString
     * @return playerInfo as found, or null if no results
     */
    static String getPlayerInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String playerJSONString = null;
        String playerURL = null;
        try {
            // Append base URL based on the query
            if (queryString.equals("")) {
                playerURL = PLAYER_BASE_URL + "players";
            } else {
                playerURL = PLAYER_BASE_URL + "player/ID";
            }

            //Build up your query URI, limiting results to 10 items and printed books
            Uri builtURI = Uri.parse(playerURL).buildUpon()
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            playerJSONString = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, playerJSONString);
        return playerJSONString;
    }
}
