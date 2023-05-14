package modes;

import modes.tools.Pencil;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PencilMode extends Mode {
    Pencil pencil;

    public PencilMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = pencil = new Pencil(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            private boolean mouseIn;
            private boolean dragging;

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseIn = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseIn = false;
                canvasPanel.setFinalDrawingActionAndRepaint(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pencil.startDraw(new Dot(e.getX(), e.getY()));
                dragging = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        pencil.draw(g);
                    }
                });
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (mouseIn && !dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            g.setColor(toolColor);
                            g.fillOval(e.getX()  - toolSize / 2, e.getY() - toolSize / 2,
                                    toolSize, toolSize);
                        }
                    });
                } else {
                    canvasPanel.setFinalDrawingActionAndRepaint(null);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            pencil.update(new Dot(e.getX(), e.getY()));
                            pencil.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
