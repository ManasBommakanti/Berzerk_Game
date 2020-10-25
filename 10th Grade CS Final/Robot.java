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
import javafx.geometry.Point2D;
import java.util.*;

public class Robot{
	private double x;
	private double y;
	private double velX;
	private double velY;
	private boolean canMove;
	double previousx;
	double previousy;
	private double playerX;
	private double playerY;
	GraphicsContext gc;
	private ArrayList<Rectangle2D> lineBounds;
	Image enemy;
	Image lose;
	private boolean hit;
	private int countLoss;
	private int inc;
	Image player = new Image("BerzerkPlayerFaceRight.png");

	public Robot(double playerX, double playerY, GraphicsContext gc, ArrayList<Rectangle2D> lines, ArrayList<Robot> enemys, int inc){
		enemy = new Image("Enemy.gif");
		lose = new Image("EnemyLose.gif");
		lineBounds = new ArrayList<>();
		for(int i = 0; i < lines.size(); i++){
			lineBounds.add(lines.get(i));
		}
		for(int i = 0; i < enemys.size(); i++){
			lineBounds.add(enemys.get(i).getRect());
		}
		this.inc = inc + 1;

		boolean pass = true;
		canMove = false;

		velX = 0;
		velY = 0;

		x = (int)(Math.random()*370)+50;
		y = (int)(Math.random()*370)+50;

		previousx = 0;
		previousy = 0;
		this.gc = gc;
	}
	public void act(){
		if(Math.random() < 0.05*inc && countLoss < 4){
			move();
		}
		else if(countLoss >= 4){
			gc.setFill(Color.BLACK);
			gc.clearRect(x, y, enemy.getWidth(), enemy.getHeight());
			gc.fillRect(x - 2, y - 2, enemy.getWidth() + 2, enemy.getHeight() + 2);
		}
		else{
			gc.drawImage(enemy, x, y);
		}
	}
	public Point2D track(){
		double distanceX = x - playerX;
		double distanceY = y - playerY;

		return new Point2D(distanceX, distanceY);
	}
	public void move(){
		Point2D point = track();
		previousx = x;
		previousy = y;
		velX = 0;
		velY = 0;
		if(point.getX() < 0)
			velX = 2;
		else if(point.getX() > 0)
			velX = -2;
		if(point.getY() < 0)
			velY = 2;
		else if(point.getY() > 0)
			velY = -2;
		canMove = true;
		for(Rectangle2D line : lineBounds){
			if(line.contains(x + velX, y + velY))
				canMove = false;
		}
		if(!canMove){
			velX = 0;
			velY = 0;
		}
		gc.setFill(Color.BLACK);
		gc.clearRect(previousx, previousy, enemy.getWidth(), enemy.getHeight());
		gc.fillRect(previousx - 2, previousy - 2, enemy.getWidth() + 2, enemy.getHeight() + 2);
		gc.drawImage(enemy, x + velX, y + velX);
		x+=velX;
		y+=velY;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void setX(int i){
		x = i;
	}
	public void setY(int i){
		y = i;
	}
	public void setPlayerX(double i){
		playerX = i;
	}
	public void setPlayerY(double i){
		playerY = i;
	}
	public Image getImage(){
		return enemy;
	}
	public Rectangle2D getRect(){
		return new Rectangle2D(x, y, enemy.getWidth(), enemy.getHeight());
	}
	public boolean didIntersect(ArrayList<Rectangle2D> lineBounds){
		for(Rectangle2D a : lineBounds){
			if(a.intersects(getRect())){
				hit = true;
				return true;
			}
		}
		return false;
	}
	public boolean bodyHit(Rectangle2D playerRect){
		if(playerRect.intersects(getRect())){
			hit = true;
			return true;
		}
		return false;
	}
	public void lose(){
		gc.drawImage(lose, x, y);
		countLoss++;
	}
	public int getCountLoss(){
		return countLoss;
	}
	public boolean didHit(){
		return hit;
	}
	public String toString(){
		return x + " " + y;
	}
}






