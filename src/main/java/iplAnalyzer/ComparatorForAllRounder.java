package iplAnalyzer;

import java.util.Comparator;

public class ComparatorForAllRounder implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return (o1.run*o1.wickets)-(o2.run*o2.wickets);
   }
}
