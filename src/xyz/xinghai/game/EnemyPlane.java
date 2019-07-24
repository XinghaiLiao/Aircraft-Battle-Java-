package xyz.xinghai.game;

import java.util.Random;

import xyz.xinghai.game.bean.ImgBean;

/**
 * 敌机类
 *
 */
public class EnemyPlane extends FlyingObject implements Enemy {
	//速度
	private int speed = 2;
	//得分
	private int score = 5;
	
	public EnemyPlane() {
		this.image = ImgBean.enemyImg;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		//随机初始化敌机位置
		Random random = new Random();
		x = random.nextInt(AircraftBattle.WIDTH - this.width);
		y = -this.height;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public void move() {
		y += speed;
	}

	@Override
	public boolean outOfBounds() {
		return this.y >= AircraftBattle.HEIGHT;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
