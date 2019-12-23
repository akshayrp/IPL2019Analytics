package iplAnalyzer;

import java.util.Map;

public class BatsMan extends IPLAdapter
{
   Map<String, PlayerDao> map;
   @Override
   public Map<String, PlayerDao> loadData(String...iplFilePath) throws IPLException
   {
      map = super.loadData(BatsManCsvBinder.class,iplFilePath[0]);
      return map;
   }
}
