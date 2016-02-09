/*Created by Jack Klein, 2016, DePaul University

This game utilizes the observer pattern to allow the pirate ships to track down the
other ship as you move it around the map. The GUI interface is created with JavaFX. 
*/

package columbusGame;

import java.util.Random;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OceanExplorer extends Application {
	
	int[][] islandMap;
	int count;
	final int dimensions = 25;
	final int scale = 20;
	final int islandCount = 10;
	Scene scene;
	Pane root;
	OceanMap oceanMap;
	Ship ship;
	PirateShip pirateShip;
	PirateShip pirateShip1;
	ImageView shipImageView;
	ImageView pImageView;
	ImageView p1ImageView;
	Label moves;
	
	@Override
	public void start(Stage oceanStage) throws Exception {
		//count = total amount of moves for each game
		count = 0;
		oceanMap = new OceanMap(dimensions, scale);
		islandMap = oceanMap.getMap();
		
		root = new AnchorPane();
		drawMap();
		//have to draw islands before declaring ships so ships don't hide under an island
		drawIslands(10);
		
		//declaring the three different ships/ adding pirates to observer list
		ship = new Ship(oceanMap);
		pirateShip = new PirateShip(oceanMap);
		pirateShip1 = new PirateShip(oceanMap);
		ship.addObserver(pirateShip);
		ship.addObserver(pirateShip1);
		loadShipImage();
		loadPirateImage();
		loadPirateImage2();
		
		//'reset' button
		Button reset = new Button("reset");
		//button resets map if pressed
		reset.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        try {
					start(oceanStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		reset.setLayoutX(0);
		reset.setLayoutY(500);
		root.getChildren().add(reset);
		
		//'total moves' label
		moves = new Label("Total moves: " + count);
		moves.setLayoutX(100);
		moves.setLayoutY(500);
		root.getChildren().add(moves);
		
		//setting the scene
		scene = new Scene(root, 500, 526);
		oceanStage.setTitle("Christopher Columbus Sails the Ocean Blue");
		oceanStage.setScene(scene);
		oceanStage.show();
		
		startSailing();
		
	}
	
	//draws 25X25 grid
	public void drawMap(){
		for(int x = 0; x < dimensions; x++){
			for(int y = 0; y < dimensions; y++){
			Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
			rect.setStroke(Color.BLACK); 
			rect.setFill(Color.PALETURQUOISE); 
			//add to the node tree in the pane
			root.getChildren().add(rect); 
			}
		}
	}
	
	//loads image of ship
	public void loadShipImage(){
		Image shipImage = new Image("file:images/ship.png",20,20,true,true); 
		shipImageView = new ImageView(shipImage);
		shipImageView.setX(ship.getShipLocation().x * scale);
		shipImageView.setY(ship.getShipLocation().y * scale);
		root.getChildren().add(shipImageView);
	}
	
	//loads first pirate ship
	public void loadPirateImage(){
		Image shipImage = new Image("file:images/pirateShip.png",20,20,true,true); 
		pImageView = new ImageView(shipImage);
		pImageView.setX(pirateShip.getPirateLocation().x * scale);
		pImageView.setY(pirateShip.getPirateLocation().y * scale);
		root.getChildren().add(pImageView);
	}
	
	//loads seconds pirate ship
	//I couldn't figure out how to combine this function and the one above
	public void loadPirateImage2(){
		Image shipImage = new Image("file:images/pirateShip.png",20,20,true,true); 
		p1ImageView = new ImageView(shipImage);
		p1ImageView.setX(pirateShip1.getPirateLocation().x * scale);
		p1ImageView.setY(pirateShip1.getPirateLocation().y * scale);
		root.getChildren().add(p1ImageView);
	}
	
	//generates islands
	public void drawIslands(int i){
		int count = 0;
		Random rand = new Random();
		while(count<i){
			int x;
			int y;
			//makes sure islands all are in diff spots
			while(true){
				x = rand.nextInt(dimensions);
				y = rand.nextInt(dimensions);
				if(islandMap[x][y]!=1)
					break;
			}
			Image islandImage = new Image("file:images/island.jpg",20,20,true,true); 
			ImageView islandImageView = new ImageView(islandImage);
			islandImageView.setX(x*scale);
			islandImageView.setY(y*scale);
			//islands are marked as '1' in 2d int array
			islandMap[x][y] = 1;
			root.getChildren().add(islandImageView);
			count++;
		}
	}
	
	//responds to key events
	private void startSailing(){
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
	public void handle(KeyEvent ke) {
		switch(ke.getCode()){
			case RIGHT:
			ship.move("EAST");
			break;
			case LEFT:
			ship.move("WEST");
			break;
			case UP:
			ship.move("NORTH");
			break;
			case DOWN:
			ship.move("SOUTH");
			break;
			default:
			break;
		}
		moves.setText("Total moves: "+ ++count);
		shipImageView.setX(ship.getShipLocation().x*scale);
		shipImageView.setY(ship.getShipLocation().y*scale);
		pImageView.setX(pirateShip.getPirateLocation().x*scale);
		pImageView.setY(pirateShip.getPirateLocation().y*scale);
		p1ImageView.setX(pirateShip1.getPirateLocation().x*scale);
		p1ImageView.setY(pirateShip1.getPirateLocation().y*scale);
		}});
		 }
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
