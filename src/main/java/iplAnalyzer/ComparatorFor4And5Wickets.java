package iplAnalyzer;

import java.util.Comparator;

public class ComparatorFor4And5Wickets implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return (o1.fours*4+o1.fives*5)-(o2.fours*4+o2.fives*5);
   }
}
