package xyz.xinghai.game;

import java.util.Random;

import xyz.xinghai.game.bean.ImgBean;

/**
 * �л���
 *
 */
public class EnemyPlane extends FlyingObject implements Enemy {
	//�ٶ�
	private int speed = 2;
	//�÷�
	private int score = 5;
	
	public EnemyPlane() {
		this.image = ImgBean.enemyImg;
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		//�����ʼ���л�λ��
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
