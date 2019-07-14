
import javax.swing.ImageIcon;
//abstract interface of symbol
public abstract interface ISymbol {
    //abstract methods
    public abstract void setImage(ImageIcon image);
    public abstract ImageIcon getImage();
    public abstract void setValue(int v);
    public abstract int getValue();    
}
    

