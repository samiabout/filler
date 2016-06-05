package filler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import filler.Menu.BoutonListener;
import filler.Menu.Bouton2Listener;

public class Menu extends JFrame {

	private JButton bouton = new JButton("Jouer");
	private JButton bouton2 = new JButton("Paramètres");
	private Background background = new Background();
	private JPanel buttons = new JPanel();

	public Menu() {
		this.setTitle("Filler");
		this.setSize(500, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		background.setLayout(new BorderLayout());
		bouton.addActionListener(new BoutonListener());
		bouton2.addActionListener(new Bouton2Listener());
		bouton.setPreferredSize(new Dimension(125, 25));
		bouton2.setPreferredSize(new Dimension(125, 25));
		this.setContentPane(background);
		buttons.add(bouton, BorderLayout.WEST);
		buttons.add(bouton2, BorderLayout.EAST);
		background.add(buttons, BorderLayout.SOUTH);
		buttons.setOpaque(false);
		this.setVisible(true);
	}

	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			BoardFrame boardFrame = new BoardFrame();
		}
	}

	class Bouton2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ParametersFrame parametersFrame = new ParametersFrame();
		}
	}
}