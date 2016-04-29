package com.factory.rimon.tcgtournamentapp.DAL;

import android.util.Log;

import com.factory.rimon.tcgtournamentapp.BE.Player;
import com.factory.rimon.tcgtournamentapp.BE.Tournament;

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

    private final String URL = "";

    private final String TAG = "Tournament";

    //private int m_pageSize = 10;

    ArrayList<Tournament> tournaments;

    public TournamentRepository(){
        tournaments = new ArrayList<>();
    }

    public ArrayList<Tournament> getTournaments(/*int idx*/)
    {
        try {
            //String url = URL + "?" + "limit=" + m_pageSize + "&page=" + idx;
            Log.d(TAG, "Calling " + URL);
            String result = getContent(URL);

            if (result == null)
            {
                Log.d(TAG, "Nothing returned...");
                return null;
            }


            JSONObject jsonMainObject = new JSONObject(result);
            JSONArray array = jsonMainObject.getJSONArray("docs");

            ArrayList<Tournament> res = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject d = array.getJSONObject(i);
                String name = d.getString("name");
                String date = d.getString("date");
                String format = d.getString("");
                String edition = d.getString("edition");

                Tournament s = new Tournament(name, date, format, edition);
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
