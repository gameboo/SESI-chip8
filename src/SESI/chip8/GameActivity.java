package SESI.chip8;

import SESI.chip8.utils.screens.*;
import SESI.chip8.utils.*;
import SESI.chip8.simu.*;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Timer ;
import java.util.TimerTask ;


public class GameActivity extends Activity
{
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				// make it fullscreen !!! :p
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
								WindowManager.LayoutParams.FLAG_FULLSCREEN);
				// gestion du layout "dynamique"
				RelativeLayout lay = new RelativeLayout(this);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
				lay.setLayoutParams(params);

				View screen;
				//if
				screen = new BasicScreen(this);
				//screen = new GLScreen(this);
				params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
				screen.setLayoutParams(params);

				Chip8Input input = new Chip8Input();

				InputView inputView = new InputView(this, input);
				//Button input = new Button(this);
				//input.setText("toto");
				params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				inputView.setLayoutParams(params);

				lay.addView(screen);
				lay.addView(inputView);
				setContentView(lay);

				
				// test //
				final Chip8Simu simu = new Chip8Simu((Chip8Screen)screen,input) ;
				
				// Mise en place d'un Handler pour recuperer les messages

				final Handler handtime= new Handler()
					{
						public void handleMessage(Message msg)
							{
								simu.updtTimers();
							}
					};
				
				final Handler handcpu= new Handler()
					{
						public void handleMessage(Message msg)
							{
								simu.step();
							}
					};

				// On cree un service de timer qui envoie des messages pour signaler l'activite
				TimerTask tasktime = new TimerTask()
				{
					public void run()
					{
						handtime.sendEmptyMessage(0);
					}
				};
				
				TimerTask taskcpu = new TimerTask()
				{
					public void run()
					{
						handcpu.sendEmptyMessage(0);
					}
				};
				
				Timer timer60hz = new Timer() ;
				timer60hz.scheduleAtFixedRate(tasktime,0,1000) ;
								
				Timer timercpu = new Timer() ;
				timercpu.scheduleAtFixedRate(taskcpu,0,200) ;
		}
}
