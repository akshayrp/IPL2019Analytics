package iplAnalyzer;

import com.opencsv.bean.CsvBindByName;

public class RunsCsvBinder
{
   @CsvBindByName(column = "Player", required = true)
   public String player;

   @CsvBindByName(column = "Runs", required = true)
   public String run;

   @CsvBindByName(column = "Avg", required = true)
   public String avg;


}
