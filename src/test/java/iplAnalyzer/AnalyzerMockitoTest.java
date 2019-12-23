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

   @Mock
   IPLAdapter adapter;
   SortedDataContainer container;

   @Rule
   public MockitoRule rule = MockitoJUnit.rule();

   Map<String,PlayerDao> map;
   IPLAnalyzer ipl;


   @Before
   public void before()
   {
      ipl = new IPLAnalyzer();
      this.adapter = mock(IPLAdapter.class);
      this.container = mock(SortedDataContainer.class);
      this.map = new HashMap<>();
      this.map.put("Abc", new PlayerDao("Abc", 123, 23.5));
      this.map.put("def", new PlayerDao("def", 123, 23.5));
      this.map.put("ghi", new PlayerDao("ghi", 123, 23.5));
   }


   @Test
   public void givenBatsManData_WhenCorrect_ReturnsCountOfThree()
   {
      try
      {
         when(adapter.loadData(ORIGINAL_RUNS_FILE_PATH)).thenReturn(map);
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
         when(adapter.loadData(WKTS_FILE_PATH)).thenReturn(map);
         Map newMap = ipl.getSortedData(IPLAnalyzer.PlayerEnum.BOWLER,WKTS_FILE_PATH);
         Assert.assertEquals(3,newMap.size());
      }
      catch (IPLException e)
      {
         e.printStackTrace();
      }
   }

   @Test
   public void givenData_WhenCorrect_ReturnsSortedData()
   {
      Comparator<PlayerDao> comparing = Comparator.comparing(ipl -> ipl.battingAvg, Comparator.reverseOrder());
      this.ipl.setMockObj(adapter,container);
      when(container.getData(SortingEnums.BATTING_AVERAGE)).thenReturn(comparing);
      String stringData = ipl.sortData(SortingEnums.BATTING_AVERAGE,this.map);
      String sampleData = new Gson().toJson(map);
      Assert.assertEquals(stringData,sampleData);
   }
}
