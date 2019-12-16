package iplAnalyzer;

import java.util.Comparator;

public class ComparatorFor4sAnd6s implements Comparator<PlayerDAO>
{
   @Override
   public int compare(PlayerDAO o1, PlayerDAO o2)
   {
      return Integer.valueOf((o1.sixs * 6) + (o1.fours * 4)).compareTo(Integer.valueOf(o2.fours * 4) + (o2.sixs * 6));
   }
}
