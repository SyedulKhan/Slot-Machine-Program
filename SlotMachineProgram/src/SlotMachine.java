
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SlotMachine extends JFrame {
    //all JComponents
    private JLabel betArea;
    private JLabel creditArea;
    private JTextField testArea;
    private JTextField testArea2;
    private JTextField testArea3;
    private JButton addCoin;
    private JButton betOne;
    private JButton betMax;
    private JButton reset;
    private JButton spin;
    private JButton stats;
    private JButton lblImage;
    private JButton lblImage2;
    private JButton lblImage3;
    //3 reels
    private Reel reel;
    private Reel reel2;
    private Reel reel3;
    //3 worker threads for each reel
    private MyWorker worker;
    private MyWorker worker2;
    private MyWorker worker3;
    //global variables
    private boolean isSpin = false;
    private int reelsStopped = 0;
    private boolean limit = false;
    private int credit = 10;
    private int bet = 0;
    final private int maxBet = 3;
    private int wins = 0;
    private int losses = 0;
    private int games = 0;
    private int creditsWon = 0;
    
    //constructor
    public SlotMachine() {
        //panel for buttons
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3, 100, 100));
        //custom font for buttons and areas
        Font myFont = new Font("SansSerif", Font.BOLD, 20);
        Font spinFont = new Font("SansSerif", Font.BOLD, 30);
        spin = new JButton("Spin");
        addCoin = new JButton("Add Coin");
        betOne = new JButton("Bet a Credit");
        betMax = new JButton("Bet Max Credit");
        reset = new JButton("Reset Bet");
        stats = new JButton("View Statistics");
        p1.add(addCoin);
        p1.add(spin);
        p1.add(stats);
        p1.add(betOne);
        p1.add(betMax);
        p1.add(reset);
        p1.setBackground(Color.RED);
        spin.setFont(spinFont);
        addCoin.setFont(myFont);
        betOne.setFont(myFont);
        betMax.setFont(myFont);
        reset.setFont(myFont);
        stats.setFont(myFont);
        spin.setForeground(Color.RED);
        //panel for credit and bet areas
        JPanel p2 = new JPanel(new BorderLayout(5, 20));
        JPanel p3 = new JPanel(new GridLayout(1, 5));
        testArea = new JTextField("");
        testArea.setEditable(false);
        testArea.setVisible(false);
        p3.add(testArea);
        creditArea = new JLabel("Credit: " + credit);
        p3.add(creditArea);
        testArea2 = new JTextField("");
        testArea2.setEditable(false);
        testArea2.setVisible(false);
        p3.add(testArea2);
        betArea = new JLabel("Bet: " + bet);
        p3.add(betArea);
        testArea3 = new JTextField("");
        testArea3.setEditable(false);
        testArea3.setVisible(false);
        p3.add(testArea3);
        creditArea.setFont(myFont);
        betArea.setFont(myFont);
        p2.add(p3, BorderLayout.NORTH);
        p2.add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.CENTER);
        
        //panel for 3 reels
        JPanel p4 = new JPanel(new GridLayout(1, 3, 20, 20));
        lblImage = new JButton(">>>");
        lblImage2 = new JButton(">>");
        lblImage3 = new JButton(">");
        p4.add(lblImage);
        p4.add(lblImage2);
        p4.add(lblImage3);
        lblImage.setBackground(Color.WHITE);
        lblImage2.setBackground(Color.WHITE);
        lblImage3.setBackground(Color.WHITE);
        lblImage.setForeground(Color.WHITE);
        lblImage2.setForeground(Color.WHITE);
        lblImage3.setForeground(Color.WHITE);
        add(p4, BorderLayout.NORTH);
        p4.setBackground(Color.BLACK);

        // created reels 
        reel = new Reel();
        Symbol firstImg = reel.spin();
        reel2 = new Reel();
        Symbol firstImg2 = reel2.spin();
        reel3 = new Reel();
        Symbol firstImg3 = reel3.spin();

        // set the images to display and the values when the user runs the slot machine for the first time
        lblImage.setIcon(firstImg.getImage());
        testArea.setText(Integer.toString(firstImg.getValue()));
        lblImage2.setIcon(firstImg2.getImage());
        testArea2.setText(Integer.toString(firstImg2.getValue()));
        lblImage3.setIcon(firstImg3.getImage());
        testArea3.setText(Integer.toString(firstImg3.getValue()));

        // event handlers
        MyListener buttonListener = new MyListener();
        spin.addActionListener(buttonListener);
        addCoin.addActionListener(buttonListener);
        betOne.addActionListener(buttonListener);
        betMax.addActionListener(buttonListener);
        reset.addActionListener(buttonListener);
        stats.addActionListener(buttonListener);
        lblImage.addActionListener(buttonListener);
        lblImage2.addActionListener(buttonListener);
        lblImage3.addActionListener(buttonListener);

    }

    private class MyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            String btnName = evt.getActionCommand();
            // event.getActionCommand() returns the button's label
            switch (btnName) {
                //each case statement will call a specific method that will be performed during run time
                case "Spin":
                    if (bet <= 0) {
                        JOptionPane.showMessageDialog(null, "Place a bet first!");
                    } else {
                        isSpin = true;
                        spinReel();
                        games++;
                    }
                    break;
                case "Add Coin":
                    addCoin();
                    break;
                case "Bet a Credit":
                    betOne();
                    break;
                case "Bet Max Credit":
                    betMax();
                    break;
                case "Reset Bet":
                    resetBet();
                    break;
                case "View Statistics":
                    //creates new JFrame to view statistics
                    ViewStats viewStats = new ViewStats(games, wins, losses, creditsWon);
                    viewStats.setTitle("Stats"); 
                    viewStats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    viewStats.setVisible(true);
                    viewStats.setSize(1200, 1200); 
                    break;
                //the case statements below will stop the reels spinning and display end game results 
                //when all three reels are stopped
                case ">>>":
                    if (isSpin == true) {
                        worker.cancel(true);
                        reelsStopped++;
                        if (reelsStopped == 3) {
                            endGameResult();
                            reelsStopped = 0;
                            isSpin = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Place a bet first then Spin");
                    }
                    break;
                case ">>":
                    if (isSpin == true) {
                        worker2.cancel(true);
                        reelsStopped++;
                        if (reelsStopped == 3) {
                            endGameResult();
                            reelsStopped = 0;
                            isSpin = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Place a bet first then Spin");
                    }
                    break;
                case ">":
                    if (isSpin == true) {
                        worker3.cancel(true);
                        reelsStopped++;
                        if (reelsStopped == 3) {
                            endGameResult();
                            reelsStopped = 0;
                            isSpin = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Place a bet first then Spin");
                    }
                    break;
                default:
                    break;
            }

        }
    }
    //spinReel() spins the three reels using the MyWorker class created that implements a SwingWorker
    public void spinReel() {
        worker = new MyWorker(lblImage, testArea);
        worker2 = new MyWorker(lblImage2, testArea2);
        worker3 = new MyWorker(lblImage3, testArea3);
        worker.execute();
        worker2.execute();
        worker3.execute();
    }
    //endGameResult() displayes the results after the reels have stpped spinning
    public void endGameResult() {
        int symbolValue = Integer.parseInt(testArea.getText());
        int symbolValue2 = Integer.parseInt(testArea2.getText());
        int symbolValue3 = Integer.parseInt(testArea3.getText());
        if (symbolValue == symbolValue2) {
            wins++;
            JOptionPane.showMessageDialog(null, "You won! You gained an additional " + betCalculation(symbolValue) + " credits!");
        } else if (symbolValue == symbolValue3) {
            wins++;
            JOptionPane.showMessageDialog(null, "You won! You gained an additional " + betCalculation(symbolValue) + " credits!");
        } else if (symbolValue2 == symbolValue3) {
            wins++;
            JOptionPane.showMessageDialog(null, "You won! You gained an additional " + betCalculation(symbolValue2) + " credits!");
        } else if (symbolValue == symbolValue2 && symbolValue == symbolValue3 && symbolValue2 == symbolValue3) {
            wins++;
            JOptionPane.showMessageDialog(null, "You won! You gained an additional " + betCalculation(symbolValue) + " credits!");
        } else {
            bet = 0;
            betArea.setText("Your Bet: " + bet);
            losses++;
            JOptionPane.showMessageDialog(null, "You Lose");
        }
        limit = false;
    }
    //addCoin() increments the credit area by 1
    public void addCoin() {
        if (credit > 0) {
            credit++;
            creditArea.setText("Credit: " + credit);
        } else {
            JOptionPane.showMessageDialog(null, "No more credit!");
        }
    }
    //betOne() increments the bet area by 1 if there are enough credits in the credit area
    public void betOne() {
        if (credit > 0) {
            bet++;
            credit--;
            betArea.setText("Bet: " + bet);
            creditArea.setText("Credit: " + credit);
        } else {
            JOptionPane.showMessageDialog(null, "You ran out of credit!");
        }
    }
    //betMax() increments the bet area by 3 if there are enough credits in the credit area
    public void betMax() {
        if (credit <= 0) {
            JOptionPane.showMessageDialog(null, "You ran out of credit!");
        } else if (limit == false && credit > 2) {
            bet = bet + maxBet;
            credit = credit - maxBet;
            betArea.setText("Bet: " + bet);
            creditArea.setText("Credit: " + credit);
            limit = true;
        } else if (credit < 3) {
            JOptionPane.showMessageDialog(null, "You don't have credits to use this button!");
        } else {
            //this button can be used once per spin
            JOptionPane.showMessageDialog(null, "You can't use this button again");
        }
    }
    //resetBet() resets the bet and returns the value to the credit area
    public void resetBet() {
        credit = credit + bet;
        bet = 0;
        betArea.setText("Bet: " + bet);
        creditArea.setText("Credit: " + credit);
        limit = false;
    }
    //betCalculation() performs the end game calculations using the payout in credits from the spec
    public int betCalculation(int symbolVal) {
        int result = 0;
        result = bet * symbolVal;
        creditsWon = result;
        credit = credit + (bet * symbolVal);
        bet = 0;
        betArea.setText("Bet: " + bet);
        creditArea.setText("Credit: " + credit);
        return result;
    }
    
    public static void main(String[] args) {
        // This will invoke the constructor by allocating an anonymous instance
        SlotMachine slotMachine = new SlotMachine();
        slotMachine.setTitle("Slot Machine"); // sets title to Slot Machine
        slotMachine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        slotMachine.setVisible(true); // makes the frame visible to user
        slotMachine.setSize(1000, 1000); // sets the size of the frame
    }
}
