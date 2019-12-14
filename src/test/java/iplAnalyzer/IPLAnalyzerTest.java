package iplAnalyzer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IPLAnalyzerTest
{
   IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

   public static final String RUNS_FILE_PATH
         ="./src/test/resources/IPL2019FactsheetMostRuns.csv";
   public static final String NON_EXISTING_FILE_PATH
         ="./src/test/resources/IPL2019MostRuns.csv";
   public static final String RUNS_SAMPLE_DATA
         ="./src/test/resources/RunsSampleData.csv";
   public static final String NO_DELIMITER_FILE_PATH
         = "./src/test/resources/RunsSampleDataWithOutDelimiter.csv";
   public static final String NO_HEADER_FILE_PATH
         = "./src/test/resources/RunsSampleDataWithoutHeader.csv";

   @Test
   public void givenFilePath_WhenFileCorrect_CanReadFile()
   {
      Reader reader = null;
      try
      {
         reader = Files.newBufferedReader(Paths.get(RUNS_FILE_PATH));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
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

   @Test
   public void  givenFilepath_WhenCorrect_returnsNumberOfRecord()
   {
      int numberOfData = 0;
      try
      {
         numberOfData = iplAnalyzer.loadData(RUNS_SAMPLE_DATA);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
      Assert.assertEquals(4,numberOfData);
   }

   @Test
   public void givenFilePath_WhenDelimiterNotFound_HandlesException()
   {
      try
      {
         ExpectedException exception = ExpectedException.none();
         exception.expect(IPLException.class);
         iplAnalyzer.loadData(NO_DELIMITER_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.UNABLE_TO_PARSE, e.type);
      }
   }

   @Test
   public void givenFilePath_WhenHeaderNotFound_HandlesException()
   {
      try
      {
         ExpectedException exception = ExpectedException.none();
         exception.expect(IPLException.class);
         iplAnalyzer.loadData(NO_HEADER_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.UNABLE_TO_PARSE, e.type);
      }
   }
}
