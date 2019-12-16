package iplAnalyzer;

public class PlayerDAO
{

   public PlayerDAO()
   {
   }

   public String player;
   public int run;
   public Double avg;
   public String notOut;
   public Double strikeRate;
   public int fours;
   public int sixs;

   public PlayerDAO(RunsCsvBinder csvData)
   {
      this.player = csvData.player;
      this.run = csvData.run;
      this.avg = csvData.avg;
      this.notOut = csvData.notOut;
      this.strikeRate = csvData.strikeRate;
      this.fours = csvData.Fours;
      this.sixs = csvData.Sixs;
   }

   public PlayerDAO(BowlerCsvBinder csvData)
   {
      this.player = csvData.player;
      this.avg = csvData.avg;
      this.strikeRate = csvData.strikeRate;
   }
}
