package site.kobatomo.akushukai.Activity;

import android.support.v4.app.FragmentActivity;

/**
 * Created by tomoya on 2018/06/20.
 */

abstract class AbstractEvent extends FragmentActivity{
    abstract void onKeyDown(int num, String str);
}
