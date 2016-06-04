package filler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NbIAs extends JFrame {
	private Panel pan = new Panel();
	private JPanel container = new JPanel();

	public NbIAs() {
		this.setTitle("Filler");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);

		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		container.add(pan, BorderLayout.CENTER);

		this.setVisible(true);
	}
}