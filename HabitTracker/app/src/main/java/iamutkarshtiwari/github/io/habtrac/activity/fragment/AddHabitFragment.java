package iamutkarshtiwari.github.io.habtrac.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import iamutkarshtiwari.github.io.habtrac.R;
import iamutkarshtiwari.github.io.habtrac.activity.models.HabitContract;
import iamutkarshtiwari.github.io.habtrac.activity.utils.HabitDbHelper;

public class AddHabitFragment extends DialogFragment {

    String mImageURI;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View addView = inflater.inflate(R.layout.dialog_add, null);


        final Dialog addDialog = builder.setView(addView)
                .setPositiveButton(R.string.add_product, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddHabitFragment.this.getDialog().cancel();
                    }
                })
                .create();

        // Current date
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        final TextView startDate = (TextView) addView.findViewById(R.id.date);
        startDate.setText(formattedDate);


        // set on the listener for the positive button of the dialog
        addDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // add wantToCloseDialog to prevent the dialog from closing when the information is not completely filled out
                        Boolean wantToCloseDialog = false;


                        EditText editHabitName = (EditText) addView.findViewById(R.id.name);

                        EditText editNumberOfTimes = (EditText) addView.findViewById(R.id.frequency);

                        String name = editHabitName.getText().toString().trim();
                        String date = startDate.getText().toString().trim();
                        String frequency = editNumberOfTimes.getText().toString().trim();

                        // validate all the required infomation
                        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(date) || TextUtils.isEmpty(frequency)) {
                            Toast.makeText(getActivity(), getString(R.string.habit_info_not_complete), Toast.LENGTH_SHORT).show();
                        } else {
                            insertHabit(name, date, frequency);
                            wantToCloseDialog = true;
                        }

                        // after successfully inserting product, dismiss the dialog
                        if (wantToCloseDialog)
                            addDialog.dismiss();
                    }
                });
            }
        });

        return addDialog;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private void insertHabit(String name, String date, String frequency) {
        HabitDbHelper mHabitDbHelper = new HabitDbHelper(getActivity());
        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_NAME, name);
        values.put(HabitContract.HabitEntry.COLUMN_START_DATE, date);
        values.put(HabitContract.HabitEntry.COLUMN_NUMBER_OF_TIMES, frequency);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.save_error), Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.habit_saved, newRowId), Toast.LENGTH_SHORT).show();
        }
    }

}