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

public class Shot{
	private String direction;
	private double x;
	private double y;
	private boolean moveXP;
	private boolean moveYP;
	private boolean moveXN;
	private boolean moveYN;
	GraphicsContext gc;
	private Image player;
	private Image shot;
	private boolean hit;

	public Shot(GraphicsContext gc, String direct, double x, double y, Image player){
		direction = direct;
		hit = false;
		this.gc = gc;
		this.player = player;
		shot = new Image("ShotDown.png");
		if(direction.equals("right")){
			this.x = x + player.getWidth() + 2;
			this.y = y + player.getHeight() / 2;
			moveXP = true;
		}
		else if(direction.equals("left")){
			this.x = x - 2;
			this.y = y + player.getHeight() / 2;
			moveXN = true;
		}
		else if(direction.equals("up")){
			this.x = x + player.getWidth() / 2;
			this.y = y + player.getHeight() + 2;
			moveYN = true;
		}
		else if(direction.equals("down")){
			this.x = x + player.getWidth() / 2;
			this.y = y - 2;
			moveYP = true;
		}
		else if(direction.equals("upright")){
			this.x = x + player.getWidth() + 2;
			this.y = y + player.getHeight() + 2;
			moveXP = true;
			moveYN = true;
		}
		else if(direction.equals("upleft")){
			this.x = x - 2;
			this.y = y + player.getHeight() + 2;
			moveXN = true;
			moveYN = true;
		}
		else if(direction.equals("downright")){
			this.x = x + player.getWidth() + 2;
			this.y = y - 2;
			moveXP = true;
			moveYP = true;
		}
		else if(direction.equals("downleft")){
			this.x = x - 2;
			this.y = y - 2;
			moveXN = true;
			moveYP = true;
		}
		gc.drawImage(shot, x, y);
		URL resource = getClass().getResource("PlayerGunShot.wav");
		AudioClip clip = new AudioClip(resource.toString());
		clip.play();
	}
	public void move(){
		if(moveXP)
			x+=4;
		if(moveXN)
			x-=4;
		if(moveYP)
			y+=4;
		if(moveYN)
			y-=4;
		gc.drawImage(shot, x, y);
	}
	public Rectangle2D getRect(){
		return new Rectangle2D(x, y, shot.getWidth(), shot.getHeight());
	}
	public void didHit(boolean hit){
		this.hit = hit;
	}
}