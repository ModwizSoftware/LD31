package com.modwiz.ld31.entities;

import com.modwiz.ld31.entities.draw.*;
import com.modwiz.ld31.world.Dimension;

public class GameObjectFactory {
	
	/**
		Make a default GameObject
		@return A default GameObject
	*/
	public GameObject createGameObject() {
		return new GameObject(null, 0, 0);
	}
	
	public GameObject createGameObject(Dimension parent, float x, float y) {
		return new GameObject(parent, x ,y);
	}
	
	
	/**
		Make a default GameBlock
		@return A default GameBlock
	*/
	public GameBlock createGameBlock() {
		return new GameBlock(null, 0, 0, 100, 100);
	}
	
	/**
	 *	Make a Wall object
	 *	@return A default wall
	 */
	public GameBlock createWall() {
		GameBlock wall = createGameBlock();
		wall.setWidth(50);
		wall.setHeight(50);
		wall.setName("Wall");
		return wall;
	}
	
	/**
	 * Make a melee Enemy
	 * @return a melee Enemy 
	 */
	public Enemy createMeleeEnemy() {
		Enemy enemy = createEnemy();
		enemy.setName("Melee Enemy");
		enemy.setWeapon(new Weapon(enemy, 35, 10, 42));
		return enemy;
	}
	
	/**
	 * Make a ranged Enemy
	 * @return a ranged Enemy 
	 */
	public Enemy createRangedEnemy() {
		Enemy enemy = createEnemy();
		enemy.setName("Ranged Enemy");
		enemy.setWeapon(new Weapon(enemy, 444, 20, 83));
		return enemy;
	}
	
	/**
	 * Creates a basic text block
	 * @return a basic text block
	 */
	public TextBlock createTextBlock() {
		TextBlock block = new TextBlock(null, 0, 0, 75, 75, "Text", 12);
		block.setName("Text Block");
		return block;
	}
	
	public GameBlock createGameBlock(Dimension parent, float x, float y, float w, float h) {
		return new GameBlock(parent, x, y, w, h);
	}
	
	
	/**
		Make a default Creature
		@return A default Creature
	*/
	public Creature createCreature() {
		return new Creature(null, 0, 0, 100, 100, 10);
	}
	
	public Creature createCreature(Dimension parent, float x, float y, float w, float h, double health) {
		return new Creature(parent, x, y, w, h, health);
	}
	
	public Creature createCreature(Dimension parent, float x, float y, float w, float h, double health, Animation anim) {
		return new Creature(parent, x, y, w, h, health, anim);
	}

	
	/**
		Make a default Player
		@return A default Player
	*/
	public Player createPlayer() {
		return new Player(null, 0, 0, 100, 100, 10);
	}
	
	public Player createPlayer(Dimension parent, float x, float y, float w, float h, double health) {
		return new Player(parent, x, y, w, h, health);
	}
	
	public Player createPlayer(Dimension parent, float x, float y, float w, float h, double health, Animation anim) {
		return new Player(parent, x, y, w, h, health, anim);
	}
	
	/**
		Make a default Enemy
		@return A default Enemy
	*/
	public Enemy createEnemy() {
		return new Enemy(null, 0, 0, 100, 100, 10);
	}
	
	public Enemy createEnemy(Dimension parent, float x, float y, float w, float h, double health) {
		return new Enemy(parent, x, y, w, h, health);
	}
	
	public Enemy createEnemy(Dimension parent, float x, float y, float w, float h, double health, Animation anim) {
		return new Enemy(parent, x, y, w, h, health, anim);
	}
	
	
	/**
		Make a default DNARepairCell
		@return A default DNARepairCell
	*/
	public DNARepairCell createDNARepairCell() {
        DNARepairCell dnar = new DNARepairCell(null, 0, 0);
        dnar.setImageString("img/generepair.png");
        return dnar;
	}
	
	public DNARepairCell createDNARepairCell(Dimension parent, float x, float y) {
        DNARepairCell dnar = new DNARepairCell(parent, x, y);
        dnar.setImageString("img/generepair.png");
        return dnar;
	}
	
	/**
		Make a default DNARepairCell
		@return A default DNARepairCell
	*/
	public RadiationSucker createRadiationSucker() {
		return new RadiationSucker(null, 0, 0);
	}
	
	public RadiationSucker createRadiationSucker(Dimension parent, float x, float y) {
		return new RadiationSucker(parent, x, y);
	}
	
	/**
		Make a default DimensionChangeBlock
		@return A default DimensionChangeBlock
	*/
	public DimensionChangeBlock createDimensionChangeBlock() {
		return new DimensionChangeBlock(null, 0, 0, 100, 100, null, null);
	}
	
	public DimensionChangeBlock createDimensionChangeBlock(Dimension parent, float x, float y, float width, float height, Dimension dimTo) {
		return new DimensionChangeBlock(parent, x, y, width, height, dimTo);
	}
	
	
	/**
		Make a default DimensionChangeBlock
		@return A default DimensionChangeBlock
	*/
	public MessageBlock createMessageBlock() {
		MessageBlock mb = new MessageBlock(null, 0, 0, 100, 100, new String[]{"Default message", "Another default message"}, null);
		mb.setName("Message Block");
		return  mb;
	}
	
	public MessageBlock createMessageBlock(Dimension parent, float x, float y, float width, float height, String[] msgs) {
		MessageBlock mb = new MessageBlock(parent, x, y, width, height, msgs, null);
		mb.setName("Message Block");
		return  mb;
	}
	
}