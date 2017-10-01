package officedepo.mediapark.com.officedepo.Base;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.widget.EditText;

/**
 * Created by Mary Songal on 30.01.2017.
 */

public class BasePhoneNumberWatcher extends PhoneNumberFormattingTextWatcher {

    private EditText edit;
    private boolean backspaceMode = false;
    private String previousText;
    private int cursorPosition;

    public BasePhoneNumberWatcher(EditText editText) {
        edit = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (after > 1) {
            return;
        }
        if (count > after) {
            backspaceMode = true;
            if (s.charAt(start) >= '0' && s.charAt(start) <= '9') {
                StringBuilder stringBuilder = new StringBuilder(s);
                stringBuilder.replace(start, start + 1, "_");
                s = stringBuilder.toString();
            }
            previousText = s.toString();
            edit.setSelection(start);
        }
        else if (start < s.length() && (s.charAt(start) == '_' || (s.charAt(start) >= '0' && s.charAt(start) <= '9'))) {
            backspaceMode = false;
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.deleteCharAt(start);
            previousText = stringBuilder.toString();
        } else {
            backspaceMode = false;
            if (start == 7) {
                edit.setSelection(9);
            } else if (start == 12) {
                edit.setSelection(13);
            }
            edit.performClick();
            previousText = s.toString();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 1) {
            return;
        }
        cursorPosition = backspaceMode ? start : start + 1;
        if (backspaceMode && start == 8) cursorPosition = start - 1;
        if (!backspaceMode && start == 11) cursorPosition = start + 2;
        if (!backspaceMode && start <= 16) {
            int insertPosition = start;
            if (start == 7) {
                insertPosition = 9;
                cursorPosition = 10;
            } else if (start == 12) {
                insertPosition = 13;
                cursorPosition = 14;
            }
            StringBuilder stringBuilder = new StringBuilder(previousText);
            if (insertPosition != start) {
                stringBuilder.deleteCharAt(insertPosition);
            }
            stringBuilder.insert(insertPosition, s.charAt(start));
            previousText = stringBuilder.toString();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!backspaceMode && cursorPosition == 7) {
            cursorPosition = 9;
        }
        if (!s.toString().equals(previousText)) {
            edit.setText(previousText);
            if (cursorPosition < 17) {
                edit.setSelection(cursorPosition);
            } else {
                edit.setSelection(s.length() - 1);
            }
            if (!backspaceMode) {
                edit.performClick();
            } else if (cursorPosition < 4) {
                edit.setSelection(4);
            }
        }
    }

}
