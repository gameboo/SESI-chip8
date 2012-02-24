package SESI.chip8.utils.screens;

import SESI.chip8.utils.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

class Pixel
{
    // static stuff //

    private final static FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private final static float[] vertices =
    {  // Vertices for the front face
        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        -1.0f,  1.0f, 1.0f,  // 2. left-top-front
        1.0f,  1.0f, 1.0f   // 3. right-top-front
    };
    static  // static initialization block
    {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    // instance members //
    
    private float _x, _y, _z;
    private float _ux, _uy, _uz;
    private float _vx, _vy, _vz;
    private float _width, _height;
    private float _red, _green, _blue, _alpha;
    
    // Constructor - Set up the buffers
    public Pixel()
    {
        _x =0.0f; _y =0.0f; _z = 0.0f;
        _ux = 0.0f; _uy = 1.0f; _uz = 0.0f; // y-axis
        _vx = 0.0f; _vy = 0.0f; _vz = 1.0f; //z-axis
        _width = 1.0f; _height = 1.0f;
	_red = 0.0f; _green = 0.0f; _blue = 0.0f; _alpha = 0.0f;
    }

    public void setRGBA(float r, float g, float b, float a)
    {
	_red = r; _green = g; _blue = b; _alpha = a;
    }
    
    public void setWH(float w, float h)
    {
        _width = w;
        _height = h;
    }

    public void setPos(float x, float y, float z)
    {
        _x = x;
        _y = y;
        _z = z;
    }
    
    public void setUp(float x, float y, float z)
    {
        _ux = x;
        _uy = y;
        _uz = z;
    }
    
    public void setV(float x, float y, float z)
    {
        _vx = x;
        _vy = y;
        _vz = z;
    }
    
    // Draw the color cube
    public void draw(GL10 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(_x, _y, _z);
 
        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)
    
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
 
        gl.glColor4f(1.0f, 0.5f, 0.4f, 0.9f);
        gl.glScalef(_width, _height, 1.0f);

        gl.glColor4f(_red, _green, _blue, _alpha);
// test
float MatSpec[] = {_red,_green,_blue,1.0f};
    float MatDif[] = {_red,_green,_blue,1.0f};
    float MatAmb[] = {_red,_green,_blue,1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR, FloatBuffer.wrap(MatSpec));
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_DIFFUSE, FloatBuffer.wrap(MatDif));
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT, FloatBuffer.wrap(MatAmb));
// end test
        // Front
        //gl.glColor4f(colors[0][0], colors[0][1], colors[0][2], colors[0][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
/*        
        // Right - Rotate 90 degree about y-axis
        gl.glRotatef(90.0f, _ux, _uy, _uz);
         //gl.glColor4f(colors[1][0], colors[1][1], colors[1][2], colors[1][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
 
        // Back - Rotate another 90 degree about y-axis
        gl.glRotatef(90.0f, _ux, _uy, _uz);
         //gl.glColor4f(colors[2][0], colors[2][1], colors[2][2], colors[2][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
 
        // Left - Rotate another 90 degree about y-axis
        gl.glRotatef(90.0f, _ux, _uy, _uz);
        //gl.glColor4f(colors[3][0], colors[3][1], colors[3][2], colors[3][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
 
 /*  
        // Bottom - Rotate 90 degree about x-axis
        gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        gl.glColor4f(colors[4][0], colors[4][1], colors[4][2], colors[4][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        
        // Top - Rotate another 180 degree about x-axis
        gl.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        gl.glColor4f(colors[5][0], colors[5][1], colors[5][2], colors[5][3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
 */  
        gl.glPopMatrix();
 
    }
}
