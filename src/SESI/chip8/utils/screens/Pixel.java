package SESI.chip8.utils.screens;

import SESI.chip8.utils.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log; //debug purpose

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
		private boolean drawing;
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
				drawing = false;
		}

		public boolean getDrawingState()
		{
				return drawing;
		}

		public void setDrawingState(boolean d)
		{
				drawing = d;
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

				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

				//gl.glColor4f(1.0f, 0.5f, 0.4f, 0.9f);
				gl.glScalef(0.5f*_width, 0.5f*_height, 1.0f);
				gl.glColor4f(_red, _green, _blue, _alpha);
				// Front
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				// Right - Rotate 90 degree about y-axis
				gl.glRotatef(90.0f, _ux, _uy, _uz);
				gl.glColor4f(0.0f,0.0f,1.0f,1.0f);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				// Back - Rotate another 90 degree about y-axis
				gl.glRotatef(90.0f, _ux, _uy, _uz);
				gl.glColor4f(1.0f,1.0f,1.0f,1.0f);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				// Left - Rotate another 90 degree about y-axis
				gl.glRotatef(90.0f, _ux, _uy, _uz);
				gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);


				// Bottom - Rotate 90 degree about x-axis
				gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
				gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

				// Top - Rotate another 180 degree about x-axis
				gl.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
				gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);


				gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
				gl.glPopMatrix();
				/* 
				   Log.v("DEBUG_SESIchip8_GLScreen >>> ","_x = "+_x);
				   Log.v("DEBUG_SESIchip8_GLScreen >>> ","_y = "+_y);
				 */
		}
}
