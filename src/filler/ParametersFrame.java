package filler;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import filler.Menu.BoutonListener;

public class ParametersFrame extends JFrame {

	private CardLayout cl = new CardLayout();
	private JPanel content = new JPanel();
	private String[] listContent = { "CARD_1", "CARD_2" };
	private JPanel container = new JPanel();
	String[] tab = { "Humain", "IA niveau 1", "IA niveau 2", "IA niveau 3", "IA niveau 4" };
	String[] tab2 = { "Aucun", "Humain", "IA niveau 1", "IA niveau 2", "IA niveau 3", "IA niveau 4" };
	String[] tab3 = { "Carr�", "Hexagonal" };
	private JComboBox joueur1 = new JComboBox(tab);
	private JComboBox joueur2 = new JComboBox(tab);
	private JComboBox joueur3 = new JComboBox(tab2);
	private JComboBox joueur4 = new JComboBox(tab2);
	private JComboBox modeDeJeu = new JComboBox(tab3);
	private JComboBox[] listeJoueurs = { joueur1, joueur2, joueur3, joueur4 };
	private JLabel label1 = new JLabel("Joueur 1");
	private JLabel label2 = new JLabel("Joueur 2");
	private JLabel label3 = new JLabel("Joueur 3");
	private JLabel label4 = new JLabel("Joueur 4");
	private JLabel label5 = new JLabel("Mode de jeu");
	private JCheckBox checkIslets = new JCheckBox("Ilots   ");
	private JCheckBox checkObstacles = new JCheckBox("Obstacles");
	private JTextField jtf1 = new JTextField("20");
	private JLabel label6 = new JLabel("    Longueur  ");
	private JTextField jtf4 = new JTextField("20");
	private JLabel label8 = new JLabel("    Largeur  ");
	private JTextField jtf2 = new JTextField();
	private JLabel label7 = new JLabel("Pourcentage d'obstacles (%)  ");
	private JCheckBox checkSave = new JCheckBox("Charger une sauvegarde ");
	private JTextField jtf3 = new JTextField();

