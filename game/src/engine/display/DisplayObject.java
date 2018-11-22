package engine.display;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A very basic display object for a java based gaming engine
 *
 * */
public class DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	//instance variables
	private Point position;
	private Point pivotPoint;
	private double Rotation;
	/* True iff the display object is meant to be drawn */
	private boolean visible;

	/* Defines how transparent to draw the object */
	private float alpha;

	/* Alpha from the last frame */
	private float oldAlpha;

	/* Scales the Images X axis up or down (1 is actual size) */
	private double scaleX;

	/* Scales the Images Y axis up  or down (1 is actual size) */
	private double scaleY;

	private DisplayObject parent;

	public boolean hasPhysics;
	public DisplayObjectPhysics phys;

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.setDefaults();
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.setDefaults();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setVisible(boolean visible) { this.visible = visible; }

	public boolean getVisible() { return this.visible; }

	public void setAlpha(float alpha) { this.alpha = alpha; }

	public float getAlpha() { return this.alpha; }

	public void setOldAlpha(float oldAlpha) { this.oldAlpha = oldAlpha; }

	public float getOldAlpha() { return this.oldAlpha; }

	public void setScaleX(double scaleX) { this.scaleX = scaleX; }

	public double getScaleX() { return this.scaleX; }

	public void setScaleY(double scaleY) { this.scaleY = scaleY; }

	public double getScaleY() { return this.scaleY; }

	public void setParent(DisplayObject parent) { this.parent = parent; }

	public DisplayObject getParent() { return this.parent; }

	public void setDefaults() {
		this.setPosition(new Point(0,0));
		this.setPivotPoint(new Point(0,0));
		this.setRotation(0);
		this.setVisible(true);
		this.setAlpha(1);
		this.setOldAlpha(0);
		this.setScaleX(1);
		this.setScaleY(1);
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}

	//instance variable getters and setters
	public Point getPosition() {
		return this.position;
	}
	public Point getPivotPoint() {
		return this.pivotPoint;
	}
	public double getRotation() {
		return this.Rotation;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public void setPivotPoint(Point pivotPoint) {
		this.pivotPoint = pivotPoint;
	}
	public void setRotation(double Rotation) {
		this.Rotation = Rotation;
	}

	public Point localToGlobal(Point p) {
		Point temp = p;
		DisplayObject par = this.parent;
		while (par != null) {
			temp = new Point(temp.x + par.getPosition().x, temp.y + par.getPosition().y);
			par = par.parent;
		}
		return temp;
	}
	public Point globalToLocal(Point p) {
		Point temp = p;
		DisplayObject par = this.parent;
		while (par != null) {
			temp = new Point(temp.x - par.getPosition().x, temp.y - par.getPosition().y);
			par = par.parent;
		}
		return temp;
	}

	// Updates curr position to the next correct position
	public Point rotateEllipse(Point curr, Point center, int xrad, int yrad, int rot, int direction) {
		int new_x, new_y, xsign, ysign;
		xsign = (curr.y > center.y) ? -1 : 1;
		if (curr.y == center.y) {
			xsign = curr.x == (center.x - xrad) ? 1 : -1;
			xsign *= direction;
		}
		ysign = xsign * -1;
		new_x = curr.x + direction * (xsign * rot);
		if (new_x < (center.x - xrad))
			new_x = (center.x - xrad) + 1;
		else if (new_x > (center.x + xrad))
			new_x = (center.x + xrad) - 1;
		double rhs = (Math.pow(yrad, 2) * (1 - (Math.pow(new_x - center.x, 2) / Math.pow(xrad, 2))));
		new_y = (int)((ysign * Math.sqrt(rhs)) + center.y);
		Point next = new Point(new_x, new_y);
		return next;
	}

	public Shape getHitbox() {
		Rectangle2D box = new Rectangle2D.Double(this.getPosition().x, this.getPosition().y, this.getUnscaledWidth() * this.getScaleX(), this.getUnscaledHeight() * this.getScaleY());
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x + this.position.x, this.pivotPoint.y + this.position.y);
		return tx.createTransformedShape(box);
	}

	public boolean collidesWith(DisplayObject other) {
		Shape otherhitbox = other.getHitbox();
		Rectangle2D otherbounds = otherhitbox.getBounds2D();
		return this.getHitbox().intersects(otherbounds);
	}

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {

	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (displayImage != null) {

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			if (this.visible) {
				g2d.drawImage(displayImage, 0, 0,
						(int) (getUnscaledWidth()),
						(int) (getUnscaledHeight()), null);
			}
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.position.x, this.position.y);
		g2d.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
		g2d.scale(this.scaleX, this.scaleY);
		float curAlpha;
		this.oldAlpha = curAlpha = ((AlphaComposite) g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(3, curAlpha * this.alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(3, this.oldAlpha));
		g2d.scale( 1 / this.scaleX, 1 / this.scaleY);
		g2d.rotate(Math.toRadians(-this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
		g2d.translate(-this.position.x, -this.position.y);
	}

}
