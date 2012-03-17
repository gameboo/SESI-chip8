package SESI.chip8;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

				// choix du jeu
				AssetManager assetManager = getAssets();
				ListView gameListView = (ListView) findViewById(R.id.gameListView);
				try
				{
//debug
for(String name:assetManager.list("roms")){
     Log.v("fichier : ",name);    
}
						gameListView.setAdapter(new ArrayAdapter<String>(this, R.layout.gamelistentry,assetManager.list("roms")));
				}
				catch (IOException e)
				{
						// handle
				}				
				// spinner screen
				Spinner screenSpinner = (Spinner) findViewById(R.id.screenSpinner);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
								this, R.array.screenType, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				screenSpinner.setAdapter(adapter);

				Button go = (Button) findViewById(R.id.buttonGo);
				go.setOnClickListener(new View.OnClickListener()
								{
								public void onClick(View view) {
								Intent myIntent = new Intent(view.getContext(), GameActivity.class);
								//Intent myIntent = new Intent(view.getContext(), SESIChip8.class);
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
