package iplAnalyzer;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class IPLAnalyzerTest
{
   IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

   public static final String ORIGINAL_RUNS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
   public static final String RUNS_SAMPLE_DATA
         = "./src/test/resources/RunsSampleData.csv";
   public static final String NON_EXISTING_FILE_PATH
         = "./src/test/resources/IPL2019MostRuns.csv";
   public static final String NO_DELIMITER_FILE_PATH
         = "./src/test/resources/RunsSampleDataWithOutDelimiter.csv";
   public static final String NO_HEADER_FILE_PATH
         = "./src/test/resources/RunsSampleDataWithoutHeader.csv";
   public static final String NO_DATA_FILE_PATH
         = "./src/test/resources/RunsSampleWithOutData.csv";
   private static final String WKTS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
   public static final String PREPARED_RUNS_FILE_PATH
         = "./src/test/resources/preparedRunsFile.csv";


   @Test
   public void givenFilePath_WhenFileCorrect_CanReadFile()
   {
      Reader reader = null;
      try
      {
         reader = Files.newBufferedReader(Paths.get(ORIGINAL_RUNS_FILE_PATH));
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
   public void givenFilepath_WhenCorrect_returnsNumberOfRecord()
   {
      Map<String, RunDAO> dataMap = null;
      try
      {
         dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
      Assert.assertEquals(100, dataMap.size());
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

   @Test
   public void givenFilePath_WhenNoDataFound_HandlesException()
   {
      try
      {
         ExpectedException exception = ExpectedException.none();
         exception.expect(IPLException.class);
         iplAnalyzer.loadData(NO_DATA_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.UNABLE_TO_PARSE, e.type);
      }
   }

   @Test
   public void givenFilePath_WhenWrong_HandlesException()
   {
      try
      {
         ExpectedException exception = ExpectedException.none();
         exception.expect(IPLException.class);
         iplAnalyzer.loadData(WKTS_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.UNABLE_TO_PARSE, e.type);
      }
   }

   @Test
   public void givenOriginalFilePath_WhenCorrect_CreateNewFileWithProperData() throws IPLException
   {
      Map<String, RunDAO> dataMap = null;
      try
      {
         dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
      Assert.assertEquals(100, dataMap.size());
   }

   @Test
   public void givenOriginalFilePath_WhenInCorrect_ThrowException()
   {
      try
      {
         ExpectedException exception = ExpectedException.none();
         exception.expect(IPLException.class);
         iplAnalyzer.prepareFile(NON_EXISTING_FILE_PATH,PREPARED_RUNS_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.CANNOT_READ_FILE, e.type);
      }
   }

   @Test
   public void givenFilePath_whenCorrect_SortDataBasedOnBattingAverage()
   {
      try
      {
         Map<String, RunDAO> dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(DataFields.BATTING_AVERAGE, dataMap);
         RunsCsvBinder[] DataInArray = new Gson().fromJson(dataString, RunsCsvBinder[].class);
         Assert.assertEquals("MS Dhoni", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenPreparedFilePath_WhenCorrect_SortDataBasedOnStrikeRate()
   {
      try
      {
         Map<String, RunDAO> dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(DataFields.STRIKE_RATE, dataMap);
         RunsCsvBinder[] DataInArray = new Gson().fromJson(dataString, RunsCsvBinder[].class);
         Assert.assertEquals("Ishant Sharma", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenRunsCsvData_WhenCorrect_CompareDataBasedOnMax4sAnd6sAndGivesSortedData()
   {
      try
      {
         Map<String, RunDAO> dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(DataFields.SORT_ON_4s_AND_6s,dataMap);
         RunsCsvBinder[] DataInArray = new Gson().fromJson(dataString, RunsCsvBinder[].class);
         Assert.assertEquals("Andre Russell", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }

   }

   @Test
   public void givenRunsCsvData_WhenCorrect_CompareDataBasedOn4sAnd6sStrikeRateAndGivesSortedData()
   {
      try
      {
         Map<String, RunDAO> dataMap = iplAnalyzer.loadData(ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(DataFields.BEST_STRIKE_RATE_WITH_6s_4s,dataMap);
         RunsCsvBinder[] DataInArray = new Gson().fromJson(dataString, RunsCsvBinder[].class);
         Assert.assertEquals("Andre Russell", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }

   }
}
