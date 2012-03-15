package SESI.chip8.utils;

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
		if(b>=0 && b<=16)
		{
			button[b]=true;
		}
	}

	public void releaseButton(int b)
	{
		if(b>=0 && b<=16)
		{
			button[b]=false;
		}
	}
	
	public boolean getButtonState(int b)
	{
		if(b>=0 && b<=16)
		{
			return button[b];
		}
		return false;
	}
}
