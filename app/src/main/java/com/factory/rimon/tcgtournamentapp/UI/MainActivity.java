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

    TextView txtName;
    TextView txtDate;
    TextView txtFormat;
    TextView txtEdition;

    ListView lvTournaments;

    ArrayList<BETournament> tournaments;
    ArrayList<BETournament> testList;

    TournamentRepository tr;
    TournamentAdapter ta;

    int nextPage = 1;
    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        lvTournaments = (ListView) findViewById(R.id.listView);

        /*testList = new ArrayList<>();
        BETournament t1 = new BETournament("Name", "dATE", "FORMAT", "EDITION");
        BETournament t2 = new BETournament("Name1", "dATE", "FORMAT", "EDITION");
        testList.add(t1);
        testList.add(t2);
        Log.d("TEST", "" + testList.size());*/

        tr = new TournamentRepository();

        tournaments = tr.getAll();

        ta = new TournamentAdapter(this);
        //ta.addAll(testList);

        lvTournaments.setAdapter(ta);

        lvTournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BETournament tournament = ta.getItem(position);

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
                txtName = (TextView) v.findViewById(R.id.txtName);
                txtDate = (TextView) v.findViewById(R.id.txtDate);
                txtEdition = (TextView) v.findViewById(R.id.txtEdition);
                txtFormat = (TextView) v.findViewById(R.id.txtFormat);
            }

            BETournament t = getItem(pos);
                txtName.setText("" + t.getTitle());
                txtDate.setText("" + t.getDate());
                txtFormat.setText("" + t.getFormat());
                txtName.setText("" + t.getEdition());
            return v;
        }
    }

    class LoadDataTask extends AsyncTask<Integer, Void, List<BETournament>> {
        @Override
        protected List<BETournament> doInBackground(Integer... page) {
            return tr.loadPage(5, page[0]);
        }

        @Override
        protected void onPostExecute(List<BETournament> beTournaments) {
            ta.addAll(beTournaments);
            Log.d("MainActivity", "amount in array:"+ beTournaments.size());
            loading = false;
        }
    }
}
