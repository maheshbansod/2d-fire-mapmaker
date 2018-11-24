
public class Asset {
	static int count = 0;
	int id;
	String type; //Tile, map, image, ...
	
	Asset(String type) {
		this.type=type;
		id=count++;
	}
}
