package site.kobatomo.akushukai;

/**
 * Created by bicpc on 2017/11/04.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
//DialogFragmentのサポートクラスをインポート

public class TestDialogFragment extends DialogFragment
{

    public static TestDialogFragment newInstance()
    {
        return new TestDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.fragment_dialog, null);

        builder.setView(dialogView)
                .setTitle("確認")
                .setMessage("本当に削除しますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        onTestDialogOKClick();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        TestDialogFragment.this.dismiss();
                    }
                });

        return builder.create();
    }

    public void onTestDialogOKClick() {

    }

}
