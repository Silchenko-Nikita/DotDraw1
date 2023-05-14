package modes.tools;

import java.awt.*;

public abstract class Tool {
    protected int size;
    protected Color color;

    public Tool(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
