package com.factory.rimon.tcgtournamentapp.UI.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;
import com.factory.rimon.tcgtournamentapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    private static BETournament tournament;

    public static DetailsFragment newInstance(Intent intent) {
            DetailsFragment f = new DetailsFragment();

            // Supply index input as an argument.

            tournament =(BETournament) intent.getSerializableExtra("tournament");

            return f;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtDetailsTitle);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDetailsDate);
        TextView txtLocation = (TextView) view.findViewById(R.id.txtLocation);
        TextView txtRel = (TextView) view.findViewById(R.id.txtRel);
        TextView txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        TextView txtEntryTime = (TextView) view.findViewById(R.id.txtEntryTime);
        TextView txtStarTtime = (TextView) view.findViewById(R.id.txtStarTtime);
        TextView txtFormat = (TextView) view.findViewById(R.id.txtFormat);
        TextView txtEdition = (TextView) view.findViewById(R.id.txtEdition);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);

        txtTitle.setText(tournament.getTitle());
        txtDate.setText(tournament.getDate());
        txtLocation.setText("Location: " + tournament.getLocation());
        txtRel.setText("Rel: " + tournament.getRel());
        txtInfo.setText("Info: " + tournament.getInfo());
        txtEntryTime.setText("Entry Time: " + tournament.getEntryTime());
        txtStarTtime.setText("Start Time: " + tournament.getStartTime());
        txtFormat.setText("Format: " + tournament.getFormat());
        txtEdition.setText("Edition: " + tournament.getEdition());
        txtPrice.setText("Price: " + tournament.getPrice() + "$");

        //return inflater.inflate(R.layout.fragment_details, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*TextView txtLoc = (TextView) findViewById(R.id.txtDetailsLocation);
        TextView txtStart = (TextView) findViewById(R.id.txtDetailsStart);
        TextView txtEnd = (TextView) findViewById(R.id.txtDetailsEnd);
        ListView lvPlayers = (ListView) findViewById(R.id.lvDetailsPlayers);*/


        //txtLoc.setText(t.getLocation());
        //txtStart.setText(t.getStartTime());
        //txtEnd.setText(t.getEndTime());

    }
}
