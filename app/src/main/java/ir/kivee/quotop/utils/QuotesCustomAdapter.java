package ir.kivee.quotop.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.kivee.quotop.QuoteActivity;
import ir.kivee.quotop.QuotesActivity;
import ir.kivee.quotop.R;
import ir.kivee.quotop.data.Quote;

/**
 * Created by payam on 9/26/17.
 */

public class QuotesCustomAdapter extends RecyclerView.Adapter<QuotesCustomAdapter.CustomViewHolder> {

    private Context context;
    private List<Quote> quotes;
    private DatabaseHelper helper;
    private CoordinatorLayout layout;

    public QuotesCustomAdapter(CoordinatorLayout layout, Context context, List<Quote> quotes) {
        this.context = context;
        this.quotes = quotes;
        this.layout = layout;
        helper = new DatabaseHelper(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_cardview, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        if (quotes.get(position).getQuote().length() >= 110) {
            String quoteText = quotes.get(position).getQuote().substring(0, 110) + "...";
            holder.txtQuote.setText(quoteText);
        } else
            holder.txtQuote.setText(quotes.get(position).getQuote());
        if (quotes.get(position).getBook() == null)
            holder.txtAuthor.setText("― " + quotes.get(position).getAuthor());
        else
            holder.txtAuthor.setText("― " + quotes.get(position).getAuthor() + ", "
                    + quotes.get(position).getBook());

        int isLiked = quotes.get(position).isFavorite();
        if (isLiked == 1)
            holder.imgLiked.setImageResource(R.drawable.liked);
        else
            holder.imgLiked.setImageResource(R.drawable.uliked);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            long lastClickTime = 0;
            long DOUBLE_CLICK_TIME_DELTA = 300;

            @Override
            public void onClick(View view) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                } else {
                    //
                }
                lastClickTime = clickTime;
            }
        });

        holder.imgLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int li = quotes.get(position).isFavorite();
                if (li == 0) {
                    quotes.get(position).setFavorite(1);
                    helper.setQuoteFavorite(quotes.get(position).getId(), 1);
                    notifyDataSetChanged();
                    customSnackbar("Quote added to favorites");
                } else {
                    quotes.get(position).setFavorite(0);
                    helper.setQuoteFavorite(quotes.get(position).getId(), 0);
                    notifyDataSetChanged();
                    customSnackbar("Quote removed from favorites");
                }
            }
        });
        holder.txtQuoteFullText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> quoteInfo=new ArrayList<>();
                quoteInfo.add(String.valueOf(quotes.get(position).getId()));
                quoteInfo.add(quotes.get(position).getQuote());
                quoteInfo.add(quotes.get(position).getAuthor());
                quoteInfo.add(quotes.get(position).getBook());

                Intent intent=new Intent(context, QuoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putStringArrayListExtra("info",quoteInfo);
                context.startActivity(intent);
            }
        });

    }

    private void customSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView txtQuote;
        TextView txtAuthor;
        TextView txtQuoteFullText;
        ImageView imgLiked;

        public CustomViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.quote_container);
            txtQuote = itemView.findViewById(R.id.quote_text);
            imgLiked = itemView.findViewById(R.id.quote_like);
            txtQuoteFullText=itemView.findViewById(R.id.quote_full_text);
            Typeface typeQuote = Typeface.createFromAsset
                    (context.getAssets(), "fonts/biko.otf");
            Typeface typeAuthor = Typeface.createFromAsset
                    (context.getAssets(), "fonts/geosans.ttf");
            txtAuthor = itemView.findViewById(R.id.quote_author);

            txtQuote.setTypeface(typeQuote);
            txtAuthor.setTypeface(typeAuthor);
        }
    }
}
