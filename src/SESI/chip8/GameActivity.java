package SESI.chip8;

import SESI.chip8.utils.screens.*;
import SESI.chip8.utils.*;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            RelativeLayout lay = new RelativeLayout(this);
            InputView input = new InputView(this);
            View screen;
            //if
            screen = new BasicScreen(this);
            //screen = new GLScreen(this);
            lay.addView(screen);
            lay.addView(input);
            setContentView(lay);

            // tests //

            byte chip8_fontset[][] =
            { 
                {(byte)0xF0, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0xF0}, // 0
                {(byte)0x20, (byte)0x60, (byte)0x20, (byte)0x20, (byte)0x70}, // 1
                {(byte)0xF0, (byte)0x10, (byte)0xF0, (byte)0x80, (byte)0xF0}, // 2
                {(byte)0xF0, (byte)0x10, (byte)0xF0, (byte)0x10, (byte)0xF0}, // 3
                {(byte)0x90, (byte)0x90, (byte)0xF0, (byte)0x10, (byte)0x10}, // 4
                {(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x10, (byte)0xF0}, // 5
                {(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x90, (byte)0xF0}, // 6
                {(byte)0xF0, (byte)0x10, (byte)0x20, (byte)0x40, (byte)0x40}, // 7
                {(byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x90, (byte)0xF0}, // 8
                {(byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x10, (byte)0xF0}, // 9
                {(byte)0xF0, (byte)0x90, (byte)0xF0, (byte)0x90, (byte)0x90}, // A
                {(byte)0xE0, (byte)0x90, (byte)0xE0, (byte)0x90, (byte)0xE0}, // B
                {(byte)0xF0, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0xF0}, // C
                {(byte)0xE0, (byte)0x90, (byte)0x90, (byte)0x90, (byte)0xE0}, // D
                {(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0xF0}, // E
                {(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80}  // F
            };

            Sprite[] sprite = new Sprite[16];
            for(int i = 0; i < 16; i++)
            {
                /*
                   sprite[i] = new Sprite(5);
                   sprite[i].setMatrix(chip8_fontset[i]);
                   */
                sprite[i] = new Sprite(chip8_fontset[i]);
                ((Chip8Screen)screen).drawSprite((i*8)%(64-8),(i%4)*5, sprite[i]);
            }
            // end tests //
        }
}
