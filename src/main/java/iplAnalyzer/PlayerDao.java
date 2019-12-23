package iplAnalyzer;

public class PlayerDao
{

   public String player;
   public int run;
   public double battingAvg;
   public double bowlingAvg;
   public String notOut;
   public double battingStrikeRate;
   public double bowlingStrikeRate;
   public double economy;
   public int fourRuns;
   public int fourWickets;
   public int sixRuns;
   public int fivesWickets;
   public int wickets;


   public PlayerDao()
   {
   }

   public PlayerDao(String player, int run, double battingAvg)
   {
      this.player = player;
      this.run = run;
      this.battingAvg = battingAvg;
   }

   public PlayerDao(String player, double bowlingAvg, int wickets)
   {
      this.player = player;
      this.bowlingAvg = bowlingAvg;
      this.wickets = wickets;
   }

   public PlayerDao(BatsManCsvBinder csvData)
   {
      this.player = csvData.player;
      this.run = csvData.run;
      this.battingAvg = csvData.avg;
      this.notOut = csvData.notOut;
      this.battingStrikeRate = csvData.strikeRate;
      this.fourRuns = csvData.Fours;
      this.sixRuns = csvData.Sixs;
   }

   public PlayerDao(BowlerCsvBinder csvData)
   {
      this.player = csvData.player;
      this.bowlingAvg = csvData.avg;
      this.bowlingStrikeRate = csvData.strikeRate;
      this.economy = csvData.economy;
      this.fivesWickets = csvData.fives;
      this.fourWickets = csvData.fours;
      this.wickets = csvData.wickets;
   }
}
