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
import android.content.res.AssetManager;

import java.util.Timer ;
import java.util.TimerTask ;
import java.io.InputStream;
import java.io.IOException;

//debug
import android.util.Log;

public class GameActivity extends Activity
{
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				// récupération des info de MenuActivity
				Bundle myBunble  = this.getIntent().getExtras();
				String screenType= myBunble.getString("screenType");
				String gameFileName= myBunble.getString("gameFileName");
				final AssetManager assetManager = getAssets();
				InputStream file;
				file = null ; // Contourne une erreur de compilation si la selection de fichier leve une exception
				try
				{
					file = assetManager.open("roms/"+gameFileName.toString());
				}
				catch (IOException e)
				{
					Log.v("@GameActivity","IOException sur l'ouverture de file");
						// handle
				}				

				// debug
				Log.v("string received", gameFileName.toString());

				// make it fullscreen !!! :p
				requestWindowFeature(Window.FEATURE_NO_TITLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
								WindowManager.LayoutParams.FLAG_FULLSCREEN);
				// gestion du layout "dynamique"
				RelativeLayout lay = new RelativeLayout(this);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
				lay.setLayoutParams(params);

				View screen;
				if(screenType.compareToIgnoreCase("BasicScreen") == 0)
					screen = new BasicScreen(this);
				else if(screenType.compareToIgnoreCase("GLScreen") == 0)
					screen = new GLScreen(this);
				else
					screen = new BasicScreen(this);
				
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
				Log.v("@GameActivity","tentative de demarrer le simulation");
				final Chip8Simu simu = new Chip8Simu((Chip8Screen)screen,input,file) ;
				Log.v("@GameActivity","simulateur demarre avec succes");

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
				timer60hz.scheduleAtFixedRate(tasktime,0,17) ;

				Timer timercpu = new Timer() ;
				timercpu.scheduleAtFixedRate(taskcpu,0,5) ;
		}
}
