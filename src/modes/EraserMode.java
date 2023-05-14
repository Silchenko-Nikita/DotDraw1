package modes;

import modes.tools.Eraser;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EraserMode extends Mode {
    Eraser eraser;

    public EraserMode(int toolSize) {
        super(toolSize, Color.WHITE);

        tool = eraser = new Eraser(toolSize);
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
                dragging = true;

                eraser.startErase(new Dot(e.getX(), e.getY()));
                eraser.setShowBounds(true);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;

                eraser.setShowBounds(false);
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        eraser.erase(g);
                    }
                });

                if (mouseIn) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            eraser.drawBounds(g, new Dot(e.getX(), e.getY()));
                        }
                    });
                } else {
                    canvasPanel.setFinalDrawingActionAndRepaint(null);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!mouseIn) return;
                canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        eraser.drawBounds(g, new Dot(e.getX(), e.getY()));
                    }
                });
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragging) return;

                canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        eraser.update(new Dot(e.getX(), e.getY()));
                        eraser.erase(g);
                    }
                });
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
