/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Tommy
 */
public class MyFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
    if (f.isDirectory())
      return true;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1)
      if (s.substring(i + 1).toLowerCase().equals("jpg") ||
          s.substring(i + 1).toLowerCase().equals("png") ||
          s.substring(i + 1).toLowerCase().equals("bmp") ||
          s.substring(i + 1).toLowerCase().equals("gif") ||
          s.substring(i + 1).toLowerCase().equals("mypsd") ||
          s.substring(i + 1).toLowerCase().equals("gz"))
        return true;

    return false;
  }

    @Override
  public String getDescription() {
    return "Accepts img files, mypsd and gzip files only.";
  }
}
