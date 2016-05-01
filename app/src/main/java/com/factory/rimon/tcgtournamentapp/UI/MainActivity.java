package com.factory.rimon.tcgtournamentapp.UI;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.factory.rimon.tcgtournamentapp.BE.Tournament;
import com.factory.rimon.tcgtournamentapp.DAL.TournamentRepository;
import com.factory.rimon.tcgtournamentapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtDate;
    TextView txtFormat;
    TextView txtEdition;
    ArrayList<Tournament> tournaments;
    ArrayList<Tournament> testList;
    TournamentRepository tr;
    TournamentAdapter ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);

        testList = new ArrayList<>();

        Tournament t1 = new Tournament("Name", "dATE", "FORMAT", "EDITION");
        Tournament t2 = new Tournament("Name1", "dATE", "FORMAT", "EDITION");
        testList.add(t1);
        testList.add(t2);
        Log.d("TEST", "" + testList.size());

        tr = new TournamentRepository();

        tournaments = tr.getTournaments();

        ta = new TournamentAdapter(this);
        ta.addAll(testList);

        listView.setAdapter(ta);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tournament tournament = ta.getItem(position);

                Intent intent = new Intent(getApplicationContext(), TournamentDetails.class);

                intent.putExtra("tournament", tournament);

                startActivity(intent);
            }
        });
    }


    private class TournamentAdapter extends ArrayAdapter<Tournament> {

        public TournamentAdapter(Context context) {
            super(context, R.layout.activity_row);
        }

        public View getView(int pos, View v, ViewGroup p) {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.activity_row, null);
                txtName = (TextView) v.findViewById(R.id.txtName);
                txtDate = (TextView) v.findViewById(R.id.txtDate);
                txtEdition = (TextView) v.findViewById(R.id.txtEdition);
                txtFormat = (TextView) v.findViewById(R.id.txtFormat);
            }

            Tournament t = getItem(pos);


                txtName.setText("" + t.getName());
                txtDate.setText("" + t.getDate());
                txtFormat.setText("" + t.getFormat());
                txtName.setText("" + t.getEdition());

            return v;

        }
    }






}
