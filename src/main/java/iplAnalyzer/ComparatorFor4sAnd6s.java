package iplAnalyzer;

import java.util.Comparator;

public class ComparatorFor4sAnd6s implements Comparator<RunDAO>
{
   @Override
   public int compare(RunDAO o1, RunDAO o2)
   {
      return Integer.valueOf((o1.sixs * 6) + (o1.fours * 4)).compareTo(Integer.valueOf(o2.fours * 4) + (o2.sixs * 6));
   }
}
