package iplAnalyzer;

import java.util.Map;

public class BatsMan extends IPLAdapter
{

   @Override
   public Map<String, PlayerDAO> loadData(String iplFilePath) throws IPLException
   {
      Map<String, PlayerDAO> map = super.loadData(RunsCsvBinder.class,iplFilePath);
      return map;
   }
}
