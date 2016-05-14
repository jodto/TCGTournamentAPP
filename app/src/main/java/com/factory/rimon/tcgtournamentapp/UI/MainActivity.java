package com.factory.rimon.tcgtournamentapp.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;
import com.factory.rimon.tcgtournamentapp.DAL.DAO;
import com.factory.rimon.tcgtournamentapp.DAL.TournamentRepository;
import com.factory.rimon.tcgtournamentapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvTournaments;

    TournamentRepository tRepo;
    TournamentAdapter tAdapter;
    LinearLayout mainLayout;
    Button btnRetry;
    private DAO dao;

    int nextPage = 1;
    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAO(this);
        lvTournaments = (ListView) findViewById(R.id.lvTournaments);

        tAdapter = new TournamentAdapter(this);
        tRepo = new TournamentRepository();
        lvTournaments.setAdapter(tAdapter);
        mainLayout =  (LinearLayout)findViewById(R.id.layout);

        lvTournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickTournament(position);
            }
        });


        lvTournaments.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                onScrollList(firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        populateTournaments();



    }

    //populates the listview depending on if the internet is on or not
    public void populateTournaments() {
        if (dao.selectAll().size() == 0 && isNetworkAvailable()) {
            new LoadDataTask().execute(nextPage);
        }
        else if(dao.selectAll().size() == 0 && !isNetworkAvailable())
        {
            Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            lvTournaments.setVisibility(View.GONE);
            addRetryButton();
        }
        else if(dao.selectAll().size() != 0 && !isNetworkAvailable())
        {
            tAdapter.addAll(dao.selectAll());

        }

    }

    //checking if the phone is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

            TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            TextView txtDate = (TextView) v.findViewById(R.id.txtDate);
            TextView txtLocation = (TextView) v.findViewById(R.id.txtLocation);

            txtTitle.setText("" + t.getTitle());
            txtDate.setText("" + t.getDate());
            txtLocation.setText("" + t.getLocation());

            return v;
        }
    }

    //Sends the user to the details activity
    public void onClickTournament(int position){
        BETournament tournament = tAdapter.getItem(position);

        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
        intent.putExtra("tournament", tournament);
        startActivity(intent);
    }

    public void onScrollList(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean loadMore =
                firstVisibleItem + visibleItemCount >= totalItemCount;

        //If we are at bottom at the page and not loading, load more data
        if (loadMore && !loading) {
            if (isNetworkAvailable()) {
                Log.d("TOURNAMENTS", "onScroll page to load = " + nextPage);
                new LoadDataTask().execute(nextPage);
                loading = true;
                Log.d("AMOUNT OF TOURNAMENT DB", dao.selectAll().size() + "");
                nextPage++;
            } else {
                Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //creates adds the retry button used in populateTournaments()
    public void addRetryButton()
    {
        btnRetry = new Button(this);
        btnRetry.setText("retry");
        btnRetry.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));
        btnRetry.setTextSize(100);
        mainLayout.addView(btnRetry);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (isNetworkAvailable()) {
                    mainLayout.removeView(btnRetry);
                    lvTournaments.setVisibility(View.VISIBLE);
                    new LoadDataTask().execute(nextPage);
                    tAdapter.addAll(dao.selectAll());

                } else {
                    Toast.makeText(getApplicationContext(), "NO INTERNET FOUND", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class LoadDataTask extends AsyncTask<Integer, Void, List<BETournament>> {
        @Override
        protected List<BETournament> doInBackground(Integer... page) {
            for(BETournament t : tRepo.loadPage(page[0]))
            {
                if (!dao.selectAll().contains(t)) {
                    dao.insert(t);
                }
            }
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
