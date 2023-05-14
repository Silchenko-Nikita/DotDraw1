package modes.tools;

import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorPicker extends Tool {

    public ColorPicker(int size, Color color) {
        super(size, color);
    }

    public void pick(Graphics g, CanvasPanel canvas, Dot pos) {
        BufferedImage image = new BufferedImage(canvas.getWidth(),
                canvas.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        canvas.printAll(g2d);
        g2d.dispose();

        int[][] bitmap = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                bitmap[x][y] = image.getRGB(x, y);
            }
        }

        canvas.setCurrentColorFromPicker(new Color(bitmap[pos.getX()][pos.getY()]));
    }
}
