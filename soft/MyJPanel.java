/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pokemon;

/**
 *
 * @author lenovo
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 *
 * @author lenovo
 */
public class MyJPanel extends JPanel{

    private int x = 0;
    private int y = 0;
    
    public MyJPanel() {
        setPreferredSize(new Dimension(400,400));
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(x, y, 30, 30);
    }

    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        switch (k) {
            case KeyEvent.VK_D:
                x++;
                break;
            case KeyEvent.VK_A:
                x--;
                break;
            case KeyEvent.VK_W:
                y--;
                break;
            case KeyEvent.VK_S:
                y++;
                break;
        }
    }
}