	public ParametersFrame() {
		this.setTitle("Param�tres");
		this.setSize(350, 260);
		this.setLocationRelativeTo(null);
		joueur1.setPreferredSize(new Dimension(100, 20));
		joueur2.setPreferredSize(new Dimension(100, 20));
		joueur3.setPreferredSize(new Dimension(100, 20));
		joueur4.setPreferredSize(new Dimension(100, 20));
		modeDeJeu.setPreferredSize(new Dimension(100, 20));

		jtf3.setPreferredSize(new Dimension(100, 25));
		jtf2.setPreferredSize(new Dimension(25, 25));
		jtf1.setPreferredSize(new Dimension(25, 25));
		jtf2.setEnabled(false);
		jtf3.setEnabled(false);

		checkObstacles.addActionListener(new StateListener1());
		checkSave.addActionListener(new StateListener2());

		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();

		JButton validation1 = new JButton("Valider");
		validation1.addActionListener(new BoutonListener());
		validation1.setPreferredSize(new Dimension(175, 25));
		JButton validation2 = new JButton("Valider");
		validation2.addActionListener(new BoutonListener());
		validation2.setPreferredSize(new Dimension(175, 25));

		JPanel field1 = new JPanel();
		field1.add(label1);
		field1.add(joueur1);
		JPanel field2 = new JPanel();
		field2.add(label2);
		field2.add(joueur2);
		JPanel field3 = new JPanel();
		field3.add(label3);
		field3.add(joueur3);
		JPanel field4 = new JPanel();
		field4.add(label4);
		field4.add(joueur4);

		JPanel field5 = new JPanel();
		field5.add(label5);
		field5.add(modeDeJeu);

		card1.add(field1);
		card1.add(field2);
		card1.add(field3);
		card1.add(field4);
		card1.add(validation1);

		JPanel b1 = new JPanel();
		b1.setLayout(new BoxLayout(b1, BoxLayout.LINE_AXIS));
		b1.add(field5);

		JPanel b2 = new JPanel();
		b2.setLayout(new BoxLayout(b2, BoxLayout.LINE_AXIS));
		b2.add(label6);
		b2.add(jtf1);
		b2.add(label8);
		b2.add(jtf4);

		JPanel b3 = new JPanel();
		b3.setLayout(new BoxLayout(b3, BoxLayout.LINE_AXIS));
		b3.add(checkIslets);
		b3.add(checkObstacles);

		JPanel b4 = new JPanel();
		b4.setLayout(new BoxLayout(b4, BoxLayout.LINE_AXIS));
		b4.add(label7);
		b4.add(jtf2);

		JPanel b5 = new JPanel();
		b5.setLayout(new BoxLayout(b5, BoxLayout.LINE_AXIS));
		b5.add(checkSave);
		b5.add(jtf3);

		JPanel b6 = new JPanel();
		b6.setLayout(new BoxLayout(b6, BoxLayout.PAGE_AXIS));
		b6.add(b1);
		b6.add(b2);
		b6.add(b3);
		b6.add(b4);
		b6.add(b5);

		card2.add(b6);
		card2.add(validation2);

		JPanel buttonPanel = new JPanel();
		JButton bouton = new JButton("Options des joueurs");
		bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cl.show(content, listContent[0]);
			}
		});

		JButton bouton2 = new JButton("Options du plateau");
		bouton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cl.show(content, listContent[1]);
			}
		});

		buttonPanel.add(bouton);
		buttonPanel.add(bouton2);
		content.setLayout(cl);
		content.add(card1, listContent[0]);
		content.add(card2, listContent[1]);

		this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		this.getContentPane().add(content, BorderLayout.CENTER);
		content.setOpaque(false);
		this.setVisible(true);
	}

	class StateListener1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jtf2.setEnabled(((JCheckBox) e.getSource()).isSelected());
		}
	}

	class StateListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			jtf3.setEnabled(((JCheckBox) e.getSource()).isSelected());
		}
	}

	class BoutonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			int length = Integer.parseInt(jtf1.getText());
			Menu.game1.setLength(length);
			
			int height = Integer.parseInt(jtf4.getText());
			Menu.game1.setHeight(height);
			
			int nbPlayers = 0;
			for (int i = 0; i < listeJoueurs.length; i++) {
				if (listeJoueurs[i].getSelectedItem() != "Aucun") {
					nbPlayers++;
				}
				if ((listeJoueurs[i].getSelectedItem() != "Aucun") && (listeJoueurs[i].getSelectedItem() != "Humain")) {
					Menu.game1.playerIA[i] = true;
				}
				if (listeJoueurs[i].getSelectedItem() == "IA niveau 1") {
					Menu.game1.ialevel[i] = 0;
				}
				if (listeJoueurs[i].getSelectedItem() == "IA niveau 2") {
					Menu.game1.ialevel[i] = 1;
				}
				if (listeJoueurs[i].getSelectedItem() == "IA niveau 3") {
					Menu.game1.ialevel[i] = 2;
				}
				if (listeJoueurs[i].getSelectedItem() == "IA niveau 4") {
					Menu.game1.ialevel[i] = 3;
				}
			}
			
			Menu.game1.nbPlayers = nbPlayers;

			if (modeDeJeu.getSelectedItem() == "Hexagonal") {
				Menu.game1.setHexagonal(true);
			} else {
				Menu.game1.setHexagonal(false);
			}
			Menu.game1.setIslet(checkIslets.isSelected());
			Menu.game1.setObstacles(checkObstacles.isSelected());
			Menu.game1.setGetOldSave(checkSave.isSelected());
			
			if (checkObstacles.isSelected()) {
				int obstaclesAmount = Integer.parseInt(jtf2.getText());
				Menu.game1.setObstaclesAmount(obstaclesAmount);
			}
		}
	}
}
