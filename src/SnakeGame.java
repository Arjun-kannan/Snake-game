import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeGame extends JFrame implements KeyListener {
    SnakeGame(){
        this.setTitle("Snake game");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new GamePanel());
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.addKeyListener(this);
    }
    public static void main(String[] args){
        new SnakeGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_R)
            new SnakeGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
