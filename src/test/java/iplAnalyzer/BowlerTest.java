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
   public void givenFilePath_whenCorrect_SortDataBasedOnBowlingAverage()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, SAMPLE_WKTS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BOWLING_AVERAGE, dataMap);
         BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
         Assert.assertEquals("Kagiso Rabada", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenPreparedFilePath_WhenCorrect_SortDataBasedOnEconomyRate()
   {
      try
      {
         Map<String, PlayerDao> dataMap = iplAnalyzer.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER, SAMPLE_WKTS_FILE_PATH);
         String dataString = iplAnalyzer.sortData(SortingEnums.BOWLING_ECONOMY, dataMap);
         BowlerCsvBinder[] DataInArray = new Gson().fromJson(dataString, BowlerCsvBinder[].class);
         Assert.assertEquals("Jasprit Bumrah", DataInArray[0].player);
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }
}
