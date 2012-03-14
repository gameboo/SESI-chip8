package SESI.chip8;

import android.view.ViewGroup;
import android.widget.Button;
import android.content.Context;

public class InputView extends ViewGroup
{
    public InputView(Context context)
    {
        super(context);
        Button but = new Button(context);
        but.setText("toto");
        addView(but);
    }
    protected void onLayout(boolean changed,int i, int t ,int r, int b)
    {
        //super.onLayout(changed, i, t, r, b);
    }
}
