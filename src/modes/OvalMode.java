package modes;

import modes.tools.OvalDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OvalMode extends Mode {

    OvalDrawer ovalDrawer;

    public OvalMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = ovalDrawer = new OvalDrawer(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            boolean dragging;

            @Override
            public void mousePressed(MouseEvent e) {
                ovalDrawer.startDraw(new Dot(e.getX(), e.getY()));
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        ovalDrawer.draw(g);
                    }
                });
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            ovalDrawer.update(new Dot(e.getX(), e.getY()));
                            ovalDrawer.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
