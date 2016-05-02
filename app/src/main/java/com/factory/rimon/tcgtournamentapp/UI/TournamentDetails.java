package com.factory.rimon.tcgtournamentapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;
import com.factory.rimon.tcgtournamentapp.R;

public class TournamentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournamentdetails);

        Intent intent = getIntent();
        BETournament t = (BETournament) intent.getSerializableExtra("tournament");

        TextView txtTitle = (TextView) findViewById(R.id.txtDetailsTitle);
        TextView txtDate = (TextView) findViewById(R.id.txtDetailsDate);
        /*TextView txtLoc = (TextView) findViewById(R.id.txtDetailsLocation);
        TextView txtStart = (TextView) findViewById(R.id.txtDetailsStart);
        TextView txtEnd = (TextView) findViewById(R.id.txtDetailsEnd);
        ListView lvPlayers = (ListView) findViewById(R.id.lvDetailsPlayers);*/

        txtTitle.setText(t.getTitle());
        txtDate.setText(t.getDate());
        //txtLoc.setText(t.getLocation());
        //txtStart.setText(t.getStartTime());
        //txtEnd.setText(t.getEndTime());
    }
}
