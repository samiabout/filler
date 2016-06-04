package filler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JFrame {

	private Panel pan = new Panel();
	private JButton bouton = new JButton("Multijoueur");
	private JButton bouton2 = new JButton("Contre IA");
	private JButton bouton3 = new JButton("Paramètres");
	private JPanel container = new JPanel();

	public Menu() {
		this.setTitle("Filler");
		this.setSize(100, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		container.setLayout(new FlowLayout());

		// Ce sont maintenant nos classes internes qui écoutent nos boutons
		bouton.addActionListener(new BoutonListener());
		bouton2.addActionListener(new Bouton2Listener());
		bouton3.addActionListener(new Bouton3Listener());
		bouton.setPreferredSize(new Dimension(125, 25));
		bouton2.setPreferredSize(new Dimension(125, 25));
		bouton3.setPreferredSize(new Dimension(125, 25));
		container.add(bouton);
		container.add(bouton2);
		container.add(bouton3);

		Font police = new Font("Tahoma", Font.BOLD, 16);
		this.setContentPane(container);
		this.setVisible(true);
	}

	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			BoardFrame fenetrePlateau = new BoardFrame();
		}
	}

	class Bouton2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BoardFrame fenetrePlateau = new BoardFrame();
		}
	}

	class Bouton3Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BoardFrame fenetrePlateau = new BoardFrame();
		}
	}
}