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
    
    if(args.length > 1 && args[0].equals("cli")) {
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
      e.printStackTrace();
    }
    
  }

  private void getCliImpl(String[] args){
      ConsoleImpl mCli = new ConsoleImpl();
      System.out.println("Client Modus");
      mCli.main(args);
  }

}
