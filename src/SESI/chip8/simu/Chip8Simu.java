package SESI.chip8.simu ;

import SESI.chip8.utils.Chip8Input ;
import SESI.chip8.utils.Chip8Screen ;
import SESI.chip8.utils.Sprite ;
import java.util.TimerTask ;
import java.util.Random ;
import java.io.InputStream ;
import android.util.Log ;

public class Chip8Simu
{
	// Pour Test, a supprimer ensuite
	private int _alive ;
	private int _alive2 ;
	private FontSet _fonts ;
	private Sprite ERR1 = new Sprite(5) ;
	private Sprite ERR2 = new Sprite(5) ;
	private int _time ;

	// Delay and Sound Timers
	private int _DT ;
	private int _ST ;

	// Composants geres par une classe externe
	private Memory _mem ;
	private Chip8Screen _screen ;
	private Chip8Input _kb ;
	private Random _rand ;
	
	// Registres du CHIP8
	private int _PC ;			// Program Counter
	private short _SP ;			// Stack Pointer
	private short[] _V ;		// Generic Purpose Registers
	private int[] _stack ;	// Stack Memory
	private int _I ;			// Adress Register
	
	// Variables utiles
	private Sprite _sprite ;

	public Chip8Simu(Chip8Screen screen, Chip8Input kb,InputStream file)
	{
		Log.v("@GameActivity","SIMU Constructeur commence !") ;
		int tmp,i ;
		// Tests
		_alive=0;
		_alive2=0;
		_fonts = new FontSet() ;
		ERR1.setMatrix(_fonts.chip8_fontset[0x12]);
		ERR2.setMatrix(_fonts.chip8_fontset[0x13]);
		_time = 0 ;
		
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
		_rand = new Random() ;

		// On copie le programme dans la memoire a partir de l'adresse 0x200
		show_life(8,0,ERR1);
		try {tmp = file.read() ;}
		catch (Exception e){tmp=-1;}
		i = 0x200 ;
		while (tmp != -1) {
			Log.v("@GameActivity","SIMU PROGRAMME ECRITURE "+Integer.toHexString(i)+" DE "+Integer.toHexString(tmp)) ;
			_mem.writeAt(i,(short) tmp) ;
			i++ ;
			try {tmp = file.read() ;}
			catch (Exception e){tmp=-1;}
		}		
		Log.v("@GameActivity","SIMU Constructeur fini !") ;
	}
	
