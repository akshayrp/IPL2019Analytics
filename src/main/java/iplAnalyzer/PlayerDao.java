package iplAnalyzer;

public class PlayerDao
{

   public String player;
   public int run;
   public double avg;
   public String notOut;
   public double strikeRate;
   public double economy;
   public int fours;
   public int sixs;
   public int fives;
   public int wickets;

   public PlayerDao(BatsManCsvBinder csvData)
   {
      this.player = csvData.player;
      this.run = csvData.run;
      this.avg = csvData.avg;
      this.notOut = csvData.notOut;
      this.strikeRate = csvData.strikeRate;
      this.fours = csvData.Fours;
      this.sixs = csvData.Sixs;
   }

   public PlayerDao(BowlerCsvBinder csvData)
   {
      this.player = csvData.player;
      this.avg = csvData.avg;
      this.strikeRate = csvData.strikeRate;
      this.economy = csvData.economy;
      this.fives = csvData.fives;
      this.fours = csvData.fours;
      this.wickets = csvData.wickets;
   }
}
