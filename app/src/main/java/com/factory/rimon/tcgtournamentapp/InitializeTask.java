package com.factory.rimon.tcgtournamentapp;

import android.os.AsyncTask;

import com.factory.rimon.tcgtournamentapp.BE.BETournament;
import com.factory.rimon.tcgtournamentapp.DAL.TournamentRepository;
import com.factory.rimon.tcgtournamentapp.UI.MainActivity;

import java.util.ArrayList;

/**
 * Created by Nikolaj on 02-05-2016.
 */
public class InitializeTask extends AsyncTask<
        TournamentRepository,
        Void,
        ArrayList<BETournament>>
{
        MainActivity context;

        public InitializeTask(MainActivity context)
        {
            context = context;
        }

        @Override
        protected ArrayList<BETournament> doInBackground(TournamentRepository... ms) {
            // params comes from the execute()

            //ms[0].loadAll();
            //ms[0].loadPage(10, 1);
            return ms[0].getAll();
        }
        // onPostExecute displays the results of the AsyncTask.doInBackground().
        // this method is invoked by the GUI thread
        @Override
        protected void onPostExecute(final ArrayList<BETournament> tournaments) {
            //context.initializeData(tournaments);
        }
}
