package modes.tools;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Eraser extends Tool {
    List<Dot> dots;
    boolean showBounds = false;

    public Eraser(int size) {
        super(size, Color.WHITE);

        dots = new LinkedList<>();
        this.size = size;
    }

    public void startErase(Dot pos) {
        dots.clear();
        dots.add(pos);
    }

    public void update(Dot pos) {
        dots.add(pos);
    }

    public void erase(Graphics g) {
        if (dots.isEmpty()) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        var iterator = dots.iterator();
        Dot prevDot = iterator.next();
        while (iterator.hasNext()){
            Dot currentDot = iterator.next();
            g.setColor(Color.WHITE);
            g.drawLine(prevDot.getX(), prevDot.getY(), currentDot.getX(), currentDot.getY());

            if (showBounds && !iterator.hasNext()) {
                drawBounds(g, currentDot);
            }

            prevDot = currentDot;
        }
    }

    public void drawBounds(Graphics g, Dot dot) {
        g.setColor(Color.BLACK);
        g.fillOval(dot.getX() - size / 2, dot.getY() - size / 2, size, size);

        g.setColor(Color.WHITE);
        g.fillOval(dot.getX()  - size / 2 + 1, dot.getY() - size / 2 + 1, size - 2, size - 2);
    }

    public void setShowBounds(boolean showBounds) {
        this.showBounds = showBounds;
    }
}
