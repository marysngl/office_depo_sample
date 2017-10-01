package officedepo.mediapark.com.officedepo.ui.Deals;

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
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.R;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class DealsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.deals_recyclerview)
    RecyclerView dealsRecycler;

    private ArrayList<DealResponse> deals = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deals, container, false);
        unbinder = ButterKnife.bind(this, view);
        setDeals(deals);
        return view;
    }

    public void setDeals(List<DealResponse> deals) {
        this.deals = new ArrayList<>(deals);
        DealsAdapter dealsAdapter = new DealsAdapter(this.deals, (DealsActivity) getActivity());
        dealsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dealsRecycler.setAdapter(dealsAdapter);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