	/* Un appel a cette fonction execute une instruction du programme chip8 */
	public void step()
	{
		int instruction ;
		int op,nnn,nn,x,y,z,kk ;
		int tmp ;
		byte[] spritebuff ;
		
		Sprite stmp ;
		if (_alive == 0) {
			_alive = 1 ;
			stmp = ERR1 ;
		} else {
			_alive = 0 ;
			stmp = ERR2 ;
		}
		show_life(0,0,stmp);
		
		instruction = (_mem.readFrom(_PC) << 8) + _mem.readFrom(_PC+1) ;

		op  =(instruction & 0xF000) >> 12 ;
		nnn = instruction & 0x0FFF;
		nn  = instruction & 0x00FF;
		// x prends parfois des valeurs etranges avec cette ligne :
		// x   = instruction & 0x0F00 >> 8;
		// y   = instruction & 0x00F0 >> 4;
		x   = (instruction >> 8) & 0xF;
		y   = (instruction >> 4) & 0xF;
		z   = instruction & 0x000F;
		kk  = instruction & 0x00FF;
		
		
		Log.v("@GameActivity","SIMU STEP "+_time+" PC="+Integer.toHexString(_PC)+", INSTRUCTION="+Integer.toHexString(instruction)) ;
		// Passage a l'instruction suivante
		_PC = _PC + 2 ;
		_time++ ;

		switch (op)
		{
			case 0 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 0 CLS") ;
						if (nnn == 0x0E0) {_screen.clear() ;}	// Clear Screen	}
						else if (nnn == 0x0EE)					// Return from subroutine
						{
							if (_SP > 0)
							{
								_PC = _stack[_SP] ;
								_SP-- ;
							}
						}
						// else : obsolete routine, just ignore
						break ;
			case 1 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 1 JUMP "+Integer.toHexString(nnn));
						_PC = nnn ;		// JUMP
						break ;
			case 2 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 2 CALL "+nnn) ;
						if (_SP < 0x10)	// CALL
						{
							_SP++;
							_stack[_SP] = _PC ;
							_PC = nnn ;
						}
						break ;
			case 3 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 3 SKIP ON EQUAL X="+x+", V[x]="+_V[x]+",kk="+kk) ;
						if (_V[x] == kk) { _PC = ((_PC + 2) & 0xFFFF) ; } // SKIP ON EQUAL
						break ;
			case 4 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 4 SKIP ON DIFFERENT") ;
						if (_V[x] != kk) { _PC = (_PC + 2) & 0x0FFFF; } // SKIP ON DIFFERENT
						break ;
			case 5 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 5 SKIP ON EQUAL REGISTERS") ;
						if (_V[x] == _V[y]) { _PC = (_PC + 2) & 0xFFFF ; } // SKIP ON EQUAL REGISTERS
						break ;
			case 6 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 6 LD IMM V["+x+"]=0x"+Integer.toHexString(kk));
						_V[x] = (short) kk ;	// LD Imm
						break ;
			case 7 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 7 ADD IMM");
						_V[x] = (short)((_V[x] + kk) & 0xFF) ; // ADD
						break ;
			case 8 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 8 OPERATORS") ;
						switch (z)
						{
							case 0 :	_V[x] = _V[y] ;	// LD Reg
										break ;
							case 1 :	_V[x] = (short) ((_V[x] | _V[y]) & 0XFF) ;	// OR Reg
										break ;
							case 2 :	_V[x] = (short) ((_V[x] & _V[y]) & 0XFF) ;	// AND Reg
										break ;
							case 3 :	_V[x] = (short) ((_V[x] ^ _V[y]) & 0XFF) ;	// XOR Reg
										break ;
							case 4 :	tmp = _V[x] + _V[y] ;	//AND Reg with CARRY
										_V[x] = (short)(tmp & 0xFF);
										if (tmp > 0xFF) {_V[0xF]=1;}
										else {_V[0xF] = 0;}
										break ;
							case 5 :	if (_V[y] >_V[x]){_V[0xF]=0;} // SUB Reg
										else {_V[0xF] = 1;}
										_V[x] = (short)((_V[x] - _V[y])&0xFF);
										break ;
							case 6 :	_V[0xF] = (short)(_V[x] & 0x1) ;	// LD Reg
										_V[x] = (short) ((_V[x]>>1) & 0x7F);
										break ;
							case 7 :	if (_V[x] >_V[y]){_V[0xF]=0;} // SUB Reg
										else {_V[0xF] = 1;}
										_V[x] = (short)((_V[y] - _V[x])&0xFF);
										break ;
							default:	// Probleme !!!!
										break ;
						}
			case 9 :	Log.v("@GameActivity","SIMU STEP INSTRUCTION 9 SKIP ON DIFFERENT REGISTERS") ;
						if (_V[x] != _V[y]) { _PC = (_PC + 2) & 0xFFFF ; } // SKIP ON DIFFERENT REGISTERS
						break ;
			case 0xA :	Log.v("@GameActivity","SIMU STEP INSTRUCTION A LD DANS I "+Integer.toHexString(nnn)) ;
						_I = nnn ;		// LD dans I
						break ;
			case 0xB :	Log.v("@GameActivity","SIMU STEP INSTRUCTION B BRANCH") ;
						_PC = (short) ((nnn + _V[0])&0xFFFF) ; // BRANCH
						break ;
			case 0xC :	Log.v("@GameActivity","SIMU STEP INSTRUCTION C RANDOM") ;
						tmp = _rand.nextInt() ;		// RANDOM
						_V[x] = (short)((tmp & kk) & 0xFF) ;
						break ;
			case 0xD :	Log.v("@GameActivity","SIMU STEP INSTRUCTION D DRAW SPRITE") ;
						spritebuff = new byte[z] ;	// DRAW SPRITE
						for (tmp=0;tmp<z;tmp++)
						{
							spritebuff[tmp] = (byte) (_mem.readFrom(_I+tmp) & 0xFF) ;
						}
						_sprite = new Sprite(spritebuff) ;
						if( _screen.drawSprite(_V[x],_V[y],_sprite)) {_V[0xF]=1;}
						else  {_V[0xF]=0;}
						break ;
			case 0xE :	Log.v("@GameActivity","SIMU STEP INSTRUCTION E SKIP IF KEY PRESSED OR NOT") ;
if (z == 0xE)			// SKIP if KEY PRESSED OR NOT
						{
							if (_kb.isButtonPressed(_V[x])) { _PC = (_PC + 2) & 0xFFFF ;}
						} else if (z == 0xE)
						{
							if (false == _kb.isButtonPressed(_V[x])) { _PC = (_PC + 2) & 0xFFFF ;}
						}
						break ;
			case 0xF :	Log.v("@GameActivity","SIMU STEP INSTRUCTION F VARIOUS OPERATORS") ;
switch(nn)				// Plusieurs operateurs
						{
							case 0x07:	Log.v("@GameActivity","SIMU STEP INSTRUCTION F GET DELAY TIMER VALUE X="+x+", DT="+_DT) ;
										_V[x] = (short) (_DT&0xFF) ; break ; // GET DELAY TIMER VALUE
							case 0x0A: tmp = _kb.isSomeButtonPressed();		// WAIT KEY
										if (tmp == -1) {_PC=_PC-2 ;} // On BLOQUE
										else { _V[x] = (short)(tmp & 0xFF); }
							case 0x15:	Log.v("@GameActivity","SIMU STEP INSTRUCTION F SET DELAY TIMER VALUE X="+x+", DT="+_DT+", V[x]="+_V[x]) ;
										_DT = _V[x] ; break ;	// SET DT
							case 0x18: _ST = _V[x] ; break ;	// SET ST
							case 0x1E: _I = (_I+_V[x]) & 0xFFFF; break ; // I = I + V[x]
							case 0x29: _I = (_V[x] * 5) & 0xFFFF ; break ;// I=Digit de VX
							case 0x33:	_mem.writeAt(_I,(short)((_V[x] / 100) & 0xFF)) ;
										_mem.writeAt(_I+1,(short)(((_V[x]%100)/ 10) & 0xFF)) ;
										_mem.writeAt(_I+2,(short)((_V[x]%10) & 0xFF)) ;
										break ;									
							case 0x55: for (tmp=0;tmp<0x10;tmp++)
										{
											_mem.writeAt((_I+tmp)&0xFFFF,(short)_V[tmp]) ;
										}
										break ;
							case 0x65:	for (tmp=0;tmp<0x10;tmp++)
										{
											_V[tmp] = (short)(_mem.readFrom((_I+tmp)&0xFFFF)&0xFF) ;
										}
										break ;
							default:break ; // Instruction non geree
						};
						break ;


			default:
						break; // Probleme si on arrive dans ce cas
					
		}

		


	}

	/* Le chip8 contient 2 timers internes mis a jours a une frequence de 60Hz, cette fonction met a jour les timers */
	public void updtTimers()
	{
		Sprite tmp ;
		if (_DT > 0) {_DT--;}
		if (_ST > 0) {_ST--;}

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
			//_screen.drawSprite(x,y,s);
	}


}



