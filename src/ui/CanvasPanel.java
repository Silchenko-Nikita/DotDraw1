package ui;

import modes.*;
import modes.DrawingAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

enum DrawingMode {
    FILL,
    PENCIL,
    BRUSH,
    LINE,
    RECT,
    FILLED_RECT,
    OVAL,
    FILLED_OVAL,
    TEXT,
    COLOR_PICKER
}

public class CanvasPanel extends JPanel {
    final PencilMode pencilMode;
    final FillMode fillMode;
    final EraserMode eraserMode;
    final LineMode lineMode;
    final RectMode rectMode;
    final FilledRectMode filledRectMode;
    final OvalMode ovalMode;
    final FilledOvalMode filledOvalMode;
    final TextMode textMode;
    final ColorPickerMode colorPickerMode;

    static ArrayList<Mode> modes;

    private Stack<BufferedImage> images;
    private Stack<BufferedImage> undoneImages;

    AppFrame appFrame;
    private Mode currentMode;
    private DrawingAction finalDrawingAction = null;

    CanvasPanel(AppFrame appFrame) {
        super();

        this.appFrame = appFrame;

        {
            pencilMode = new PencilMode(1, Color.BLACK);
            fillMode = new FillMode(1, Color.BLACK);
            eraserMode = new EraserMode(1);
            lineMode = new LineMode(1, Color.BLACK);
            rectMode = new RectMode(1, Color.BLACK);;
            filledRectMode = new FilledRectMode(1, Color.BLACK);;
            ovalMode = new OvalMode(1, Color.BLACK);;
            filledOvalMode = new FilledOvalMode(1, Color.BLACK);
            textMode = new TextMode(1, Color.BLACK);
            colorPickerMode = new ColorPickerMode();

            modes = new ArrayList<>();
            modes.add(pencilMode);
            modes.add(fillMode);
            modes.add(eraserMode);
            modes.add(lineMode);
            modes.add(rectMode);
            modes.add(filledRectMode);
            modes.add(ovalMode);
            modes.add(filledOvalMode);
            modes.add(textMode);
            modes.add(colorPickerMode);
        }

        images = new Stack<>();
        undoneImages = new Stack<>();
        setBackground(Color.WHITE);

        applyMode(pencilMode);
        currentMode.setToolColor(Color.BLACK);
        repaint();
    }

    public Color getCurrentColor() {
        return currentMode.getToolColor();
    }

    public void setCurrentColor(Color currentColor) {
        for (Mode mode : modes) {
            mode.setToolColor(currentColor);
        }

        repaint();
    }

    public void setCurrentColorFromPicker(Color currentColor) {
        setCurrentColor(currentColor);
        appFrame.setColor(currentColor);
    }

    public int getToolSize() {
        return currentMode.getToolSize();
    }

    public void setToolSize(int size) {
        for (Mode mode : modes) {
            mode.setToolSize(size);
        }

        repaint();
    }

    public void switchDrawingMode(DrawingMode drawingMode) {
        switch (drawingMode) {
            case PENCIL -> {
                applyMode(pencilMode);
            }
            case FILL -> {
                applyMode(fillMode);
            }
            case BRUSH -> {
                applyMode(eraserMode);
            }
            case LINE -> {
                applyMode(lineMode);
            }
            case RECT -> {
                applyMode(rectMode);
            }
            case FILLED_RECT -> {
                applyMode(filledRectMode);
            }
            case OVAL -> {
                applyMode(ovalMode);
            }
            case FILLED_OVAL -> {
                applyMode(filledOvalMode);
            }
            case TEXT -> {
                Font currentFont = getGraphics().getFont();
                int fontSize = currentFont.getSize();
                textMode.setFontSize(fontSize);
                applyMode(textMode);
            }
            case COLOR_PICKER -> {
                applyMode(colorPickerMode);
            }
        }
    }

    private void applyMode(Mode mode) {
        if (currentMode != null) {
            currentMode.unapply(this);
        }

        this.currentMode = mode;

        currentMode.apply(this);
    }

    public void setFinishedDrawingAction(DrawingAction finishedAction) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        printAll(g2d);

        finishedAction.perform(g2d);

        g2d.dispose();
        images.push(image);

        finalDrawingAction = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!images.isEmpty()) {
            g.drawImage(images.peek(), 0, 0, null);
        }

        if (finalDrawingAction != null) {
            finalDrawingAction.perform(g);
        }
    }

    public void undo() {
        if (images.isEmpty()) return;

        undoneImages.push(images.pop());

        repaint();
    }

    public void redo() {
        if (undoneImages.isEmpty()) return;

        BufferedImage lastUndoneImage = undoneImages.pop();
        images.push(lastUndoneImage);

        repaint();
    }

    public void clear() {
        images.clear();
        undoneImages.clear();
        repaint();
    }

    public void saveToFile(String fileName) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        printAll(g2d);
        g2d.dispose();

        try {
            File outputFile = new File(fileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Panel saved to " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFinalDrawingActionAndRepaint(DrawingAction action) {
        this.finalDrawingAction = action;
        repaint();
    }
}
