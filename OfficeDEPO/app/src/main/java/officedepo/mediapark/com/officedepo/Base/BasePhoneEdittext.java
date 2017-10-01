package officedepo.mediapark.com.officedepo.Base;

import android.content.Context;
import android.util.AttributeSet;

import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Mary Songal on 30.01.2017.
 */

public class BasePhoneEdittext extends MaterialEditText {

    public BasePhoneEdittext(Context context) {
        super(context);
    }

    public BasePhoneEdittext(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);

    }

    public BasePhoneEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart <= 4) {
            selStart = 5;
        }
        setSelection(selStart, selEnd);
    }
}
