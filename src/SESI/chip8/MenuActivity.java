package SESI.chip8;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.res.AssetManager;
import android.widget.ListView;

//debug
import android.util.Log;

public class MenuActivity extends Activity implements OnItemSelectedListener
{
		public void onCreate(Bundle savedInstanceState)
		{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.menuactivity);

				final Bundle myBundle = new Bundle();
				final AssetManager assetManager = getAssets();

				// choix du jeu
				ListView gameListView = (ListView) findViewById(R.id.gameListView);
				try
				{
						//debug
						for(String name:assetManager.list("roms"))
						{
								Log.v("fichier : ",name);    
						}
						gameListView.setAdapter(new ArrayAdapter<String>(this, R.layout.gamelistentry,assetManager.list("roms")));
				}
				catch (IOException e)
				{
						// handle
				}				
				gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
								{
								public void onItemClick(AdapterView<?> av, View v, int pos, long id)
								{
								myBundle.putString("gameFileName",((TextView) v).getText().toString());
								}
								});

				// Choix du screen
				final Spinner screenSpinner = (Spinner) findViewById(R.id.screenSpinner);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
								this, R.array.screenType, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				screenSpinner.setAdapter(adapter);

				// valider les choix
				Button go = (Button) findViewById(R.id.buttonGo);
				go.setOnClickListener(new View.OnClickListener()
								{
								public void onClick(View view)
								{
								Intent myIntent = new Intent(view.getContext(), GameActivity.class);
								myBundle.putString("screenType",screenSpinner.getSelectedItem().toString());
								if(myBundle.get("gameFileName") == null)
								{
								try
								{
								myBundle.putString("gameFileName",assetManager.list("roms")[0].toString());
								}
								catch (IOException e)
								{
								// handle
								}				
								}
								myIntent.putExtras(myBundle);
								startActivityForResult(myIntent, 0);
								}

								});
		}

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
		{
				// Do something
		}

		public void onNothingSelected(AdapterView parent)
		{
				// Do nothing.
		}
}
