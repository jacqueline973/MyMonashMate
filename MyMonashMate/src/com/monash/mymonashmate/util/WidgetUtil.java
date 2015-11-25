package com.monash.mymonashmate.util;

import android.text.Editable;
import android.widget.EditText;

public class WidgetUtil {
	
	public static boolean haveEmptyInput(EditText... editTexts) {
		
		for (int i=0; i< editTexts.length; i++ ) {
			Editable text = editTexts[i].getText();
			if (null == text || text.toString().toString().length() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean allEmptyInput(EditText... editTexts) {
		
		for (int i=0; i< editTexts.length; i++ ) {
			Editable text = editTexts[i].getText();
			if (null != text && text.toString().toString().length() != 0) {
				return false;
			}
		}
		return true;
	}
	
	public static String getInput(EditText editText) {

		if (editText.getText() == null || editText.getText().length() == 0
				|| editText.getText().toString().trim().length() == 0) {
			return null;
		}
		return editText.getText().toString().trim();
	}
	
	public static boolean isInteger(String text) {
        try {
            Integer.valueOf(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}

}
