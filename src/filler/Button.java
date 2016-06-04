package filler;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Button extends JButton {
	private String name;

	public Button(String str) {
		super(str);
		this.name = str;
	}

	public void mouseReleased(MouseEvent event) {

	}
}
