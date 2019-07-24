package xyz.xinghai.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import xyz.xinghai.game.bean.GameStateBean;
import xyz.xinghai.game.bean.ImgBean;

/**
 * ��������
 * @author 15321
 *
 */
public class AircraftBattle extends JPanel {
	private static final long serialVersionUID = 1L;
	//��Ϸ������
	public static final int WIDTH = 400;
	//��Ϸ����߶�
	public static final int HEIGHT = 645;
	
	private int state = GameStateBean.START;
	//���ɻ�
	private Hero hero = new Hero();
	//�л�����
	private FlyingObject[] flyings = {};
	//�ӵ�����
	private Bullet[] bullets = {};
	//�÷�
	private int score = 0;
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(ImgBean.backgroundImg, 0, 0, null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreAndLife(g);
		patintState(g);
	}
	
	/**
	 * ���÷ֺ�����
	 * @param g ����
	 */
	private void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
		g.drawString("SCORE:" + score, 10, 25);
		g.drawString("LIFE:"+ hero.getLife(), 10, 50);
		g.drawString("DoubleFire:" + hero.getDoubleFire(), 10, 75);
	}
	
	/**
	 * �����ɻ�
	 * @param g ����
	 */
	private void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	
	/**
	 * ����Ļ�еĵл��ȷ�������
	 * @param g ����
	 */
	private void paintFlyingObjects(Graphics g){
		for(FlyingObject f : flyings)
			g.drawImage(f.image, f.x, f.y, null);
	}
	
	/**
	 * ���ӵ�
	 * @param g ����
	 */
	private void paintBullets(Graphics g){
		for(Bullet b : bullets)
			g.drawImage(b.image, b.x, b.y, null);
	}
	
	/**
	 * ������Ϸ״̬ 
	 * @param g ����
	 */
	private void patintState(Graphics g) {
    	switch(state){
    		case GameStateBean.START:
    			g.drawImage(ImgBean.startImg, 0, 0, null);
    			break;
    		case GameStateBean.PAUSE:
    			g.drawImage(ImgBean.pauseImg, 0, 0, null);
    			break;
    		case GameStateBean.GAMEOVER:
    			g.drawImage(ImgBean.gameOverImg, 0, 0, null);
    	}
    }
	
	/**
	 * ���ɵл�
	 * @return �л�
	 */
	private FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if(type < 4) {
			return new BigBee();
		} else if (type == 5) {
			return new Bee();
		} else {
			return new EnemyPlane();
		}
	}
	
	//�л��볡��index
	int flyEnteredIndex = 0;
	/**
	 * �л��볡��action
	 */
	private void enterAction() {
		flyEnteredIndex++;
		if(flyEnteredIndex % 40 ==0){
			FlyingObject obj = nextOne();
			//���µл�����	
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = obj;
		}
	}
	
	//�ӵ��볡��index
	int shootIndex = 0;
	/**
	 * ���ɻ������ӵ���Action ���ɻ������ӵ����� �ѷ��ص�����ӵ���Ϸ�ӵ����鼴��
	 */
	private void shootAction(){
		shootIndex++;
		if (shootIndex % 30 == 0) {
		   Bullet[] bs = hero.shoot();
		   //�����ӵ�����
		   bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
		   System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		} 
	}
	
	/**
	 *�������ƶ���action ���ø��Ե��ƶ���������
	 */
	private void moveAction(){
		hero.move();
		for(FlyingObject f : flyings) 
			f.move();
		for(Bullet b : bullets)
			b.move();
	}
	
	/**
	 * ������ɳ������action ���ǰѷɳ�����ķ������������ȥ��
	 */
	private void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for(FlyingObject f : flyings) 
			if(!f.outOfBounds()) 
				flyingLives[index++] = f;
		flyings=Arrays.copyOf(flyingLives, index);
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(Bullet b : bullets) 
			if(!b.outOfBounds()) 
				bulletLives[index++] = b;
		bullets=Arrays.copyOf(bulletLives, index);
	}
	
	int j = 0;
	/**
	 * ��ײ��action
	 */
	private void collisionAction() {
		for(int i=0;i < bullets.length;i++){
			collision(bullets[i],i);
		}
	}
	
	/**
	 * ��ײ
	 * @param b �ӵ�
	 * @param bu ��ײ���ӵ����ӵ������е�index
	 */
	private void collision(Bullet b, int bu) {
		int index = -1;
		
		for(int i=0;i<flyings.length;i++){
			FlyingObject f = flyings[i];
			if (f.shootBy(b)) {
				index = i;
				break;
			} 
		}
		
		if(index != -1) {
			FlyingObject one = flyings[index];
			
			if(one instanceof EnemyPlane){
				Enemy e = (Enemy)one;
				score += e.getScore();
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings=Arrays.copyOf(flyings, flyings.length-1);//ȥ�����ӵ�ײ�ϵĵ���
			}
			if(one instanceof BigBee){//�����²Ż��
				j++;			
				if(j==5){
					Enemy e = (Enemy)one;
					score += e.getScore();
					j = 0;
					FlyingObject t = flyings[index];
					flyings[index] = flyings[flyings.length-1];
					flyings[flyings.length-1] = t;
					flyings = Arrays.copyOf(flyings, flyings.length-1);//ȥ�����ӵ�ײ�ϵĵ���
				}
			}
			if(one instanceof Award){
				Award a = (Award)one;
				int type = a.getType();
				switch(type){
					case Award.DOUBLE_FIRE://����Ϊ����ֵ
						hero.addDoubleFire();
						break;
					case Award.LIFE://����Ϊ����
						hero.addLife();
						break;
				}
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);//ȥ�����ӵ�ײ�ϵĵ���
			}
			
			Bullet tBullet = bullets[bu];
			bullets[bu] = bullets[bullets.length-1];
			bullets[bullets.length-1] = tBullet;
			bullets=Arrays.copyOf(bullets, bullets.length-1);//ȥ���ӵ�
		}
	}
	
	/**
	 * �����Ϸ�Ƿ����
	 */
	public void checkGameOverAction(){
		if(GameOver()){
			state = GameStateBean.GAMEOVER;
		}
	}
	
	private boolean GameOver() {
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			if(hero.hit(f)){
				hero.subtractLife();
				hero.clealDoubleFire();
				FlyingObject t = flyings[i];//����ײ����������һ��Ԫ�ؽ���
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = t;
				flyings = Arrays.copyOf(flyings, flyings.length-1);//ȥ�����ӵ�ײ�ϵĵ���
			}
		}
		return hero.getLife() <= 0;//����С�ڵ���0����
	}
	
	/**
	 * ���������action
	 */
	public void action(){
		MouseAdapter l = new MouseAdapter() {
			//��д����ƶ��¼�
			public void mouseMoved(MouseEvent e){
				if(state == GameStateBean.RUNNING){
					hero.moveTo(e.getX(),e.getY());
				}
			}
			//������¼�
			public void mouseClicked(MouseEvent e){
				switch(state){
					case GameStateBean.START:
						state= GameStateBean.RUNNING;
						break;
					case GameStateBean.GAMEOVER:
						state = GameStateBean.START;
						//�����ֳ�
						score = 0;
						hero = new Hero();
						flyings = new FlyingObject[0];
						bullets = new Bullet[0];
						break;
				}
			}
			//����Ƴ�
			public void mouseExited(MouseEvent e){
				if(state == GameStateBean.RUNNING){
					state = GameStateBean.PAUSE;
				}
			}
			//�������
			public void mouseEntered(MouseEvent e){
				if(state == GameStateBean.PAUSE){
					state = GameStateBean.RUNNING;
				}
			}
		};
		this.addMouseListener(l);//�����������¼�
		this.addMouseMotionListener(l);//���Ļ���
		Timer timer = new Timer();//��ʱ��
		int intervel = 10;//��ʱ������Ժ���Ϊ��λ��
		timer.schedule(new TimerTask() {
			public void run(){
				if(state == GameStateBean.RUNNING){
					enterAction();//�����볡
					moveAction();//�����ƶ�
					shootAction();//�ӵ��볡
					collisionAction();//�ӵ��������ײ
					outOfBoundsAction();//ɾ��Խ��ĵ��˺��ӵ�
					checkGameOverAction();//���˺�Ӣ�ۻ���ײ					
				}
				repaint();//���»��ƽ���
			}
		}, intervel, intervel);
	}
	
	public static void main(String[] args) {
		JFrame gameFrame = new JFrame();
		gameFrame.setTitle("AircraftBattle");
		AircraftBattle game = new AircraftBattle();
		gameFrame.getContentPane().add(game);
		gameFrame.setSize(new Dimension(WIDTH, HEIGHT));
		gameFrame.setAlwaysOnTop(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
		game.action();
	}
}
