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

import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Line;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;


public class ManasBommakanti extends Application implements EventHandler<InputEvent>{
	GraphicsContext gc;
	AnimateObjects animate;
	ArrayList<Rectangle2D> lineBounds;
	Image player;
	Image losePlayer;
	Image enemy;
	Image enemyLose;
	Image evilOtto;
	int countRelease;
	boolean hit = true;
	boolean canPlay = false;
	boolean played = false;
	boolean wasRight = true;
	boolean intersectedSave = false;
	boolean pause = false;
	boolean win = false;
	boolean moreRobots = false;
	double playerMoveStyle = 0;
	double x;
	double y;
	double previousx;
	double previousy;
	double addRobot;
	boolean ifShot = false;
	int count = 0;
	int canStop = 0;
	int canShoot = 0;
	int countFrames = 0;
	int points = 0;
	int numLives = 3;
	int numRobots = 10;
	int numWon = 0;
	String directionOfShot;
	Savers saver;
	ClassifyKey classified;
	ArrayList<Robot> robots = new ArrayList<>();
	ArrayList<Shot> shotsFired = new ArrayList<>();
	ArrayList<KeyCode> keyCommandHistory = new ArrayList<>();
	String previousKey;
	double setX = 0;
	double setY = 0;
	Lines lines;
	Rectangle2D startRect;
	Scene scene;

	public static void main(String[] args){
		launch();
	}
	public void start(Stage stage){
		stage.setTitle("Berzerk 2.0!");
		Group root = new Group();

		Canvas canvas = new Canvas(512, 612);
		gc = canvas.getGraphicsContext2D(); //creates canvas

		root.getChildren().add(canvas);

		scene = new Scene(root);
		stage.setScene(scene);

		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


		Image menu = new Image("MainMenu.gif");
		gc.drawImage(menu, 0, 0);
		gc.setStroke(Color.WHITE);
		Font font = Font.font("TimesRoman", FontWeight.NORMAL, 10);
		gc.setFont(font);
		gc.strokeText("Welcome to Berzerk!\nMove with the arrow keys.\nTo shoot diagonally, press the two buttons\nthat indicate the direction\nyou want to shoot.\nIf you want to shoot vertically or\nhorizontally, double tap the indicated\nbutton. To shoot press space. Have Fun!\n\nDo not crash into walls or robots.\nDoing so will lose a life!", 260, 35);
		gc.strokeText("You can only save the\nhostage by killing all the robots!", 190, 190);
		Font font5 = Font.font("TimesRoman", FontWeight.NORMAL, 15);
		gc.setFont(font5);
		gc.strokeText("Press any button to start", 175, 550);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);

