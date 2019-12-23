package iplAnalyzer;

import java.util.Comparator;

public class ComparatorFor4And5Wickets implements Comparator<PlayerDao>
{
   @Override
   public int compare(PlayerDao o1, PlayerDao o2)
   {
      return (o1.fourWickets*4+o1.fivesWickets *5)-(o2.fourWickets*4+o2.fivesWickets *5);
   }
}
