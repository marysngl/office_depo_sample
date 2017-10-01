package officedepo.mediapark.com.officedepo.ui.History;

import java.util.List;

import officedepo.mediapark.com.officedepo.Base.MvpView;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public interface HistoryMvpView extends MvpView {

    void showError();

    void setupHistory(List<HistoryResponse> historyItems);

    void setupTotalSpent(int totalSpent);

    void setupTotalBonus(int totalBonus);

}
