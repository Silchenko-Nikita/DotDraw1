package modes;

import modes.tools.Filler;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class FillMode extends Mode {
    Filler filler;

    public FillMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = filler = new Filler(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            private boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pressed) return;

                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        BufferedImage image = new BufferedImage(canvasPanel.getWidth(),
                                canvasPanel.getHeight(),
                                BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = image.createGraphics();
                        canvasPanel.printAll(g2d);
                        g2d.dispose();

                        int[][] bitmap = new int[image.getWidth()][image.getHeight()];
                        for (int x = 0; x < image.getWidth(); x++) {
                            for (int y = 0; y < image.getHeight(); y++) {
                                bitmap[x][y] = image.getRGB(x, y);
                            }
                        }

                        filler.fill(g, new Dot(e.getX(), e.getY()), bitmap);
                    }
                });
                canvasPanel.repaint();
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
