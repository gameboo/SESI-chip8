package SESI.chip8.utils.screens;

import SESI.chip8.utils.*;

import android.opengl.GLSurfaceView;
import android.content.Context;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import android.opengl.GLU;

import android.util.Log; //debug purpose

public class GLScreen extends GLSurfaceView implements GLSurfaceView.Renderer, Chip8Screen
{
    
    /////////////
    // members //
    /////////////

    private final int _width = 64;
    private final int _height = 32;
    private final float _rendererWidth = 64.0f;
    private final float _rendererHeight = 32.0f;

    private float _pixelWidth;
    private float _pixelHeight;
    
    private Pixel[][] _pixel;
    private Camera _camera;


// tests                   
                    float oldx = 0.0f;
                    float oldy = 0.0f;
                    float curx = 0.0f;
                    float cury = 0.0f;
    private int pointerId = -1;
//
    
    //////////////////
    // constructeur //
    //////////////////
    
    public GLScreen (Context context)
    {
        super(context);
        setRenderer(this);
        // init pixels
        _pixelWidth = _rendererWidth/_width;
        _pixelHeight = _rendererHeight/_height;
	_pixel = new Pixel[_width][_height];
        float x,y,z;
        z = 0.0f;
        for(int j = 0; j < _height; j++) // for each line
        {
            y = (_rendererHeight/2)-(j*_pixelHeight);
            for(int i = 0; i < _width; i++) // for each column
            {
                x = -(_rendererWidth/2)+(i*_pixelWidth);
                _pixel[i][j] = new Pixel();
                _pixel[i][j].setPos(x,y,z);
                //_pixel[i][j].setRGBA(0.0f,0.0f,0.0f,0.0f);
                _pixel[i][j].setRGBA((i%2)*0.8f,(j%3)*0.2f,((i*j)%4)*0.5f,1.0f); // pour tester
                _pixel[i][j].setWH(1.0f, 1.0f); // pour tester
            }
        }
        // init camera
        _camera = new Camera(0.0f, 0.0f, 50.0f);
    }

// tests
    public boolean onTouchEvent(final MotionEvent event)
    {
        queueEvent(
            new Runnable()
            {
                public void run()
                {
                    //setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
                    //_pix.setRGBA(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f, 1.0f);
                    switch (event.getAction() & MotionEvent.ACTION_MASK)
                    {
                        //case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_POINTER_DOWN:
                           // multitouch!! - touch down
                           int count = event.getPointerCount(); // Number of 'fingers' in this time
                           curx = event.getX();
                           cury = event.getY();
                           oldx = curx;
                           oldy = cury;
                           pointerId = event.getPointerId(0);
                           break;
                        case MotionEvent.ACTION_MOVE:
                               curx = event.getX();
                               cury = event.getY();
                               _camera.lookAround(0.05f*(curx-oldx), -0.05f*(cury-oldy), 0.0f);
                           break;
                        case MotionEvent.ACTION_POINTER_UP:
                           // multitouch!! - touch down
                           break;
                    }
                    oldx = curx;
                    oldy = cury;
                }
            }
       );
       return true;
    }
//

    ////////////////////////////////////////
    //                                    //
    //  Interface GLSurfaceView.Renderer  //
    //                                    //
    ////////////////////////////////////////
    
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glEnable(GL10.GL_DEPTH_TEST);  // Active le test de profondeur
 	gl.glEnable(GL10.GL_LIGHTING);    // Active l'éclairage
 	gl.glEnable(GL10.GL_LIGHT0);

        gl.glClearColor(0, 0, 0, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl.glViewport(0, 0, width, height);

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
    }
    
    public void onDrawFrame(GL10 gl)
    {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        _camera.refresh(gl);
        for(int j = 0; j < _height; j++) // for each line
        {
            for(int i = 0; i < _width; i++) // for each column
            {
                _pixel[i][j].draw(gl);
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
        for(int j = 0; j < _height; j++) // for each line
        {
            for(int i = 0; i < _width; i++) // for each column
            {
                _pixel[i][j].setRGBA(0.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    public void drawSprite(int x, int y, Sprite sprite)
    {
        if(x >= 0 && x < _width && y >= 0 && y < _height)
        {
            byte[] spriteMatrix = sprite.getMatrix();
	    for(int j = y; j < (y+spriteMatrix.length); j++) // for each line (of the sprite)
	    {
                if(j >= _height) break; // overflow par le bas
                byte currentSpriteLine = spriteMatrix[j-y]; // récupérer la ligne du sprite
                for(int i = x; i < x+8; i++) // for each column (of the sprite)
                {
                    if(i >= _width) break; // overflow par la droite
                    if((currentSpriteLine & (1 << (7-(i-x)))) == 0) // si msb = leftmost pixel
                        _pixel[i][j].setRGBA(0.0f,0.0f,0.0f,1.0f);
                    else
                        _pixel[i][j].setRGBA(1.0f,1.0f,1.0f,1.0f);
                }
            }
        }
        else
        {
            //out of bounds
            Log.v("DEBUG_SESIchip8_GLScreen >>> ","out of bounds");
            Log.v("DEBUG_SESIchip8_GLScreen >>> ","x = "+x);
            Log.v("DEBUG_SESIchip8_GLScreen >>> ","y = "+y);
            Log.v("DEBUG_SESIchip8_GLScreen >>> ","_nbPixelWidth = "+_width);
            Log.v("DEBUG_SESIchip8_GLScreen >>> ","_nbPixelHeight = "+_height);
        }
    }
}
