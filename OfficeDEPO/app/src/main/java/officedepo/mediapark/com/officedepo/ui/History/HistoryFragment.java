package officedepo.mediapark.com.officedepo.ui.History;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import officedepo.mediapark.com.officedepo.R;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class HistoryFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.history_recyclerview)
    RecyclerView historyRecyclerView;

    private ArrayList<HistoryResponse> historyItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHistoryItems(historyItems);
        return view;
    }

    public void setHistoryItems(List<HistoryResponse> historyItems) {
        this.historyItems = new ArrayList<>(historyItems);
        HistoryAdapter historyAdapter = new HistoryAdapter(this.historyItems);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        historyRecyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
