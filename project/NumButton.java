package project.tools;
import javax.swing.JButton;
public class NumButton extends JButton{
  int number;
  public NumButton(int n, ImageIcon i){
    super(i);
    number=n;
  }
  public NumButton(int n, String s){
    super(s);
    number=n;
  }
  public int getNum(){
    return number;
  }
}
