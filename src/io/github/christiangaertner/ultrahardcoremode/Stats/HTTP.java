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
    
    private String query;
    private String target;
    
    
    public void setTarget(String url) {
        this.target = url;
    }
    public void setParam(String key, String param) {
        if (this.query == null) {
            this.query = "?" + key + "=" + param;
            return;
        }
        param = "&" + key + "=" + param;
        this.query = this.query.concat(param);
        
    }
    
    public void resetParams() {
        this.query = null;
    }

    public String get() throws IOException {

        // get URL content
        URL url = new URL(this.target.concat(this.query));
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