package ir.kivee.quotop.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import ir.kivee.quotop.QuoteActivity;
import ir.kivee.quotop.R;

/**
 * Created by payam on 10/6/17.
 */

public class FontSizeFragment extends Fragment {
    private SeekBar seekBar;
    private static float val;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_font_size, container, false);
        seekBar = rootView.findViewById(R.id.seekbar_font_size);
        seekBar.setMax(50);
        seekBar.setProgress(25);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
                QuoteActivity.changeQuoteFontSize((float) value);
                val = value;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }

}
