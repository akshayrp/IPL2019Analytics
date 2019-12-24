package iplAnalyzer;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class BowlerTest
{
   private static final String WKTS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
   private static final String SAMPLE_WKTS_FILE_PATH
         = "./src/test/resources/BowlingSampleData.csv";

   IPLAnalyzer iplAnalyzer = new IPLAnalyzer();

   @Test
   public void givenBowlerFilePath_whenCorrect_SortDataBasedOnBowlingAverage() throws IPLException
   {
      Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, WKTS_FILE_PATH);
      String dataString = iplAnalyzer.sortData(SortingEnums.BOWLING_AVERAGE, dataMap);
      BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
      Assert.assertEquals("Suresh Raina", DataInArray[0].player);
   }

   @Test
   public void giveBowlerFilePath_WhenCorrect_SortDataBasedOnEconomyRate() throws IPLException
   {
      Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, WKTS_FILE_PATH);
      String dataString = iplAnalyzer.sortData(SortingEnums.BOWLING_ECONOMY, dataMap);
      BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
      Assert.assertEquals("Shivam Dube", DataInArray[0].player);
   }

   @Test
   public void givenBowlerFilePath_WhenCorrect_SortDataOnWicketsAndGivesSortedData() throws IPLException
   {
      Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, WKTS_FILE_PATH);
      String dataString = iplAnalyzer.sortData(SortingEnums.SORT_ON_4w_AND_5w, dataMap);
      BatsManCsvBinder[] DataInArray = new Gson().fromJson(dataString, BatsManCsvBinder[].class);
      Assert.assertEquals("Kagiso Rabada", DataInArray[0].player);
   }

   @Test
   public void givenBowlerFilePath_WhenCorrect_SortDataOnAverageAndStrikeRateAndGivesSortedData() throws IPLException
   {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, WKTS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BOWLING_STRIKE_RATE_AND_AVERAGE, dataMap);
         BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
         Assert.assertEquals("Suresh Raina", DataInArray[0].player);
   }


   @Test
   public void givenBowlerFilePath_WhenCorrect_SortDataOnMaxWicketAndAverageAndGivesSortedData() throws IPLException
   {
      Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, WKTS_FILE_PATH);
      String dataString = iplAnalyzer.sortData(SortingEnums.WICKETS_AND_AVERAGE, dataMap);
      BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
      Assert.assertEquals("Suresh Raina", DataInArray[0].player);
   }
}
