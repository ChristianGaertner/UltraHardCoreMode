/*
 *
 *
 */
package io.github.christiangaertner.ultrahardcoremode.Stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class HTTP {

    public String get(String req, String guid, String version) throws IOException {

        // get URL content
        URL url = new URL(req + "?guid=" + guid + "&ver=" + version);
        URLConnection conn = url.openConnection();

        // open the stream and put it into BufferedReader
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String inputLine;
        String result = "";


        while ((inputLine = br.readLine()) != null) {
            result += inputLine;
        }

        br.close();

        return result;
    }
   

}