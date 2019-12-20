package iplAnalyzer;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class BatsManTest
{
   IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

   public static final String ORIGINAL_RUNS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostRuns.csv";

   @Test
   public void givenFilePath_whenCorrect_SortDataBasedOnBattingAverage()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BATTING_AVERAGE, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
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
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BATTING_STRIKE_RATE, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
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
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.SORT_ON_4s_AND_6s, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
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
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BEST_STRIKE_RATE_WITH_6s_4s, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
         Assert.assertEquals("Andre Russell", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenRunsCsvData_WhenCorrect_CompareStrikeRateAndAveragesToSortData()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.STRIKE_RATE_AND_AVERAGE, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
         Assert.assertEquals("MS Dhoni", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenRunsCvsData_WhenCorrect_CompareRunsAndStrikeRateToSort()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN, ORIGINAL_RUNS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.STRIKE_RATE_AND_RUNS, dataMap);
         BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
         Assert.assertEquals("David Warner", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }
}
