package columbusGame;
import java.util.Observable;
import java.util.Random;
import java.awt.Point;

public class Ship extends Observable{
	OceanMap oceanMap;
	Point currentLocation;
	Random rand = new Random();
	
	public Ship(OceanMap oceanMap){
		//links ocean to ship
		this.oceanMap = oceanMap;
		oceanMap.ship = this;
		//initially assigns ship random point
		while(true){
			int x = rand.nextInt(oceanMap.dimensions);
			int y = rand.nextInt(oceanMap.dimensions);
			//initial point can't be an island
			if(oceanMap.getMap()[x][y] != 1){
				currentLocation = new Point(x,y);
				break;
			}
		}
	}
	
	public Point getShipLocation(){
		return this.currentLocation;
	}
	
	//directions account for islands
	public void move(String s){
		if(s.equals("EAST")){
			if(currentLocation.x<24 && oceanMap.getMap()[currentLocation.x+1][currentLocation.y]!=1)
				currentLocation.x++;
		}
		else if(s.equals("WEST")){
			if(currentLocation.x>0 && oceanMap.getMap()[currentLocation.x-1][currentLocation.y]!=1 )
				currentLocation.x--;
		}
		else if(s.equals("NORTH")){
			if(currentLocation.y>0 && oceanMap.getMap()[currentLocation.x][currentLocation.y-1]!=1)
				currentLocation.y--;
		}
		else{
			if(currentLocation.y<24 && oceanMap.getMap()[currentLocation.x][currentLocation.y+1]!=1)
				currentLocation.y++;
		}
		
		//notify the observers
		setChanged();
		notifyObservers();
	}
}
