package iplAnalyzer;

import com.opencsv.bean.CsvBindByName;

public class BowlerCsvBinder
{

   public BowlerCsvBinder()
   {
   }

   @CsvBindByName(column = "PLAYER", required = true)
   public String player;

   @CsvBindByName(column = "Avg", required = true)
   public double avg;

   @CsvBindByName(column = "SR", required = true)
   public double strikeRate;

   @CsvBindByName(column = "Econ", required = true)
   public double economy;

   @CsvBindByName(column = "5w", required = true)
   public int fives;

   @CsvBindByName(column = "4w", required = true)
   public int fours;
}
