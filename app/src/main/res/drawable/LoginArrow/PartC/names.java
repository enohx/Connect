import java.util.Random;
public class names
{
  String names[] = {"John", "Albert", "Mathew", "George", "David", "Sarah", "Anna", "Michael", 
                       "Jenny", "Simon", "Bella", "Rudolph", "Hope"};
                       
  String surnames[] = {"Smith", "Jones", "Taylor", "Brown", "Williams", "Wilson", 
                         "Johnson", "Wilson", "Davies"};
  
                         
  public String generateName()
  {
    return names[new Random().nextInt(names.length)] + " "+ surnames[new Random().nextInt(surnames.length)];
    }
}
