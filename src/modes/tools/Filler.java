package modes.tools;

import modes.Mode;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Stack;

public class Filler extends Tool {
    int startColorRGB;
    LinkedList<Dot> dots;
    int[][] bitmap;

    public Filler(int size, Color color) {
        super(size, color);

        dots = new LinkedList<>();
    }


    private boolean isStopDot(int px, int py, int startColorRGB) {
        return px < 0 || px >= bitmap.length || py < 0 || py >= bitmap[0].length
                || startColorRGB != bitmap[px][py];
    }

    public void fill(Graphics g, Dot pos, int[][] bitmap) {
        this.bitmap = bitmap;

        if (pos == null) return;
        if (pos.getX() < 0 || pos.getX() >= bitmap.length ||
                pos.getY() < 0 || pos.getY() >= bitmap[0].length) return;
        int fillColorRGB = color.getRGB();

        if (startColorRGB == fillColorRGB) return;

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Stack<Dot> stack = new Stack<>();
        stack.push(new Dot(pos.getX(), pos.getY()));
        while (!stack.isEmpty()) {
            Dot p = stack.pop();
            dots.add(p);
            int px = p.getX();
            int py = p.getY();

            bitmap[px][py] = fillColorRGB;
            g.drawLine(px, py, px, py);

            if (!isStopDot(px - 1, py, startColorRGB)) stack.push(new Dot(px - 1, py));
            if (!isStopDot(px + 1, py, startColorRGB)) stack.push(new Dot(px + 1, py));
            if (!isStopDot(px, py - 1, startColorRGB)) stack.push(new Dot(px, py - 1));
            if (!isStopDot(px, py + 1, startColorRGB)) stack.push(new Dot(px, py + 1));

        }
    }
}
