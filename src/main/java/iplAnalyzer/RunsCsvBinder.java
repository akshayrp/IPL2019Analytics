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
   public String run;

   @CsvBindByName(column = "Avg", required = true)
   public String avg;

   @CsvBindByName(column = "NO", required = true)
   public String notOut;

   public RunsCsvBinder(String player, String run, String avg, String notOut)
   {
      this.player = player;
      this.run = run;
      this.avg = avg;
      this.notOut = notOut;
   }
}
