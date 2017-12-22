package site.kobatomo.akushukai;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by bicpc on 2017/11/03.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    private OnClickListener listener;


    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fontawesome-webfont.ttf" );
        setTypeface(tf);
    }
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }


}
