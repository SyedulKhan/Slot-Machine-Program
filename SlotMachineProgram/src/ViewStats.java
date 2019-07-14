
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class ViewStats extends JFrame {
    //all JComponents
    private JTextField win;
    private JTextField lose;
    private JTextField avgCreditsWon;
    private JLabel lblWins;
    private JLabel lblLosses;
    private JLabel lblGames;
    private JLabel barGames;
    private JLabel barWins;
    private JLabel barLosses;
    private JButton saveStats;
    //global variables
    private int wins;
    private int losses;
    private int games;
    private int creditsWon;
    private int avg;

    //constructor
    public ViewStats(int games, int wins, int losses, int creditsWon) {
        this.games = games;
        this.wins = wins;
        this.losses = losses;
        this.creditsWon = creditsWon;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(3, 1));
        //panel for wins, losses and credits won
        JPanel p = new JPanel();
        //custom font
        Font myFont = new Font("SansSerif", Font.BOLD, 30);
        Font starFont = new Font("SansSerif", Font.BOLD, 50);
        win = new JTextField("Wins: " + wins);
        win.setEditable(false);
        lose = new JTextField("Losses: " + losses);
        lose.setEditable(false);
        if (creditsWon == 0) {
            avg = 0;
        } else {
            avg = creditsWon / wins;
        }
        avgCreditsWon = new JTextField("Average credits won per game: " + avg);
        avgCreditsWon.setEditable(false);
        win.setFont(myFont);
        lose.setFont(myFont);
        avgCreditsWon.setFont(myFont);
        p.add(win);
        p.add(lose);
        p.add(avgCreditsWon);
        add(p, BorderLayout.NORTH);
        //panels for visual histogram of wins, losses and games played
        //every time a game is played, won or lost the specifed label will be incremented by 1 star
        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        lblGames = new JLabel("Games Played: ");
        lblGames.setFont(myFont);
        p2.add(lblGames);
        for (int i = 0; i < games; i++) {
            barGames = new JLabel("*");
            barGames.setFont(starFont);
            barGames.setForeground(Color.GREEN);
            p2.add(barGames);

        }
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        lblWins = new JLabel("Wins: ");
        lblWins.setFont(myFont);
        p3.add(lblWins);
        for (int i = 0; i < wins; i++) {
            barWins = new JLabel("*");
            barWins.setFont(starFont);
            barWins.setForeground(Color.BLUE);
            p3.add(barWins);
        }
        JPanel p4 = new JPanel();
        p4.setLayout(new FlowLayout());
        lblLosses = new JLabel("losses: ");
        lblLosses.setFont(myFont);
        p4.add(lblLosses);
        for (int i = 0; i < losses; i++) {
            barLosses = new JLabel("*");
            barLosses.setFont(starFont);
            barLosses.setForeground(Color.RED);
            p4.add(barLosses);
        }

        cp.add(p2, BorderLayout.WEST);
        cp.add(p3, BorderLayout.CENTER);
        cp.add(p4, BorderLayout.EAST);
        //JButton to save current statistics
        saveStats = new JButton("Save Statistics");
        cp.add(saveStats, BorderLayout.SOUTH);
        saveStats.setFont(myFont);
        //event handler
        MyListener buttonListener = new MyListener();
        saveStats.addActionListener(buttonListener);
    }

    private class MyListener implements ActionListener {
        //calls the SaveStats class to be performed when user clicks the save button to save statistics
        @Override
        public void actionPerformed(ActionEvent evt) {
            //it passes the current values of each game to the saveStats class
            SaveStats saveStats = new SaveStats(games, wins, losses, creditsWon);
            saveStats.input(games, wins, losses, creditsWon);
            //confirms statistics have been saved
            JOptionPane.showMessageDialog(null, "Saved Successfully!");
        }
    }

}
