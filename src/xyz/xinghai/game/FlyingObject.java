package xyz.xinghai.game;

import java.awt.image.BufferedImage;

/**
 * ��������ĳ�����
 */
public abstract class FlyingObject {
	//�������ͼƬ
	protected BufferedImage image; 
	//������Ŀ��
	protected int width;
	//������ĸ߶�
	protected int height;
	//�������x����
	protected int x;
	//�������y����
	protected int y;
	
	//��������ƶ�
	public abstract void move();
	
	//�Ƿ�ɳ���Ϸ����
	public abstract boolean outOfBounds();
	
	//�ж��Ƿ񱻻���
	public boolean shootBy(Bullet bullet) {
		int x = bullet.x;
		int y = bullet.y;
		
		return (x > this.x && x < (this.x + this.width)) && (y > this.y && y < (this.y + this.height));
	}
}
