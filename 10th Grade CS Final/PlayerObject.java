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

public class PlayerObject{
	private Image image;
	private String type;
	private double mod;

	public PlayerObject(String type, double i){
		this.type = type;
		mod = i;
		image = moveStyle(type);
	}
	public String toString(){
		return type;
	}
	public Image getImage(){
		return image;
	}
	public Image moveStyle(String type){
		if(type.equals("left"))
			return leftPlayer();
		return rightPlayer();
	}
	public Image leftPlayer(){
		if((int)mod % 2 == 0)
			return new Image("BerzerkPlayerLeft.png");
		return new Image("BerzerkPlayerLeft2.png");
	}
	public Image rightPlayer(){
		if((int)mod % 2 == 0)
			return new Image("BerzerkPlayerRight.png");
		return new Image("BerzerkPlayerRight2.png");
	}
}