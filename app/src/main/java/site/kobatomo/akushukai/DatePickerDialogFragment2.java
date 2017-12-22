package site.kobatomo.akushukai;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by bicpc on 2017/11/09.
 */

public class DatePickerDialogFragment2 extends DatePickerDialogFragment{
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (UpdateEvent)getActivity(),  year, month, day);
    }


}
