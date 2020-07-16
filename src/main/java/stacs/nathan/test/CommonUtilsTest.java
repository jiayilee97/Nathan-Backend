package stacs.nathan.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import stacs.nathan.utils.CommonUtils;
import static org.junit.Assert.assertNotNull;

public class CommonUtilsTest {

  @Autowired
  CommonUtils commonUtils;

  @Test
  public void generateUuid() {
    String uuid = commonUtils.generateRandomUUID();
    System.out.println("Generated UUID : " + uuid);
    assertNotNull(uuid);
  }

}
