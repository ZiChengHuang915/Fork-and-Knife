/*
   Class Name: Restaurant
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class loads restaurant information from textfiles, and
            contains the run method which runs the entire program.
*/

import java.io.*;
import java.util.*;
import java.lang.reflect.Field;
public class Restaurant
{
   //Encapsulation
   
   // Constants
   public static final int MONTHSPERYEAR = 12;
   public static final int DAYSPERMONTH = 30;
   public static final String MASTERKEY = "Ms.Lam";
   public static final char WALL = 'W';
   public static final char TAKEN = 'T';
   public static final char FLOOR = '.';
   public static final char TABLE = '*';
   public static final char MARKEDTABLE = 'O';
   public static final char TAKENTABLE = 'X';
   public static final char DOOR = 'd';
   public static final String SOFTWARENAME = "Fork and Knife";
   public static final String VERSION = "1.2.7";
   
   // Restaurant Information
   private String name;
   private int phoneNumber;
   private String address;
   private int horizontalLength;
   private int verticalLength;
   private double goldDiscount;
   private double platDiscount;
   private double tax;
   private int doorRow;
   private int doorColumn;
   
   
   // Pseudo Constants
   private int maxEmployee;
   private int maxPossibleFoodsInCategory;
   private int maxPossibleCategoriesInMenu;
   private int maxCoupon;
   private int maxPossibleFoodsInMenu;
   
   // Restaurant Attributes 
   private char [][] map;
   private Menu restaurantMenu;
   private Date restaurantDate;
   private Coupon[] couponDatabase;  // Array of object
   private Staff staff;
   private ArrayList<Customer> customers; // Array of object
   private Finance restaurantFinances;
   private int numCoupons;
   private int currentOrderNumber;
   
   // Temporary Restaurant Attributes
   private Order currentOrder;
   private Coupon currentCoupon;
   private Customer currentCustomer;
   
   /*
      Parameter(s): all the text file names
      Description: This method is the constructor for the Restaurant class.
      Purpose: Loads/creates restaurant information from text files,
               and sets the respective fields to 0/null.
   */
   public Restaurant (String restaurantInfoFileName, String employeeInfoFileName, String foodInfoFileName, String categoryInfoFileName,String floorPlanFileName, String couponFileName)
   {
      loadAllFiles(restaurantInfoFileName, employeeInfoFileName, foodInfoFileName, categoryInfoFileName, floorPlanFileName, couponFileName);
   }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
// METHODS RELATED TO LOADING
  
   /*
      Parameter(s): all the text file names
      Description: This method reads in all the text file
      Purpose: Loads/creates restaurant information from text files,
               and sets the respective fields to 0/null.
   */
   public void loadAllFiles(String restaurantInfoFileName, String employeeInfoFileName, String foodInfoFileName, String categoryInfoFileName,String floorPlanFileName, String couponFileName) {
      if (loadRestaurant(restaurantInfoFileName)) { // Checks for errors in getting the restaurant information
         
         // Setting temporary variables to 0/null
         numCoupons = 0;
         currentOrderNumber = 0;  
         currentOrder = null;
         currentCoupon = null;
         currentCustomer = null;
          
         // Checks for errors in getting the category information
         if (getAllCategories(categoryInfoFileName) == null) {
            System.out.println("The system will now terminate.");
            quit();
         }
         
         // Setting Restaurant attributes
         restaurantMenu = new Menu(maxPossibleCategoriesInMenu, maxPossibleFoodsInCategory, getAllCategories(categoryInfoFileName));
         map = new char [horizontalLength][verticalLength];
         customers = new ArrayList<Customer>();
         couponDatabase = new Coupon[maxCoupon]; 
         staff = new Staff (maxEmployee);
         
         /*
            This tells that whether the user has inputted the correct information of text file
            If not, it goes to quit option and ends the program
         */
         if (!loadNumCategories(categoryInfoFileName)) {
            System.out.println("\nThe system will now terminate.");
            quit();
         }
         
         if (!loadMenu(foodInfoFileName)) {
            System.out.println("\nThe system will now terminate.");
            quit();
         }
         
         if (!loadEmployeeInfo(employeeInfoFileName)) {
            System.out.println("\nThe system will now terminate.");
            quit();
         } 
         
         if (!loadFloorPlan(floorPlanFileName)) {
            System.out.println("\nThe system will now terminate.");
            quit();
         }
         
         if (!loadCoupon(couponFileName)) {
            System.out.println("\nThe system will now terminate.");
            quit();
         }
             
         restaurantFinances = new Finance (staff.getNumEmployees(), restaurantDate);
      
      } else {
         System.out.println("\nThe system will now terminate.");
         quit();
      }
      
      // Delay after loading all files, and before booting up to the main screen
      try {
         Thread.sleep(100);
      } catch (InterruptedException ite) {
         System.out.println("\nThe system could not sleep. Continuing without delay.");
      }
      /*
         Error trapping: if the Thread encounters an error on the backend side, 
                         it will fail to sleep. This not an error related to the user.
      */ 
   }
   
