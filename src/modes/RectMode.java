package modes;

import modes.tools.RectDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RectMode extends Mode {

    RectDrawer rectDrawer;

    public RectMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = rectDrawer = new RectDrawer(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            boolean dragging;

            @Override
            public void mousePressed(MouseEvent e) {
                rectDrawer.startDraw(new Dot(e.getX(), e.getY()));
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        rectDrawer.draw(g);
                    }
                });
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            rectDrawer.update(new Dot(e.getX(), e.getY()));
                            rectDrawer.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
