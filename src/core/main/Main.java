package core.main;

import core.utils.Constants;
import core.utils.KeyInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    boolean running = false;
    public int showFrames = 0;

    private Thread thread;
    private KeyInput keyInput;
    private Handler handler;

    public Main() {
        handler = new Handler();
        keyInput = new KeyInput(handler);

        this.addKeyListener(keyInput);


        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int newX = j * (Constants.WIDTH/8);
                int newY = i * (Constants.HEIGHT/8);
                Space newSpace = new Space(newX, newY, j + (i*10), handler, this);
                this.addMouseListener(newSpace);
                handler.addObject(newSpace);
            }
        }

        new Window(Constants.WIDTH, Constants.HEIGHT, "Queens", this);
    }

    synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderLines(Graphics g) {
        g.setColor(Color.WHITE);

        final int eigth_width = Constants.WIDTH/8;
        final int eigth_height = Constants.HEIGHT/8;
        for (int i = 0; i < 8; i++) {
            g.drawLine(eigth_width * (i+1), 0, eigth_width * (i+1), Constants.HEIGHT);
            g.drawLine(0, eigth_height * (i+1), Constants.WIDTH, eigth_height * (i+1));
        }

    }

    public Space getSpacebyId(int id) {
        for(int i = 0; i < handler.getObjects().size(); i++) {
            if(handler.getObjects().get(i).getID() == id) {
                    return (Space) handler.getObjects().get(i);
            }
        }

        // no matches
        return null;
    }

    public void tick() {
        handler.tick();
        keyInput.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor((Color.BLACK));
        g.fillRect(0,0,Constants.WIDTH, Constants.HEIGHT);

        //renderLines(g);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                showFrames = frames;
                frames = 0;
            }
        }
        stop();
    }

    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }


    public static void main(String[] args) {
        new Main();
    }

}
