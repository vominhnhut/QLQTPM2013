package com.example.ultils;

import com.example.wego.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogGenerator {

	public static AlertDialog createExitConfirmationDialog(Context context,
			OnClickListener okClickEvent) {
		AlertDialog.Builder builder = new Builder(context);

		builder.setMessage(R.string.exit_dialog_title);
		builder.setPositiveButton(R.string.ok_text, okClickEvent);
		builder.setNegativeButton(R.string.cancel_text, null);
		
		return builder.create();
	}

}
