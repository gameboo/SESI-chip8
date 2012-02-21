package SESI.chip8.utils;

import android.util.Log; //debug purpose

public class Sprite
{
    private byte[] _matrix;

    public Sprite(int N)
    {
        if(N <= 0 || N > 16 )
        {
            // out of bounds => DIE
            Log.v("DEBUG_SESIchip8_Sprite >>> ","invalid sprite size");
        }
        else
        {
            _matrix = new byte[N];
        }
    }
    
    public Sprite(byte[] tab)
    {
        if(tab.length <= 0 || tab.length > 16 )
        {
            // out of bounds => DIE
            Log.v("DEBUG_SESIchip8_Sprite >>> ","invalid sprite size");
        }
        else
        {
            _matrix = tab.clone();
        }
    }

    public void setMatrix(byte[] newMatrix)
    {
        if(newMatrix == null)
        {
            for(int i = 0; i < _matrix.length; i++)
                _matrix[i]=0;
        }
        else if(newMatrix.length > 0 && newMatrix.length <= 16)
        {
                _matrix = newMatrix.clone();
        }
        else
        {
            // DIE
            Log.v("DEBUG_SESIchip8_Sprite >>> ","invalid sprite size");
        }
    }

    public byte[] getMatrix()
    {
        return _matrix.clone();
    }
}
