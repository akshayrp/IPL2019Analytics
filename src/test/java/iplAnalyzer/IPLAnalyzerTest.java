package iplAnalyzer;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class IPLAnalyzerTest
{
   public static final String RUNS_FILE_PATH
   ="./src/test/resources/IPL2019FactsheetMostRuns.csv";

   @Test
   public void givenFilePath_WhenFileCorrect_CanReadFile() throws IOException
   {
      IPLAnalyzer iplAnalyzer = new IPLAnalyzer();
      Reader reader = iplAnalyzer.loadData(RUNS_FILE_PATH);
      Assert.assertNotNull(reader);
   }
}
