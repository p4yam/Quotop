package ir.kivee.quotop;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import ir.kivee.quotop.utils.CategoriesCustomAdapter;
import ir.kivee.quotop.utils.DatabaseHelper;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DuoDrawerLayout drawerLayout;
    private DuoDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        recyclerView = findViewById(R.id.activity_main_recycler);
        helper = new DatabaseHelper(this);
        try {
            helper.createDataBase();
            helper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new getCategories(this).execute();

    }

    class getCategories extends AsyncTask<Void, Void, CategoriesCustomAdapter> {

        Context context;

        public getCategories(Context context) {
            this.context = context;
        }

        @Override
        protected CategoriesCustomAdapter doInBackground(Void... voids) {
            CategoriesCustomAdapter adapter = new CategoriesCustomAdapter(helper.getCategories(), context);
            return adapter;
        }

        @Override
        protected void onPostExecute(CategoriesCustomAdapter adapter) {
            super.onPostExecute(adapter);
            recyclerView.setAdapter(adapter);
            helper.close();
        }
    }
}
