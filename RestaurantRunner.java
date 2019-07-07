/*
   Class Name: RestaurantRunner
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class contains the main method that calls the run method in
            the Restaurant class which runs the entire program.
*/

import java.util.*;
import java.io.File;
import java.io.FilenameFilter;
class RestaurantRunner
{
   /*
      Description: This method contains the main method that will start running the program.
      Purpose: Creates a new restaurant with the following text file names, then
               calls on the run method in the Restaurant class.
   */
   public static void main(String[] args) throws InterruptedException
   {
      Scanner sc = new Scanner (System.in);
      
      String fileDirectoryName = ".\\"; 
      File directory = new File(fileDirectoryName);
     
      String restaurantInfoFileName;
      String foodInfoFileName;
      String categoryInfoFileName;
      String employeeInfoFileName;
      String floorPlanFileName;
      String couponFileName;
      
      long maxMemory = Long.MAX_VALUE;
      int longSleepTime = 300;
      int shortSleepTime = 100;
      
      String [] textfileInputChoices = {"Enter your own textfile names.", "Use default textfile name setup. See user manual for more information."};
      Options textfileInputMenu =  new Options (textfileInputChoices);
      
      // DOS art, and title screen    
      System.out.println("       _");       
      System.out.println("      / )");
      System.out.println("|||| / /      ______         _                      _   _  __      _  __     ");
      System.out.println("||||/ /      |  ____|       | |                    | | | |/ /     (_)/ _|    ");
      System.out.println("\\__(_/       | |__ ___  _ __| | __   __ _ _ __   __| | | ' / _ __  _| |_ ___ ");
      System.out.println(" ||//        |  __/ _ \\| '__| |/ /  / _` | '_ \\ / _` | |  < | '_ \\| |  _/ _ \\");
      System.out.println(" ||/         | | | (_) | |  |   <  | (_| | | | | (_| | | . \\| | | | | ||  __/");
      System.out.println(" ||          |_|  \\___/|_|  |_|\\_\\  \\__,_|_| |_|\\__,_| |_|\\_\\_| |_|_|_| \\___|");
      System.out.println("(||");
      System.out.println(" \"\"                     Created by John, King, Nancy, and ZiCheng");
      System.out.println("                                     Version " + Restaurant.VERSION);
      System.out.println("_______________________________________________________________________________");
      System.out.println ("\nBefore you begin running your restaurant with our software,");
      System.out.println ("we need the name of your textfiles used to store information");
      System.out.println ("about your restuarant, employees, foods, food categories, floor plan,");
      System.out.println ("and coupons. Read user manual to see what the textfile formats are.\n");
      System.out.println ("Enter one of the listed option numbers below to make your choice.\n");
      
      textfileInputMenu.printAllOptions();
      int userInput = textfileInputMenu.getUserInput();
      
      if (userInput == 1) {
         /*
         This program runs based on the textfile that the user (restaurant owner) has. By writing 
         all the file names down, the program will be able to use specific text files for particular restaurnat    
         */  
         
         System.out.println("Finding text files...");
         System.out.println("These are all the text file names that you can enter: \n");
         
         if(directory.exists() && directory.isDirectory()) 
         { 
            File arr[] = directory.listFiles();             
            for (int i = 0; i < arr.length; i++) {
               if (arr[i].getName().endsWith(".txt")) {
                  System.out.println(arr[i].getName());
               }
            }
         }  
         
         System.out.print ("\nEnter restaurant info textfile name: ");
         String userRestaurantInfoFileName = sc.nextLine();
         System.out.print ("Enter employee info textfile name: ");
         String userEmployeeInfoName = sc.nextLine();
         System.out.print ("Enter food database textfile name: ");
         String userFoodInfoFileName = sc.nextLine();
         System.out.print ("Enter food category database textfile name: ");
         String userCategoryInfoFileName = sc.nextLine();
         System.out.print ("Enter floor plan textfile name: ");
         String userFloorPlanFileName = sc.nextLine();
         System.out.print ("Enter coupon info textfile name: ");
         String userCouponFileName = sc.nextLine();
         
         restaurantInfoFileName = userRestaurantInfoFileName;
         employeeInfoFileName = userEmployeeInfoName;
         foodInfoFileName = userFoodInfoFileName;
         categoryInfoFileName = userCategoryInfoFileName;
         floorPlanFileName = userFloorPlanFileName;
         couponFileName = userCouponFileName;
      } else {       
         restaurantInfoFileName = "RestaurantInfo.txt";
         employeeInfoFileName = "EmployeeDatabase.txt";
         foodInfoFileName = "FoodDatabase.txt";
         categoryInfoFileName = "FoodCategoryDatabase.txt";
         floorPlanFileName = "FloorPlan.txt";
         couponFileName = "CouponDatabase.txt";
      }
      
      Thread.sleep(longSleepTime);
      System.out.print ("Restaurant now loading");
      Thread.sleep(longSleepTime);
      System.out.print (".");
      Thread.sleep(longSleepTime);
      System.out.print(".");
      Thread.sleep(longSleepTime);
      System.out.println (".");
      Thread.sleep(longSleepTime);
         
      System.out.println("\nSystem Information: ");
      String nameOS = System.getProperty("os.name");
      System.out.println("Running " + nameOS);
      Thread.sleep(shortSleepTime);
      System.out.println("Running java version " + Runtime.class.getPackage().getImplementationVersion());
      Thread.sleep(shortSleepTime);
      System.out.println("Available processor cores: " + Runtime.getRuntime().availableProcessors());
      Thread.sleep(shortSleepTime);
      
      // Extra loading messages
      System.out.print("\nSharpening forks and knifes.");
      Thread.sleep(shortSleepTime);
      System.out.print(".");
      Thread.sleep(shortSleepTime);
      System.out.print(".");
      Thread.sleep(shortSleepTime);
      System.out.println(".");
      Thread.sleep(shortSleepTime);
         
      //Tells user the quality of the device that one is using
      System.out.println("\nRuntime Environment Information: ");
      System.out.println("Free memory in JRE: " + Runtime.getRuntime().freeMemory() + " bytes");
      Thread.sleep(shortSleepTime);
      System.out.println("Maximum memory allocated to JRE: " + (maxMemory == Long.MAX_VALUE ? Long.MAX_VALUE : maxMemory) + " bytes");
      Thread.sleep(shortSleepTime);
      System.out.println("Total memory available to JVM: " + Runtime.getRuntime().totalMemory() + " bytes");
           
      System.out.print("\nLooking for text files");
      Thread.sleep(longSleepTime);
      System.out.print(".");
      Thread.sleep(longSleepTime);   
      System.out.print(".");
      Thread.sleep(longSleepTime);
      System.out.println(".\n");
      Thread.sleep(longSleepTime); 
        
      //Creates Restaurant instance, and passes in the text file information  
      Restaurant AYJRestaurant = new Restaurant(restaurantInfoFileName, employeeInfoFileName, foodInfoFileName, categoryInfoFileName, floorPlanFileName, couponFileName);
         
      // Extra loading messages
      System.out.print("\nScrubbing tables.");
      Thread.sleep(shortSleepTime);
      System.out.print(".");
      Thread.sleep(shortSleepTime);
      System.out.print(".");
      Thread.sleep(shortSleepTime);
      System.out.println(".");
      Thread.sleep(shortSleepTime);
      
      //Runs the program
         
      AYJRestaurant.run();
   }
}