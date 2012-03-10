package SESI.chip8;

import SESI.chip8.utils.Sprite;
import SESI.chip8.utils.screens.*;

import android.app.Activity;
import android.os.Bundle;

public class SESIChip8 extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //GLScreenTest screen = new GLScreenTest(this);
        GLScreen screen = new GLScreen(this);
        //BasicScreen screen = new BasicScreen(this);
        setContentView(screen);

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
            screen.drawSprite((i*8)%(64-8),(i%4)*5, sprite[i]);
        }
    }
}