   /*
      Parameter(s): 
         - fileName: String file name of the Restaurant file
                     that contains the Restaurant information
      Description: This method is used to load Restaurant information into the program.
      Purpose: Sets the current date to -1 respectively, and try/catch loading
               information of a Restaurant.
   */
   public boolean loadRestaurant (String fileName)
   {
      int year = -1, month = -1, day = -1;
      
      try
      {
         //File Input
         BufferedReader in = new BufferedReader (new FileReader (fileName));
         name = in.readLine();
         phoneNumber = Integer.parseInt(in.readLine());
         address = in.readLine();
         maxEmployee = Integer.parseInt(in.readLine());
         maxPossibleFoodsInCategory = Integer.parseInt(in.readLine());
         maxPossibleCategoriesInMenu = Integer.parseInt(in.readLine());
         maxCoupon = Integer.parseInt (in.readLine());
         maxPossibleFoodsInMenu = Integer.parseInt (in.readLine());
         horizontalLength = Integer.parseInt (in.readLine());
         verticalLength = Integer.parseInt (in.readLine());
         goldDiscount = Double.parseDouble (in.readLine());
         platDiscount = Double.parseDouble (in.readLine());
         tax = Double.parseDouble(in.readLine());
         doorRow = Integer.parseInt (in.readLine());
         doorColumn = Integer.parseInt (in.readLine());
         year = Integer.parseInt(in.readLine());
         month = Integer.parseInt(in.readLine());
         day = Integer.parseInt(in.readLine());
         restaurantDate = new Date(year, month, day);
         in.close();
         
         if (phoneNumber < 0 || maxEmployee < 0 || maxPossibleFoodsInCategory < 0 || maxPossibleCategoriesInMenu < 0 || maxCoupon < 0 || horizontalLength < 2 || verticalLength < 2 || goldDiscount <= 0 || goldDiscount >= 1
             || platDiscount <= 0 || platDiscount >= 1 || tax <= 0 || doorRow < 0 || doorRow >= verticalLength || doorColumn < 0 || doorColumn >= horizontalLength || month < 1 || month > 12 || day < 0 || day > 30) { // Logic error trapping            
            System.out.println("One or more restaurant info values are incorrect. Please double check the instructions and restart the program.");
            System.out.println("\nThe program will now terminate.");
            quit();
         }
         if (doorRow == 0 && doorColumn == 0 || doorRow == verticalLength - 1 && doorColumn == 0 || doorRow == 0 && doorColumn == horizontalLength - 1 || doorRow == verticalLength - 1 && doorColumn == horizontalLength - 1) { // Logic error trapping
            System.out.println("The doors cannot be on corners of the floor plan. Please double check the instructions and restart the program.");
            System.out.println("\nThe program will now terminate.");
            quit();
         }
             
         System.out.println("Restaurant info loaded.");
         return true;
      }
      catch (IOException iox) {
         System.out.println("\nInvalid restaurant info file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the restaurant info file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it outputs an error message
                         if a String is tried to be converted into a non-String type, then it outputs an error message
      */ 
   }
   
   /*
      Parameter(s): 
         - fileName: String file name of the Food Categories file
                     that contains all Category information
      Description: This method is used to load Food Category information into the program.
      Purpose: Try/catch loading Food Category information from the text file.
   */
   private String[] getAllCategories(String fileName) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(fileName));
         int numCategories = Integer.parseInt(in.readLine());
         
         if (numCategories < 1) { // Logic error trapping
            System.out.println("\nThe number of categories that you have loaded is invalid. Please go back and set a valid number.");
            System.out.println("\nThe program will now terminate.");
            quit();
         }
         
         String[] categories = new String[numCategories];
         
         for (int i = 0; i < numCategories; i++) {
            categories[i] = in.readLine();
         }  
           
         in.close();  
         System.out.println("Category information loaded.");
         return categories;
      } catch (IOException iox) {
         System.out.println("\nInvalid category info file.");
         return null;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the category info file. Please check the file and restart the program.");
         return null;
      }   
      /*
         Error Trapping: returns error message if the file name cannot be read, also outputs error message if a String
                         value(not a number) was tried to converted into int. 
     */
   }
   
   /*
      Parameter(s): 
         - fileName: String file name of the Menu file
                     that contains the Menu information
      Description: This method is used to load Menu information into the program.
      Purpose: Try/catch loading Menu information from the text file.
   */
   public boolean loadMenu (String fileName)
   {  
      int numItem;
      String name;
      String ingredients;
      String foodCategory;
      double price;
      int calories;
      String description;
      int availability;
      double costToMake;
   
      try 
      {
         //File Input
         BufferedReader in = new BufferedReader (new FileReader (fileName));
         numItem = Integer.parseInt(in.readLine());
         for (int i = 0; i < numItem; i++)
         {
            name = in.readLine();
            ingredients = in.readLine();
            foodCategory = in.readLine();
            price = Double.parseDouble(in.readLine());
            calories = Integer.parseInt(in.readLine());
            description = in.readLine();
            availability = Integer.parseInt(in.readLine());
            costToMake = Double.parseDouble (in.readLine());
                 
            if (!restaurantMenu.addFood(name, ingredients, foodCategory, price, calories, description, availability, costToMake)) { // Logic error trapping
               System.out.println(name + " could not be added due to invalid attributes.");
               System.out.println("The program will continue without that food on the menu.\n");
            }
         }
         in.close();
         
         System.out.println("Menu file loaded.");
         return true;
      } catch (IOException iox) {
         System.out.println("\nInvalid menu info file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the menu info file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it returns a error message
                         if the String is tried to converted into a non-String, then it outputs an error message
      */
   } 
   
   /*
      Parameter(s): 
         - fileName: String file name of the Food Category file
                     that contains the Restaurant information
      Description: This method is used to load Food Category information into the program.
      Purpose: Try/catch loading Food Category information from the text file.
   */
   public boolean loadNumCategories(String categoryInfoFileName) {
      try {
         //File Input
         BufferedReader in = new BufferedReader(new FileReader(categoryInfoFileName));
         int numCategories = Integer.parseInt(in.readLine());
         
         if (numCategories < 1) { // Logic error trapping
            System.out.println("The number of categories that you have loaded is invalid. Please go back and set a valid number.");
            System.out.println("\nThe program will now terminate.");
            quit();
         }
         restaurantMenu.setNumCategories(numCategories);
         
         System.out.println("Number of categories loaded.");  
         in.close();
         return true;
      } catch (IOException iox) {
         System.out.println("\nInvalid category info file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the category info file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it returns a error message
                         if the String is tried to converted into a non-String, then it outputs an error message
      */
      
   }

   /*
         Parameter(s): 
            - fileName: String file name of the employeeInfo file
                        that contains the Restaurant information
         Description: This method is used to load Employee information into the program.
         Purpose: Try/catch loading Employee information from the text file.
   */
   public boolean loadEmployeeInfo (String fileName)
   {
      int numEmployee;
      String name;
      String occupation;
   
      try
      {
         //File Input
         BufferedReader in = new BufferedReader (new FileReader (fileName));
         numEmployee = Integer.parseInt(in.readLine());
         
         if (numEmployee > maxEmployee) { // Logic error trapping
            numEmployee = maxEmployee;
            System.out.println("\nWarning! You have more employees than the database can store.");
            System.out.println("Employees up to the max limit were loaded, and the rest discarded.");
         }
         
         for (int i = 0; i < numEmployee; i++)
         {
            name = in.readLine();
            occupation = in.readLine();
            staff.addEmployee(name, occupation);
         }
         in.close();
         
         System.out.println("Employee information loaded.");
         return true;
      } catch (IOException iox) {
         System.out.println("\nInvalid employee info file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the employee info file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it returns a error message
                         if the String is tried to converted into a non-String, then it outputs an error message
      */
   }

    /*
      Parameter(s): 
         - fileName: String file name of the Floor Plan file
                     that contains the map of how the restaurant looks like
      Description: This method is used to load the floor plan into the program.
      Purpose: Try/catch loading floor plan map from the text file.
   */
   public boolean loadFloorPlan (String fileName)
   {
      try 
      {
         //File Input
         BufferedReader in = new BufferedReader (new FileReader (fileName)); 
         for (int i = 0; i < horizontalLength; i++)
         {
            for (int j = 0; j < verticalLength; j++)
            {  
               map [i][j] = (char)(in.read());
            }
            in.readLine();
         }
         in.close();
         
         System.out.println("Floor plan loaded.");
         return true; 
      } catch (IOException iox) {
         System.out.println("\nInvalid floor plan file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the floor plan file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it returns a error message
                         if the String is tried to converted into a non-String, then it outputs an error message
      */
   }
   
   /*
      Parameter(s): 
         - fileName: String file name of the Coupon file
                     that contains the Restaurant information
      Description: This method is used to load Coupon information into the program.
      Purpose: First creates pre-existing "empty" Coupon objects.
               Then try/catch loading Coupon information from the text file.
   */
   public boolean loadCoupon (String fileName) {      
      try {
         //File Input
         BufferedReader in = new BufferedReader (new FileReader (fileName));
         this.numCoupons = Integer.parseInt(in.readLine());
      
         if (this.numCoupons > maxCoupon) {
            this.numCoupons = maxCoupon;
            System.out.println("\nWarning! You have more coupons than the database can store.");
            System.out.println("Coupons up to the max limit were loaded, and the rest discarded.");
         }
           
         for (int i = 0; i < this.numCoupons; i++) {
            couponDatabase[i] = new Coupon();
            couponDatabase[i].setCode(in.readLine()); 
            
            double discount = Double.parseDouble(in.readLine());
            if (discount <= 0 || discount >= 1) { // Logic error trapping: 0 < discounts < 1 is the range of allowed parameters
               System.out.println("One of the coupons has an unusual discount. Please close the program and double check.");
               System.out.println("\nThe program will now terminate.");
               quit();
            } else {
               couponDatabase[i].setDiscount(discount);
            }
         }
         in.close();
         
         System.out.println("Coupon information loaded.");
         return true;
      } catch (IOException iox) {
         System.out.println("\nInvalid coupon info file.");
         return false;
      } catch (NumberFormatException nfx) {
         System.out.println("\nError loading the coupon info file. Please check the file and restart the program.");
         return false;
      }   
      /*
         Error trapping: if textfile cannot be found, it returns a error message
                         if the String is tried to converted into a non-String, then it outputs an error message
      */
   }
   
// METHODS RELATED TO LOADING
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
// METHODS RELATED TO RUNNING      
   /*
      Description: This method is used to run the entire program.
      Purpose: All functionalities are tied to the run method
               of the Restaurant class.
   */
   public void run()  throws InterruptedException 
   {  
      if (!areWallsOnSides()) { // Check if the floor plan is in the correct format
         System.out.println("\nEither walls do not surround the restaurant, or the doors are not quite correct.");
         System.out.println("Please shut down the program and follow the instruction manual on how to set up the floor plan.!");
         System.out.println("The system will now terminate.");
         quit();
      }
      
      // Check if tables are placed properly (not blocked)
      if (!isAllTablesAccessible()) {
         System.out.println("\nWarning! Some tables are blocked and cannot be accessed. ");
         System.out.println("Please shut down the program and move your tables around!");
         System.out.println("The system will now terminate.");
         quit();
      }
      
      // Check if the menu is empty
      if (restaurantMenu.isMenuEmpty()) {
         System.out.println("\nWarning! The restaurant does not have any food items on the menu. ");
         System.out.println("Please shut down the program and add some food.");
         System.out.println("The system will now terminate.");
         quit();
      }
      
      System.out.print ("\n-");
      Thread.sleep(100);
      for (int i = 0; i < 60; i++) {
         System.out.print ("-");
         Thread.sleep(20);
      }
      
      System.out.println("\n\nWelcome to " + SOFTWARENAME + "!\n");
            
      runOption();
   }
   
// METHODS RELATED TO RUNNING      
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// METHODS RELATED TO MENU SCREENS
   
   /*
      Description: This method is used to display the very beginning of the program after the loading is finished.
      Purpose: Display choices where the user can input which
               option they want to move forward with.
   */
   public void runOption() {
      String[] initialMenuChoices = {"Start", "Help", "Quit"};
      Options initialMenu = new Options (initialMenuChoices);
      
      initialMenu.printAllOptions();
      int userInput = initialMenu.getUserInput();    
            
      if (userInput == 1)
         startOption();
      else if (userInput == 2)
         helpOption();
      else
         quit();
   }
   
   /*
      Description: This method is used to display the start of the program.
      Purpose: Display choices where the user can input which
               option they want to move forward with.
   */
   public void startOption ()
   {
      String[] startMenuChoices = {"Customer", "Owner", "Quit"};
      Options startMenu = new Options(startMenuChoices);
      
      startMenu.printAllOptions ();     
      int startMenuUserInput = startMenu.getUserInput();
          
      if (startMenuUserInput == 1)
         landingOption(true);
      else if (startMenuUserInput == 2)
         ownerOption();
      else
         quit();
   }
   
   /*
      Description: This method is used to display the help options.
      Purpose: Educates the user on what this program does, and how to choose an Option.
   */
   public void helpOption ()
   {
      //This help option informs the user of what the program does 
      Scanner sc = new Scanner (System.in);
      System.out.println ("This program allows you to:");
      System.out.println("Customer:");
      System.out.println ("\t- view our restaurant menu");
      System.out.println ("\t- add food(s) to an order");
      System.out.println ("\t- purchase a gold or platinum membership with respective benefits");
      System.out.println ("\t- checkout with coupon options");
      System.out.println ("\t- order takeout or dine in the restaurant");
      System.out.println("Owner: ");
      System.out.println ("\t- Add food to the existing menu");
      System.out.println ("\t- Delete food from the existing menu");
      System.out.println ("\t- View the current and past restaurant portfolio");
      System.out.println ("\t- Manage the staff members");
      System.out.println ("\t- Manage the current date");
      System.out.println ("\nTo choose an option, an input of a single digit will activate the corresponding step.");
      System.out.println ("If bad data in entered, this program will loop until good data is entered.");
      System.out.print ("\nPlease press any key to return back to the main menu: ");
      sc.nextLine();
      System.out.println();
      startOption();
   }
   
   /*
      Description: This method is used to display the owner options.
      Purpose: Display the choices exclusively only the owner has access to
   */
   public void ownerOption() {
      printHeader("OWNER OPTIONS");
   
      String[] ownerChoices = {"Add Food Item", "Remove Food Item", "Restaurant Business", "Manage Staff", "Manage Inventory", "Manage Date", "Go Back"};
      Options ownerMenu = new Options(ownerChoices);
      
      ownerMenu.printAllOptions();
      int userInput = ownerMenu.getUserInput();
      
      if (userInput == 1)
         addFoodItemOption();
      else if (userInput == 2)
         removeFoodItemOption();
      else if (userInput == 3)
         viewRestaurantBusiness();
      else if (userInput == 4)
         employeeOption();
      else if (userInput == 5)
         manageInventoryOption();
      else if (userInput == 6)
         manageDateOption();
      else
         startOption();
   }
   
   /*
      Description: This method is the option to add an item onto the menu.
      Purpose: Reads in input of fields of a Food object, and adds the
               item under the correct Food Category. If there are any errors
               made, the user can be warped back.
   */
   public void addFoodItemOption() {
      addFoodItem();
   }
   
   /*
      Description: This method is the option to remove an itemon the menu.
      Purpose: Reads in input from the user on which Food do delete. If there
               are any errors, the user can be warped back.
   */
   public void removeFoodItemOption() {
      removeFoodItem();
   }
   
   /*
      Description: This method is used to display the Employee options.
      Purpose: Displays the choices of which an employee can choose
               which option they would like to proceed with.
   */
   public void employeeOption() {
      printHeader("MANAGE STAFF");
   
      String[] employeeChoices = {"Add Employee", "Remove Employee", "View All Employees", "Go Back"};
      Options employeeMenu = new Options(employeeChoices);
      
      employeeMenu.printAllOptions();
      int userInput = employeeMenu.getUserInput();
      
      if (userInput == 1)
         addEmployee();
      else if (userInput == 2)
         removeEmployee();
      else if (userInput == 3)
         listAllEmployees();
      else
         ownerOption();
   }
   
   /*
      Description: This method is the option to manage the inventory.
      Purpose: Displays the choices of which an Owner can choose
               to restock the quantity of an item or liquify one.
   */
   public void manageInventoryOption() {
      printHeader("MANAGE INVENTORY");
   
      String[] manageInventoryChoices = {"Restock an Item", "Liquify an Item", "Go Back"};
      Options manageInventoryMenu = new Options(manageInventoryChoices);
      
      manageInventoryMenu.printAllOptions();
      int userInput = manageInventoryMenu.getUserInput();
      
      if (userInput == 1)
         changeAvailability(true);
      else if (userInput == 2)
         changeAvailability(false);
      else
         ownerOption();
   }
   
   /*
      Description: This method is used to display the date options for the user.
      Purpose: This allows the owner to update the information of the restaurant date.
   */
   public void manageDateOption () {
      Scanner sc = new Scanner (System.in);
      
      printHeader("MANAGE DATE");
      
      //tells the owner today's date
      System.out.println ("Current date is " + restaurantDate);
      
      String [] manageDateChoices = {"Go To Next Day", "Set Custom Date", "Go Back"};
      Options manageDateMenu = new Options (manageDateChoices);
      
      manageDateMenu.printAllOptions();
      int userInput = manageDateMenu.getUserInput();
      
      if (userInput == 1)
         goToNextDay();
      else if (userInput == 2)
         setCustomDate();
      else
         ownerOption();
   }
   
   /*
      Description: This method creates a new current order
      Purpose: This method creates an order for new customer.
   */   
   public void landingOption (boolean newOrderNumber)
   {
      Scanner sc = new Scanner (System.in);
            
      System.out.println ("Welcome to " + name + "!\n");
      if (newOrderNumber) {
         currentOrderNumber = generateOrderNumber();
      } 
      currentOrder = new Order (currentOrderNumber, restaurantDate, maxPossibleFoodsInMenu, tax);
      currentCoupon = new Coupon();
      currentCustomer = null;
      
      String[] landingChoices = {"Become a Premium Customer", "Continue To View Menu", "Go Back"};
      Options landingMenu = new Options (landingChoices);
      
      landingMenu.printAllOptions();
      int landingPageUserInput = landingMenu.getUserInput();
      
      if (landingPageUserInput == 1)
         chooseCustomerOption();
      else if (landingPageUserInput == 2)
         displayFoodCategoryOption();
      else {  
         System.out.print ("Enter protected password: ");
         String userInput = sc.nextLine();
         
         if (userInput.equals (MASTERKEY)) {
            System.out.println("Master key entered successfully.\n");
            startOption();
         } 
         else {
            System.out.println("Master key entered unsuccessfully. Going back to welcome screen.\n");
            landingOption(false);
         }
      }
   }
   
    /*
      Description: This method is used to display the possible choices of being a special customer.
      Purpose: To give a chance for customer to become a special customer and have different discounts.
   */
   public void chooseCustomerOption() {
      printHeader("BECOME A PREMIUM CUSTOMER");
   
      String[] chooseCustomerChoices = {"Gold Customer", "Platinum Customer", "Go Back"};
      Options chooseCustomerMenu = new Options (chooseCustomerChoices);
      
      chooseCustomerMenu.printAllOptions();
      int chooseCustomerUserInput = chooseCustomerMenu.getUserInput();
      
      if (chooseCustomerUserInput == 1)
         newCustomerCredentialsOption(1);
      else if (chooseCustomerUserInput == 2)
         newCustomerCredentialsOption(2);
      else
         landingOption(false);  
   }

   /*
      Parameter(s):
         - customerType: an integer that will correspond whether the choice
                         was to create a Gold or Platinum Customer.
      Description: This method grants the ability to create either a Gold or Platinum Customer.
      Purpose: Reads in a Customer username and password, and moves
               on to display the Food Category options.
   */
   public void newCustomerCredentialsOption(int customerType) {
      Scanner sc = new Scanner(System.in);
      
      System.out.print("Enter new username: ");
      String customerID = sc.nextLine();
      System.out.print("Enter new password: ");
      String password = sc.nextLine();
      
      if (customerType == 1)
         newGoldCustomer(customerID, password);
      else if (customerType == 2)
         newPlatCustomer(customerID, password);
   }
   
   /*
      Description: This method is the option to display all the Food Categories.
      Purpose: Displays all the Category choices which the Customer can choose from to proceed.
   */ 
   public void displayFoodCategoryOption ()
   {
      Scanner sc = new Scanner(System.in);
      System.out.println ("FOOD CATEGORIES:");
   
      String [] displayOptions = new String[restaurantMenu.getNumCategories() + 2];
      
      for (int i = 0; i < displayOptions.length-2; i++)
         displayOptions[i] = restaurantMenu.getCategoryList()[i].getCategoryName(); 
         
      displayOptions[displayOptions.length - 2] = "Proceed To Pay";
      displayOptions[displayOptions.length - 1] = "Cancel Order";
      
      Options displayMenu = new Options(displayOptions);   
   
      displayMenu.printAllOptions();   
      int userInput = displayMenu.getUserInput();
      
      if (userInput >= 1 && userInput <= restaurantMenu.getNumCategories())
         displayFoodOption(userInput);
      
      else if (userInput == displayOptions.length - 1)
      {
         if (currentOrder.getNumItems() > 0)
            paymentOption();
         else {
            System.out.print("You have no items ordered. Press any key to go back to the menu: ");
            sc.nextLine();
            System.out.println();
            displayFoodCategoryOption();
         }
      }
      else
         landingOption(false);
   }
   
   /*
      Parameter(s): 
         - categoryIndexPlusOne: the Category index inputted from
                                 the displayFoodCategoryOption added by 1
      Description: This method is the option to display all the Food items.
      Purpose: Using the categoryIndexPlusOne index, will display all the Food items
               of the index minus 1, and reads in which Food option the user chooses.
   */
   public void displayFoodOption(int categoryIndexPlusOne) 
   {
      Scanner sc = new Scanner(System.in);
      
      System.out.println ("FOOD CHOICES");
      
      String [] displayOptions = new String[restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getNumFood() + 2];  
       
      for (int i = 0; i < displayOptions.length-2; i++)
         displayOptions[i] = restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getFoodItems()[i].getFoodName(); 
   
      displayOptions[displayOptions.length - 2] = "Proceed To Pay"; 
      displayOptions[displayOptions.length - 1] = "Go Back";
      Options displayMenu = new Options(displayOptions);
      
      displayMenu.printAllOptions(); 
      int userInput = displayMenu.getUserInput ();
      
      if (userInput >= 1 && userInput <= restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getNumFood()) 
         foodOption(userInput, categoryIndexPlusOne); 
      else if (userInput == displayOptions.length - 1)
      {
         if (currentOrder.getNumItems() > 0)
            paymentOption();
         else {
            System.out.println("You have no items ordered. Press any key to go back to the menu: ");
            sc.nextLine();
            System.out.println();
            displayFoodCategoryOption();
         }
      }
      else
      {
         System.out.println();
         displayFoodCategoryOption();
      }
   }
   
   /*
      Parameter(s): 
         - categoryIndexPlusOne: the Category index inputted from displayFoodCategoryOption added by 1
         - foodIndexPlusOne: the Food index inputted from displayFoodOption added by 1
      Description: This method is the option to display all the Food options.
      Purpose: Displays options where the Customer can add it to their order or proceed to pay.
   */
   public void foodOption (int foodIndexPlusOne, int categoryIndexPlusOne)
   {
      Scanner sc = new Scanner(System.in);
      
      System.out.println (restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getFoodItems()[foodIndexPlusOne - 1]); 
      System.out.println("Current availability: " + restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getFoodItems()[foodIndexPlusOne - 1].getAvailability() + "\n");
      
      String [] mainOptions = {"Add To Order", "Proceed To Pay", "Go Back"};
      Options foodMenu = new Options(mainOptions);
      
      foodMenu.printAllOptions(); 
      int userInput = foodMenu.getUserInput();
      
      if (userInput == 1)
         addOrderOption (categoryIndexPlusOne, foodIndexPlusOne);
         
      else if (userInput == 2)
      {
         if (currentOrder.getNumItems() > 0)
            paymentOption();
         else {
            System.out.println("You have no items ordered. Press any key to go back to the menu: ");
            sc.nextLine();
            System.out.println();
            displayFoodCategoryOption();
         }
      }
      else
         displayFoodOption (categoryIndexPlusOne);  
   }
   
   /*
      Parameter(s): 
         - categoryIndexPlusOne: the Category index inputted from displayFoodCategoryOption added by 1
         - foodIndexPlusOne: the Food index inputted from displayFoodOption added by 1
      Description: This method is the Option to add it to an Order.
      Purpose: Reads the amount wanted, adds it to their Order, and subtracts the quantity from the menu.
   */
   public void addOrderOption (int categoryIndexPlusOne, int foodIndexPlusOne)
   {
      Scanner sc = new Scanner (System.in);
      int quantity = -1; // -1 is temporary, it will either be caught in the try/catch or a user input value will be fetched
      
      System.out.print ("Enter amount wanted: ");
      try {
         quantity = sc.nextInt();
         sc.nextLine();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print("\nPlease enter a valid number. Press any key to order again: ");
         sc.nextLine();
         System.out.println();
         foodOption(categoryIndexPlusOne, foodIndexPlusOne);
      }
      /*
         Error Trapping: it returns error message if the user's input doesn't match the variable's datatype
      */
      
      if (quantity > 0) {
         System.out.println();
      
         Food menuFood = restaurantMenu.getCategoryList()[categoryIndexPlusOne - 1].getFoodItems()[foodIndexPlusOne - 1]; 
         Food orderFood = new Food (menuFood.getFoodName(), menuFood.getIngredient(), menuFood.getFoodCategory(), menuFood.getPrice(), menuFood.getCalories(), menuFood.getDescription(), menuFood.getAvailability(), menuFood.getCostToMake());
      
         addFoodToOrder(menuFood, orderFood, quantity, categoryIndexPlusOne, foodIndexPlusOne);
      } else {
         System.out.println("\nPlease enter a valid number. Press any key to order again: ");
         sc.nextLine();
         System.out.println();
         foodOption(categoryIndexPlusOne, foodIndexPlusOne);
      }
   }
   
   /*
      Description: This method is the Option finalize an Order.
      Purpose: Displays options where the Customer can add
               more to their Order or proceed to checkout.
   */
   public void paymentOption ()
   {
      String [] paymentOption = {"Add More Order", "Proceed To Checkout"};
      Options paymentMenu = new Options (paymentOption);
      
      paymentMenu.printAllOptions(); 
      int userInput = paymentMenu.getUserInput();
      
      if (userInput == 1)
         displayFoodCategoryOption();
      else if (userInput == 2)
         checkoutOption();
   }    
   
   /*
      Description: This method is the option to checkout an Order.
      Purpose: Displays the current Food(s) in an Order, and the choices
               which the Customer can delete an item, add more, or directly pay.
   */
   public void checkoutOption ()
   {
      Scanner sc = new Scanner(System.in);
      
      //Giving the opportunity for customer to go back and make order again
      String [] checkOptionChoice = {"Delete Item", "Add More Order", "Pay"};
      Options checkoutMenu = new Options (checkOptionChoice);
      
      displayCurrentlyOrderedItems();
   
      checkoutMenu.printAllOptions ();
      int userInput = checkoutMenu.getUserInput();
      
      if (userInput == 1)
         deleteItemFromOrder();
      else if (userInput == 2)
         displayFoodCategoryOption();
      else if (userInput == 3)
         discountOption();
   }
   
   /*
      Description: This method is used to display discount Options.
      Purpose: Displays the options to use a Coupon code or directly skip.
               If the Coupon option is chosen, it will read in a String and
               checks if the Coupon code inputted is within the list of valid codes.
   */
   public void discountOption ()
   {
      String[] discountOptionChoice = {"Use Coupon Code", "Skip"};
      Options discountMenu = new Options (discountOptionChoice);
      
      discountMenu.printAllOptions();
      int userInput = discountMenu.getUserInput();
      
      if (userInput == 1) 
         useCouponCode();
         
      else
         premiumCustomerOption ();   
   }
   
   /*
      Description: This method is used to apply a premium customer discount.
      Purpose: Displays the Option to either apply or skip and proceed to pay.
               If apply discount is chosen, it will read in a username and password,
               and checks if it matches with the original credentials inputted.
   */
   public void premiumCustomerOption() {
      Scanner sc = new Scanner(System.in);
      
      String [] premiumCustomerChoices = {"Apply a premium customer discount!", "Skip"};
      Options premiumCustomerMenu = new Options (premiumCustomerChoices);
      
      premiumCustomerMenu.printAllOptions();
      int userInput = premiumCustomerMenu.getUserInput();
      
      if (userInput == 1)
         useCustomerDiscount();
      else
         paymentInfoOptions();
   }
   
   /*
      Description: This method is used to proceed with the payment of an Order.
      Purpose: Checks if the current Customer exists and applies the respective
               discount, calculates total after discount, tax, and final total.
               Then displays the options to confirm the pay or cancel the entire order.
   */
   public void paymentInfoOptions() {
      calculateCurrentOrderTotals();
      
      printHeader("YOUR ORDER TOTALS");
      
      //Tells all of the information of what customer has ordered. Telling the cost and food items
      System.out.println (currentOrder);
      
      String [] paymentInfoChoices = {"Confirm Pay", "Cancel Order (Last Chance!!)"};
      Options paymentInfoMenu = new Options (paymentInfoChoices);
      System.out.println();
      paymentInfoMenu.printAllOptions();   
      int userInput = paymentInfoMenu.getUserInput();
      
      if (userInput == 1)
         confirmPayOptions();
      else
         landingOption(false);
   }
   
   /*
      Description: This method is the confirmation of the payment.
      Purpose: Reads in the tip amount, and reads the choices of eat-in or takeout.
   */
   public void confirmPayOptions () {    
      askForTips();  
      
      currentOrder.setOrderDate (restaurantDate);
      currentOrder.calculateTotalCostToMake();
      restaurantFinances.getOrdersThisMonth().add(restaurantFinances.getOrdersThisMonth().size(), currentOrder);
      printReceipt(currentOrder);
      
      System.out.println("\nWill you be..");
      
      String [] confirmPayChoices = {"Eating in?", "Taking out?"};
      Options confirmPayMenu = new Options (confirmPayChoices);
      System.out.println();
      confirmPayMenu.printAllOptions();    
      int userInput = confirmPayMenu.getUserInput();
      
      if (userInput == 1)
         eatin();
      else
         takeout();
   }
   
   /*
      Description: This method is the option to quit the entire program.
      Purpose: Runs one line of code to end the program.
   */
   public void quit ()
   {
      System.out.println ("\nThank you for choosing " + SOFTWARENAME + ".");
      System.exit(0);
   }

// METHODS RELATED TO MENU SCREENS   
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
// METHODS RELATED TO LOGIC   
   
   /*
      Parameter: 
         -menuFood: the particular food that is in the menu
         -orderFood: the particular food that the owner has added
         -quantity: the amount of food that one wants
         -categoryIndexPlusOne: the index of category that the menu food is in
         -foodIndexPlusONe: the index of food that the menu food is in
      Description: this method adds the chosen food to one's order. It takes in the ordered food
                   and displays the what the customer has ordered
      Purpose: To add the chosen food item to one's order and tell the customer what one has ordered. 
   */
   public void addFoodToOrder(Food menuFood, Food orderFood, int quantity, int categoryIndexPlusOne, int foodIndexPlusOne) {
      try 
      {
         if (currentOrder.addToOrder(orderFood, quantity))
         {
            //Informing the user what one has ordered so far
            System.out.println ("You have ordered " + quantity + " " + orderFood.getFoodName() + ".");
            menuFood.setAvailability(menuFood.getAvailability() - quantity);
            System.out.println ("There are " + menuFood.getAvailability() + " left.\n");
            paymentOption();
         }
         else
         {
            System.out.println ("Invalid input.\n");
            addOrderOption (categoryIndexPlusOne, foodIndexPlusOne);
         }
      } 
      catch (InputMismatchException ime)
      {
         System.out.println ("Input error.\n");
         addOrderOption (categoryIndexPlusOne, foodIndexPlusOne);
      }
      /*
         Error Trapping: it returns error message if the user's input doesn't match the variable's datatype
      */
   }
   
   /*
      Description: This method gets user inputs for the fields that composes a food. If 
                   this process successful, it returns true, but false if not. 
      Purpose: This method allows the user to add new food items to the restaurant menu
   */
   public void addFoodItem() {
      Scanner sc = new Scanner(System.in);
      
      // These variable values are all temporary, and will either be caught or assigned new values
      String foodName = "";
      String ingredient = "";
      String foodCategory = "";
      double price = 0;
      int calories = 0;
      String description = "";
      int availability = 0;
      double costToMake = 0;
      
      printHeader("ADD AN ITEM");
      
      System.out.println("These are all the possible categories: ");
      restaurantMenu.printAllCategories();
      System.out.println();
      
      try 
      {
         System.out.print("Enter food name: ");
         foodName = sc.nextLine();
         System.out.print("Enter ingredients: ");
         ingredient = sc.nextLine();
         System.out.print("Enter food category: ");
         foodCategory = sc.nextLine();
         System.out.print("Enter price: ");
         price = sc.nextDouble();
         System.out.print("Enter calories: ");
         calories = sc.nextInt();
         sc.nextLine();
         System.out.print("Enter description: ");
         description = sc.nextLine();
         System.out.print("Enter availability: ");
         availability = sc.nextInt();
         System.out.print("Enter cost to make: ");
         costToMake = sc.nextDouble();
         sc.nextLine();
         System.out.println();
      }
      /* Error trapping: if the user enters bad data, this catch will inform
                         the user and allows them to input information again.
      */
      catch (InputMismatchException ime)
      {
         System.out.println ("Wrong Input. Start Again.\n");
         ownerOption();
      }
      
      //Sequential search
      if (restaurantMenu.searchCategoryByName(foodCategory) != null && restaurantMenu.addFood(foodName, ingredient, foodCategory, price, calories, description, availability, costToMake)) {
         System.out.println("New item added successfully.");
         System.out.print("Press any key to continue: ");
         sc.nextLine();
         System.out.println();
         ownerOption();
         
         /* Error trapping: if adding a Food onto the Restaurant menu fails,
                         the user will be notified and will return to the
                         Owner Options choices.
      */
      } else {
         System.out.print("Error! Enter any key to go back: ");
         sc.nextLine();
         System.out.println();
         ownerOption();
      }
   }
   
   /*
      Description: This method gets user to write the name of the food that one wants to delete. 
      Purpose: This method allows the user to delete existing food items from the restaurant menu
   */
   public void removeFoodItem() {
      Scanner sc = new Scanner(System.in);
      
      /*
         printAllFoodItem lists the name of all foods available in the restaurant. 
         This allows the user to write the exact name of food when removing the food item.
      */  
      
      printHeader("REMOVE AN ITEM");
      
      printAllFoodItem();
      System.out.print("Enter food name: ");
      String foodName = sc.nextLine();
      
      if (restaurantMenu.searchFoodByName(foodName) != null) {
         //Searching
         String foodCategory = restaurantMenu.searchFoodByName(foodName).getFoodCategory();
         restaurantMenu.deleteFood(foodName, foodCategory);
         System.out.print("Success! Press any key to continue: ");
      } else {
      /* Error trapping: if removing a Food from the Restaurant menu fails,
                         the user will be notified and will return to the
                         Onwer Options choices.
      */
         System.out.print("The food does not exist. Enter any key to go back: ");
      }
      sc.nextLine();
      System.out.println();
      ownerOption();
   }
   
   /*
      Description: This method is printing out all the food items in the menu.
      Purpose: To show overview of name of foods to the owner when deleting food item
   */
   public void printAllFoodItem ()
   {
      restaurantMenu.printAllFoodItem();
   }
   
   /*
      Description: This method is used to view the Restaurant business.
      Purpose: Calculates and prints revenue, spendings, and profit by month and by day.
               In addtion, calculates and prints the daily tips for each employee, and
               the predicted revenue of the corresponding month.
   */
   public void viewRestaurantBusiness() {
      Scanner sc = new Scanner (System.in);
   
      restaurantFinances.calculateAllValues();
   
      printHeader("RESTAURANT BUSINESS");
   
      System.out.println(restaurantFinances);
      System.out.print ("\nPress any key to go back: ");
      sc.nextLine();
      System.out.println();
      ownerOption();
   }
    
   /*
      Description: This method is used to add an Employee into the Restaurant.
      Purpose: Reads in information that make up an Employee object.
   */
   public void addEmployee() {
      Scanner sc = new Scanner (System.in);
      
      printHeader("ADD AN EMPLOYEE");
      
      System.out.print ("Enter name: ");
      String name = sc.nextLine();
      System.out.print ("Enter position: ");
      String position = sc.nextLine();
      
      if (staff.addEmployee (name, position)) {
         restaurantFinances.setNumEmployees(restaurantFinances.getNumEmployees() + 1);
         System.out.print ("Successful!! Press any key to continue: ");
      } else {
      /* Error trapping: if adding an Employee onto the Employee list fails,
                         the user will be notified, and any input will send the
                         user back to the Employee Options page.
      */
         System.out.print ("Unsuccessful. Press any key to continue: ");
      }
      
      sc.nextLine();
      System.out.println();
      employeeOption();
   }
   
   /*
      Description: This method is used to delete an Employee..
      Purpose: Prints out the list of Employees, and inputs which index
               of the Employee object they would like to remove.
   */
   public void removeEmployee() {
      Scanner sc = new Scanner (System.in);
         
      printHeader("REMOVE AN EMPLOYEE");
         
      staff.printAllEmployees();
      
      System.out.print ("\nEnter a number on the list to remove: ");
      int listNum = -1; // -1 is a temporary value, will be either caught or assigned a new value
      
      try {
         listNum = sc.nextInt();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to go back: ");
         sc.nextLine();
         System.out.println();
         ownerOption();
      } 
      /*
         Traps any invalid data type errors
      */
      
      sc.nextLine();
      
      if (listNum > 0 && listNum <= staff.getNumEmployees()) {
         staff.deleteEmployeeByIndex (listNum);
         restaurantFinances.setNumEmployees(restaurantFinances.getNumEmployees() - 1);
         System.out.print ("Successful!! Press any key to continue: ");
      } else {
      /* Error trapping: if removing an Employee from the Employee list fails,
                         the user will be notified, and any input will send the
                         user back to the Employee Options page.
      */
         System.out.print ("Unsuccessful... Press any key to continue: ");
      }
      
      sc.nextLine();
      System.out.println();
      employeeOption();
   }
   
   /*
      Description: This method is used to display all the Employees.
      Purpose: Displays all the Employees, and warps back when any key is inputted.
   */
   public void listAllEmployees() {
      Scanner sc = new Scanner(System.in);
        
      printHeader("ALL EMPLOYEES");  
        
      //Sorting
      staff.sortByAlphaOrder();
      staff.printAllEmployees();
      System.out.print("\nPress any key to go back: ");
      sc.nextLine();
      System.out.println();
      employeeOption();
   }
   
   /*
      Parameter(s):
         -positive: inputted boolean from the manageInventoryOption method, can be
                    either true or false is used to runs different functionalities
      Description: This method is used to change the availability of the given food.
      Purpose: Inputs which Food Category and which Food they want to either restock or liquify,
               depending on the boolean value of 'positive'.
   */
   public void changeAvailability (boolean positive) { 
      Scanner sc = new Scanner(System.in);
      
      if (positive) {
         printHeader("RESTOCK AN ITEM");
      } else {
         printHeader("LIQUEFY AN ITEM");
      }
      
      System.out.println("Choose a category..");
      restaurantMenu.printAllCategories();
      
      System.out.print("\nEnter the list number: ");
      
      int listCategoryNum = -1; // -1 is a temporary value, will be caught or assigned new value
      
      try {
         listCategoryNum = sc.nextInt();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to go back: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
      }
      // Error trapping: returns error message if the given input does not match the variable's datatype
         
      if (listCategoryNum < 1 || listCategoryNum > restaurantMenu.getNumCategories()) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to go back: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
      }
      
      System.out.println("\nChoose an item..");
      restaurantMenu.getCategoryList()[listCategoryNum - 1].printAllFood(); 
      System.out.print("\nEnter the list number: ");
      
      int listFoodNum = -1; // -1 is a temporary value, will be caught or assigned new value
      
      try {
         listFoodNum = sc.nextInt();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to go back: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
         /* Error trapping: returns error message if the given input does not match the variable's datatype
                            Also, goes back to previous menu and allow the user to repeat the step
         */
      }
         
      if (listFoodNum < 1 || listFoodNum > restaurantMenu.getCategoryList()[listCategoryNum - 1].getNumFood()) { 
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to go back: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
      }
      
      sc.nextLine();
      System.out.println("\nCurrent availability: " + restaurantMenu.getCategoryList()[listCategoryNum - 1].getFoodItems()[listFoodNum - 1].getAvailability()); 
      System.out.print("Enter the amount: ");
         
      int amount = -1; // -1 is a temporary value, will be caught or assigned new value
         
      try {
         amount = sc.nextInt();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to try again: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
        /* Error trapping: returns error message if the given input does not match the variable's datatype
                            Also, goes back to previous menu and allow the user to repeat the step
         */
      }
         
      if (amount < 1) { // Logic error trapping 
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to try again: ");
         sc.nextLine();
         System.out.println();
         manageInventoryOption();
      }
      
      sc.nextLine();
      if (positive) {
         restaurantMenu.getCategoryList()[listCategoryNum - 1].getFoodItems()[listFoodNum - 1].changeAvailability(restaurantMenu.getCategoryList()[listCategoryNum - 1].getFoodItems()[listFoodNum - 1].getAvailability() + amount); 
      } else {
         restaurantMenu.getCategoryList()[listCategoryNum - 1].getFoodItems()[listFoodNum - 1].changeAvailability(restaurantMenu.getCategoryList()[listCategoryNum - 1].getFoodItems()[listFoodNum - 1].getAvailability() - amount); 
      }
         
      System.out.print("Success! Press any key to continue: ");
      sc.nextLine();
      System.out.println();
      ownerOption();   
   }
   
   /*
      Description: This method updates the restaurant date to the next day depending on the current status of the date
      Purpose: This method allows the owner to change the date to the next day
   */
   public void goToNextDay() {
      Scanner sc = new Scanner(System.in);
      restaurantDate = restaurantDate.goToNextDay(restaurantDate);
      restaurantFinances.setRestaurantDate(restaurantDate);
      updateOrdersByDate(restaurantDate);
      resetTables();
      
      //this allows the owner to easily change the restaurant date to the next day (without setting next day using custom date option)
      System.out.println ("Updated date to " + restaurantDate);
      System.out.print ("Press any key to continue: ");
      sc.nextLine();
      System.out.println();
      ownerOption();
   }
   
   /*
      Description: This method updates the restaurant date to the custom date that the owner has inputed
      Purpose: This method allows the owner to change the restaurant date to particular date
   */
   public void setCustomDate() {
      Scanner sc = new Scanner(System.in);
      int year = 0, month = 0, day = 0;
         
      printHeader("SET CUSTOM DATE");
         
      //This allows the user to set the date 
      try {
         System.out.print ("Enter year: ");
         year = sc.nextInt();
         System.out.print ("Enter month: ");
         month = sc.nextInt();
         System.out.print ("Enter day: ");
         day = sc.nextInt();
      } catch (InputMismatchException imx) {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to try again: ");
         sc.nextLine();
         System.out.println();
         manageDateOption();
      }
      /* Error trapping: returns error message if the given input does not match the variable's datatype
                            Also, goes back to previous menu and allow the user to repeat the step
      */
      
      if (restaurantDate.setCustomDate (year, month, day) != null) { // Logic error trapping
         restaurantDate = restaurantDate.setCustomDate (year, month, day);
         restaurantFinances.setRestaurantDate(restaurantDate);
         updateOrdersByDate(restaurantDate);
         resetTables();
            
         sc.nextLine();
            
         System.out.println ("Successfully updated date to " + restaurantDate);
         System.out.print ("Press any key to continue: ");
         sc.nextLine();
         System.out.println();
         ownerOption();
      } else {
         sc.nextLine();
         System.out.print ("Invalid input. Press any key to try again: ");
         System.out.println();
         sc.nextLine();
         manageDateOption();
      }
   }
    
   /*
      Parameter: 
         - date: the date of the order that is made
      Description: this method clears the information of the finance if the month of the order made and month used in finance
                   calculation is different
      Purpose: If a new month is reached, then all the existing orders must be archived.
   */
   public void updateOrdersByDate (Date date) {
      if (!restaurantFinances.getRestaurantDate().isSameMonth(date)) {      
         restaurantFinances.getOrdersThisMonth().clear();       
      }
      restaurantFinances.setRestaurantDate(date);
   }
 
   /*
      Parameter: 
         - customerID: String value that the user inputs when becoming a gold customer
         - password: String value that the user inputs when becoming a gold customer 
      Description: This method gives a corresponding message based on the boolean expression returned
                   from makeNewGoldCustomer method            
      Purpose: This method outputs successful message if the current customer is added to a
               premium gold customer
   */
   public void newGoldCustomer (String customerID, String password) {
      Scanner sc = new Scanner(System.in);
      
      if (makeNewGoldCustomer(customerID, password)) {
         System.out.println("\nSuccessful!\n");
         System.out.print("Press any key to continue! ");
         sc.nextLine();
         System.out.println();
         landingOption(false);
      } else {
         /* Error trapping: if a new premium Gold Customer cannot be made,
                            the user will be notified and can try again.
         */
         System.out.print("\nUnsuccessful! Press any key to go back.");
         sc.nextLine();
         System.out.println();
         chooseCustomerOption();
      }
   }
   
   /*
      Parameter: 
         - customerID: String value that the user inputs when becoming a plat customer
         - password: String value that the user inputs when becoming a plat customer 
      Description: This method gives a corresponding message based on the boolean expression returned
                   from makeNewPlatCustomer method            
      Purpose: This method outputs successful message if the current customer is added to a
               premium plat customer
   */
   public void newPlatCustomer(String customerID, String password) {
      Scanner sc = new Scanner(System.in);
      if (makeNewPlatCustomer(customerID, password)) {
         System.out.println("Successful!");
         System.out.print("Press any key to continue! ");
         sc.nextLine();
         System.out.println();
         landingOption(false);
      } else {
         /* Error trapping: if a new premium Platinum Customer cannot be made,
                            the user will be notified and can try again.
         */
         System.out.print("\nUnsuccessful! Press any key to go back.");
         sc.nextLine();
         System.out.println();
         chooseCustomerOption();
      }
   }
   
   /*
      Parameter(s): 
         - customerID/password: String inputted by user
      Description: This method creates a Gold Customer.
      Purpose: Using the customerID and password, a new Gold Customer is made.
               Will return false if the ID already exist.
   */
   public boolean makeNewGoldCustomer (String customerID, String password) {
      if (!checkExistingCustomerID(customerID)) {
         customers.add(new GoldCustomer(customerID, password));
         return true;
      }
      return false;
   }
   
   /*
      Parameter(s): 
         - customerID/password: String inputted by user
      Description: This method creates a Platinum Customer.
      Purpose: Using the customerID and password, a new Platinum Customer is made.
               Will return false if the ID already exist.
   */
   public boolean makeNewPlatCustomer (String customerID, String password) {
      if (!checkExistingCustomerID(customerID)) {
         customers.add(new PlatCustomer(customerID, password));
         return true;
      }
      return false;
   }
   
   /*
      Parameter(s): 
         - customerID: String inputs from user
      Description: This method checks for already existing credential.
      Purpose: Returns true if ID already exists, otherwise returns false.
   */
   public boolean checkExistingCustomerID (String customerID) {
      for (int i = 0; i < customers.size(); i++) {
         if (customers.get(i).getCustomerID().equals(customerID)) {
            return true;
         }
      }
      
      return false;
   }
   
   /*
      Description: This method gives writes all food names and amount of foods that one has ordered    
      Purpose: This method was created to show the customer all of the foods that one has ordered in a list form. 
   */
   public void displayCurrentlyOrderedItems () {
      System.out.println ("Currently you have ordered:");
      for (int i = 0; i < currentOrder.getNumItems(); i++)
      {
         System.out.println ((i+1) + ". " + currentOrder.getFoodList()[i].getAvailability() + " " + currentOrder.getFoodList()[i].getFoodName());
      }
      System.out.println ();
   }
   
   /*
      Description: This method is the option to delete an item off of an Order.
      Purpose: Inputs the index of which the user would like off of their Order,
               and restocks the original Food's quantity in the menu.
   */
   public void deleteItemFromOrder ()
   {
      Scanner sc = new Scanner (System.in);
      System.out.print ("Enter the list number you want to delete: ");
      
      int num = -1; // -1 is a temporary value, it will be caught or assigned a new value
      try {
         num = sc.nextInt();
         sc.nextLine();
      } catch (InputMismatchException imx) {
         System.out.println ("Invalid input. Press any key to try again.");
         sc.nextLine();
         deleteItemFromOrder();
      }
      /* Error trapping: returns error message if the given input does not match the variable's datatype
                            Also, goes back to previous menu and allow the user to repeat the step
      */
      
      Food tempFood = currentOrder.getFoodList()[num - 1]; 
      Food tempRestaurantFood = restaurantMenu.searchFoodByName(tempFood.getFoodName());
      tempRestaurantFood.setAvailability(tempRestaurantFood.getAvailability() + tempFood.getAvailability());
      
      int numItemsToBeAddedBack = currentOrder.deleteAnOrder (num);
      if (numItemsToBeAddedBack != -1)
      {
         //Food temptempFood = restaurantMenu.getCategoryList()[index].searchFoodByName(tempFood.getFoodName());
         System.out.println ("Successfully removed.\n");
         checkoutOption ();
      }
      else
      {
         System.out.println ("Try again.\n");
      }
   }   
   
   /*
      Description: This method gets user input of the coupon code and returns message corresponding to the
                   boolean expression from checkForValidCoupon method         
      Purpose: This method allows the customer to have a discount by writing the correct coupon code
   */
   public void useCouponCode() {
      Scanner sc = new Scanner (System.in);
      System.out.print ("Enter the Coupon code: ");
      String code = sc.nextLine();
                
      if (checkForValidCoupon(code))
      {
         currentCoupon = searchCouponByCode(code);
         System.out.println ("Successfully used.\n");
         premiumCustomerOption ();
      }
      else
      {
         System.out.println ("Invalid code.\n");
         discountOption ();
      }
   }
   
   
   /*
      Description: This method gets user input of the user name and password and returns message corresponding to the
                   boolean expression from isCustomerCredentialsCorrect method         
      Purpose: This method allows the customer to have a discount by checking if the customer is a premium customer
   */
   public void useCustomerDiscount() {
      Scanner sc = new Scanner(System.in); 
      System.out.print("Enter username: ");
      String username = sc.nextLine();
      System.out.print("Enter password: ");
      String password = sc.nextLine();
      
      if (isCustomerCredentialsCorrect(username, password)) {
         currentCustomer = searchCustomerByID(username);
         
         System.out.println("Successful! Discount will be applied."); 
         System.out.print("Press any key to continue: ");
         sc.nextLine();
         System.out.println();
         paymentInfoOptions();
      } else {
         System.out.println("Invalid credentials! Enter again!\n");
         premiumCustomerOption();
      } 
   }
   
   /*
      Description: This method calculate the amount of money that one has to pay after one has finished ordering      
      Purpose: This method tells the customer the amount of money that one has paid, taking account of whether one is 
               premium customer
   */
   public void calculateCurrentOrderTotals() {
      currentOrder.calculateSubtotal();
      currentOrder.calculateTotalAfterCouponDiscount(currentCoupon.getDiscount());
      
      if (currentCustomer != null) {
         if (currentCustomer instanceof GoldCustomer) { //Polymorphism (using instanceof)
            currentOrder.calculateTotalAfterCustomerDiscount(currentCustomer.getDiscount(goldDiscount));
         } else {
            currentOrder.calculateTotalAfterCustomerDiscount(currentCustomer.getDiscount(platDiscount));
         }
      } else {
         currentOrder.calculateTotalAfterCustomerDiscount(0);
      }  
   
      currentOrder.calculateTaxAmount();
      currentOrder.calculateFinalTotal();
   }
   
   /*
      Description: This method gets user input of the amount of tips that one will give      
      Purpose: This method makes the customers to give tips
   */
   public void askForTips() {
      Scanner sc = new Scanner (System.in);
      System.out.print ("Enter desired tips: $");
      
      try {
         double tipAmount = sc.nextDouble();
         
         if (tipAmount < 0) {
            System.out.println ("Invalid input!\n");
            confirmPayOptions();
         }
         
         currentOrder.setTip (tipAmount);
      } catch (InputMismatchException imx) {
         System.out.println ("Invalid input!\n");
         confirmPayOptions();
      } 
      /* Error trapping: returns error message if the given input does not match the variable's datatype
                            Also, goes back to previous menu and allow the user to repeat the step
      */
   }
  
   /*
      Parameter: 
         - order: the order that the customer has finally made
      Description: This method prints out the reciept based on the foods that the customer has ordered
      Purpose: To create a text file of the orders that a customer has made, and the amount of money that the
               customer has to pay
   */
   public void printReceipt (Order order) {
      try {
         //File Output
         BufferedWriter out = new BufferedWriter (new FileWriter ("Receipts/" + order.getOrderNumber() + ".txt", false)); 
         
         out.write (name);
         out.newLine();
         out.write (phoneNumber + "");
         out.newLine();
         out.write (address);
         out.newLine();
         out.write (restaurantDate.toString());
         out.newLine();
         out.newLine();
         out.write ("Items purchased:");
         out.newLine();
         
         for (int i = 0; i < order.getNumItems(); i++) {
            out.write(currentOrder.getFoodList()[i].getAvailability() + " " + currentOrder.getFoodList()[i].getFoodName());
            out.newLine();
         }
         
         out.newLine();
         //out.write(order.toString()); //fix later
         
         out.write("Subtotal: $" + order.getSubtotal());
         out.newLine();
         out.write("Discounts: -$" + (order.getSubtotal() - order.getTotalAfterCustomerDiscount()));
         out.newLine();
         out.write("Tax: $" + order.getTaxAmount());
         out.newLine();
         out.write("Total: $" + order.getFinalTotal());
         out.newLine();
         out.newLine();
         out.newLine();
         out.write("Tips: $" + order.getTip());
         out.newLine();
         out.write("---------------------");
         out.newLine();
         out.write("Approved - Thank You!");
         out.newLine();
         out.write(order.getOrderNumber() + "");
         
         out.close();
      } catch (IOException iox) {
         System.out.println ("Oops.");
      }
      /*
         Error Trapping: returns error message if the reciept cannot be written in txt file. 
     */
   }
   
   /*
      Description: This method is the option to eat-in at the Restaurant.
      Purpose: Calls on methods to find a table, and announce the arrival of an Order.
   */
   public void eatin() {
      //this displays the user's seating place and tells when the foods will come
      System.out.println("Your table is marked with an " + MARKEDTABLE + ".");
      System.out.println("Any tables currently in use is marked with an " + TAKENTABLE + ".\n");
   
      findTable();
      System.out.println();
      orderArrival();  
      goBackToWelcomeScreen(); 
   }
   
   /*
      Description: This method is the option to takeout an Order.
      Purpose: Calls on methods to find a table, and announce the arrival of an Order.
   */
   public void takeout () {
      //tells the user when the food will come 
      orderArrival();   
      goBackToWelcomeScreen(); 
   }
   
   /*
      Description: This method is used to display the arrival Order time in minutes.
      Purpose: Prints the generated number of minutes required to wait for an Order.
   */
   public void orderArrival () {
      System.out.println ("Your order will arrive in " + currentOrder.determineArrivalTime() + " minutes.");
   }
   
   /*
      Description: This method is used to return to the welcome screen.
      Purpose: Reads in any key to warp back to the welcome screen to make another Order.
   */
   public void goBackToWelcomeScreen () {
      Scanner sc = new Scanner (System.in);
      
      System.out.print ("\nPress any key to make a new order: ");
      sc.nextLine();
      System.out.println();
      
      landingOption(true);  
   }
   
   /*
      Description: This method is used to generate an Order number.
      Purpose: Takes the current Order integer and returns it added by 1.
   */
   public int generateOrderNumber() {
      currentOrderNumber++;
      return currentOrderNumber;
   }
 
   /*
      Description: This method is used to find an empty table for a Customer.
      Purpose: For each row and column, find the first empty table, and assigns it 'O' 
               to show the customer where the table is, and then sets it to taken.
   */
   public void findTable(){      
      if (!isAllTablesAccessible()) {
         Scanner sc = new Scanner(System.in);
         System.out.println("The owner seems to have had a mistake in placing their tables. Seating could not be found at this time.");
         System.out.println("Please call an employee over to collect this device.");
         System.out.println("Press any key to go back to the main menu.");
         sc.nextLine();
         startOption();
      }
      
      int takenRow = 0;
      int takenCol = 0;
      
      boolean found = false;
   
      for (int row = 0; row < horizontalLength && !found; row++) {
         for (int col = 0; col < verticalLength && !found; col++) {
            if (map[row][col] == TABLE) {
               map[row][col] = MARKEDTABLE; 
               found = true;
               takenRow = row;
               takenCol = col;
            }
         }
      }
      
      if (!found) {
         System.out.println ("Please leave immediately.");
      } else {
         //tells the customer where to sit
         for (int i = 0; i < horizontalLength; i++) {
            for (int k = 0; k < verticalLength; k++) {
               System.out.print (map [i][k]);
            }
            System.out.println();
         }
         
         map[takenRow][takenCol] = TAKENTABLE;
      }
   } 

   /*
      Description: This method is used to reset tables back to empty.
      Purpose: Resets all originally taken tables to be regular tables.
   */
   public void resetTables()
   {
      for (int i = 0; i < horizontalLength; i++)
      {
         for (int j = 0; j < verticalLength; j++)
         {
            if (map[i][j] == TAKENTABLE)
            {
               map[i][j] = TABLE;
            }
         }
      }
   }
   
   /*
      Parameter(s): 
         - beforeReset: character on the current map that wants to be set
         - afterReset: character which the beforeReset will be set to
      Description: This method is used to reset tables back to empty.
      Purpose: Resets all originally taken tables to be regular tables.
   */
   public void resetTable(char beforeReset, char afterReset) {
      for (int i = 0; i < horizontalLength; i++)
      {
         for (int j = 0; j < verticalLength; j++)
         {
            if (map[i][j] == beforeReset)
            {
               map[i][j] = afterReset;
            }
         }
      }
   }
   /*
      Parameter(s): 
         none
      Description: Checks if the restaurant is surrounded by walls, and 
                   that there is only one door for the floor. This is
                   to make sure that the backtracking does not run into
                   an error.
      Purpose: Check if the text file is formatted correctly.
   */
   public boolean areWallsOnSides () {
      int doors = 0;
      
      if (map[doorRow][doorColumn] != DOOR) {
         return false;
      }
      
      for (int i = 0; i < horizontalLength; i++) {
         if (map[0][i] == WALL || map[0][i] == DOOR) {
            if (map[0][i] == DOOR) {
               doors++;
            }  
         } else {
            return false;
         }
         if (map[verticalLength - 1][i] == WALL || map[verticalLength - 1][i] == DOOR) {
            if (map[verticalLength - 1][i] == DOOR) {
               doors++;
            }  
         } else {
            return false;
         }
      }
      
      for (int i = 1; i < verticalLength - 1; i++) {
         if (map[i][0] == WALL || map[i][0] == DOOR) {
            if (map[i][0] == DOOR) {
               doors++;
            }  
         } else {
            return false;
         }
         if (map[horizontalLength - 1][i] == WALL || map[horizontalLength - 1][i] == DOOR) {
            if (map[horizontalLength - 1][i] == DOOR) {
               doors++;
            }  
         } else {
            return false;
         }
      }
      
      if (doors > 1) {
         return false;
      }
      
      return true;
   }
   
   /*
      Parameter(s): 
         - row: desired row integer
         - col: desired column integer
      Description: This method is used to find out whether the table is accessible.
      Purpose: Uses recursion to confirm whether the table is accessible.
   */
   public boolean isTableAccessible(int row, int col) {
      boolean success = false;
      
      //Recursion
      if (map[row][col] == DOOR) {
         return true;
      } else {
         if (map[row][col] != TABLE) {
            map[row][col] = TAKEN;
         }
         
         if (map[row - 1][col] == FLOOR || map[row - 1][col] == DOOR) {
            success = isTableAccessible(row - 1, col);
         }
         if (!success) {
            if (map[row][col + 1] == FLOOR || map[row][col + 1] == DOOR) {
               success = isTableAccessible(row, col + 1);
            } 
         }
         if (!success) {
            if (map[row + 1][col] == FLOOR || map[row + 1][col] == DOOR) {
               success = isTableAccessible(row + 1, col);
            } 
         }
         if (!success) {
            if (map[row][col - 1] == FLOOR || map[row][col - 1] == DOOR) {
               success = isTableAccessible(row, col - 1);
            } 
         }
      }
      
      return success;
   }
   
   /*
      Description: This method is used find out whether all tables are accessible.
      Purpose: For every row and column, will return false if just one table is not 
               accessible from the isTableAccessible method it will return false. 
               Otherwise will return true if all tables are accessible.
   */
   public boolean isAllTablesAccessible ()
   {
      for (int i = 0; i < horizontalLength; i++)
      {
         for (int j = 0; j < verticalLength; j++)
         {
            if (map[i][j] == TABLE)
            {
               if (isTableAccessible(i, j) == false)
               {
                  resetTable(TAKEN, FLOOR);
                  return false;
               }
               
               resetTable(TAKEN, FLOOR);
            }
         }
      }
      return true;
   }
   
   /*
      Parameter(s):
         -couponCode: String inputted by user
      Description: This method is used find out whether the inputted Coupon exists.
      Purpose: For every Coupon code, if it matches with the inputted code it will
               return true, otherwise return false.
   */
   public boolean checkForValidCoupon (String couponCode) {
      for (int i = 0; i < numCoupons; i++) {
         if (couponDatabase[i].getCode().equals (couponCode)) { 
            return true;
         }
      }
   
      return false;
   }

   /*
      Parameter(s):
         -couponCode: Coupon code in String format
      Description: This method is used to search for a Coupon by its code.
      Purpose: For every Coupon code, if it matches with couponCode it will
               return the Coupon object, otherwise return null.
   */
   public Coupon searchCouponByCode (String couponCode) { //Sequential Search
      if (checkForValidCoupon(couponCode)) {
         for (int i = 0; i < numCoupons; i++) {
            if (couponDatabase[i].getCode().equals (couponCode)) {
               return couponDatabase[i];
            }
         }
      
      }
      return null;
   }
   
   /*
      Parameter(s):
         -customerID: Customer username/ID in String format
      Description: This method is used to search for a Customer by their ID.
      Purpose: For every Customer, if the ID matches with the explicit ID it will
               return the Customer object, otherwise return null.
   */
   public Customer searchCustomerByID(String customerID) { //Sequential Search
      for (int i = 0; i < customers.size(); i++) {
         if (customers.get(i).getCustomerID().equals(customerID)) {
            return customers.get(i);
         }
      }
   
      return null; 
   }
   
   /*
      Parameter(s):
         -customerID/password: inputted credentials in String format
      Description: This method is used to check if the Customer credentials are correct.
      Purpose: Creates a temporary Customer object with customerID and password, 
               and if those credentials match it will return true, otherwise return false.
   */
   public boolean isCustomerCredentialsCorrect(String customerID, String password) {
      Customer tempCustomer = searchCustomerByID(customerID);
      if (tempCustomer != null) {
         if (tempCustomer.getCustomerPassword().equals(password)) {
            return true;
         }
      }
      
      return false;
   }
   
   /*
      Parameter(s):
         -header: inputted header title in String format
      Description: This method is used to print header titles
      Purpose: To print header titles at ease
   */
   public void printHeader(String header) {
      for (int i = 0; i < header.length(); i++) {
         System.out.print("*");  
      }
      System.out.println();
      System.out.println(header);
      for (int i = 0; i < header.length(); i++) {
         System.out.print("*");  
      }
      
      System.out.println();
      System.out.println();
   }
}



// public Order[] getAllMonthlyOrders (Date restaurantDate){
//   for (i = 0; i < numOrders; i++)
//     if (orders[i].month == restaurantDate.month && orders[i].year == restaurantDate.year)
//       counter++;
//   
//   Orders [] tempMonthlyOrders = new Orders [counter];
// 
//   int j = 0;
// 
//   for (int i = 0; i < numOrders; i++)
//     if orders[i].month == restaurantDate.month && orders[i].year == restaurantDate.year){
//       tempMonthlyOrders[j] = orders[i];
//       j++;
//     }
//   
//   return tempMonthlyOrders;
// 
// }

// public Order[] getAllDailyOrders(Date restaurantDate)
// {
//   for (int i = 0; i < numOrders)
//     if orders[i].day == restaurantDate.day && orders[i].month == restaurantDate.month && orders[i].year == restaurantDate.year)
//       counter++;
//   Orders [] tempDailyOrders = new Orders [counter];
// 
//   int j = 0;
// 
//   for (int i = 0; i < numOrders; i++)
//   {
//     if orders[i].day == restaurantDate.day && orders[i].month == restaurantDate.month && orders[i].year == restaurantDate.year){
//       tempDailyOrders[j] = orders[i];
//       j++;
//     }
//   }
// 
//   return tempDailyOrders;
// }

// public Order searchOrderByOrderNumber (int givenOrderNum)
// {
//   for (int i = 0; i < orderList.length; i++)
//     if (orderList[i].getOrderNumber() == givenOrderNum)
//       return orderList[i];
//     else
//       return null;
// }

// public void associateCustomerToOrder(int orderNumber, int customerID) {
//   int customerIndex = -1;
//   int orderIndex = -1;
// 
//   Customer tempCustomer = searchCustomerByID(customerID);
//   Order tempOrder = searchOrderByOrderNumber(orderNumber);
// 
//   for (int i = 0; i < numCustomers; i++) {
//     if (customers[i].getCustomerID() == tempCustomer.getCustomerID()) {
//       customerIndex = i;
//     }
//   }
// 
//   if (customerIndex != -1) {
//     for (int i = 0; i < numOrders; i++) {
//       if (orders[i].getOrderNumber() == orders.getOrderNumber()) {
//         orderIndex = i;
//       }
//     }
// 
//     if (orderIndex != -1) {
//       orders[orderIndex].setCustomer(customers[customerIndex]);
//       customers[customerIndex].setOrder(orders[orderIndex]);
//     }
//   }
// }

// public int generateCustomerID() {
//   currentCustomerID++;
//   return currentCustomerID;
// }

   // public Order searchByOrder (int num)
//    {
//       for (int i = 0; i < orders.length; i++)
//       {
//          if (orders[i].getOrderNumber() == num)
//          {
//             return (orders[i]);
//          }
//       }
//       return null;
//    }
// public boolean couponCodeCheck ()
//    {
//       Scanner sc = new Scanner (System.in);
//       System.out.println ("Enter the Coupon Code");
//       String code = sc.nextLine();
//             
//       return checkForValidCoupon (code);      
//    }
// FoodCategory tempFoodCategory = restaurantMenu.searchCategoryByName(tempFood.getFoodCategory());
   //       int index = -1;
   //    
   //       for (int i = 0; i < restaurantMenu.getNumCategories(); i++) 
   //       {
   //          if (tempFoodCategory.getCategoryName().equals(restaurantMenu.getCategoryList()[i].getCategoryName())) 
   //          {
   //             index = i;
   //          }
   //       }
   //    
   //       if (index != -1) 
   //       {        
   //          Food temptempFood = restaurantMenu.getCategoryList()[index].searchFoodByName(tempFood.getFoodName());
   //          
   //          for (int j = 0; j < restaurantMenu.getCategoryList()[index].getNumFood(); j++) {
   //             if (restaurantMenu.getCategoryList()[index].getFoodItems()[j].getFoodName().equals(temptempFood.getFoodName())) {
   //                restaurantMenu.getCategoryList()[index].getFoodItems()[j].setAvailability(tempFood.getAvailability() + temptempFood.getAvailability());
   //                
   //             }
   //          }
   //       }
      // public void customerOption()
//    {
//       String[] customerMenuChoices = {"View Food Category", "Quit"};
//       Options customerMenu = new Options(customerMenuChoices);
//       
//       customerMenu.printAllOptions(); 
//         
//       int customerUserInput = customerMenu.getUserInput();
//       
//            
//       if (customerUserInput == 1)
//          displayFoodCategoryOption ();
//       else
//          quit();
//    }
// public void editEmployee() {
//       Scanner sc = new Scanner (System.in);
//       for (int i = 0; i < staff.getNumEmployees(); i++) {
//          System.out.println (i + 1 + "." +staff.getEmployeeList() [i]);
//       }
//       
//       System.out.print ("Enter a number on the list to edit: ");
//       int listNum = sc.nextInt();
//       sc.nextLine();
//       //fix using try catch
//       if (listNum > 0 && listNum <= staff.getNumEmployees()) {
//          String name = staff.getEmployeeList()[listNum - 1].getName();
//          Field[] employeeFields = Employee.class.getFields();
//          System.out.println(employeeFields.length);
//          for (int i = 0; i < employeeFields.length - 1; i++) {
//             System.out.println (i + 1 + "." + employeeFields[i].getName());
//          }
//          System.out.print ("Enter a number on the list to edit: ");
//          int fieldListNum = sc.nextInt();
//          sc.nextLine();
//          
//          if (listNum > 0 && listNum <= staff.getNumEmployees()) {
//             System.out.print("Enter the value of the attribute: ");
//             String value = sc.nextLine();
//             if (staff.editEmployee(name, employeeFields[fieldListNum - 1].getName(), value)) {
//                System.out.println ("Successful!! Press any key to continue...");
//             } else {
//                System.out.println ("Unsuccessful... Press any key to continue...");
//             }
//          } else {
//             System.out.println ("Unsuccessful... Press any key to continue...");
//          }
//       } else {
//          System.out.println ("Unsuccessful... Press any key to continue...");
//       }
//       
//       sc.nextLine();
//       employeeOption();
//    }
   
   // public void loadRestaurantOption() {
//       String[] loadRestaurantChoices = {"Restaurant Infoformation", "Employee Information", "Food Database", "Floor Plan", "CouponDatabase", "Go Back"};
//       Options loadRestaurantMenu = new Options(loadRestaurantChoices);
//       Scanner sc = new Scanner(System.in);
//       
//       loadRestaurantMenu.printAllOptions();
//       int userInput = loadRestaurantMenu.getUserInput();
//       
//       if (userInput == 1) {
//          System.out.print("Enter text file name: ");
//          String fileName = sc.nextLine();
//          
//          if (loadRestaurant(fileName)) {
//             System.out.println("Success! Press any key to continue.");
//             sc.nextLine();
//          } else {
//             System.out.println ("Unable to load... Press any key to continue.");
//             sc.nextLine();
//          }
//          loadRestaurantOption();
//       } else if (userInput == 2) {
//          System.out.print("Enter text file name: ");
//          String fileName = sc.nextLine();
//          
//          if (loadEmployeeInfo(fileName)) {
//             System.out.println("Success! Press any key to continue.");
//             sc.nextLine();
//          } else {
//             System.out.println ("Unable to load... Press any key to continue.");
//             sc.nextLine();
//          }
//          loadRestaurantOption();
//       } else if (userInput == 3) {
//          System.out.print("Enter text file name: ");
//          String fileName = sc.nextLine();
//          
//          if (loadMenu(fileName)) {
//             System.out.println("Success! Press any key to continue.");
//             sc.nextLine();
//          } else {
//             System.out.println ("Unable to load... Press any key to continue.");
//             sc.nextLine();
//          }
//          loadRestaurantOption();
//       } else if (userInput == 4) {
//          System.out.print("Enter text file name: ");
//          String fileName = sc.nextLine();
//          
//          if (loadFloorPlan(fileName)) {
//             System.out.println("Success! Press any key to continue.");
//             sc.nextLine();
//          } else {
//             System.out.println ("Unable to load... Press any key to continue.");
//             sc.nextLine();
//          }
//          loadRestaurantOption();
//       } else if (userInput == 5) {
//          System.out.print("Enter text file name: ");
//          String fileName = sc.nextLine();
//          
//          if (loadCoupon(fileName)) {
//             System.out.println("Success! Press any key to continue.");
//             sc.nextLine();
//          } else {
//             System.out.println ("Unable to load... Press any key to continue.");
//             sc.nextLine();
//          }
//          loadRestaurantOption();
//       } else {
//          ownerOption();
//       }
//    }
//       for (int i = 0; i < restaurantMenu.getNumCategories(); i++)
   //       {
   //          int num = restaurantMenu.getCategoryList().length;
   //          String name = restaurantMenu.getCategoryList()[i].getCategoryName();
   //          System.out.println (name);
   //       }
   //       System.out.println (restaurantMenu.getNumCategories());