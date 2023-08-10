import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BouncingBallGameWithRetry extends JPanel implements ActionListener, KeyListener {
    private int ballX = 100;
    private int ballY = 100;
    private int ballSize = 20;
    private int ballSpeedX = 2;
    private int ballSpeedY = 2;

    private int paddleX = 150;
    private int paddleY = 350;
    private int paddleWidth = 100;
    private int paddleHeight = 10;

    private boolean gameRunning = true;
    private boolean gameOver = false;

    private Timer timer;
    private JButton retryButton;

    public BouncingBallGameWithRetry() {
        timer = new Timer(10, this); // 10 milliseconds interval
        timer.start();
        addKeyListener(this);
        setFocusable(true);
setBackground(Color.YELLOW);

        retryButton = new JButton("Retry");
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        retryButton.setVisible(false);
        add(retryButton);
    }

    private void restartGame() {
        gameRunning = true;
        gameOver = false;
        retryButton.setVisible(false);
        ballX = 100;
        ballY = 100;
        ballSpeedX = 2;
        ballSpeedY = 2;
        paddleX = 150;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning) {
            retryButton.setVisible(true);
            return;
        }

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Bounce off the walls
        if (ballX <= 0 || ballX >= getWidth() - ballSize) {
            ballSpeedX = -ballSpeedX;
        }
        if (ballY <= 0) {
            ballSpeedY = -ballSpeedY;
        } else if (ballY >= getHeight() - ballSize) {
            if (ballX >= paddleX && ballX <= paddleX + paddleWidth) {
                ballSpeedY = -ballSpeedY;
            } else {
                gameRunning = false;
                gameOver = true;
            }
        }

        repaint(); // Request repaint to update the animation
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBall(g);
        drawPaddle(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Game Over", getWidth() / 2 - 70, getHeight() / 2);
        }
    }

    private void drawBall(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(ballX, ballY, ballSize, ballSize);
    }

    private void drawPaddle(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            paddleX -= 20;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            paddleX += 20;
        }

        // Ensure paddle stays within the frame
        paddleX = Math.max(0, Math.min(getWidth() - paddleWidth, paddleX));

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball Game");
        BouncingBallGameWithRetry game = new BouncingBallGameWithRetry();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
	frame.setBackground(Color.yellow);
    }
}
