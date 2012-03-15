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
		Chip8Input kb;
		public InputView(Context context, Chip8Input input)
		{
				super(context);
				LayoutInflater inflater = LayoutInflater.from(context);
				inflater.inflate(R.layout.inputview,this);
				kb = input;
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
												kb.pressButton(0x0);	
												break;
										case R.id.b1:
												kb.pressButton(0x1);	
												break;
										case R.id.b2:
												kb.pressButton(0x2);	
												break;
										case R.id.b3:
												kb.pressButton(0x3);	
												break;
										case R.id.b4:
												kb.pressButton(0x4);	
												break;
										case R.id.b5:
												kb.pressButton(0x5);	
												break;
										case R.id.b6:
												kb.pressButton(0x6);	
												break;
										case R.id.b7:
												kb.pressButton(0x7);	
												break;
										case R.id.b8:
												kb.pressButton(0x8);	
												break;
										case R.id.b9:
												kb.pressButton(0x9);	
												break;
										case R.id.ba:
												kb.pressButton(0xA);	
												break;
										case R.id.bb:
												kb.pressButton(0xB);	
												break;
										case R.id.bc:
												kb.pressButton(0xC);	
												break;
										case R.id.bd:
												kb.pressButton(0xD);	
												break;
										case R.id.be:
												kb.pressButton(0xE);	
												break;
										case R.id.bf:
												kb.pressButton(0xF);	
												break;
								}
								break;
						case MotionEvent.ACTION_UP :
								switch(v.getId())
								{
										case R.id.b0:
												kb.releaseButton(0x0);	
												break;
										case R.id.b1:
												kb.releaseButton(0x1);	
												break;
										case R.id.b2:
												kb.releaseButton(0x2);	
												break;
										case R.id.b3:
												kb.releaseButton(0x3);	
												break;
										case R.id.b4:
												kb.releaseButton(0x4);	
												break;
										case R.id.b5:
												kb.releaseButton(0x5);	
												break;
										case R.id.b6:
												kb.releaseButton(0x6);	
												break;
										case R.id.b7:
												kb.releaseButton(0x7);	
												break;
										case R.id.b8:
												kb.releaseButton(0x8);	
												break;
										case R.id.b9:
												kb.releaseButton(0x9);	
												break;
										case R.id.ba:
												kb.releaseButton(0xA);	
												break;
										case R.id.bb:
												kb.releaseButton(0xB);	
												break;
										case R.id.bc:
												kb.releaseButton(0xC);	
												break;
										case R.id.bd:
												kb.releaseButton(0xD);	
												break;
										case R.id.be:
												kb.releaseButton(0xE);	
												break;
										case R.id.bf:
												kb.releaseButton(0xF);	
												break;
								}
								break;
				}
				return true;
		}
}
