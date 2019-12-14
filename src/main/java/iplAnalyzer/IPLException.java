package iplAnalyzer;

public class IPLException extends Throwable
{
   enum ExceptionType
   {
      CANNOT_READ_FILE
   }

   ExceptionType type;

   public IPLException(String message, ExceptionType type)
   {
      super(message);
      this.type = type;
   }
}
