import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int TILE_SIZE = 25;
    private final int GRID_HEIGHT = 600 / TILE_SIZE;
    private final int GRID_WIDTH = 600 / TILE_SIZE;
    int speed = 200;
    private ArrayList<Point> snake;
    private Point food;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
//    JLabel score = new JLabel();

    public GamePanel(){
        this.setPreferredSize(new Dimension(600, 600));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        setDoubleBuffered(true);
//        score.setText("Score: " + 0);
//        this.add(score);
        startGame();
    }

    private void startGame(){
        snake = new ArrayList<>();
        snake.add(new Point(5,5));
        spawnFood();
        running = true;
        timer = new Timer(speed, this);
        timer.start();
    }

    private void spawnFood(){
        Random rand = new Random();
        food = new Point(rand.nextInt(GRID_HEIGHT), rand.nextInt(GRID_WIDTH));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g){
        if(running){
            //Score display
            g.setColor(Color.darkGray);
            g.setFont(new Font("Arial", Font.BOLD, 100));
            String currentScore = Integer.toString(snake.size());
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            // Calculate center position
            int x = (getWidth() - metrics.stringWidth(currentScore)) / 2;
            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

            g.drawString(currentScore, x, y);
        }
            //Food
            g.setColor(Color.red);
            g.fillRect(food.x * TILE_SIZE, food.y*TILE_SIZE, TILE_SIZE, TILE_SIZE);

            //snake
            for(int i = 0; i<snake.size(); i++){
                Point segment = snake.get(i);
                if(i%2 == 0) g.setColor(Color.GREEN);
                else g.setColor(new Color(0, 100, 0));
                g.fillRect(segment.x * TILE_SIZE, segment.y*TILE_SIZE, TILE_SIZE, TILE_SIZE );
            }
        if(!running){
            gameOver(g);
        }
    }

    private void gameOver(Graphics g){
        g.setColor(Color.gray);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String message = "Game Over";
        String scoreMessage = "Score: " + snake.size();

        FontMetrics metrics = g.getFontMetrics(g.getFont());

        // Calculate horizontal center position
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

        g.drawString(message, x, y);

        int scoreX = (getWidth() - metrics.stringWidth(scoreMessage)) / 2;
        int scoreY = y + metrics.getHeight();

        g.drawString(scoreMessage, scoreX, scoreY);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
//            displayScore();
            move();
            checkCollision();
            repaint();
        }
    }

//    private void displayScore(){
//        score.setText("Score:" + snake.size());
//    }

    private void move(){
        Point newHead = new Point(snake.get(0));
        switch(direction){
            case 'U': newHead.y--; break;
            case 'D': newHead.y++; break;
            case 'R': newHead.x++; break;
            case 'L': newHead.x--; break;
        }

        snake.add(0, newHead);

        if(newHead.equals(food)){
            if(speed >= 80) {
                speed -= 20;
                timer.setDelay(speed);
            }
            spawnFood();
        }
        else{
            snake.remove(snake.size() -1); //removes the tail
        }
    }

    private void checkCollision(){
        Point head = snake.get(0);
        if(head.x < 0){
            head.x = GRID_WIDTH;
        } else if (head.y < 0) {
            head.y = GRID_HEIGHT;
        } else if (head.x > GRID_WIDTH) {
            head.x = 0;
        } else if (head.y > GRID_HEIGHT) {
            head.y = 0;
        }

        for(int i = 1; i< snake.size(); i++){
            if(snake.get(i).equals(head)){
                running = false;
                timer.stop();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!running && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)) {
            restartGame();
        }

        switch(e.getKeyCode()){
            case KeyEvent.VK_UP: if(direction != 'D') direction = 'U'; break;
            case KeyEvent.VK_DOWN: if(direction != 'U') direction = 'D'; break;
            case KeyEvent.VK_LEFT: if(direction != 'R') direction = 'L'; break;
            case KeyEvent.VK_RIGHT:  if(direction != 'L') direction = 'R'; break;
        }
    }

    private void restartGame(){
        snake.clear();
        snake.add(new Point(5,5));
        direction = 'R';
        spawnFood();
        running = true;
        speed = 200;
        timer = new Timer(speed, this);
        timer.start();

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
