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
import java.awt.Point;
import java.util.*;

public class ClassifyKey{
	private KeyCode key1;
	private KeyCode key2;
	private String direct;

	public ClassifyKey(KeyCode key1, KeyCode key2){
		this.key1 = key1;
		this.key2 = key2;
		direct = "";
	}
	public void classify(){
		if(key1 == key2){
			if(key1 == KeyCode.RIGHT)
				direct = "right";
			else if(key1 == KeyCode.LEFT)
				direct = "left";
			else if(key1 == KeyCode.UP)
				direct = "up";
			else if(key1 == KeyCode.DOWN)
				direct = "down";
		}
		else if(key1 == KeyCode.UP && key2 == KeyCode.DOWN)
			direct = "up";
		else if(key2 == KeyCode.UP && key1 == KeyCode.DOWN)
			direct = "down";
		else if(key1 == KeyCode.RIGHT && key2 == KeyCode.LEFT)
			direct = "right";
		else if(key2 == KeyCode.RIGHT && key1 == KeyCode.LEFT)
			direct = "left";

		else if(key1 == KeyCode.UP){
			if(key2 == KeyCode.RIGHT)
				direct = "upright";
			else
				direct = "upleft";
		}
		else if(key2 == KeyCode.UP){
			if(key1 == KeyCode.RIGHT)
				direct = "upright";
			else
				direct = "upleft";
		}
		else if(key1 == KeyCode.DOWN){
			if(key2 == KeyCode.RIGHT)
				direct = "downright";
			else
				direct = "downleft";
		}
		else if(key2 == KeyCode.DOWN){
			if(key1 == KeyCode.RIGHT)
				direct = "downright";
			else
				direct = "downleft";
		}
	}
	public String getDirection(){
		return direct;
	}
}