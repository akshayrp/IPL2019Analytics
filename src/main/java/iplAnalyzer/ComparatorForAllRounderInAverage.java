package iplAnalyzer;

import java.util.Comparator;

public class ComparatorForAllRounderInAverage implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return (int) ((o1.battingAvg - (1/o1.bowlingAvg)) - (o2.battingAvg - (1/o2.bowlingAvg)));
   }
}
