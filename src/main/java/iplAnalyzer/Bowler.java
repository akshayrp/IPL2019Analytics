package iplAnalyzer;

import java.util.Map;

public class Bowler extends IPLAdapter
{

   @Override
   public Map<String, PlayerDAO> loadData(String iplFilePath) throws IPLException
   {
      Map<String, PlayerDAO> map = super.loadData(BowlerCsvBinder.class,iplFilePath);
      return map;
   }
}
