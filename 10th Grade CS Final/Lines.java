import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;

import javafx.scene.shape.Line;
import java.util.*;

public class Lines{
	private ArrayList<Rectangle2D> lines;
	private GraphicsContext gc;
	private int lineWidth;

	public Lines(GraphicsContext gc, int width){
		lines = new ArrayList<>();
		this.gc = gc;
		lineWidth = width;
	}
	public void createBounds(){
		gc.setFill(Color.BLUE);

		gc.fillRect(0, 0, lineWidth, 512);
		lines.add(new Rectangle2D(0, 0, lineWidth, 512));
		gc.fillRect(0, 0, 512, lineWidth);
		lines.add(new Rectangle2D(0, 0, 512, lineWidth));
		gc.fillRect(0, 512 - lineWidth, 512, lineWidth);
		lines.add(new Rectangle2D(0, 512 - lineWidth, 512, lineWidth));
		gc.fillRect(512 - lineWidth, 0, 512, 512);
		lines.add(new Rectangle2D(512 - lineWidth, 0, 512, 512));
	}
	public void createLines(){
		gc.setFill(Color.BLUE);

		int startX = (int)(Math.random()*300) + 130;
		int endY = (int)(Math.random()*200) + 130;
		int addX = 0;
		int addY = 0;

		gc.fillRect(startX, 0, lineWidth, endY);
		lines.add(new Rectangle2D(startX, 0, lineWidth, endY));

		gc.fillRect(0, endY - 50, startX - 70, lineWidth);
		lines.add(new Rectangle2D(0, endY - 50, startX - 70, lineWidth));

		gc.fillRect(startX, endY, 450 - startX, lineWidth);
		lines.add(new Rectangle2D(startX, endY, 450 - startX, lineWidth));

		gc.fillRect(startX, endY, lineWidth, 450 - endY);
		lines.add(new Rectangle2D(startX, endY, lineWidth, 450 - endY));

		gc.fillRect(130, endY + 50, startX - 130, lineWidth);
		lines.add(new Rectangle2D(130, endY + 50, startX - 130, lineWidth));

		gc.fillRect(60, endY - 50, lineWidth, 400 - endY + 50);
		lines.add(new Rectangle2D(60, endY - 50, lineWidth, 400 - endY + 50));
	}
	public ArrayList<Rectangle2D> getLineBounds(){
		return lines;
	}
	public void repaint(){
		gc.setFill(Color.BLUE);

		for(Rectangle2D i : lines){
			gc.fillRect(i.getMinX(), i.getMinY(), i.getWidth(), i.getHeight());
		}
		gc.setFill(Color.TRANSPARENT);
	}
	public String toString(){
		return " ";
	}
}