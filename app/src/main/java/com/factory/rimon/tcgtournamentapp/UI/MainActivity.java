package com.factory.rimon.tcgtournamentapp.UI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;
import com.factory.rimon.tcgtournamentapp.DAL.TournamentRepository;
import com.factory.rimon.tcgtournamentapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvTournaments;

    TournamentRepository tRepo;
    TournamentAdapter tAdapter;

    int nextPage = 1;
    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTournaments = (ListView) findViewById(R.id.lvTournaments);

        tAdapter = new TournamentAdapter(this);
        tRepo = new TournamentRepository();
        lvTournaments.setAdapter(tAdapter);

        lvTournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BETournament tournament = tAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), TournamentDetails.class);

                intent.putExtra("tournament", tournament);

                startActivity(intent);
            }
        });

        lvTournaments.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean loadMore =
                        firstVisibleItem + visibleItemCount >= totalItemCount;

                //If we are at bottom at the page and not loading, load more data
                if(loadMore && !loading) {
                    Log.d("TOURNAMENTS", "onScroll page to load = " + nextPage);
                    new LoadDataTask().execute(nextPage);
                    loading = true;
                    nextPage++;
                }
            }
        });
    }


    private class TournamentAdapter extends ArrayAdapter<BETournament> {

        public TournamentAdapter(Context context) {
            super(context, R.layout.activity_row);
        }

        public View getView(int pos, View v, ViewGroup p) {
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.activity_row, null);
            }
            BETournament t = getItem(pos);

            //TextView txtId = (TextView) v.findViewById(R.id.txtId);
            TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            TextView txtDate = (TextView) v.findViewById(R.id.txtDate);
            TextView txtLocation = (TextView) v.findViewById(R.id.txtLocation);
            //TextView txtFormat = (TextView) v.findViewById(R.id.txtFormat);

            //txtId.setText("" + t.getId());
            txtTitle.setText("" + t.getTitle());
            txtDate.setText("" + t.getDate());
            txtLocation.setText("" + t.getLocation());
            //txtEdition.setText("" + t.getEdition());
            //txtFormat.setText("" + t.getFormat());

            return v;
        }
    }

    class LoadDataTask extends AsyncTask<Integer, Void, List<BETournament>> {
        @Override
        protected List<BETournament> doInBackground(Integer... page) {
            return tRepo.loadPage(page[0]);
        }

        @Override
        protected void onPostExecute(List<BETournament> beTournaments) {
            tAdapter.addAll(beTournaments);
            Log.d("MainActivity", "amount in array:"+ beTournaments.size());
            loading = false;
        }
    }
}
