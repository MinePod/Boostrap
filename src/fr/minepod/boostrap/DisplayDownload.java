package fr.minepod.boostrap;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class DisplayDownload extends JFrame
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JProgressBar current = new JProgressBar(0, 100);
  JTextArea out;
  JButton find;
  Thread runner;

  public DisplayDownload() {
    JPanel pane = new JPanel();
    pane.setBackground(Color.WHITE);
    this.current.setValue(0);
    this.current.setStringPainted(true);
    pane.add(this.current);
    setContentPane(pane);
  }

  public void Update(int UpdateNumber) {
    this.current.setValue(UpdateNumber);
  }
}