		stage.show();
	}
	public void sleep(int sec){
		try {
			Thread.sleep(sec);
		}
		catch (InterruptedException e) {
			System.out.println("");
		}
	}
	public void handle(final InputEvent event){ //Player moving capabilities
		gc.setFill(Color.BLACK);
		previousx = x;
		previousy = y;
		boolean right = false;
		boolean left = false;
		boolean up = false;
		boolean down = false;
		int countSides = 0;
		int countVertical = 0;

		if(!canPlay && event.getEventType().toString().equals("KEY_PRESSED") && event instanceof KeyEvent){
			if(!played){
				URL resource = getClass().getResource("bzk_escp.wav");
				AudioClip clip = new AudioClip(resource.toString());
				clip.play();
				canPlay = true;
				played = true;
				gc.clearRect(0, 0, 512, 612);
				gc.setFill(Color.BLACK);
				gc.setStroke(Color.BLACK);
				gc.fillRect(0, 0, 512, 612);

				lines = new Lines(gc, 10);
				lines.createBounds();
				lines.createLines();

				countRelease = 0;

				previousKey = "RIGHT";

				x = 15;
				y = 15;

				player = new Image("BerzerkPlayerFaceRight.png");
				startRect = new Rectangle2D(0, 0, player.getWidth() + 2, player.getHeight() + 2);
				gc.drawImage(player, x, y);

				keyCommandHistory.add(KeyCode.RIGHT);
				keyCommandHistory.add(KeyCode.RIGHT);

				enemyLose = new Image("EnemyLose.gif");

				saver = new Savers(gc, 450, 10);
				gc.drawImage(saver.getImage(), saver.getX(), saver.getY());

				for(Robot i : robots)
					gc.drawImage(i.getImage(), i.getX(), i.getY());

				losePlayer = new Image("Lose.gif");

				lineBounds = lines.getLineBounds();


				for(int i = 0; i < numRobots;){
					Robot robot = new Robot(x, y, gc, lineBounds, robots, numWon);
					if(!robot.didIntersect(lineBounds)){
						robots.add(robot);
						i++;
					}
				}

				animate = new AnimateObjects();
				animate.start();

				scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
				scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
				scene.addEventHandler(KeyEvent.KEY_RELEASED, this);
			}
			else if(played){
				if(((KeyEvent)event).getCode() == KeyCode.SPACE){
					URL resource = getClass().getResource("bzk_escp.wav");
					AudioClip clip = new AudioClip(resource.toString());
					clip.play();
					canPlay = true;
					lines = new Lines(gc, 10);
					lines.createBounds();
					lines.createLines();

					lineBounds.clear();
					lineBounds = lines.getLineBounds();

					countRelease = 0;

					previousKey = "RIGHT";

					if(!win){
						numLives = 4;
						points = 100;
					}

					x = 15;
					y = 15;

					player = new Image("BerzerkPlayerFaceRight.png");
					startRect = new Rectangle2D(0, 0, player.getWidth() + 2, player.getHeight() + 2);
					gc.drawImage(player, x, y);

					keyCommandHistory.clear();
					keyCommandHistory.add(KeyCode.RIGHT);
					keyCommandHistory.add(KeyCode.RIGHT);

					enemyLose = new Image("EnemyLose.gif");

					robots.clear();

					for(int i = 0; i < numRobots; i++){
						robots.add(new Robot(x, y, gc, lineBounds, robots, numWon));
					}

					saver = new Savers(gc, 450, 10);
					gc.drawImage(saver.getImage(), saver.getX(), saver.getY());

					for(Robot i : robots)
						gc.drawImage(i.getImage(), i.getX(), i.getY());

					losePlayer = new Image("Lose.gif");

					animate = new AnimateObjects();
					animate.start();

					scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
					scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
					scene.addEventHandler(KeyEvent.KEY_RELEASED, this);
				}
				else if(((KeyEvent)event).getCode() == KeyCode.ESCAPE){
					gc.clearRect(0, 0, 512, 612);
					gc.fillRect(0, 0, 512, 612);
					gc.setFill(Color.GREEN);
					gc.setStroke(Color.WHITE);
					gc.fillText("Thanks for playing!\nNumber of games won: " + numWon, 100, 300);
					gc.strokeText("Thanks for playing!\nNumber of games won: " + numWon, 100, 300);
					scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
				}
			}
		}
		if(!pause && (((KeyEvent)event).getCode() == KeyCode.RIGHT || ((KeyEvent)event).getCode() == KeyCode.LEFT || ((KeyEvent)event).getCode() == KeyCode.UP || ((KeyEvent)event).getCode() == KeyCode.DOWN) && ((KeyEvent)event).getCode().toString() != previousKey && previousKey != keyCommandHistory.get(keyCommandHistory.size() - 2).toString()){
			keyCommandHistory.add(((KeyEvent)event).getCode());
			System.out.println(keyCommandHistory.get(keyCommandHistory.size() - 1));
		}
		if(canPlay && event.getEventType().toString().equals("KEY_RELEASED")){
			if(wasRight){
				player = new Image("BerzerkPlayerFaceRight.png");
				gc.clearRect(previousx, previousy, player.getWidth(), player.getHeight());
				gc.fillRect(previousx - 2, previousy - 2, player.getWidth() + 2, player.getHeight() + 2);
				gc.drawImage(player, x, y);
			}
			else{
				player = new Image("BerzerkPlayerFaceLeft.png");
				gc.clearRect(previousx, previousy, player.getWidth(), player.getHeight());
				gc.fillRect(previousx - 2, previousy - 2, player.getWidth() + 2, player.getHeight() + 2);
				gc.drawImage(player, x, y);
			}
			playerMoveStyle = 0;
			setX = 0;
			setY = 0;
			previousKey = "";
		}
		else if(canPlay && event.getEventType().toString().equals("KEY_PRESSED") && event instanceof KeyEvent && hit){
			playerMoveStyle++;
			if(((KeyEvent)event).getCode() == KeyCode.P){
				if(!pause){
					animate.stop();
					pause = true;
					gc.setFill(Color.BLACK);
					gc.clearRect(0, 0, 512, 512);
					gc.fillRect(0, 0, 512, 512);
					gc.setFill(Color.GREEN);
					gc.setStroke(Color.WHITE);
					gc.fillText("Press P to unpause", 100, 350);
					gc.strokeText("Press P to unpause", 100, 350);
				}
				else{
					gc.drawImage(player, x, y);
					animate.start();
					pause = false;
				}
			}
			if(((KeyEvent)event).getCode() == KeyCode.LEFT){
				setX = -2;
				PlayerObject obj = new PlayerObject("left", playerMoveStyle);
				player = obj.getImage();
				wasRight = false;
				previousKey = "LEFT";
			}
			else if(((KeyEvent)event).getCode() == KeyCode.RIGHT){
				setX = 2;
				PlayerObject obj = new PlayerObject("right", playerMoveStyle);
				player = obj.getImage();
				wasRight = true;
				previousKey = "RIGHT";
			}
			else if(((KeyEvent)event).getCode() == KeyCode.UP){
				setY = -1;
				PlayerObject obj = new PlayerObject("right", playerMoveStyle);
				if(!wasRight)
					obj = new PlayerObject("left", playerMoveStyle);
				player = obj.getImage();
				previousKey = "UP";
			}
			else if(((KeyEvent)event).getCode() == KeyCode.DOWN){
				setY = 1;
				PlayerObject obj = new PlayerObject("right", playerMoveStyle);
				if(!wasRight)
					obj = new PlayerObject("left", playerMoveStyle);
				player = obj.getImage();
				previousKey = "DOWN";
			}
			if(((KeyEvent)event).getCode() == KeyCode.SPACE && canShoot >= 20){
				ifShot = true;
				setX = 0;
				setY = 0;
			}
		}
		else{
			gc.clearRect(x, y, player.getWidth(), player.getHeight());
			gc.fillRect(x - 2, y - 2, player.getWidth() + 2, player.getHeight() + 2);
		}
	}

	public class AnimateObjects extends AnimationTimer{
		public void handle(long now){ //Object moving capabilities
			Rectangle2D playerRect = new Rectangle2D(x, y, player.getWidth(), player.getHeight());
			canShoot++;
			countFrames++;
			if(canPlay && countFrames % 400 == 0 && robots.size() != 0){
				URL resource = getClass().getResource("bzk_chic.wav");
				AudioClip clip = new AudioClip(resource.toString());
				clip.play();
			}
			gc.setFill(Color.BLACK);
			gc.clearRect(0, 0, 512, 612);
			gc.fillRect(0, 0, 512, 612);

			gc.setStroke(Color.WHITE);
			gc.strokeRect(10, 10, player.getWidth() + 10, player.getHeight() + 10);

			gc.setFill(Color.GREEN);
			gc.setStroke(Color.WHITE);
			Font font2 = Font.font("Helvetica", FontWeight.NORMAL, 40);
			gc.setFont(font2);

			gc.fillText("Score: " + points, 50, 580);

			String lives = "";
			for(int i = 0; i < numLives; i++){
				lives+="X";
			}
			gc.strokeText("Lives: " + lives, 300, 580);
			if(!intersectedSave)
				gc.drawImage(saver.getImage(), saver.getX(), saver.getY());

			Rectangle2D playerRectDouble = new Rectangle2D(x - 40, y - 40, player.getWidth() + 80, player.getHeight() + 80);

			for(int i = 0; i < shotsFired.size(); i++){
				shotsFired.get(i).move();
			}
			if(canPlay){
				x+=setX;
				y+=setY;
			}
			gc.drawImage(player, x, y);

			if(ifShot){
				ClassifyKey classified = new ClassifyKey(keyCommandHistory.get(keyCommandHistory.size() - 1), keyCommandHistory.get(keyCommandHistory.size() - 2));
				classified.classify();
				shotsFired.add(new Shot(gc, classified.getDirection(), x, y, player));
				countFrames = 0;
				ifShot = false;
				canShoot = 0;
			}

			for(Robot i : robots){
				i.setPlayerX(x);
				i.setPlayerY(y);
				i.act();
			}
			if(playerRect.intersects(saver.getRect()) && robots.size() == 0){
				if(!intersectedSave)
					moreRobots = true;
				else
					moreRobots = false;
				intersectedSave = true;
				if(moreRobots){
					int num = robots.size();
					robots.clear();
					for(int i = 0; i < num + 10 + numWon*5;){
						Robot robot = new Robot(x, y, gc, lineBounds, robots, numWon);
						if(!robot.didIntersect(lineBounds)){
							robots.add(robot);
							i++;
						}
					}
				}
			}
			for(Rectangle2D i : lineBounds){
				if(playerRect.intersects(i)){
					hit = false;
					setX = 0;
					setY = 0;
				}
				for(int j = 0; j < shotsFired.size();){
					if(shotsFired.get(j).getRect().intersects(i))
						shotsFired.remove(j);
					else
						j++;
				}
			}
			for(int i = 0; i < robots.size(); i++){
				boolean b = false;
				if(robots.get(i).didIntersect(lineBounds)){
					b = true;
				}
				if(robots.get(i).bodyHit(playerRect)){
					b = true;
					hit = false;
					setX = 0;
					setY = 0;
				}
				for(int j = 0; j < shotsFired.size();){
					if(robots.get(i).getRect().intersects(shotsFired.get(j).getRect())){
						shotsFired.remove(j);
						points+=countFrames;
						countFrames = 0;
						b = true;
					}
					else
						j++;
				}
				if(b){
					robots.remove(i);
				}
			}
			if(!hit){
				if(numLives != 1){
					numLives--;
					points-=100;
					gc.drawImage(player, 15, 15);
					x = 15;
					y = 15;
					intersectedSave = false;
					gc.drawImage(saver.getImage(), saver.getX(), saver.getY());
					hit = true;
					int num = robots.size();
					robots.clear();
					for(int i = 0; i < num + 10 + numWon*5;){
						Robot robot = new Robot(x, y, gc, lineBounds, robots, numWon);
						if(!robot.didIntersect(lineBounds)){
							robots.add(robot);
							i++;
						}
					}
				}
				else{
					removeHandler();
					count++;
					gc.drawImage(losePlayer, x, y);
					URL resource = getClass().getResource("bzk_got.wav");
					AudioClip clip = new AudioClip(resource.toString());
					if(canPlay)
						clip.play();
					canPlay = false;
					win = false;

					gc.setFill(Color.BLACK);
					gc.clearRect(0, 512, 512, 100);
					gc.fillRect(0, 512, 512, 100);

					gc.setFill(Color.GREEN);
					gc.setStroke(Color.WHITE);
					Font font3 = Font.font("Arial", FontWeight.NORMAL, 20);
					gc.setFont(font3);
					gc.fillText("You lose! \nScore: " + points, 210, 560);
					gc.strokeText("You lose! \nScore: " + points, 210, 560);
					if(count == 200){
						animate.stop();
						intersectedSave = false;
						count = 0;
						restart();
					}
				}
			}
			if(startRect.intersects(playerRect) && intersectedSave && robots.size() == 0){
				removeHandler();
				count++;
				gc.setFill(Color.BLACK);
				gc.clearRect(0, 512, 512, 100);
				gc.fillRect(0, 512, 512, 100);
				win = true;

				canPlay = false;
				gc.setFill(Color.GREEN);
				gc.setStroke(Color.WHITE);
				Font font3 = Font.font("Arial", FontWeight.NORMAL, 20);
				gc.setFont(font3);
				gc.fillText("Congrats, you have won this round! \nScore: " + points, 120, 560);
				gc.strokeText("Congrats, you have won this round! \nScore: " + points, 120, 560);
				if(count == 200){
					numWon++;
					animate.stop();
					count = 0;
					intersectedSave = false;
					restart();
				}
			}
			if(canPlay)
				lines.repaint();
		}
	}
	public void restart(){
		gc.setFill(Color.BLACK);
		gc.clearRect(0, 0, 512, 612);
		gc.fillRect(0, 0, 512, 612);
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 512, 612);
		gc.setStroke(Color.WHITE);
		Font font = Font.font("TimesRoman", FontWeight.NORMAL, 30);
		gc.setFont(font);
		if(win)
			gc.strokeText("Are you ready for the\nnext level? If\nso, press space.\nIf you would like to exit\nthe game, press escape.", 120, 140);
		else
			gc.strokeText("If you would like to\nplay again, press space.\nIf you want to exit\nthe game, press escape.", 120, 140);
		evilOtto = new Image("EvilOtto.png");
		gc.drawImage(evilOtto, 120, 400);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this);
	}
	public void removeHandler(){
		scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
	}
}