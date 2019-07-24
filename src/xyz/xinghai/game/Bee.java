package xyz.xinghai.game;

import java.util.Random;

import xyz.xinghai.game.bean.ImgBean;

/**
 * 打败后获得子弹加成
 * @author 15321
 *
 */
public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1;
	private int ySpeed = 2;
	private int awardType;
	
	public int getType() {
		return awardType;
	}
	
	public Bee() {
		this.image = ImgBean.beeImg;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		Random rand=new Random();
		this.x = rand.nextInt(AircraftBattle.WIDTH-this.width);
		this.y = -this.height;
		awardType=rand.nextInt(2);
	}

	@Override
	public void move() {
		y += ySpeed;
		x += xSpeed;
		if(x >= AircraftBattle.WIDTH-this.width){
			xSpeed =-1;
		}
		if(x <= 0){
			xSpeed =1;
		}
	}

	@Override
	public boolean outOfBounds() {
		return this.height >= AircraftBattle.HEIGHT;
	}	
}
