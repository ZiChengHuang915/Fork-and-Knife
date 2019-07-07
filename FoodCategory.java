/*
   Class Name: FoodCategory
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines Food Category, and it is used as a classificiation for particular food item
*/

class FoodCategory
{
   //Encapsulation
   private Food[] foodItems; //Array of Object
   private int numFood;
   private String categoryName;
   private int maxPossibleFoodsInCategory;
   
   //Constructor
   public FoodCategory (String categoryName, int maxPossibleFoodsInCategory)
   {
      this.maxPossibleFoodsInCategory = maxPossibleFoodsInCategory;
      this.categoryName = categoryName;
      foodItems = new Food[maxPossibleFoodsInCategory];
      numFood = 0;
   }
   
   //Accessors and Mutators
   //Encapsulation
   public int getNumFood() {
      return numFood;
   }
   
   public Food[] getFoodItems ()
   {
      return foodItems;
   }
  
   public String getCategoryName() {
      return categoryName;
   }
   
   public void setNumFood(int numFood) {
      this.numFood = numFood;
   }
     
   /*
      Parameters: 
         - foodName: name of the food
         - ingredient: ingredients
         - categoryName: the food's category
         - price: the cost of the food
         - calories: the calories of the food
         - description: description of the food
         - availability: number of foods available
         - costToMake: cost of making the food
      Return Value: This is a constructor, so it doesn't return any value
      Purpose: This method creates a new food object
   */
   public boolean addFood (String foodName, String ingredient, String categoryName, double price, int calories, String description, int availability, double costToMake)
   {  
      if (price > 0 && calories > 0 && availability > 0 && costToMake > 0) {
         if (numFood < maxPossibleFoodsInCategory) {
         //Array of Object
            foodItems[numFood] = new Food(foodName, ingredient, categoryName, price, calories, description, availability, costToMake);
            numFood++;
            return true;
         }
         return false;
      } 
      return false;
   }

   /*
      Parameters: 
         - name: the name of the food from the user input
      Return Value: This method doesn't return any value,
                    already assuming that the food exists.
      Purpose: To delete the food item from restaurant
   */
   public void deleteFood(String name) {
      Food tempFood = searchFoodByName(name); //Sequential Search
      int index = -1;
   
      for (int i = 0; i < numFood; i++) {
         //Array of Object
         if (foodItems[i].getFoodName().equals(tempFood.getFoodName())) {
            index = i;
         }
      } 
   
      if (index != -1) {
         for(int i = index; i < numFood - 1; i++) {
            foodItems[i] = foodItems[i + 1]; //Array Object
         }
         
         foodItems [numFood - 1] = null; //Array of Object
         numFood--;
      }
   }
   
   /*
      Parameters: 
         - name: the name of the food given by user input
      Return Value: the Food corresponding to the name of the food.
      Purpose: To search the food item based on the name of the food item .
   */
   public Food searchFoodByName(String name) { //Sequential Search
      for (int i = 0; i < numFood; i++) {
         if (foodItems[i].getFoodName().equals(name)) {//Array of Object
            return foodItems[i]; 
         }
      }
   
      return null;
   }
  
   /*
      Parameters: No parameters
      Return Value: This method returns String value
      Purpose: To write the list of foods in particular food category
   */
   public String toString ()
   {
      String word = "";
      for (int i = 0; i < foodItems.length; i++)
      {
         word = word + foodItems[i].toString() + "\n"; //Array of Object
      }
      return word;
   }
   
   /*
      Parameters: No parameters
      Return Value: This method doesn't return any value
      Purpose: To write the list of foods in particular food cetegory
   */  
   public void printAllFood () //Sorting (Bubble Sort)
   {
      boolean sorted = false;
      Food temp = null;
      
      for (int i = numFood - 1; i > 0 && sorted == false; i--)
      {
         sorted = true;
         for (int j = 0; j < i; j++)
         {
            temp = foodItems[j]; //Array of Object
            if (foodItems[j].getFoodName().compareTo(foodItems[j + 1].getFoodName()) > 0)
            {
               sorted = false;
               foodItems[j] = foodItems[j + 1];
               foodItems[j + 1] = temp;
            }
         }
      }    
      for (int i = 0; i < numFood; i++)
      {
         System.out.println((i+1)+ "." + foodItems[i].getFoodName()); //Array of Object
      }
   }
}
   // public void printAllFood() {
//       for (int i = 0; i < numFood; i++) {
//          System.out.println(i + 1 + "." + foodItems[i].getFoodName());
//       }
//    }
