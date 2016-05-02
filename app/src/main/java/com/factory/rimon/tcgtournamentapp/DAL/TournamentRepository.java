package com.factory.rimon.tcgtournamentapp.DAL;

import android.util.Log;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rimon on 4/29/2016.
 */
public class TournamentRepository {

    private final String URL = "http://letest-rasnikat.rhcloud.com/api/tournaments";

    private final String TAG = "TOURNAMENT";

    ArrayList<BETournament> tournaments;

    public TournamentRepository(){
        tournaments = new ArrayList<BETournament>();
    }

    /*public void loadPage(int limit, int page){
        try{
            String result = getContent(URL + "?limit=" + limit + "&page=" + page);
            if(result == null) return;

            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("docs");

            for(int i = 0; i < array.length(); i++){
                //JSONObject o = array.getJSONObject(i);

                //Get info here

                BETournament t = new BETournament("name", "date", "format", "edition");
                tournaments.add(t);
            }
        }
        catch(JSONException e) {
            Log.e(TAG, "There was an error parsing the JSON", e);
        }catch(Exception e){
            Log.d(TAG, "General exception in loadPage " + e.getMessage());
        }
    }*/

    public ArrayList<BETournament> getAll(){return tournaments;}

    public ArrayList<BETournament> getTournaments(/*int idx*/)
    {
        try {
            //String url = URL + "?" + "limit=" + m_pageSize + "&page=" + idx;
            Log.d(TAG, "Calling " + URL);
            String result = getContent(URL);

            if (result == null)
            {
                Log.d(TAG, "Error - No results found.");
                return null;
            }


            JSONObject jsonMainObject = new JSONObject(result);
            JSONArray array = jsonMainObject.getJSONArray("docs");

            ArrayList<BETournament> res = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                String name = d.getString("name");
                String date = d.getString("date");
                String format = d.getString("");
                String edition = d.getString("edition");

                BETournament s = new BETournament(name, date, format, edition);
                res.add(s);
            }

            return res;

        } catch (JSONException e) {
            Log.e(TAG,
                    "There was an error parsing the JSON", e);
        } catch (Exception e)
        {  Log.d(TAG, "General exception in loadAll " + e.getMessage());
        }
        return null;
    }

    /**
     * Get the content of the url as a string. Based on using a scanner.
     * @param urlString - the url must return data typical in either json, xml, csv etc..
     * @return the content as a string. Null if something goes wrong.
     */
    private String getContent(String urlString)
    {
        StringBuilder sb = new StringBuilder();
        try {
            java.net.URL url = new URL(urlString);
            Scanner s = new Scanner(url.openStream());

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }


}
