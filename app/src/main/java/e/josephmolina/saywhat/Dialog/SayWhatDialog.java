package e.josephmolina.saywhat.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import e.josephmolina.saywhat.R;

public class SayWhatDialog extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.*/
    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialogFragment);

        void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    // Use this instance of the interface to deliver action events
    DialogListener dialogListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        if (context instanceof DialogListener) {
            dialogListener = (DialogListener) context;
        } else {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + "must implement dialog listener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.save_translation_dialog, null))
                .setTitle("Save your translation")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.onDialogPositiveClick(SayWhatDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.onDialogNegativeClick(SayWhatDialog.this);
                    }
                });
        builder.setCancelable(false);

        return builder.create();
    }
}
