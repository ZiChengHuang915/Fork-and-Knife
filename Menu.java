/*
   Class Name: Menu
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: 
*/

class Menu {
   //Encapsulation
   private FoodCategory[] categoryList; //Array of Object
   private int numCategories;
   private int maxPossibleCategoriesInMenu;
   
   //Constructor
   public Menu (int maxPossibleCategoriesInMenu, int maxPossibleFoodsInCategory, String[] foodCategoryNames){
      this.maxPossibleCategoriesInMenu = maxPossibleCategoriesInMenu;
      categoryList = new FoodCategory[maxPossibleCategoriesInMenu];
      
      for (int i = 0; i < foodCategoryNames.length; i++) {
         categoryList[i] = new FoodCategory(foodCategoryNames[i], maxPossibleFoodsInCategory); // Array of Object
      }
       
      numCategories = 0;
   }
  
   //Accessors and Mutators
   //Encapsulation
   public FoodCategory[] getCategoryList ()
   {
      return categoryList;
   }
   
   public int getNumCategories ()
   {
      return numCategories;
   }
  
   public void setNumCategories (int num)
   {
      numCategories = num;
   }
  
  /*
      Parameter(s):
         - foodName: name of the new food
         - ingredient: ingredient of the new food
         - foodCategory: the food catgory that the new food is in
         - price: the cost of the food
         - calories: calories of the food
         - description: explanation for particular food item
         - availability: number of food available
         - costToMake: cost to make the actual food item
      Return Value: this method returns a boolean expression
      Description: This method creates a new food item that will go into the menu
   */
   public boolean addFood (String foodName, String ingredient, String foodCategory, double price, int calories, String description, int availability, double costToMake) {
      Food tempFood = searchFoodByName(foodName);
      if (tempFood == null) {
         FoodCategory tempFoodCategory = searchCategoryByName(foodCategory);
         
         if (tempFoodCategory == null) { // If the specified category of the food does not match any of the current categories
            return false;
         }
         
         int index = -1;
      
         for (int i = 0; i < numCategories; i++) 
         {
            //Array of Object
            if (tempFoodCategory.getCategoryName().equals(categoryList[i].getCategoryName())) 
            {
               index = i;
            }
         }
      
         if (index != -1) 
         {
            //Array of Object
            if (categoryList[index].addFood (foodName, ingredient, foodCategory, price, calories, description, availability, costToMake)) {
               return true;
            }
         }    
         return false;    
      }
      return false;
   }
   
   /*
      Parameter(s):
         - foodName: name of the food
         - categoryName: the name of the category that the food item is in
      Return Value: This method does not return any value since it is a void. 
      Purpose: This method is created to delte a specific food item. With a given input
               of foodName and categoryName, this method deletes the corresponding food item
               in the restaurant menu
   */
   public void deleteFood(String foodName, String categoryName) {
      FoodCategory tempFoodCategory = searchCategoryByName(categoryName);
      int index = -1;
   
      for (int i = 0; i < numCategories; i++) {
         if (tempFoodCategory.getCategoryName().equals(categoryList[i].getCategoryName())) { // Array of Object
            index = i;
         }
      } 
   
      if (index != -1) {
         categoryList[index].deleteFood(foodName); //Array of Object
      }
   }
   
   /*
      Parameter(s): No parameter
      Return Value: This method does not return any value because it is a void
      Purpose: This method sets all of the food item into null, which deletes the whole
               food items in the restaurant menu 
   */
   public void deleteAllFood() {
      for (int i = 0; i < numCategories; i++) {
         for (int j = 0; j < categoryList[i].getNumFood(); j++) {
            Food tempFood = categoryList[i].getFoodItems()[j]; //Array of Object
            tempFood = null; 
         }
         categoryList[i].setNumFood(0);
      }
   }
   
   /*
      Parameter(s):
         - categoryName: the name of the category that the food is in
      Return Value: This food returns a corresponding Food Category based on the user input
      Purpose: This method allows one to search for particular food category from the 
               user input. If it is found, it returns the following food category, if it doesn't
               it returns null
   */
   public FoodCategory searchCategoryByName(String categoryName) {
      for (int i = 0; i < numCategories; i++) {
         if (categoryList[i].getCategoryName().equals(categoryName)) { //Array of Object
            return categoryList[i];
         }
      }
        
      return null;
   }
   
   /*
      Parameter(s):
         - foodName: the name of the food that the user inputs
      Return Value: This method returns a Food item corresponding to the the foodName
                     that the user has inputed
      Purpose: This method is created so that user can find the food item in the restaurant
               menu.
   */
   public Food searchFoodByName(String foodName) {
      for (int i = 0; i < numCategories; i++) {
         for (int j = 0; j < categoryList[i].getNumFood(); j++) {
            if (categoryList[i].getFoodItems()[j].getFoodName().equals(foodName)) { //Array Object
               return categoryList[i].getFoodItems()[j];
            }
         }
      }
      
      return null;
   }
   
   /*
      Parameter(s): No parameter
      Description: This method doesn not return any value because it is a void
      Purpose: This method prints all of the names of categories. It allows user
               to easily view all of the food categories when making an order
   */
   public void printAllCategories() {
      for (int i = 0; i < numCategories; i++) {
         System.out.println(i + 1 + "." + categoryList[i].getCategoryName()); //Array of Object
      }
   }
   
   /*
      Description: This method is printing out all the food items in the menu.
      Purpose: To show overview of name of foods to the owner when deleting food item
   */
   public void printAllFoodItem ()
   {
      System.out.println ("These are all the menu items in the restaurant: ");
      for (int i = 0; i < numCategories; i++)
      {
         System.out.println ("\n" + categoryList[i].getCategoryName());
         categoryList[i].printAllFood();
      }
      System.out.println ();
   }
   
   /*
      Description: Returns the total number of types of food on the menu.
      Purpose: Used in checking if the menu is empty;
   */
   public int getAllFoodCount() {
      int num = 0;
      
      for (int i = 0; i < numCategories; i++) {
         num += categoryList[i].getNumFood();
      }
      
      return num;
   }
   
   /*
      Description: Checks if the menu is currently empty or not.
      Returns: True if the menu has no food items.
               False if the menu has food items.
   */
   public boolean isMenuEmpty() {
      if (getAllFoodCount() == 0) {
         return true;
      }
      
      return false;
   }
}