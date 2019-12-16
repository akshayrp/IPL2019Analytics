package iplAnalyzer;

import com.opencsv.bean.CsvBindByName;

public class BowlerCsvBinder
{

   public BowlerCsvBinder()
   {
   }

   @CsvBindByName(column = "Player", required = true)
   public String player;

   @CsvBindByName(column = "Avg", required = true)
   public Double avg;

   @CsvBindByName(column = "SR", required = true)
   public Double strikeRate;
}
