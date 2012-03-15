package SESI.chip8;

import SESI.chip8.utils.Chip8Input;

import android.view.View;
import android.view.MotionEvent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.content.Context;
import android.app.Activity;

import android.util.Log; //debug purpose

public class InputView extends RelativeLayout implements View.OnTouchListener
{
		public InputView(Context context, Chip8Input input)
		{
				super(context);
				LayoutInflater inflater = LayoutInflater.from(context);
				inflater.inflate(R.layout.inputview,this);
				/*
				   for(int i = 0; i<nbButton; i++)
				   {
				   button[i] = new Button(context);
				   button[i].setText(Integer.toString(i));
				   addView(button[i]);
				   }
				 */
		}

		public boolean onTouch(View v, MotionEvent event)
		{
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN :
						switch(v.getId())
						{
								case R.id.b0:
										
										break;
						}
					break;
					case MotionEvent.ACTION_UP :
					break;
				}
				return true;
		}
}
