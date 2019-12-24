package iplAnalyzer;

import com.google.gson.Gson;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AnalyzerMockitoTest
{

   public static final String ORIGINAL_RUNS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostRuns.csv";

   private static final String WKTS_FILE_PATH
         = "./src/test/resources/IPL2019FactsheetMostWkts.csv";
   private static final String nullFile
         ="/home/admin1/IdeaProjects/IPL2019Analytics/src/test/resources/RunsSampleWithOutData.csv";

   @Mock
   IPLAdapter adapter;
   SortedDataContainer container;

   @Rule
   public MockitoRule rule = MockitoJUnit.rule();

   Map<String,PlayerDao> battingMap;
   Map<String,PlayerDao> bowlingMap;
   IPLAnalyzer ipl;


   @Before
   public void before()
   {
      ipl = new IPLAnalyzer();
      this.adapter = mock(IPLAdapter.class);
      this.container = mock(SortedDataContainer.class);
      this.battingMap = new HashMap<>();
      this.battingMap.put("Abc", new PlayerDao("Abc", 123, 23.5));
      this.battingMap.put("def", new PlayerDao("def", 123, 23.5));
      this.battingMap.put("ghi", new PlayerDao("ghi", 123, 23.5));
      this.bowlingMap = new HashMap<>();
      this.bowlingMap.put("Abc", new PlayerDao("Abc", 123.3, 23));
      this.bowlingMap.put("def", new PlayerDao("def", 123.5, 23));
      this.bowlingMap.put("ghi", new PlayerDao("ghi", 123.6, 23));
   }

   @Test
   public void givenBatsManData_WhenCorrect_ReturnsCountOfThree()
   {
      try
      {
         when(adapter.loadData(ORIGINAL_RUNS_FILE_PATH)).thenReturn(battingMap);
         this.ipl.setMockObj(adapter,container);
         Map newMap = ipl.getSortedData(IPLAnalyzer.PlayerEnum.BATSMAN,ORIGINAL_RUNS_FILE_PATH);
         Assert.assertEquals(3,newMap.size());
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenBowlerData_WhenCorrect_ReturnsCountOfThree()
   {
      try
      {
         this.ipl.setMockObj(adapter,container);
         when(adapter.loadData(WKTS_FILE_PATH)).thenReturn(bowlingMap);
         Map newMap = ipl.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER,WKTS_FILE_PATH);
         Assert.assertEquals(3,newMap.size());
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenBattingData_WhenCorrect_ReturnsSortedData()
   {
      Comparator<PlayerDao> comparing = Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder());
      this.ipl.setMockObj(adapter,container);
      when(container.getData(SortingEnums.BATTING_AVERAGE)).thenReturn(comparing);
      String stringData = ipl.sortData(SortingEnums.BATTING_AVERAGE,this.battingMap);
      BatsManCsvBinder[] sampleData = new Gson().fromJson(stringData,BatsManCsvBinder[].class);
      Assert.assertEquals("Abc",sampleData[0].player);
   }

   @Test
   public void givenBowlingData_WhenCorrect_ReturnsSortedData()
   {
      Comparator<PlayerDao> comparing = Comparator.comparing(ipl -> ipl.bowlingAvg);
      this.ipl.setMockObj(adapter,container);
      when(container.getData(SortingEnums.BOWLING_AVERAGE)).thenReturn(comparing);
      String stringData = ipl.sortData(SortingEnums.BOWLING_AVERAGE,this.bowlingMap);
      BatsManCsvBinder[] sampleData = new Gson().fromJson(stringData,BatsManCsvBinder[].class);
      Assert.assertEquals("Abc",sampleData[0].player); try
      {
         this.ipl.setMockObj(adapter,container);
         when(adapter.loadData(WKTS_FILE_PATH)).thenReturn(bowlingMap);
         Map newMap = ipl.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER,WKTS_FILE_PATH);
         Assert.assertEquals(3,newMap.size());
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenData_WhenFileInCorrect_HandlesException()
   {
      try
      {
         this.ipl.setMockObj(adapter,container);
         when(adapter.loadData(nullFile)).thenThrow(new IPLException("",IPLException.ExceptionType.CANNOT_READ_FILE));
         Map newMap = ipl.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER,nullFile);
         Assert.assertEquals(3,newMap.size());
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }
}
