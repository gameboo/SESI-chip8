package SESI.chip8.simu ;

import SESI.chip8.utils.Chip8Input ;
import SESI.chip8.utils.Chip8Screen ;
import SESI.chip8.utils.Sprite ;

public class Chip8Simu
{
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
		{(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80},  // F
		{(byte)0x80},  // pixel gauche
		{(byte)0xFF}  // ligne pleine
	};
	public Chip8Simu(Chip8Screen screen, Chip8Input kb)
	{
		Sprite test = new Sprite(1) ;
		Sprite test2 = new Sprite(1) ;
		test.setMatrix(chip8_fontset[0x10]);
		test2.setMatrix(chip8_fontset[0x11]);
		for (int i = 0;i<32;i++)
		{
			screen.drawSprite(i,i, test);
			screen.drawSprite(0,i, test);
			screen.drawSprite(i,0, test2);
		}

	}


}



