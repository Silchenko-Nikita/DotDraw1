package modes;

import modes.tools.FilledOvalDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FilledOvalMode extends Mode {

    FilledOvalDrawer filledOvalDrawer;

    public FilledOvalMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = filledOvalDrawer = new FilledOvalDrawer(toolSize, toolColor);
    }

    @Override
    public void apply(CanvasPanel canvasPanel)
    {
        mouseAdapter = new MouseAdapter() {
            boolean dragging;

            @Override
            public void mousePressed(MouseEvent e) {
                filledOvalDrawer.startDraw(new Dot(e.getX(), e.getY()));
                dragging = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        filledOvalDrawer.draw(g);
                    }
                });
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            filledOvalDrawer.update(new Dot(e.getX(), e.getY()));
                            filledOvalDrawer.draw(g);
                        }
                    });
                }
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
    }
}
