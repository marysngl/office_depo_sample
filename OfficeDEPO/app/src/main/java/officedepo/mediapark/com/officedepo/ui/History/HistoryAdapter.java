package officedepo.mediapark.com.officedepo.ui.History;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.App;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistoryResponse> historyItems;

    protected class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.history_date_textview)
        TextView bonusDateTextview;
        @BindView(R.id.history_description_textview)
        TextView descriptionTextview;
        @BindView(R.id.history_price_textview)
        TextView priceTextview;
        @BindView(R.id.history_bonus_textview)
        TextView bonusTextview;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public HistoryAdapter(List<HistoryResponse> historyItems) {
        this.historyItems = historyItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryAdapter.ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryAdapter.ViewHolderItem viewHolderItem = (HistoryAdapter.ViewHolderItem)holder;
        HistoryResponse historyItem = historyItems.get(position);
        viewHolderItem.bonusDateTextview.setText(historyItem.date);
        viewHolderItem.descriptionTextview.setText(historyItem.item);
        viewHolderItem.priceTextview.setText(String.valueOf(historyItem.spent) + "\n" + App.getAppContext().getResources().getString(R.string.history_currency));
        viewHolderItem.bonusTextview.setText(String.valueOf(historyItem.bonus) + "\n" + App.getAppContext().getResources().getString(R.string.history_bonus));
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

}
