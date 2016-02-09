package columbusGame;

import javafx.scene.layout.Pane;

//this class creates the 2d array "map" that the ships navigate through
public class OceanMap {
	int[][] myMap;
	int dimensions;
	int scale;
	int islandCount;
	Pane root;
	Ship ship;
	
	public OceanMap(int dimensions, int scale){
		this.dimensions = dimensions;
		this.scale = scale;
		myMap = new int[dimensions][dimensions];
	}
	
	public int[][] getMap(){
		return myMap;
	}	
}
