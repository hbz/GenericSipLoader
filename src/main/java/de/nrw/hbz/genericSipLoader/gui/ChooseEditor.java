/**
 * 
 */
package de.nrw.hbz.genericSipLoader.gui;

/**
 * 
 */
public abstract class ChooseEditor {

  final static String LEDIT =  "xdg-open";
  final static String WEDIT =  "notepad.exe";
  final static String MEDIT =  "open -t";  

  public static String getEditor(MainGui mGui) {
    String os = System.getProperty("os.name").toLowerCase();
    String editorCommand = null;;
    
    if (os.contains("win")) {
      editorCommand = WEDIT;
    } else if (os.contains("mac")) {
      editorCommand = MEDIT;
    } else if (os.contains("nix") || os.contains("nux")) {
      editorCommand = LEDIT;
    } else {
      mGui.showMessage("Error", "Unsupported operating system.");
    }
    return editorCommand;     
  }


}
