package ir.kivee.quotop;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.kivee.quotop.data.Quote;
import ir.kivee.quotop.utils.DatabaseHelper;
import ir.kivee.quotop.utils.QuotesCustomAdapter;

public class QuotesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CoordinatorLayout layout;
    private DatabaseHelper helper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);
        id = getIntent().getExtras().getInt("id");
        String cat = getIntent().getExtras().getString("category");
        layout = findViewById(R.id.main_content);
        helper = new DatabaseHelper(this);
        helper.openDataBase();

        recyclerView = findViewById(R.id.activity_quotes_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        new GetData(this).execute();

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cat);
        Typeface title = Typeface.createFromAsset(getAssets(), "fonts/biko.otf");
        collapsingToolbar.setCollapsedTitleTypeface(title);
    }

    class GetData extends AsyncTask<Void, Void, List<Quote>> {
        Context context;

        public GetData(Context context) {
            this.context = context;
        }

        @Override
        protected List<Quote> doInBackground(Void... voids) {
            List<Quote> quotes = new ArrayList<>(helper.getQuotes(id));
            return quotes;
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            super.onPostExecute(quotes);
            QuotesCustomAdapter adapter = new QuotesCustomAdapter(layout, context, quotes);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            helper.close();
        }
    }
}
