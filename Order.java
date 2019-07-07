/*
   Class Name: Order
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines what an Order is, and calculates
            the total of an Order based off the Food list.
*/

import java.text.DecimalFormat;

class Order {
   //Encapsulation
   private int maxPossibleFoodsInMenu;
   private double tax;
   private Date orderDate;

   private int orderNumber;
   private Food[] foodList;
   private int numItems;
   
   private double tip;
   private double subtotal;
   private double totalAfterCouponDiscount;
   private double totalAfterCustomerDiscount;
   private double taxAmount;
   private double finalTotal;
   
   private double totalCostToMake;

   /*
      Parameter(s):
         - orderNumber: the current Order number assigned to this Order
         - date: the current Date
         - maxPossibleFoodsInMenu: an integer value that sets the maximum
                                   number of Foods in an Order
         - tax: the tax in double format
      Description: This method is the constructor of the Order class.
      Purpose: Creates an Order, and assigns the fields respectively.
   */
   public Order (int orderNumber, Date date, int maxPossibleFoodsInMenu, double tax) {
      this.orderNumber = orderNumber;
      orderDate = date;
      
      this.maxPossibleFoodsInMenu = maxPossibleFoodsInMenu;
      foodList = new Food [maxPossibleFoodsInMenu];
      numItems = 0;
      
      this.tax = tax;
      
      subtotal = -1;
      totalAfterCouponDiscount = -1;
      totalAfterCustomerDiscount = -1;
      taxAmount = -1;
      finalTotal = -1;
      
      totalCostToMake = -1;
      tip = 0;
   }
   
   // The following are accessors and mutators.
   // Encapsulation
   public double getTaxAmount() {
      return taxAmount;
   }
   
   public double getFinalTotal() {
      return finalTotal;
   }

   public double getSubtotal() {
      return subtotal;
   }
   
   public double getTotalAfterCustomerDiscount() {
      return totalAfterCustomerDiscount;
   }

   public Food[] getFoodList ()
   {
      return foodList;
   }
   
   public int getNumItems ()
   {
      return numItems;
   }
   
   public Date getOrderDate () {
      return orderDate;
   }

   public double getTotalCostToMake () {
      return totalCostToMake;
   }
   
   public int getOrderNumber () {
      return orderNumber;
   }
   
   public double getTip () {
      return tip;
   }
   
   public void setNumItems (int numItems) {
      this.numItems = numItems;
   }
    
   public void setOrderNumber (int num)
   {
      orderNumber = num;
   }
   
   public void setTip (double tip) {
      this.tip = tip;
   }
   
   public void setOrderDate (Date date) {
      orderDate = date;
   }
   
   /*
      Description: This method is used to reset an Order.
      Purpose: For every item on the Food list, sets it to null.
   */
   public void resetOrder ()
   {
      for (int i = 0; i < foodList.length; i ++)
      {
         foodList[i] = null; //Array of Object
      }
   }

   /*
      Parameter(s):
         - orderFood: the Food object to be added to the Order
      Description: This method is checks if adding the Food into the Food list is successful.
      Purpose: If the number of items on the current Food list is less than maximum number
               of Foods in an Order, and the menu quantity is greater than the desired amount,
               the Food is added to the Food list, and sets the Order quantity while the number
               of items on the Food list is added by 1, which will return true, otherwise return false.
   */
   public boolean addToOrder (Food orderFood, int orderSize) {
      if (numItems < maxPossibleFoodsInMenu && orderFood.getAvailability() >= orderSize) {
         foodList[numItems] = orderFood; // Array of Object
         
         foodList[numItems].setAvailability (orderSize);
         numItems++;
         
         return true;
      } else {
         return false;
      }
   }

   /*
      Parameter(s):
         - orderNumber: the current Order number assigned to this Order
         - date: the current Date
         - maxPossibleFoodsInMenu: an integer value that sets the maximum
                                   number of Foods in an Order
         - tax: the tax in double format
      Description: Deletes a food item depending on the input of the customer, foodListNum.
                   Returns the quantity of the food item that has to be added back to the menu.
      Purpose: To allow the user to delete an existing item off their order.
   */
   public int deleteAnOrder (int foodListNum) {
      int index = foodListNum - 1;
      if (index < numItems && index >= 0) {
         int quantity = foodList [index].getAvailability(); // Array of Object
         
         for (int i = index; i < numItems; i++) {
            foodList [i] = foodList [i+1];
         }
      
         foodList [numItems] = null;     
         numItems--;
      
         return quantity;
      } else {
         return -1;
      }
   }

   /*
      Description: This method is used to calculate the subtotal of the Order.
      Purpose: For every item on the Food list, the subtotals adds the Food
               price multiplied by the amount wanted.
   */
   public void calculateSubtotal() {
      subtotal = 0;
   
      for (int i = 0; i < numItems; i++) {
         subtotal += foodList [i].getPrice() * foodList [i].getAvailability(); // Array of Object
      }
   }
   
   /*
      Parameter(s):
         - discountDecimal: a double value that contains the discount rate
      Description: This method is used to calculate the total after applying a Coupon.
      Purpose: The totalAfterCouponDiscount is the subtotal multiplied by 1 minus discount rate.
   */
   public void calculateTotalAfterCouponDiscount(double discountDecimal) {
      totalAfterCouponDiscount = subtotal * (1 - discountDecimal); 
   }
   
   /*
      Parameter(s):
         - discountDecimal: a double value that contains the Customer discount rate
      Description: This method is used to calculate the total if user is a premium Customer.
      Purpose: The totalAfterCustomerDiscount is the subtotal multiplied by 1 minus discount rate.
   */
   public void calculateTotalAfterCustomerDiscount(double discountDecimal) {
      totalAfterCustomerDiscount = totalAfterCouponDiscount * (1 - discountDecimal); 
   }
   
   /*
      Description: This method is used to calculate the amount of tax.
      Purpose: The taxAmount is the totalAfterCustomerDiscount multiplied by the tax.
   */
   public void calculateTaxAmount() {
      taxAmount = totalAfterCustomerDiscount * tax;
   }
   
   /*
      Description: This method is used to calculate the final price.
      Purpose: The finalTotal is the totalAfterCustomerDiscount added by taxAmount.
   */
   public void calculateFinalTotal() {
      finalTotal = totalAfterCustomerDiscount + taxAmount;
   }
   
   /*
      Description: This method is used to calculate the total cost to make the entire order.
      Purpose: For each Food on the Food list, the total cost is added by the cost
               to make multiplied by the amount wanted.
   */ 
   public void calculateTotalCostToMake () {
      totalCostToMake = 0;
   
      for (int i = 0; i < numItems; i++) {
         totalCostToMake += foodList [i].getCostToMake() * foodList [i].getAvailability(); //Array of Object
      }
   }
   
   /*
      Description: This method is used to determine the arrival time of an Order.
      Purpose: Returns an arrival time of the number of items on the Food list
               multiplied by 4.
   */
   public int determineArrivalTime () {
      return numItems * 4;
   }
   
   /*
      Description: This method is used to print out all Order total details.
      Purpose: Called when printing the total for an Order, and returns the following Strings.
   */
   DecimalFormat df = new DecimalFormat ("0.00");
   
   public String toString () {
      return "Subtotal: $" + df.format(subtotal) + 
             "\nDiscounts: -$" + df.format((subtotal - totalAfterCustomerDiscount)) +
             "\nTax: $" + df.format(taxAmount) +
             "\nTotal: $" + df.format(finalTotal);
   }
}