package iplAnalyzer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.Reader;

public class IPLAnalyzerTest
{
   IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

   public static final String RUNS_FILE_PATH
      ="./src/test/resources/IPL2019FactsheetMostRuns.csv";
   public static final String NON_EXISTING_FILE_PATH
         ="./src/test/resources/IPL2019MostRuns.csv";

   @Test
   public void givenFilePath_WhenFileCorrect_CanReadFile() throws IPLException
   {
      Reader reader = iplAnalyzer.loadData(RUNS_FILE_PATH);
      Assert.assertNotNull(reader);
   }

   @Test
   public void givenFilePath_WhenDoesNotExist_ShouldThrowException()
   {
      try
      {
      ExpectedException exception = ExpectedException.none();
      exception.expect(IPLException.class);
      iplAnalyzer.loadData(NON_EXISTING_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.CANNOT_READ_FILE, e.type);
      }
   }
}
