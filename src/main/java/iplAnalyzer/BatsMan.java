package iplAnalyzer;

import java.util.Map;

public class BatsMan extends IPLAdapter
{
   @Override
   public Map<String, PlayerDao> loadData(String iplFilePath) throws IPLException
   {
      Map<String, PlayerDao> map = super.loadData(BatsManCsvBinder.class,iplFilePath);
      return map;
   }
}
