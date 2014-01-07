package com.c306.core.fileupload.utils.img;

import java.io.*;

public class ExtensionFilenameFilter
    implements FilenameFilter {
  private String[] extension;
  private boolean matchCase;

  public ExtensionFilenameFilter() {
    this.extension = null;
    this.matchCase = false;
  }

  public ExtensionFilenameFilter(String[] extension, boolean matchCase) {
    this.extension = extension;
    this.matchCase = matchCase;
  }

  public boolean accept(File dir, String name) {
    if (this.extension == null) {
      return true;
    }
    File file = new File(dir, name);
    if (file.isDirectory()) {
      return true;
    }
    String fname = file.getName();
    int pos = fname.lastIndexOf(".");
    for (int i = 0; i < this.extension.length; i++) {
      if (pos == -1) {
        if (this.extension[i].length() == 0) {
          return true;
        }
      }
      else if (pos == fname.length() - 1) {
        if (this.extension[i].length() == 0) {
          return true;
        }
      }
      else {
        boolean b = this.matchCase ?
            this.extension[i].equals(fname.substring(pos + 1)) :
            this.extension[i].equalsIgnoreCase(fname.substring(pos + 1));
        if (b) {
          return true;
        }
      }
    }
    return false;
  }

  public void setExtension(String[] extension) {
    this.extension = extension;
  }

  public void setMatchCase(boolean matchCase) {
    this.matchCase = matchCase;
  }

  public String[] getExtension() {
    return extension;
  }

  public boolean isMatchCase() {
    return matchCase;
  }
}
