package ir.kivee.quotop.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.kivee.quotop.R;

/**
 * Created by payam on 10/5/17.
 */

public class ColorsFragment extends Fragment {

    private static int adapterCase;
    RecyclerView recyclerView;

    public ColorsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colors, container, false);

        recyclerView = rootView.findViewById(R.id.colors_grid_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerCustomAdapter adapter = new RecyclerCustomAdapter(getContext(), adapterCase);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public static void getAdapterCase(int ac) {
        adapterCase = ac;
    }
}
