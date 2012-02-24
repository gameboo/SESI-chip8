package SESI.chip8.utils.screens;

import SESI.chip8.utils.*;

import android.view.View;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log; //debug purpose

public class BasicScreen extends View implements Chip8Screen
{
    
    /////////////
    // members //
    /////////////

    private final int _nbPixelWidth = 64;
    private final int _nbPixelHeight = 32;
    private int _colorMatrix[][];

    private float _pixelWidth;
    private float _pixelHeight;

    //////////////////
    // constructeur //
    //////////////////
    
    public BasicScreen (Context context)
    {
        super(context);
        // init pixels
        _colorMatrix = new int[_nbPixelWidth][_nbPixelHeight];
        for(int y = 0; y < _nbPixelHeight; y++)
       	{
       	    for(int x = 0;  x < _nbPixelWidth; x++)
       	    {
       	        _colorMatrix[x][y] = Color.BLACK;
       	    }
       	}
    }

    ////////////
    //        //
    //  View  //
    //        //
    ////////////

    protected void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        _pixelWidth = getMeasuredWidth()/_nbPixelWidth;
        _pixelHeight = getMeasuredHeight()/_nbPixelHeight;
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","size changed");
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","getMeasuredWidth():"+getMeasuredWidth());
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_nbPixelWidth:"+_nbPixelWidth);
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_pixelWidth:"+_pixelWidth);
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","getMeasuredHeight():"+getMeasuredHeight());
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_nbPixelHeight:"+_nbPixelHeight);
        Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_pixelHeight:"+_pixelHeight);
    } 
    
    protected void onDraw(Canvas c)
    {
        super.onDraw(c);
        float left, top, right, bottom;
        Paint paint = new Paint();
        for(int y = 0; y < _nbPixelHeight; y++)
       	{
       	    top = y*_pixelHeight;
       	    bottom = (y+1)*_pixelHeight;
       	    for(int x = 0;  x < _nbPixelWidth; x++)
       	    {
       	        left = x*_pixelWidth;
                right = (x+1)*_pixelWidth;
                paint.setColor(_colorMatrix[x][y]);
                //paint.setColor(Color.rgb(x,y,x*y));
       	        c.drawRect(left, top, right, bottom, paint);
       	        //c.drawRect(left, top, (10*x)+10, (10*y)+10, paint);
                // tests
                /*
                if(x == 0 && y == 0) _colorMatrix[x][y] = Color.RED;
                if(x == _nbPixelWidth-1 && y == 0) _colorMatrix[x][y] = Color.RED;
                if(x == 0 && y == _nbPixelHeight-1) _colorMatrix[x][y] = Color.RED;
                if(x == _nbPixelWidth-1 && y == _nbPixelHeight-1) _colorMatrix[x][y] = Color.RED;
                */
       	    }
       	}
    }


    /////////////////////////////
    //                         //
    //  Interface Chip8Screen  //
    //                         //
    /////////////////////////////

    public void clear()
    {
        for(int y = 0; y < _nbPixelHeight; y++)
       	{
       	    for(int x = 0;  x < _nbPixelWidth; x++)
       	    {
       	        _colorMatrix[x][y] = Color.BLACK;
       	    }
       	}
        invalidate();
    }

    public void drawSprite(int x, int y, Sprite sprite)
    {
        if(x >= 0 && x < _nbPixelWidth && y >= 0 && y < _nbPixelHeight)
        {
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","now drawing sprite");
            byte[] spriteMatrix = sprite.getMatrix();
	    for(int j = y; j < (y+spriteMatrix.length); j++) // for each line (of the sprite)
	    {
                if(j >= _nbPixelHeight) break; // overflow par le bas
                byte currentSpriteLine = spriteMatrix[j-y]; // récupérer la ligne du sprite
                for(int i = x; i < x+8; i++) // for each column (of the sprite)
                {
                    Log.v("DEBUG_SESIchip8_BasicScreen >>> ","pixel("+i+","+j+") ==> in sprite ("+(i-x)+","+(j-y)+")");
                    if(i >= _nbPixelWidth) break; // overflow par la droite
                    if((currentSpriteLine & (1 << (7-(i-x)))) == 0) // si msb = leftmost pixel
                        _colorMatrix[i][j] = Color.BLACK;
                    else
                        _colorMatrix[i][j] = Color.WHITE;
                }
            }
        }
        else
        {
            //out of bounds
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","out of bounds");
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","x = "+x);
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","y = "+y);
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_nbPixelWidth = "+_nbPixelWidth);
            Log.v("DEBUG_SESIchip8_BasicScreen >>> ","_nbPixelHeight = "+_nbPixelHeight);
        }
        invalidate();
    }
}
