package SESI.chip8.simu ;

import SESI.chip8.utils.Chip8Input ;
import SESI.chip8.utils.Chip8Screen ;
import SESI.chip8.utils.Sprite ;
import java.util.TimerTask ;

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
	private Sprite OK = new Sprite(5) ;
	private Sprite ERR1 = new Sprite(5) ;
	private Sprite ERR2 = new Sprite(5) ;
	private Sprite ERR3 = new Sprite(5) ;
	private int _chip8time = 0 ;
	private Chip8Screen _screen ;

	public Chip8Simu(Chip8Screen screen, Chip8Input kb)
	{

		OK.setMatrix(chip8_fontset[1]);
		ERR1.setMatrix(chip8_fontset[10]);
		ERR2.setMatrix(chip8_fontset[11]);
		ERR3.setMatrix(chip8_fontset[12]);

		_chip8time = 0 ;
		_screen = screen ;
	}
	
/*		while (i<64)
		{
			i++ ;
			try
			{
				//	Thread.currentThread().sleep(1000) ;
				wait(300) ;
			}
			catch (IllegalArgumentException e){ screen.drawSprite(0,5, ERR1);}
			catch (IllegalMonitorStateException e){ screen.drawSprite(0,10, ERR2);}
			catch (InterruptedException e){ screen.drawSprite(0,15, ERR3);}
			screen.drawSprite(0,0, OK);
		
		}

*/

	public void step1()
	{
		if (_chip8time == 0) {
			_screen.drawSprite(3,3, ERR1);
			_chip8time = 1 ;
		} else {
			_screen.drawSprite(4,4, ERR2);
			_chip8time = 0 ;
		}
	}
		
	public void step2()
	{
		_screen.drawSprite(0,5, ERR2);
		_chip8time++;
	}


}



