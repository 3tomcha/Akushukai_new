package site.kobatomo.akushukai;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class AddDialogFragment extends DialogFragment {
    private String[] location_list;
    private EditText showLoc;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        location_list=getResources().getStringArray(R.array.list);

        final Activity add_event = getActivity();

        showLoc=add_event.findViewById(R.id.showLoc);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(location_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showLoc.setText(location_list[0]);
                        break;
                    case 1:
                        showLoc.setText(location_list[1]);
                        break;
                    case 2:
                        showLoc.setText(location_list[2]);
                        break;
                    case 3:
                        showLoc.setText(location_list[3]);
                        break;
                    case 4:
                        showLoc.setText(location_list[4]);
                        break;
                    default:
                        break;
                }
            }
        });
        return builder.create();
    }
}