package officedepo.mediapark.com.officedepo.Base;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mary Songal on 08.02.2017.
 */

public class BasePhoneNumberTouchListener implements View.OnTouchListener {

    private EditText phoneEdittext;

    public BasePhoneNumberTouchListener(EditText editText) {
        phoneEdittext = editText;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // Ctrl-C - Ctrl-V from clickListener (don't do this at home, kids)
        phoneEdittext.requestFocusFromTouch();
        String phoneText = phoneEdittext.getText().toString();
        List<Integer> positions = Arrays.asList(4, 5, 6, 9, 10, 11, 13, 14, 15, 16);
        if (phoneEdittext.getSelectionStart() <= 3) {
            phoneEdittext.setSelection(4);
        } else if (phoneEdittext.getSelectionStart() == 8) {
            phoneEdittext.setSelection(9);
        } else if (phoneEdittext.getSelectionStart() >= 18) {
            phoneEdittext.setSelection(17);
        }
        boolean allNumbers = true;
        for (int position : positions) {
            if (phoneText.charAt(position) < '0' || phoneText.charAt(position) > '9') {
                phoneEdittext.setSelection(position);
                allNumbers = false;
                break;
            }
        }
        if (allNumbers) {
            phoneEdittext.setSelection(17);
        }
        return false;
    }

}
