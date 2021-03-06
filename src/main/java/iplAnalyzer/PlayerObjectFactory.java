package iplAnalyzer;

public class PlayerObjectFactory
{
   public static IPLAdapter getPlayer(IPLAnalyzer.PlayerEnum player)
   {
      if(player.equals(IPLAnalyzer.PlayerEnum.BATSMAN))
         return new BatsMan();
      else if(player.equals(IPLAnalyzer.PlayerEnum.BOWLER))
         return new Bowler();
      else if(player.equals(IPLAnalyzer.PlayerEnum.ALL_ROUNDER))
         return new AllRounderAdapter();
      return null;
   }
}
