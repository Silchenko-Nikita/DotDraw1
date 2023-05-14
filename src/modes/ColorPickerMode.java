package modes;

import modes.tools.ColorPicker;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPickerMode extends Mode {
    ColorPicker colorPicker;

    public ColorPickerMode() {
        super(0, null);

        tool = colorPicker = new ColorPicker(0, null);
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
                        colorPicker.pick(canvasPanel.getGraphics(), canvasPanel, new Dot(e.getX(), e.getY()));
                    }
                });
                canvasPanel.repaint();
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
