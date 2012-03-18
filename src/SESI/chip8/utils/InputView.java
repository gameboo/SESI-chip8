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
import android.graphics.Color;

import android.util.Log; //debug purpose

public class InputView extends RelativeLayout
{
		Chip8Input kb;
		Button[] button;
		int nbButton = 16;

		private class ButtonHandler implements OnTouchListener
		{
				private int _b;
				private Chip8Input _kb;
				public ButtonHandler(Chip8Input kb, int b)
				{
						_b = b;
						_kb = kb;
				}
				public boolean onTouch(View v, MotionEvent e)
				{
						switch(e.getAction())
						{
								case MotionEvent.ACTION_DOWN :
										v.setBackgroundColor(Color.argb(100,255,0,0));
										_kb.pressButton(_b);
										break;
								case MotionEvent.ACTION_UP :
										v.setBackgroundColor(Color.argb(50,100,100,100));
										_kb.releaseButton(_b);
										break;
						}
						return true;
				}
		}

		public InputView(Context context, Chip8Input input)
		{
				super(context);
				LayoutInflater inflater = LayoutInflater.from(context);
				inflater.inflate(R.layout.inputview,this);
				kb = input;
				button = new Button[nbButton];
				button[0] = (Button) findViewById(R.id.b0);
				button[1] = (Button) findViewById(R.id.b1);
				button[2] = (Button) findViewById(R.id.b2);
				button[3] = (Button) findViewById(R.id.b3);
				button[4] = (Button) findViewById(R.id.b4);
				button[5] = (Button) findViewById(R.id.b5);
				button[6] = (Button) findViewById(R.id.b6);
				button[7] = (Button) findViewById(R.id.b7);
				button[8] = (Button) findViewById(R.id.b8);
				button[9] = (Button) findViewById(R.id.b9);
				button[10] = (Button) findViewById(R.id.ba);
				button[11] = (Button) findViewById(R.id.bb);
				button[12] = (Button) findViewById(R.id.bc);
				button[13] = (Button) findViewById(R.id.bd);
				button[14] = (Button) findViewById(R.id.be);
				button[15] = (Button) findViewById(R.id.bf);
				for(int i = 0; i<nbButton; i++)
				{
						button[i].setBackgroundColor(Color.argb(50,100,100,100));
						button[i].setOnTouchListener(new ButtonHandler(kb,i));
				}
		}
}
