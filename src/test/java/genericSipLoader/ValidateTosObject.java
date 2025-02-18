/**
 * @author aquast
 */
package genericSipLoader;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.google.gson.Gson;

import de.nrw.hbz.genericSipLoader.model.ToScienceObject;

/**
 * 
 */
public class ValidateTosObject {

  String expectedResultPp = "{\n"
      + "  \"accessScheme\": \"public\",\n"
      + "  \"contentType\": \"ResearchData\",\n"
      + "  \"publishScheme\": \"public\",\n"
      + "  \"parentPid\": \"123\"\n"
      + "}";

  String expectedResultGson = "{"
      + "\"contentType\":\"ResearchData\","
      + "\"accessScheme\":\"public\","
      + "\"publishScheme\":\"public\","
      + "\"parentPid\":\"123\""
      + "}";
  ToScienceObject tos = new ToScienceObject();

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    ValidateTosObject vTos = new ValidateTosObject();
  }
  
  private ToScienceObject getTos() {
    return tos;
  }

  public void setToScienceObject() {
    tos.setAccessScheme("public");
    tos.setContentType("ResearchData");
    tos.setPublishScheme("public");
    tos.setParentPid("123");
  }
  
  
  @Test
  public void validateJson(){
    setToScienceObject();
    String result = tos.toString(2);
    assertEquals(expectedResultPp, result);
  }

  @Test
  public void validateGson() {
    setToScienceObject();
    Gson gson = new Gson();
    String json = gson.toJson(tos);
    assertEquals(expectedResultGson, json);   
  }

}
