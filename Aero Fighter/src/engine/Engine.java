package engine;

import interfaces.Init;
import interfaces.Runnable;
import interfaces.Tick;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Engine implements Runnable, Init , Tick , java.lang.Runnable {

    private String title;
    private int width;
    private int height;
    private Thread  thread;
    private boolean running;
    private BufferStrategy buffer;
    private Graphics g;
    private int y;
    private boolean start;
    private GameManager manager;


    private LoadImage loadImage;

    private Display display;
    public static final int gameWidth = 400;
    public static final int gameHeight = 400;

    public Engine(String title,int width,int height){
        this.loadImage = loadImage;
        this.title = title;
        this.width = width;
        this.height = height;

    }
    @Override
    public void init(){

        display = new Display(title,width,height);
        loadImage= new LoadImage();
        loadImage.init();
        manager = new GameManager();
        manager.init();
        start = true;
    }

    public synchronized void start(){
        if(running)
            return ;
        running = true;
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }

    }
    public synchronized void stop(){
        if(!(running))
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void tick(){
        manager.tick();
    }

    public void rendering(){

        buffer = display.getCanvas().getBufferStrategy();
        if(buffer == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = buffer.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //draw

        g.drawImage(LoadImage.image,50,50, gameWidth, gameHeight,null);


        manager.render(g);
        // menu

        //end of draw

        buffer.show();
        g.dispose();

    }

@Override
    public void run() {
        init();

        int fps = 50;
        double timePerTick = 1000000000/fps;
        double delta = 0;
        long current = System.nanoTime();


        while(running){

            delta = delta + (System.nanoTime()-current)/timePerTick;
            current = System.nanoTime();
            if(delta>=1){
                tick();
                rendering();
                delta--;
            }

        }
    }
}
