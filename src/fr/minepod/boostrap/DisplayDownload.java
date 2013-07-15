package fr.minepod.boostrap;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class DisplayDownload extends JFrame
{
  private JProgressBar current = new JProgressBar(0, 2000);
  JTextArea out;
  JButton find;
  Thread runner;

  public DisplayDownload()
  {
    JFrame JFrame = new JFrame();

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