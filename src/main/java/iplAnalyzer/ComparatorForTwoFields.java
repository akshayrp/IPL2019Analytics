package iplAnalyzer;

import java.util.Comparator;

public class ComparatorForTwoFields implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return Integer.valueOf((o1.sixs * 6) + (o1.fours * 4)).compareTo(Integer.valueOf(o2.fours * 4) + (o2.sixs * 6));
   }
}
