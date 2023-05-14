package modes;

import modes.tools.TextDrawer;
import ui.CanvasPanel;
import utils.Dot;

import java.awt.*;
import java.awt.event.*;

public class TextMode extends Mode {
    private int fontSize;
    public TextDrawer textDrawer;

    protected KeyAdapter keyAdapter;

    public TextMode(int toolSize, Color toolColor) {
        super(toolSize, toolColor);

        tool = textDrawer = new TextDrawer(toolSize, toolColor, fontSize);
    }

    public void setFontSize(int val) {
        fontSize = val;
    }

    @Override
    public void apply(CanvasPanel canvasPanel) {
        mouseAdapter = new MouseAdapter() {
            private boolean pressed;

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pressed) return;

                canvasPanel.requestFocusInWindow();

                if (textDrawer.isFinished()) {
                    textDrawer.setBeingActiveState(false);
                    canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            textDrawer.draw(g);
                        }
                    });

                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            textDrawer.draw(g);

                            textDrawer.startDraw(new Dot(e.getX(), e.getY()));
                            textDrawer.setBeingActiveState(true);

                            canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                                @Override
                                public void perform(Graphics g) {
                                    textDrawer.draw(g);
                                }
                            });
                        }
                    });
                } else {
                    textDrawer.startDraw(new Dot(e.getX(), e.getY()));
                    textDrawer.setBeingActiveState(true);

                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            textDrawer.draw(g);
                        }
                    });
                }
            }
        };

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_ENTER) {
                    textDrawer.setBeingActiveState(false);
                    canvasPanel.setFinishedDrawingAction(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            textDrawer.draw(g);
                        }
                    });

                    canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                        @Override
                        public void perform(Graphics g) {
                            textDrawer.draw(g);
                            canvasPanel.setFinalDrawingActionAndRepaint(null);
                        }
                    });
                }
            }

            private boolean isReadableUnicodeChar(char c) {
                return Character.isDefined(c) &&
                        (Character.getType(c) != Character.CONTROL) &&
                        (Character.getType(c) != Character.FORMAT) &&
                        (Character.getType(c) != Character.PRIVATE_USE) &&
                        (Character.getType(c) != Character.SURROGATE);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_ENTER) {
                    return;
                }

                char keyChar = e.getKeyChar();
                if (isReadableUnicodeChar(keyChar)) {
                    textDrawer.update(keyChar);
                }


                canvasPanel.setFinalDrawingActionAndRepaint(new DrawingAction() {
                    @Override
                    public void perform(Graphics g) {
                        textDrawer.draw(g);
                    }
                });
            }
        };

        canvasPanel.addMouseListener(mouseAdapter);
        canvasPanel.addMouseMotionListener(mouseAdapter);
        canvasPanel.addKeyListener(keyAdapter);
    }

    @Override
    public void unapply(CanvasPanel canvasPanel) {
        super.unapply(canvasPanel);

        textDrawer.finish();
        canvasPanel.removeKeyListener(keyAdapter);
    }
}
