package iplAnalyzer;

import com.opencsv.bean.CsvBindByName;

public class BatsManCsvBinder
{
   public BatsManCsvBinder()
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

   @CsvBindByName(column = "4s")
   public int Fours;

   @CsvBindByName(column = "6s", required = true)
   public int Sixs;

}
