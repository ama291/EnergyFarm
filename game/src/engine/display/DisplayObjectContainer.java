package engine.display;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DisplayObjectContainer extends DisplayObject {
    private ArrayList<DisplayObject> children = new ArrayList<DisplayObject>();

    public DisplayObjectContainer(String id) {
        super(id);
    }
    public DisplayObjectContainer(String id, String imageFileName) {
        super(id, imageFileName);
    }


    public void addChild(DisplayObject child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChildAtIndex(String id, int i) {
        DisplayObject child = new DisplayObject(id);
        child.setParent(this);
        this.children.add(i, child);
    }

    public void removeChild(String id) {
        Iterator itr = this.children.iterator();
        while (itr.hasNext()) {
            DisplayObject d = (DisplayObject) itr.next();
            if (d.getId() == id) {
                itr.remove();
            }
        }
    }
    public void removeByIndex(int i) {
        this.children.remove(i);
    }
    public void removeAll() {
        this.children.clear();
    }

    public boolean contains(DisplayObject child) {
        if (this.children.contains(child)) {
            return true;
        }
        return false;
    }

    public ArrayList<DisplayObject> getChildren() {
        return this.children;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        if (!children.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;

            applyTransformations(g2d);

            for (DisplayObject child : children) {
                child.draw(g);
            }

            reverseTransformations(g2d);
        }
    }
}
