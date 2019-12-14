package iplAnalyzer;

public class RunDAO
{
   public String player;
   public int run;
   public Double avg;
   public String notOut;
   public Double strikeRate;

   public RunDAO(RunsCsvBinder csvData)
   {
      this.player = csvData.player;
      this.run = csvData.run;
      this.avg = csvData.avg;
      this.notOut = csvData.notOut;
      this.strikeRate = csvData.strikeRate;
   }
}
