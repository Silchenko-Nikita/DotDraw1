package modes;

import modes.tools.Eraser;
import modes.tools.Tool;
import ui.CanvasPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;

public abstract class Mode {
    protected int toolSize;
    protected Color toolColor;
    protected MouseAdapter mouseAdapter;

    protected Tool tool;

    public Mode(int toolSize, Color toolColor) {
        this.toolSize = toolSize;
        this.toolColor = toolColor;
    }

    public void setToolSize(int size) {
        this.toolSize = size;

        if (tool == null) return;
        tool.setSize(size);
    }

    public void setToolColor(Color color){
        this.toolColor = color;

        if (tool == null) return;
        tool.setColor(color);
    }

    public int getToolSize() {
        return toolSize;
    }

    public Color getToolColor() {
        return toolColor;
    }

    abstract public void apply(CanvasPanel canvasPanel);

    public void unapply(CanvasPanel canvasPanel) {
        canvasPanel.removeMouseListener(mouseAdapter);
        canvasPanel.removeMouseMotionListener(mouseAdapter);
    }
}