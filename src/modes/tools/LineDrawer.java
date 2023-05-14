package modes.tools;

import utils.Dot;

import java.awt.*;

public class LineDrawer extends Tool {
    Dot startDot;
    Dot endDot;

    public LineDrawer(int size, Color color) {
        super(size, color);
    }

    public void startDraw(Dot pos) {
        startDot = pos;
    }

    public void update(Dot pos) {
        endDot = pos;
    }

    public void draw(Graphics g) {
        if (startDot == null || endDot == null) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        g.drawLine(startDot.getX(), startDot.getY(), endDot.getX(), endDot.getY());
    }
}
