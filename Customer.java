/*
   Class Name: Customer
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: It is an abstract class that defines a customer, and can be divided into 
            different type of customers
*/

//Polymorphism
abstract class Customer {
   //Encapsulation
   private String customerID;
   private String customerPassword;

   //Constructor
   public Customer (String customerID, String customerPassword) {
      this.customerID = customerID;
      this.customerPassword = customerPassword;
   }
   
   //Accessors and Mutators
   //Encapsulation
   public String getCustomerID () {
      return this.customerID;
   }
   
   public String getCustomerPassword() {
      return this.customerPassword;
   }
   public void setCustomerID (String customerID) {
      this.customerID = customerID;
   }
  
   public void setCustomerPassword (String cusomterPassword) {
      this.customerPassword = customerPassword;
   }
  
   //Inheritance (abstract method)
   abstract double getDiscount(double discount);
}
  
  /*public void addToOrder (int orderNum) {
    Order tempOrder = Restaurant.searchOrderByOrderNumber (orderNum);
    Order[] tempOrders = Restaurant.getOrders();
    
    for (int i = 0; i < numOrders; i++){
      if (tempOrders[i].orderNum == orderNum)
        this.order = tempOrders[i];
    }
  } */

  // public void deleteAnOrder (int orderNum){
//     //Order.deleteAnOrder (orderNum)
//     order = null;
//   }

  // public void addCouponToUsed (String used){
//     usedCoupons [couponsUsed] = used;
//     couponsUsed++;
//   }