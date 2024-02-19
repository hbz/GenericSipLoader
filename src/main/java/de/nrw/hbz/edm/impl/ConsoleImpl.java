/**
 * 
 */
package de.nrw.hbz.edm.impl;

import java.util.ArrayList;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.nrw.hbz.edm.model.Aggregation;
import de.nrw.hbz.edm.model.OaiPmh;
import de.nrw.hbz.edm.model.OaiRecord;
import de.nrw.hbz.edm.model.ProvidedCHO;
import de.nrw.hbz.edm.model.Rdf;
import de.nrw.hbz.edm.model.serialize.SerializeAggregation;
import de.nrw.hbz.edm.model.serialize.SerializeOaiPmh;
import de.nrw.hbz.edm.model.serialize.SerializeProvidedCHO;
import de.nrw.hbz.edm.model.serialize.SerializeResourceAttribute;

/**
 * 
 */
public class ConsoleImpl {
  
  final static Logger logger = LogManager.getLogger(ConsoleImpl.class);

  /**
   * @param args
   */
    String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "src/test/resources/OaiPmh.xml";
    
    //static String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "src/main/resources/OAIBase.xml";

    /**
     * @param args
     */
    public static void main(String[] args) {
      
      ConsoleImpl conImpl = new ConsoleImpl();
      OaiPmhImpl impl = new OaiPmhImpl();
      SerializeOaiPmh exOaiPmh = (SerializeOaiPmh) conImpl.generateExampleOaiPmh();
      // impl.serializeXml(exOaiPmh);

      if (args != null && args.length > 0) {
        conImpl.filePath = args[0];
      }
      
      impl.setFilePath(conImpl.filePath);
      
      OaiPmh resultOaiPmh = impl.deserializeXml();
      // logger.debug(resultOaiPmh.getOaiMethod().getRecord().get(0).getMetadata().getRdf().getProvidedCho());
      //impl.serializeXml(resultOaiPmh);

      conImpl.filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "src/test/resources/ExampleSip/EDM.xml";
      // EdmImpl edmImpl = new EdmImpl(conImpl.filePath);
      Rdf resultEdm = EdmProvider.deserialize(new File(conImpl.filePath));
      logger.debug(resultOaiPmh.getOaiMethod().getRecord().get(0).getMetadata().getRdf().getProvidedCho());
      // edmImpl.serializeXml(resultEdm);
      
      ArrayList<String> isShownByReplacement = new ArrayList<>();
      isShownByReplacement.add("https://www.q-terra.de/Part-1/000-0002.csv");
      isShownByReplacement.add("https://www.q-terra.de/Part-2/000-0001.txt");
      
      AggregationElementOperator rISB = new AggregationElementOperator(conImpl.filePath);
       rISB.replaceAllIsShownBy(isShownByReplacement);
       // logger.info(rISB.toString());
      
    }
    
    /**
     * @return SerializeOaiPmh as SerializeOaiPmh object for testing
     */
    public OaiPmh generateExampleOaiPmh() {
      
      ArrayList<String> creator = new ArrayList<>();
      creator.add("Christiane Wesling");
      ArrayList<String> description = new ArrayList<>();
      description.add("Bericht über ein neues Leben");
      description.add("Erfahrungen mit dem Eierrat");
      ArrayList<String> extent = new ArrayList<>();
      extent.add("100 S.");
      extent.add("3 Tafeln");
      String edmType ="TEXT";
      String dcType="Archivalie";

      OaiPmh oaiPmh = new SerializeOaiPmh();
      
      ProvidedCHO provCho = new SerializeProvidedCHO();
      provCho.addDcTitle("Eine neue Hühnerfarm");
      provCho.setDcCreator(creator);
      provCho.setDcDescription(description);
      provCho.addDcContributor("Andres Quast");
      provCho.addDcContributor("Björn Quast");
      provCho.setDctermsExtent(extent);
      provCho.setEdmType(edmType);
      provCho.addDcType(dcType);
      provCho.setProvidedCHOAbout("test");

      Aggregation aggregation = new SerializeAggregation();
      aggregation.setEdmDataProvider("Hochschulbibliothekszentrum NRW");
      aggregation.setEdmAggregatedCHO(new SerializeResourceAttribute("12345"));
      
      ArrayList<? extends OaiRecord> oRecord = oaiPmh.getOaiMethod().getRecord();
      oRecord.get(0).getMetadata().getRdf().setProvidedCho(provCho);
      oRecord.get(0).getMetadata().getRdf().addAggregation(aggregation);
      return oaiPmh;
      
    }

  }


