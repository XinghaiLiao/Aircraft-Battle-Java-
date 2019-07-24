package xyz.xinghai.game;

import java.util.Random;

import xyz.xinghai.game.bean.ImgBean;

/**
 * 打败后奖励积分更多
 *
 */
public class BigBee extends FlyingObject implements Enemy {
	private int speed = 1;
	
	public BigBee() {
		this.image = ImgBean.bigBeeImg;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		Random random = new Random();
		x = random.nextInt(AircraftBattle.WIDTH - this.width);
		y = -this.height;
	}

	@Override
	public void move() {
		y += speed;
	}

	@Override
	public boolean outOfBounds() {
		return this.y >= AircraftBattle.HEIGHT;
	}

	@Override
	public int getScore() {
		return 40;
	}
}
