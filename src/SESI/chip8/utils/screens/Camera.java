package SESI.chip8.utils.screens;

import SESI.chip8.utils.*;
import SESI.chip8.screens.*;

import android.opengl.GLSurfaceView;
import android.content.Context;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

class Camera
{
    private float _x, _y, _z, _dx, _dy, _dz, _ux, _uy, _uz;
    private float _zoomFactor, _fieldOfView, _aspectRatio, _zNear, _zFar;

    public Camera (float x, float y, float z)
    {
        _x = x;
        _y = y;
        _z = z;
        
        _dx = 1.0f;
        _dy = 0.0f;
        _dz = 0.0f;
        
        _ux = 0.0f;
        _uy = 1.0f;
        _uz = 0.0f;
        
        _zoomFactor = 1.0f;
        _fieldOfView = 45.0f;
        _aspectRatio = 1.0f;
        _zNear = 0.5f;
        _zFar = 100.0f;
    }

    public void zoom(float zoomFactor)
    {
	_zoomFactor = zoomFactor;
    }
 
    public void refresh(GL10 gl)
    {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, _fieldOfView * _zoomFactor, _aspectRatio, _zNear, _zFar);
        
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, _x, _y, _z, _dx, _dy, _dz, _ux, _uy, _uz);
    }

    public void move(float vx, float vy, float vz)
    {
        _x += vx;
        _y += vy;
        _z += vz;
    }

    public void setDest(float dx, float dy, float dz)
    {
        _dx = dx;
        _dy = dy;
        _dz = dz;
    }
    
    public void lookAround(float dx, float dy, float dz)
    {
        _dx += dx;
        _dy += dy;
        _dz += dz;
    }

    public void setPos(float x, float y, float z)
    {
        _x = x;
        _y = y;
        _z = z;
    }
}
