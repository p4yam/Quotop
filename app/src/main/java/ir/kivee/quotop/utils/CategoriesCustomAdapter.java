package ir.kivee.quotop.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.kivee.quotop.QuotesActivity;
import ir.kivee.quotop.R;

/**
 * Created by payam on 9/23/17.
 */

public class CategoriesCustomAdapter extends RecyclerView.Adapter<CategoriesCustomAdapter.CatViewHolder> {

    private List<String> categories;
    private Context context;

    public CategoriesCustomAdapter(List<String> cats, Context context) {
        this.categories = cats;
        this.context = context;
    }

    @Override
    public CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recycler_contents, parent, false);
        CatViewHolder viewHolder = new CatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CatViewHolder holder, final int position) {

        int resImg = context.getResources().getIdentifier(categories.get(position).toLowerCase().replace(" ", "")
                , "drawable",
                context.getPackageName());
        holder.catImage.setImageResource(resImg);
        holder.catTitle.setText(categories.get(position));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=position+1;
                Intent intent=new Intent(context, QuotesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",id);
                intent.putExtra("category",categories.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        ImageView catImage;
        TextView catTitle;
        LinearLayout linearLayout;

        public CatViewHolder(View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.card_category_image);
            catTitle = itemView.findViewById(R.id.card_category_name);
            linearLayout=itemView.findViewById(R.id.card_category_container);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    "fonts/parisish.ttf");
            catTitle.setTypeface(typeface);
        }
    }
}
