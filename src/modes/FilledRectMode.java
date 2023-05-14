package modes;

import modes.tools.FilledRectDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FilledRectMode extends Mode {

    FilledRectDrawer filledRectDrawer;

    public FilledRectMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = filledRectDrawer = new FilledRectDrawer(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            boolean dragging;

            @Override
            public void mousePressed(MouseEvent e) {
                filledRectDrawer.startDraw(new Dot(e.getX(), e.getY()));
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        filledRectDrawer.draw(g);
                    }
                });
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            filledRectDrawer.update(new Dot(e.getX(), e.getY()));
                            filledRectDrawer.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}

