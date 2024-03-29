package com.baynine;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Speedo {
	private final static int VERTS = 3;
	private final static int FRAMECOUNT = 50;

	private FloatBuffer mFVertexBuffer;
	private ShortBuffer mIndexBuffer;

	private float mFrameRate;
	private long[] lastTenFrames;
	private int currentFrame = 0;

	public Speedo() {
		lastTenFrames = new long[FRAMECOUNT];
	    float[] coords = {
	    		-0.05f, 0.0f, 3,
	    		0.05f, 0.0f, 3,
	    		0.0f, .40f, 3
	    };
	
	    short[] icoords = {0,1,2};

	    mFVertexBuffer = FloatBuffer.wrap(coords);
	    mIndexBuffer = ShortBuffer.wrap(icoords);
	}

	public void draw(GL10 gl) {
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glPushMatrix();
        gl.glColor4f(1f, 1f, 1f, 1f);
//		gl.glTranslatef(-2.0f, 0f, 3.0f);
		gl.glRotatef((-1.5f*mFrameRate)+90f, 0f, 0f, 1f);
	    gl.glFrontFace(GL10.GL_CCW);
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
	    gl.glDrawElements(GL10.GL_TRIANGLES, VERTS,
	            GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
		gl.glPopMatrix();
	}

	public void recordRate(long mseconds) {
		float totalRate = 0.0f;
		lastTenFrames[currentFrame] = mseconds;
		currentFrame++;
		if (currentFrame == FRAMECOUNT) {
			Log.e("Framerate", Float.toString(mFrameRate));
			currentFrame = 0;
			for (int i = 0; i < FRAMECOUNT; i++) {
				totalRate = totalRate + lastTenFrames[i];
			}
			mFrameRate = 1000f / (totalRate / FRAMECOUNT);
		}
	}

	public float getRate()
	{
		return mFrameRate;
	}

}
