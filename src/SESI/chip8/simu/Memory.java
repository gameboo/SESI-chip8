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

    public void clear() {
        for (int i = 0x200; i < 0xFFF; i++) {
            _memory[i] = 0x0;
        }
    }
}
