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

public class Savers{
	private Image saver;
	private double x;
	private double y;
	private GraphicsContext gc;

	public Savers(GraphicsContext gc, double x, double y){
		int num = (int)(Math.random()*2);
		this.gc = gc;
		if(num == 0)
			saver = new Image("SaveGirl.gif");
		else
			saver = new Image("SaveBoy.gif");
		this.x = x;
		this.y = y;
		this.gc.drawImage(saver, this.x, this.y);
	}
	public Rectangle2D getRect(){
		return new Rectangle2D(x, y, saver.getWidth(), saver.getHeight());
	}
	public Image getImage(){
		return saver;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
}