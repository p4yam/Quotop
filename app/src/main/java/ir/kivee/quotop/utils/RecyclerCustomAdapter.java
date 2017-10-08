package ir.kivee.quotop.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.kivee.quotop.QuoteActivity;
import ir.kivee.quotop.R;
import ir.kivee.quotop.data.ColorsAndFonts;
import ir.kivee.quotop.data.Quote;

/**
 * Created by payam on 10/6/17.
 */

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.CustomViewHolder>{

    private static final int CASE_FONTS = 1;
    private static final int CASE_FONT_COLOR = 2;
    private static final int CASE_BACK_COLOR = 3;
    private int adapterCase;
    private Context context;
    private QuoteActivity activity;

    public RecyclerCustomAdapter(Context context, int adapterCase) {
        this.context = context;
        this.adapterCase = adapterCase;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_single_color, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        switch (adapterCase) {
            case CASE_FONTS:
                holder.layout.setVisibility(View.GONE);
                holder.txtFontNames.setVisibility(View.VISIBLE);
                holder.txtFontNames.setText(ColorsAndFonts.getFontNames().get(position));
                Typeface typeface=Typeface.createFromAsset(
                        context.getAssets(),
                        ColorsAndFonts.getFontRes().get(position)
                );
                holder.txtFontNames.setTypeface(typeface);
                break;
            case CASE_FONT_COLOR:
                holder.layout.setVisibility(View.VISIBLE);
                holder.txtFontNames.setVisibility(View.GONE);
                holder.layout.setBackgroundColor(context.getResources()
                        .getColor(ColorsAndFonts.getFontColors().get(position)));
                break;
            case CASE_BACK_COLOR:
                holder.layout.setVisibility(View.VISIBLE);
                holder.txtFontNames.setVisibility(View.GONE);
                if (position==0) {
                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_perm_media);
                    holder.layout.setBackground(drawable);
                } else {
                    holder.layout.setBackgroundColor(context.getResources()
                            .getColor(ColorsAndFonts.getFontColors().get(position-1)));
                }
                break;
            default:
                break;
        }
        final int pos=position;
        holder.txtFontNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuoteActivity.changeQuoteFont(ColorsAndFonts.getFontRes().get(pos));
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapterCase==2)
                    QuoteActivity.changeQuoteFontColor(ColorsAndFonts.getFontColors().get(pos));
                else if (adapterCase==3){
                    if (pos==0)
                        QuoteActivity.changeQuoteBackground(0);
                    else
                        QuoteActivity.changeQuoteBackground(ColorsAndFonts.getBackColors().get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        switch (adapterCase) {
            case CASE_FONTS:
                return ColorsAndFonts.getFontNames().size();

            case CASE_FONT_COLOR:
                return ColorsAndFonts.getFontColors().size();

            case CASE_BACK_COLOR:
                return ColorsAndFonts.getBackColors().size();
            default:
                return 0;
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView layout;
        TextView txtFontNames;
        public CustomViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.fragment_single_color_layout);
            txtFontNames=itemView.findViewById(R.id.fragment_font_style);
        }
    }
}
