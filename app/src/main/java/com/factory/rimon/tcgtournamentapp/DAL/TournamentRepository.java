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

    public ArrayList<BETournament> loadPage(int limit, int page){
        try{
            String result = getContent(URL + "?limit=" + limit + "&page=" + page);
            if (result == null)
            {
                Log.d(TAG, "Nothing returned...");
                return null;
            }

            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("docs");

            ArrayList<BETournament> myList = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                JSONObject o = array.getJSONObject(i);

                BETournament t = new BETournament(o.getString("title"), o.getString("date"), o.getString("format"), o.getString("edition"));
                myList.add(t);
            }
            Log.d("Repository:", "amount in array" + tournaments.size());
            return myList;
        }
        catch(JSONException e) {
            Log.e(TAG, "There was an error parsing the JSON", e);
        }catch(Exception e){
            Log.d(TAG, "General exception in loadPage " + e.getMessage());
        }
        return null;
    }

    public ArrayList<BETournament> getAll(){return tournaments;}

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
