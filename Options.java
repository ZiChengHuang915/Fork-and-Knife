 /********************************************************
   Class Name: Options                                  *
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao  *
   Date: January 10, 2019                               *
   School: A.Y. Jackson Secondary School                *
   Purpose: This class defines what an Option is, and is
            able to get user input and print all the
            current Options.
********************************************************/

import java.util.*;

class Options 
{
   // Encapsulation
   private String [] choices;

   /*
      Parameter(s):
         - choices: a String array of choices
      Description: This method is the constructor of the Options class.
      Purpose: Sets the inplicit array of choices to the explicit choices array.
   */
   public Options (String [] choices)
   {
      this.choices = choices;
   }
   
   /*
      Description: This method is used to get the user's input.
      Purpose: Using the Scanner function, it reads in an input and returns
               the user's selection on the Options screen. Otherwise, it
               will return -1 if the input is invalid.
   */
   public int getUserInput ()
   {
      Scanner sc = new Scanner (System.in);
      System.out.print ("Enter option number: ");  
      String userInput = sc.nextLine();  
      System.out.println();
   
      try 
      {
         int userInputInt = Integer.parseInt (userInput);
    
         if (userInputInt > 0 && userInputInt <= choices.length) 
            return userInputInt;
         else
            return getUserInput(-1);
      } 
      /* Error trapping: if the user enters bad data, this will
                         go to the getUserInput method with a parameter
                         and will ask the user to input again until good
                         good data is entered.
      */
      catch (NumberFormatException iox)
      {
         return getUserInput(-1);
      }
      
   }
   
   /*
      Parameter(s):
         - error: integer value that holds the input error value of -1
      Description: This method is used to get the user's input if there
                   was an error from the previous method.
      Purpose: Using the Scanner function, it reads in an input and returns
               the user's selection on the Options screen. Otherwise, it
               will return -1 if the input is invalid.
   */
   public int getUserInput (int error)
   {
      Scanner sc = new Scanner (System.in);
      
      /*
         This method gives the customer/owner another chance when the user has made a mistake
         writing an input
      */
      System.out.println("Invalid Option. Enter again.");
      System.out.print ("Enter option number: ");    
      String userInput = sc.nextLine();    
      System.out.println();
   
      try 
      {
         int userInputInt = Integer.parseInt (userInput);
      
         if (userInputInt > 0 && userInputInt <= choices.length) 
            return userInputInt;
         else
            return getUserInput(-1);
      } 
      /* Error trapping: if the user enters bad data, this will
                         loop back to the same method until good
                         data is entered.
      */
      catch (NumberFormatException iox)
      {
         return getUserInput(-1);
      }
   } 
   
   /*
      Description: This method is used to print all the Options from any selection of choices.
      Purpose: For every choice an user can choose, it will print all the Options where the
               user can proceed with.
   */
   public void printAllOptions ()
   { 
      for (int i = 0; i < choices.length; i++) 
      {
         System.out.println ((i+1) + ". " + choices [i]);
      }
      
      System.out.println();
   }
}