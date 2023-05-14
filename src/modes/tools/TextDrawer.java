package modes.tools;

import utils.Dot;

import java.awt.*;

public class TextDrawer extends Tool {
    int fontSize;
    Dot startDot;
    String text;

    boolean isActive = false;

    boolean isFinished = false;

    public TextDrawer(int size, Color color, int fontSize) {
        super(size, color);

        this.fontSize = fontSize;

        text = "";
    }

    public void startDraw(Dot pos) {
        startDot = pos;
    }

    public void draw(Graphics g) {
        if (startDot == null) return;

        g.setColor(color);

        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont((float) (fontSize + size));
        g.setFont(newFont);

        ((Graphics2D) g).setStroke(new BasicStroke(size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawString(text, startDot.getX(), startDot.getY() + newFont.getSize());

        if (isActive) {
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    0, new float[]{2}, 0));

            FontMetrics fontMetrics = g.getFontMetrics(newFont);
            int stringWidth = fontMetrics.stringWidth(text);
            int stringHeight = fontMetrics.getHeight();
            g.drawRect(startDot.getX() - 2, startDot.getY(),
                    stringWidth + 4, stringHeight);
        }
    }


    public void setBeingActiveState(boolean val) {
        isActive = val;
    }

    public void update(char ch) {
        this.text += ch;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void finish() {
        text = "";
        isFinished = true;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
