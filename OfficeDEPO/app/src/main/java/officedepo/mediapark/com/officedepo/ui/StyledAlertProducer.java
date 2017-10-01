package officedepo.mediapark.com.officedepo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import officedepo.mediapark.com.officedepo.R;

/**
 * Created by Mary Songal on 18.11.2016.
 */

public class StyledAlertProducer {

    public static void showStyledAlert(Context context, int titleRes, String message, int positiveTextRes, int negativeTextRes,
                                       MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(titleRes)
                .titleColorRes(R.color.colorAccent)
                .titleGravity(GravityEnum.CENTER)
                .content(message)
                .contentColorRes(R.color.colorPrimaryDark)
                .contentGravity(GravityEnum.CENTER)
                .negativeText(negativeTextRes)
                .negativeColorRes(R.color.colorPrimaryDark)
                .positiveText(positiveTextRes)
                .positiveColorRes(R.color.colorPrimaryDark)
                .typeface("DidactGothic.ttf", "DidactGothic.ttf")
                .onPositive(callback)
                .show();
    }

    public static void showDismissiveStyledAlert(Context context, int titleRes, String message, int positiveTextRes) {
        new MaterialDialog.Builder(context)
                .title(titleRes)
                .titleColorRes(R.color.colorAccent)
                .titleGravity(GravityEnum.CENTER)
                .content(message)
                .contentColorRes(R.color.colorPrimaryDark)
                .contentGravity(GravityEnum.CENTER)
                .positiveText(positiveTextRes)
                .positiveColorRes(R.color.colorPrimaryDark)
                .typeface("DidactGothic.ttf", "DidactGothic.ttf")
                .show();
    }

}


