
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Reel {
    //global variables and arrayList
    private ArrayList<Symbol> reel;
    private boolean isStopped = false;
    private int currentSym;
    //constructor
    public Reel() {
        Symbol bell = null;
        Symbol cherry = null;
        Symbol lemon = null;
        Symbol plum = null;
        Symbol redseven = null;
        Symbol watermelon = null;

        // instanciates array list
        reel = new ArrayList<Symbol>();
        //retrieves the symbol image
        try {
            bell = new Symbol(6, new ImageIcon(getClass().getResource("bell.png")));
        } catch (NullPointerException e) {
            System.out.println("The image bell is not found");
        }
        try {
            cherry = new Symbol(2, new ImageIcon(getClass().getResource("cherry.png")));
        } catch (NullPointerException e) {
            System.out.println("The image cherry is not found");
        }
        try {
            lemon = new Symbol(3, new ImageIcon(getClass().getResource("lemon.png")));
        } catch (NullPointerException e) {
            System.out.println("The image lemon is not found");
        }
        try {
            plum = new Symbol(4, new ImageIcon(getClass().getResource("plum.png")));
        } catch (NullPointerException e) {
            System.out.println("The image plum is not found");
        }
        try {
            redseven = new Symbol(7, new ImageIcon(getClass().getResource("redseven.png")));
        } catch (NullPointerException e) {
            System.out.println("The image red seven is not found");
        }
        try {
            watermelon = new Symbol(5, new ImageIcon(getClass().getResource("watermelon.png")));
        } catch (NullPointerException e) {
            System.out.println("The image watermelon is not found");
        }

        // adds symbol to the ArrayList
        reel.add(bell);
        reel.add(cherry);
        reel.add(lemon);
        reel.add(plum);
        reel.add(redseven);
        reel.add(watermelon);
    }

    public Symbol spin() {
        // retrieves a random number that will cause the reel to display a different symbol while spinning
        if(isStopped == false){
        Random rand = new Random();
        int randomNum;
        randomNum = rand.nextInt(reel.size());
        currentSym = randomNum;
        return reel.get(randomNum);
        } else{
            isStopped = false;
            return reel.get(currentSym);
        }
    }
    //this is to make sure that the spin function has stopped when user clicks on reels to be stopped
    public Boolean isStopped(){
        return isStopped = true;
    }
    

}
