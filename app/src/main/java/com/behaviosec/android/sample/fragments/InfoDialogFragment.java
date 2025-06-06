package com.behaviosec.android.sample.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.behaviosec.android.sample.R;

public class InfoDialogFragment extends DialogFragment
{
	@StringRes
	private int mTitleText;

	@StringRes
	private int mMessageText;

	public static InfoDialogFragment newInstance(final @StringRes int titleText, final @StringRes int messageText) {
		InfoDialogFragment frag = new InfoDialogFragment();
		frag.setProps(titleText, messageText);
		return frag;
	}

	private void setProps(final @StringRes int titleText, final @StringRes int messageText) {
		mTitleText = titleText;
		mMessageText = messageText;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View infoDialog = inflater.inflate(R.layout.dialog_info, null);

		// Set Text messages
		TextView titleView = infoDialog.findViewById(R.id.infoDialog_title);
		titleView.setText(mTitleText);
		TextView messageView = infoDialog.findViewById(R.id.infoDialog_text);
		messageView.setText(mMessageText);

		// Create the AlertDialog object and return it
		final AlertDialog dialog = builder.setView(infoDialog).create();
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		infoDialog.findViewById(R.id.infoDialog_okButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		return dialog;
	}
}
