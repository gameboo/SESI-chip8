package SESI.chip8.utils;

public interface Chip8Screen
{
    public void clear();
    public boolean drawSprite(int x, int y, Sprite sprite);
}
