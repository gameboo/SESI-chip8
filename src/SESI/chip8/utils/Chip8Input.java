package SESI.chip8.utils;

//debug
import android.util.Log;

public class Chip8Input
{
	private int nbButton = 16;
    private boolean [] button;
    
	public Chip8Input()
	{
		button = new boolean[nbButton];
		for(int i=0; i<nbButton; i++)
		{
			button[i] = false;
		}
	}
	
	public void pressButton(int b)
	{
		// debug
		Log.v("button pressed : ", Integer.toString(b));
		if(b>=0 && b<=16)
		{
			button[b]=true;
		}
	}

	public void releaseButton(int b)
	{
		// debug
		Log.v("button released : ", Integer.toString(b));
		if(b>=0 && b<=16)
		{
			button[b]=false;
		}
	}
	
	public boolean isButtonPressed(int b)
	{
		if(b>=0 && b<=16)
		{
			return button[b];
		}
		return false;
	}
	
	public int isSomeButtonPressed()
	{
		int i ;
		for (i=0;i<16;i++)
		{
			if (button[i]) return i ;
		}
		return -1 ;
	}
}
