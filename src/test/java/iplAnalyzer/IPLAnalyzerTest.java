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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, NON_EXISTING_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.CANNOT_READ_FILE, e.type);
      }
   }

   @Test
   public void givenFilepath_WhenCorrect_returnsNumberOfRecord()
   {
      Map<String, PlayerDao> dataMap = null;
      try
      {
         dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, NO_DELIMITER_FILE_PATH);
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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, NO_HEADER_FILE_PATH);
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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, NO_DATA_FILE_PATH);
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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, WKTS_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.UNABLE_TO_PARSE, e.type);
      }
   }

   @Test
   public void givenOriginalFilePath_WhenCorrect_CreateNewFileWithProperData() throws IPLException
   {
      Map<String, PlayerDao> dataMap = null;
      try
      {
         dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
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
         iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, NON_EXISTING_FILE_PATH);
      }
      catch (IPLException e)
      {
         Assert.assertEquals(IPLException.ExceptionType.CANNOT_READ_FILE, e.type);
      }
   }

   @Test
   public void givenBatsManAndBowlerData_WhenCorrect_CompareAverageAndGivesAllRounderPlayer()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.ALL_ROUNDER,"/home/admin1/IdeaProjects/IPL2019Analytics/src/test/resources/IPL2019FactsheetMostRuns.csv",WKTS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.ALL_ROUNDER, dataMap);
         PlayerDao[] DataInArray = new Gson().fromJson(dataString, PlayerDao[].class);
         Assert.assertEquals("Andre Russell", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }
}
