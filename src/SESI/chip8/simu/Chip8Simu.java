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
	private FontSet _fonts ;
	private Sprite ERR1 = new Sprite(5) ;
	private Sprite ERR2 = new Sprite(5) ;
	
	// Delay and Sound Timers
	private int _DT ;
	private int _ST ;

	// Composants geres par une classe externe
	private Memory _mem ;
	private Chip8Screen _screen ;
	private Chip8Input _kb ;
	
	// Registres du CHIP8
	private short _PC ;			// Program Counter
	private short _SP ;			// Stack Pointer
	private short[] _V ;		// Generic Purpose Registers
	private short[] _stack ;	// Stack Memory
	private short _I ;			// Adress Register


	public Chip8Simu(Chip8Screen screen, Chip8Input kb)
	{
		// Tests
		_alive=0;
		_alive2=0;
		_fonts = new FontSet() ;
		ERR1.setMatrix(_fonts.chip8_fontset[0x12]);
		ERR2.setMatrix(_fonts.chip8_fontset[0x13]);
		
		// Initialisation des variables CHIP8
		_DT = 0 ;
		_ST = 0 ;
		_mem = new Memory() ;
		_screen = screen ;
		_kb = kb ;

		_PC = 0x200 ;
		_V = new short[0x10] ;
		_stack = new short[0x10] ;		// 16 niveaux de pile
	}
	
	/* Un appel a cette fonction execute une instruction du programme chip8 */
	public void step()
	{
		short op ;
		Sprite tmp ;
		
		op = 0 ;

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

	/* Le chip8 contient 2 timers internes mis a jours a une frequence de 60Hz, cette fonction met a jour les timers */
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



