package iplAnalyzer;

import com.opencsv.bean.CsvBindByName;

public class RunsCsvBinder
{
   public RunsCsvBinder()
   {
   }

   @CsvBindByName(column = "Player", required = true)
   public String player;

   @CsvBindByName(column = "Runs", required = true)
   public int run;

   @CsvBindByName(column = "Avg", required = true)
   public Double avg;

   @CsvBindByName(column = "NO", required = true)
   public String notOut;

   @CsvBindByName(column = "SR", required = true)
   public Double strikeRate;

}
