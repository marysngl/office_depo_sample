package officedepo.mediapark.com.officedepo.ui.Deals;

import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.DealType;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.App;

import static officedepo.mediapark.com.officedepo.Model.Items.DealType.NEW;
import static officedepo.mediapark.com.officedepo.Model.Items.DealType.PERCENT_DISCOUNT;
import static officedepo.mediapark.com.officedepo.Model.Items.DealType.PRICE_DROP;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class DealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DealsActivity dealsActivity;
    private List<DealResponse> deals;

    protected class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.deal_image)
        ImageView dealImage;
        @BindView(R.id.deal_title_textview)
        TextView dealDescription;
        @BindView(R.id.deal_price_layout)
        RelativeLayout priceLayout;
        @BindView(R.id.deal_price_textview)
        TextView priceTextview;
        @BindView(R.id.deal_type_textview)
        TextView dealTypeTextview;
        @BindView(R.id._deal_triangle_text_image)
        ImageView dealTriangleImage;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DealsAdapter(List<DealResponse> deals, DealsActivity dealsActivity) {
        this.deals = deals;
        this.dealsActivity = dealsActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DealsAdapter.ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deal, parent, false));
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        DealsAdapter.ViewHolderItem viewHolderItem = (DealsAdapter.ViewHolderItem)holder;
        DealResponse deal = deals.get(position);
        Picasso.with(dealsActivity)
                .load(deal.pictureUrl)
                .into(viewHolderItem.dealImage);
        viewHolderItem.dealDescription.setText(deal.name);
        viewHolderItem.priceLayout.setBackgroundDrawable(ActivityCompat.getDrawable(dealsActivity, position % 2 == 0 ? R.drawable.price_violet : R.drawable.price_orange));
        viewHolderItem.dealTriangleImage.setImageResource(position % 2 == 0 ? R.drawable.triangle_purple : R.drawable.triangle_orange);
        viewHolderItem.priceTextview.setText(String.valueOf(deal.price));

        DisplayMetrics metrics = App.getAppContext().getResources().getDisplayMetrics();
        if (metrics.densityDpi == DisplayMetrics.DENSITY_280) {
            viewHolderItem.dealTypeTextview.setPadding(0, 0, 0, 60);
        }

        if (deal.promoText != null) {
            viewHolderItem.dealTypeTextview.setText(deal.promoText);
        } else {
            int dealTypeId = Integer.valueOf(deal.dealType);
            if (dealTypeId == NEW.getTypeId()) {
                viewHolderItem.dealTypeTextview.setText(R.string.deals_new);
            } else if (dealTypeId == PERCENT_DISCOUNT.getTypeId()) {
                viewHolderItem.dealTypeTextview.setText("-" + deal.percent + "%");
            } else if (dealTypeId == PRICE_DROP.getTypeId()) {
                viewHolderItem.dealTypeTextview.setText(String.valueOf(deal.oldPrice));
                viewHolderItem.dealTypeTextview.setPaintFlags(viewHolderItem.dealTypeTextview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

}
