package org.tron.core.actuator.utils;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tron.common.application.Application;
import org.tron.common.application.ApplicationFactory;
import org.tron.common.application.TronApplicationContext;
import org.tron.common.utils.FileUtil;
import org.tron.core.Constant;
import org.tron.core.config.DefaultConfig;
import org.tron.core.config.args.Args;
import org.tron.core.utils.TransactionUtil;


@Slf4j(topic = "capsule")
public class TransactionUtilTest {
  private static final String dbPath = "output_zenchainparams_test";
  public static Application AppT;
  private static TronApplicationContext context;

  /**
   * Init .
   */
  @BeforeClass
  public static void init() {
    Args.setParam(new String[] {"--output-directory", dbPath}, Constant.TEST_CONF);
    context = new TronApplicationContext(DefaultConfig.class);
    AppT = ApplicationFactory.create(context);
  }

  /**
   * Release resources.
   */
  @AfterClass
  public static void destroy() {
    Args.clearParam();
    AppT.shutdownServices();
    AppT.shutdown();

    context.destroy();
    if (FileUtil.deleteDir(new File(dbPath))) {
      logger.info("Release resources successful.");
    } else {
      logger.info("Release resources failure.");
    }
  }

  @Test
  public void validAccountNameCheck() {
    TransactionUtil actuatorUtil = new TransactionUtil();
    String account = "";
    Assert.assertEquals(true, actuatorUtil.validAccountName(account.getBytes()));
    for (int i = 0; i < 200; i++) {
      account += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(true, actuatorUtil.validAccountName(account.getBytes()));
    account += 'z';
    Assert.assertEquals(false, actuatorUtil.validAccountName(account.getBytes()));

  }

  @Test
  public void validAccountIdCheck() {
    TransactionUtil actuatorUtil = new TransactionUtil();
    String accountId = "";
    Assert.assertEquals(false, actuatorUtil.validAccountId(accountId.getBytes()));
    for (int i = 0; i < 7; i++) {
      accountId += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(false, actuatorUtil.validAccountId(accountId.getBytes()));
    for (int i = 0; i < 26; i++) {
      accountId += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(false, actuatorUtil.validAccountId(accountId.getBytes()));
    accountId = "ab  cdefghij";
    Assert.assertEquals(false, actuatorUtil.validAccountId(accountId.getBytes()));
    accountId = Character.toString((char) 128) + "abcdefjijk" + Character.toString((char) 129);
    Assert.assertEquals(false, actuatorUtil.validAccountId(accountId.getBytes()));
    accountId = "";
    for (int i = 0; i < 30; i++) {
      accountId += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(true, actuatorUtil.validAccountId(accountId.getBytes()));

  }

  @Test
  public void validAssetNameCheck() {
    TransactionUtil actuatorUtil = new TransactionUtil();
    String assetName = "";
    Assert.assertEquals(false, actuatorUtil.validAssetName(assetName.getBytes()));
    for (int i = 0; i < 33; i++) {
      assetName += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(false, actuatorUtil.validAssetName(assetName.getBytes()));
    assetName = "ab  cdefghij";
    Assert.assertEquals(false, actuatorUtil.validAssetName(assetName.getBytes()));
    assetName = Character.toString((char) 128) + "abcdefjijk" + Character.toString((char) 129);
    Assert.assertEquals(false, actuatorUtil.validAssetName(assetName.getBytes()));
    assetName = "";
    for (int i = 0; i < 20; i++) {
      assetName += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(true, actuatorUtil.validAssetName(assetName.getBytes()));
  }

  @Test
  public void validTokenAbbrNameCheck() {

    TransactionUtil actuatorUtil = new TransactionUtil();
    String abbrName = "";
    Assert.assertEquals(false, actuatorUtil.validTokenAbbrName(abbrName.getBytes()));
    for (int i = 0; i < 6; i++) {
      abbrName += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(false, actuatorUtil.validTokenAbbrName(abbrName.getBytes()));
    abbrName = "a bd";
    Assert.assertEquals(false, actuatorUtil.validTokenAbbrName(abbrName.getBytes()));
    abbrName = "a" + Character.toString((char) 129) + 'f';
    Assert.assertEquals(false, actuatorUtil.validTokenAbbrName(abbrName.getBytes()));
    abbrName = "";
    for (int i = 0; i < 5; i++) {
      abbrName += (char) ('a' + (i % 26));
    }
    Assert.assertEquals(true, actuatorUtil.validTokenAbbrName(abbrName.getBytes()));
  }

  @Test
  public void isNumberCheck() {
    TransactionUtil actuatorUtil = new TransactionUtil();
    String number = "";
    Assert.assertEquals(false, actuatorUtil.isNumber(number.getBytes()));

    number = "123df34";
    Assert.assertEquals(false, actuatorUtil.isNumber(number.getBytes()));
    number = "013";
    Assert.assertEquals(false, actuatorUtil.isNumber(number.getBytes()));
    number = "24";
    Assert.assertEquals(true, actuatorUtil.isNumber(number.getBytes()));
  }

}
