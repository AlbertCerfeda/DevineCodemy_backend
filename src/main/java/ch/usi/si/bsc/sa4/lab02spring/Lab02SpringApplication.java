package ch.usi.si.bsc.sa4.lab02spring;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile.TileDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.BridgeTile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.GrassTile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.WaterTile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab02SpringApplication {

	public static void main(String[] args) {
		//SpringApplication.run(Lab02SpringApplication.class, args);
		Tile water = new WaterTile(0,0,0);
		Tile bridge = new BridgeTile(0,1,0);
		
		TileDTO waterDTO = water.toTileDTO();
		TileDTO grassDTO = bridge.toTileDTO();
		
		System.out.println("Tile water: " + water.toTileDTO().getType());
		
	}

}
