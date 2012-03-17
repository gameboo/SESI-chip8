package SESI.chip8.simu ;

import SESI.chip8.utils.Chip8Input ;
import SESI.chip8.utils.Chip8Screen ;
import SESI.chip8.utils.Sprite ;
import java.util.TimerTask ;

public class Chip8Simu
{
	// Pour Test, a supprimer ensuite
	private int _alive ;
	private int _alive2 ;
	
	// Delay and Sound Timers
	private int _DT ;
	private int _ST ;

	private Memory _mem ;



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
		{(byte)0xF0, (byte)0x80, (byte)0xF0, (byte)0x80, (byte)0x80}, // F
		{(byte)0x80},  // pixel gauche
		{(byte)0xFF},  // ligne pleine
		{(byte)0xC3, (byte)0x24, (byte)0x5A, (byte)0xFF,
		 (byte)0xFF, (byte)0x7E, (byte)0x81, (byte)0x66},		// Surprise 8x8
		{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
		 (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}		// Blank 8x8
	};
	private Sprite ERR1 = new Sprite(5) ;
	private Sprite ERR2 = new Sprite(5) ;
	private Chip8Screen _screen ;

	public Chip8Simu(Chip8Screen screen, Chip8Input kb)
	{
		// Tests
		_alive=0;
		_alive2=0;
		
		// Initialisation des variables CHIP8
		_DT = 0 ;
		_ST = 0 ;
		_mem = new Memory() ;

		ERR1.setMatrix(chip8_fontset[0x12]);
		ERR2.setMatrix(chip8_fontset[0x13]);

		_screen = screen ;
	}
	
	/* Un appel a cette fonction execute une instruction du programme chip8

	*/
	public void step()
	{
		Sprite tmp ;

		// Pour test uniquement
		if (_alive == 0) {
			_alive = 1 ;
			tmp = ERR1 ;
		} else {
			_alive = 0 ;
			tmp = ERR2 ;
		}
		
		show_life(0,0,tmp);
	}

	/* Le chip8 contient 2 timers internes mis a jours a une frequence de 60Hz, cette fonction met a jour les timers
	
	*/
	public void updtTimers()
	{
		Sprite tmp ;
		if (_DT > 0) {_DT--;}
		if (_ST > 0) {_DT--;}

		// Pour test uniquement
		if (_alive2 == 0) {
			_alive2 = 1 ;
			tmp = ERR1 ;
		} else {
			_alive2 = 0 ;
			tmp = ERR2 ;
		}
		
		show_life(8,8,tmp);
	}

	public void show_life(int x, int y,Sprite s)
	{
			_screen.drawSprite(x,y,s);
	}


}



