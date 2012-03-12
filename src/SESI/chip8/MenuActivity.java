package SESI.chip8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class MenuActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.menuactivity);

            Button next = (Button) findViewById(R.id.button1);
            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), SESIChip8.class);
                    startActivityForResult(myIntent, 0);
                }

            });
        }
}
