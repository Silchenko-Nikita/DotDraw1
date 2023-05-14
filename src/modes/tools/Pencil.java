package modes.tools;

import utils.Dot;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Pencil extends Tool{
    List<Dot> dots;

    public Pencil(int size, Color color) {
        super(size, color);
        dots = new LinkedList<>();
    }

    public void startDraw(Dot pos) {
        dots.clear();
        dots.add(pos);
    }

    public void update(Dot pos) {
        dots.add(pos);
    }

    public void draw(Graphics g) {
        if (dots.isEmpty()) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        var iterator = dots.iterator();
        Dot prevDot = iterator.next();
        while (iterator.hasNext()){
            Dot currentDot = iterator.next();
            g.drawLine(prevDot.getX(), prevDot.getY(), currentDot.getX(), currentDot.getY());
            prevDot = currentDot;
        }
    }
}
