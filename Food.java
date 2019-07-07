/*
   Class Name: Food
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines food, and initializes the field that composes a food item
*/

public class Food
{
   //Encapsulation
   private String foodName;
   private String ingredient;
   private String foodCategory;
   private double price;
   private int calories;
   private String description;
   private int availability;
   private double costToMake;
   
   //Constructor
   public Food (String foodName, String ingredient, String foodCategory, double price, int calories, String description, int availability, double costToMake)
   {
      this.foodName = foodName;
      this.ingredient = ingredient;
      this.foodCategory = foodCategory;
      this.price = price;
      this.calories = calories;
      this.description = description;
      this.availability = availability;
      this.costToMake = costToMake;
   }
   
   //Accessors and Mutators
   //Encapsulation
   public String getFoodName() {
      return foodName;
   }
   
   public String getIngredient() {
      return ingredient;
   }
   
   public int getCalories() {
      return calories;
   }
   
   public String getDescription() {
      return description;
   }
   
   public double getPrice() {
      return price;
   }
   
   public int getAvailability() {
      return availability;
   }
   
   public String getFoodCategory ()
   {
      return foodCategory;
   }
   
   public double getCostToMake () {
      return costToMake;
   }
   
   public void setAvailability (int availability) {
      this.availability = availability;
   }
   
   /*
      Parameters: 
         - amount: The number of items that the food needs to be changed to.
      Return Value: This method does not return anything because it is a void
      Purpose: To set the availability based on the number of food items left after it has been added to order
   */
   public void changeAvailability (int amount) {
      if (amount < 0) {
         System.out.println ("You have entered an amount greater than the availability.\nChanging availability to 0.");
         this.availability = 0;
      } else {
         this.availability = amount;
      }
   }  
   
   /*
      Return Value: This method returns a String value.
      Purpose: To show the food information in a written form.
   */
   public String toString ()
   {
      return "Name: " + foodName + "\n" +
             "Ingredient: " + ingredient + "\n" +
             "Category: " + foodCategory + "\n" +
             "Price: $" + price + "\n" +
             "Calories: " + calories + "\n" +
             "Description: " + description + "\n";      
   }
}