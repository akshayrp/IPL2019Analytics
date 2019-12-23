package iplAnalyzer;

import java.util.Comparator;

public class ComparatorFor4sAnd6sRuns implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return Integer.valueOf((o1.sixRuns * 6) + (o1.fourRuns * 4)).compareTo(Integer.valueOf(o2.fourRuns * 4) + (o2.sixRuns * 6));
   }
}
