package android.corso.dispensa.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);



        return new android.app.TimePickerDialog(getActivity(),(android.app.TimePickerDialog.OnTimeSetListener) getActivity(), hour,minute,
                android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
