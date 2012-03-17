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
	
	/* Un appel a cette fonction execute une instruction du programme chip8

	*/
	public void step()
	{
		if (_chip8time == 0) {
			_screen.drawSprite(0,0, ERR1);
			_chip8time = 1 ;
		} else {
			_screen.drawSprite(0,0, ERR2);
			_chip8time = 0 ;
		}
	}

	/* Le chip8 contient 2 timers internes mis a jours a une frequence de 60Hz, cette fonction met a jour les timers
	
	*/
	public void updtTimers()
	{
		if (_chip8time == 0) {
			_screen.drawSprite(8,8, ERR1);
			_chip8time = 1 ;
		} else {
			_screen.drawSprite(8,8, ERR2);
			_chip8time = 0 ;
		}
	}


}



