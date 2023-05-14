package modes;

import modes.tools.LineDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LineMode extends Mode {

    LineDrawer lineDrawer;

    public LineMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = lineDrawer = new LineDrawer(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            boolean dragging;

            @Override
            public void mousePressed(MouseEvent e) {
                lineDrawer.startDraw(new Dot(e.getX(), e.getY()));

                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        lineDrawer.draw(g);
                    }
                });
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            lineDrawer.update(new Dot(e.getX(), e.getY()));
                            lineDrawer.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}

