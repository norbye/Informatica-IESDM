package ejercicio_1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

	int michi_x_origen = 400;
	int michi_x = michi_x_origen;
	int michi_y_origen = 300;
	int michi_y = michi_y_origen;
	int[] pelotas_x = new int[]{10, 1000};
	int[] pelotas_y = new int[]{400, 400, };
	int[] pelotas_multipler_y = new int[]{0, 0, 0};
	String[] pelotas_img_src = new String[]{"/manzana.png", "/banana.jpg"};
	boolean[] pelotas_is_eaten = new boolean[]{false, false};
	static int multipler_michi_x = 0;
	static int multipler_michi_y = 0;
	static int frame_height;
	static int frame_width;
	BufferedImage michi;
	BufferedImage[] pelotas = new BufferedImage[2];
	
	private void moveBall(){
		//Pelotas
		for(int i = 0; i < pelotas.length; i++){
			if(pelotas_y[i] <= 0){
				if(pelotas_multipler_y[i] < 0){
					pelotas_multipler_y[i] = pelotas_multipler_y[i] * (-1);
				}
			}else if(pelotas[i] != null && pelotas_y[i] >= frame_height - pelotas[i].getHeight()){
				if(pelotas_multipler_y[i] > 0){
					pelotas_multipler_y[i] = pelotas_multipler_y[i] * (-1);
				}
			}
			pelotas_y[i] = pelotas_y[i] + pelotas_multipler_y[i];
			//Calcula tocas
			if(michi != null && pelotas[i] != null){
				if(pelotas_x[i] < michi_x + michi.getWidth() && michi_x < pelotas_x[i] + pelotas[i].getWidth()){
					if(pelotas_y[i] < michi_y + michi.getHeight() && michi_y < pelotas_y[i] + pelotas[i].getHeight()){
						//Ha tocado una de las cosas
						if(!pelotas_is_eaten[i]){
							final JComponent[] inputs = new JComponent[] {
									new JLabel("��Ummmm, qu� rico!!")
							};
							JOptionPane.showMessageDialog(null, inputs, "Mmmmm", JOptionPane.PLAIN_MESSAGE);
							pelotas_is_eaten[i] = true;
						}
					}
				}
			}
			
		}
		//Bloquear movimiento de michi afuera de la pantalla
		if(multipler_michi_x > 0){
			
		}else{
			if(michi != null && michi_x <= 0){
				michi_x = 0;
				multipler_michi_x = 0;
			}
		}
		if(multipler_michi_y > 0){
			if(michi != null && michi_y >= frame_height - michi.getHeight()){
				multipler_michi_y = 0;
				michi_y = frame_height - michi.getHeight();
			}
		}else{
			if(michi != null && michi_y <= 0){
				multipler_michi_y = 0;
				michi_y = 0;
			}
		}
		michi_x = michi_x + multipler_michi_x;
		michi_y = michi_y + multipler_michi_y;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		try {
			michi = ImageIO.read(getClass().getResource("/michi.png"));
			for(int i = 0; i < pelotas.length; i++){
				pelotas[i] = ImageIO.read(getClass().getResource(pelotas_img_src[i]));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform michi_at = new AffineTransform();
        michi_at.translate(michi_x, michi_y);
        g2d.drawImage(michi, michi_at, null);
        //Pelota
        for(int i = 0; i < pelotas.length; i++){
        	if(!pelotas_is_eaten[i]){
	        	AffineTransform pelota_at = new AffineTransform();
		        pelota_at.translate(pelotas_x[i], pelotas_y[i]);
		        g2d.drawImage(pelotas[i], pelota_at, null);
        	}
        }
	}
	
	public static void main(String[] args) throws InterruptedException, LineUnavailableException{
		JFrame frame = new JFrame("EJERCICIO 1");
		Main game = new Main();
		frame.add(game);
		frame.setSize(1000, 600);
		//Start in fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        	
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	        	/*
	             * W = 81
	             * E = 69
	             * R = 82
	             * Space = 32
	             * Flecha izquierda = 37
	             * Flecha derecha = 39
	             * Flecha arriba = 38
	             * Flecha abajo = 40
	             */
	        	if(e.getKeyCode() == 39){
	        		multipler_michi_x = 3;
	        	}else if(e.getKeyCode() == 37){
	        		multipler_michi_x = -3;
	        	}else if(e.getKeyCode() == 38){
        			multipler_michi_y = -3;
	        	}else if(e.getKeyCode() == 40){
	        		multipler_michi_y = 3;
	        	}
	        }

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == 39 || e.getKeyCode() == 37){
	        		multipler_michi_x = 0;
	        	}else if(e.getKeyCode() == 38 || e.getKeyCode() == 40){
	        		multipler_michi_y = 0;
	        	}
			}
        });
		
		while(true){
			frame_height = frame.getContentPane().getHeight();
			frame_width = frame.getContentPane().getWidth();
			game.moveBall();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
