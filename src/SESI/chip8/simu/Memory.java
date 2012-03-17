/*
This file is part of JChip8BR.

JChip8BR is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JChip8BR is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JChip8BR.  If not, see <http://www.gnu.org/licenses/>.
 */
package SESI.chip8.simu;

/**
 * @author dreampeppers99
 */
public class Memory {

    private short[] _memory = new short[0x1000];

    public Memory() {
    }

    public short readFrom(int add) {
        if ((add > 0xFFF ) || (add < 0)) {
			// Gerer les erreurs
			return 0 ;
        }
        return _memory[add];
    }

    public void writeAt(int add, short value) {
        if ((add > 0xFFF ) || (add < 0)) {
        }
        _memory[add] = value;
    }

	public void init() {
        //0 SPRITE
        _memory[0x000] = 0xF0;
        _memory[0x001] = 0x90;
        _memory[0x002] = 0x90;
        _memory[0x003] = 0x90;
        _memory[0x004] = 0xF0;
        //1 SPRITE
        _memory[0x005] = 0x20;
        _memory[0x006] = 0x60;
        _memory[0x007] = 0x20;
        _memory[0x008] = 0x20;
        _memory[0x009] = 0x70;
        //2 SPRITE add: 0x00A
        _memory[0x00A] = 0xF0;
        _memory[0x00B] = 0x10;
        _memory[0x00C] = 0xF0;
        _memory[0x00D] = 0x80;
        _memory[0x00E] = 0xF0;
        //3 SPRITE add: 0x00F
        _memory[0x00F] = 0xF0;
        _memory[0x010] = 0x10;
        _memory[0x011] = 0xF0;
        _memory[0x012] = 0x10;
        _memory[0x013] = 0xF0;
        //4 SPRITE add: 0x014
        _memory[0x014] = 0x90;
        _memory[0x015] = 0x90;
        _memory[0x016] = 0xF0;
        _memory[0x017] = 0x10;
        _memory[0x018] = 0x10;
        //5 SPRITE add: 0x019
        _memory[0x019] = 0xF0;
        _memory[0x01A] = 0x80;
        _memory[0x01B] = 0xF0;
        _memory[0x01C] = 0x10;
        _memory[0x01D] = 0xF0;
        //6 SPRITE add: 0x01E
        _memory[0x01E] = 0xF0;
        _memory[0x01F] = 0x80;
        _memory[0x020] = 0xF0;
        _memory[0x021] = 0x90;
        _memory[0x022] = 0xF0;
        //7 SPRITE add: 0x023
        _memory[0x023] = 0xF0;
        _memory[0x024] = 0x10;
        _memory[0x025] = 0x20;
        _memory[0x026] = 0x40;
        _memory[0x027] = 0x40;
        //8 SPRITE add: 0x028
        _memory[0x028] = 0xF0;
        _memory[0x029] = 0x90;
        _memory[0x02A] = 0xF0;
        _memory[0x02B] = 0x90;
        _memory[0x02C] = 0xF0;
        //9 SPRITE add: 0x02D
        _memory[0x02D] = 0xF0;
        _memory[0x02E] = 0x90;
        _memory[0x02F] = 0xF0;
        _memory[0x030] = 0x10;
        _memory[0x031] = 0xF0;
        //A SPRITE add: 0x032
        _memory[0x032] = 0xF0;
        _memory[0x033] = 0x90;
        _memory[0x034] = 0xF0;
        _memory[0x035] = 0x90;
        _memory[0x036] = 0x90;
        //B SPRITE add: 0x037
        _memory[0x037] = 0xE0;
        _memory[0x038] = 0x90;
        _memory[0x039] = 0xE0;
        _memory[0x03A] = 0x90;
        _memory[0x03B] = 0xE0;
        //C SPRITE add: 0x03C
        _memory[0x03C] = 0xF0;
        _memory[0x03D] = 0x80;
        _memory[0x03E] = 0x80;
        _memory[0x03F] = 0x80;
        _memory[0x040] = 0xF0;
        //D SPRITE add: 0x041
        _memory[0x041] = 0xE0;
        _memory[0x042] = 0x90;
        _memory[0x043] = 0x90;
        _memory[0x044] = 0x90;
        _memory[0x045] = 0xE0;
        //E SPRITE add: 0x046
        _memory[0x046] = 0xF0;
        _memory[0x047] = 0x80;
        _memory[0x048] = 0xF0;
        _memory[0x049] = 0x80;
        _memory[0x04A] = 0xF0;
        //F SPRITE add: 0x04B
        _memory[0x04B] = 0xF0;
        _memory[0x04C] = 0x80;
        _memory[0x04D] = 0xF0;
        _memory[0x04E] = 0x80;
        _memory[0x04F] = 0x80;

	}

    public void clear() {
        for (int i = 0x200; i < 0xFFF; i++) {
            _memory[i] = 0x0;
        }
    }
}
