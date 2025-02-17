/**
 * 
 */
package de.nrw.hbz.genericSipLoader;

import javax.swing.UnsupportedLookAndFeelException;

import de.nrw.hbz.genericSipLoader.gui.MainGui;
import de.nrw.hbz.genericSipLoader.impl.ConsoleImpl;

/**
 * 
 */
public class AppStarter {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    AppStarter appSt = new AppStarter();
    
    if(args[0].equals("cli")) {
      appSt.getCliImpl(args);
    } else {
      appSt.getGuiImpl(args);      
    }
  }
  
  
  
  private void getGuiImpl(String[] args){
    try {
      MainGui mGui = new MainGui();
      mGui.main(args);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  private void getCliImpl(String[] args){
      ConsoleImpl mCli = new ConsoleImpl();
      mCli.main(args);
  }

}
