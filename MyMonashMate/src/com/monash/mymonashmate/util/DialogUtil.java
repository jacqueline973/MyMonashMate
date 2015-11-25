package com.monash.mymonashmate.util;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtil {
	
	public static void showAlertDialog(AlertDialog.Builder builder, Context context, String title, String message) {
		builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", null).create().show();
	}
	
}
