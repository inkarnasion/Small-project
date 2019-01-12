package engine;

import interfaces.Render;
import interfaces.Tick;

import java.awt.*;

public class Bullet implements Tick, Render {

    private int x;
    private int y;
    private int speed;

    public Bullet(int x, int y)
    {
        this.x = x;
        this.y = y;
        speed = 10;

    }
    @Override
    public void tick()
    {
        y -= speed;
    }
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }
@Override
    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, 6, 10);
        g.setColor(Color.BLACK);
    }
}
