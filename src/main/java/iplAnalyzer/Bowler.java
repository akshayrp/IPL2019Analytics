package iplAnalyzer;

import java.util.Map;

public class Bowler extends IPLAdapter
{
   @Override
   public Map<String, PlayerDao> loadData(String...iplFilePath) throws IPLException
   {
      Map<String, PlayerDao> map = super.loadData(BowlerCsvBinder.class,iplFilePath[0]);
      return map;
   }
}
