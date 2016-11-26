package view;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class View implements ActionListener {
	JFrame frame;
	MyDrawPanel drawPanel;

	class MyDrawPanel extends JPanel {

		public void paintComponent(Graphics g) {
			g.fillRect(0, 0, this.getHeight(), this.getWidth());

			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);

			Color randomColor = new Color(red, green, blue);

			g.setColor(randomColor);

			g.fillOval(0, 0, this.getHeight(), this.getWidth());
		}

		public void repaint(Graphics g) {

			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);

			Color randomColor = new Color(red, green, blue);

			g.setColor(randomColor);
			g.fillOval(0, 0, this.getHeight() / 2, this.getWidth() / 2);
		}
	}

	public void go() {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton("klik");
		button.addActionListener(this);

		drawPanel = new MyDrawPanel();

		frame.getContentPane().add(BorderLayout.SOUTH, button);
		frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		frame.setSize(700, 700);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		View view = new View();
		view.go();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();

	}

}
