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
	private int _PC ;			// Program Counter
	private short _SP ;			// Stack Pointer
	private short[] _V ;		// Generic Purpose Registers
	private int[] _stack ;	// Stack Memory
	private int _I ;			// Adress Register


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
		_mem.init() ;
		_screen = screen ;
		_kb = kb ;

		_PC = 0x200 ;
		_V = new short[0x10] ;
		_stack = new int[0x10] ;		// 16 niveaux de pile
	}
	
	/* Un appel a cette fonction execute une instruction du programme chip8 */
	public void step()
	{
		int instruction ;
		int op ;
		int nnn ;
		int x,y ;
		short kk ;

		Sprite tmp ;
		
		instruction = 0 ;

		op  =(instruction & 0xF000) >> 12 ;
		nnn = instruction & 0x0FFF;
		x   = instruction & 0x0F00;
		y   = instruction & 0x00F0;
		kk  = (short) (instruction & 0x00FF);
		switch (op)
		{
			case 0 :	if (nnn == 0x0E0) {_screen.clear() ;}	// Clear Screen	}
						else if (nnn == 0x0EE)					// Return from subroutine
						{
							if (_SP > 0)
							{
								_PC = _stack[_SP] ;
								_SP--;
							}
						}
						// else : obsolete routine, just ignore
						break ;
			case 1 :	_PC = nnn ;		// JUMP
						break ;
			case 2 :	if (_SP < 0x10)	// CALL
						{
							_SP++;
							_stack[_SP] = _PC ;
							_PC = nnn ;
						}
						break ;
			case 3 :	if (_V[x] == kk) { _PC = (_PC + 2) % 0x10000 ; } // SKIP ON EQUAL
						break ;
			case 4 :	if (_V[x] != kk) { _PC = (_PC + 2) % 0x10000 ; } // SKIP ON DIFFERENT
						break ;
			case 5 :	if (_V[x] == _V[y]) { _PC = (_PC + 2) % 0x10000 ; } // SKIP ON EQUAL REGISTERS
						break ;
			case 6 :	_V[x] = kk ;	// LD Imm
						break ;
			case 7 :	_V[x] = (short)((_V[x] + kk) & 0xFF) ; // ADD
						break ;
			case 8 :	_V[x] = _V[y] ;	// LD Reg
						break ;
			case 9 :	_V[x] = (short) (_V[x] | _V[y]);	// OR
						break ;
			case 10:	_V[x] = (short) (_V[x] & _V[y]) ;	// OR
						break ;
			case 11:	_V[x] = kk ;	// LD
						break ;

			default:
						break; // Probleme si on arrive dans ce cas
					
		}

		


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



