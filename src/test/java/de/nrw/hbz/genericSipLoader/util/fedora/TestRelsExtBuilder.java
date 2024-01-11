package de.nrw.hbz.genericSipLoader.util.fedora;

import org.junit.Test;

public class TestRelsExtBuilder {

  public static void main(String[] args) {
    
    TestRelsExtBuilder testReBuilder = new TestRelsExtBuilder();
    testReBuilder.produceRelsExtStream();
  }

  @Test
  public void produceRelsExtStream() {
    RelsExtBuilder reB = new RelsExtBuilder("1234");
    reB.generateRelsExt();
  }
}
