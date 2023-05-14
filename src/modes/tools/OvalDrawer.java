package modes.tools;

import utils.Dot;

import java.awt.*;

public class OvalDrawer extends Tool {
    Dot startDot;
    Dot endDot;

    public OvalDrawer(int size, Color color) {
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
        ((Graphics2D) g).setStroke(new BasicStroke(size, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        int width = endDot.getX() - startDot.getX();
        int height = endDot.getY() - startDot.getY();

        g.drawOval(startDot.getX() + (width >= 0 ? 0 : width),
                   startDot.getY() + (height >= 0 ? 0 : height),
                   width >= 0 ? width : -width,
                   height >= 0 ? height : -height);
    }
}
