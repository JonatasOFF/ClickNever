package Models;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    private Robot r;

    {
        try {
            r = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private int x;
    private int y;
    private int clickers;
    private int ordem;


    public void executarClickerMouse(int Velocidade) {
        r.mouseMove(getX(),getY());
        for(int i = 0; i < clickers; i++) {
            r.delay(Velocidade);
            r.mousePress(InputEvent.BUTTON1_MASK);
            r.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "x=" + x +
                ", y=" + y +
                ", clickers=" + clickers +
                ", ordem=" + ordem +
                '}';
    }

    public Mouse(int x, int y, int clickers, int ordem) {
        this.x = x;
        this.y = y;
        this.clickers = clickers;
        this.ordem = ordem;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getClickers() {
        return clickers;
    }

    public void setClickers(int clickers) {
        this.clickers = clickers;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

}
