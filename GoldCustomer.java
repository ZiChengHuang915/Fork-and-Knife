/*
   Class Name: GoldCustomer
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class is one of the branches from Customer class. This class calculate the discount
            specifically for Gold Customers
*/

class GoldCustomer extends Customer{ //Inheritance

  //Constructor
  public GoldCustomer (String customerID, String customerPassword) {
    super(customerID, customerPassword);
  }
  
  /*
      Parameters: 
         - discount: the amount of discount that the customer gets
      Return Value: It returns a value of double. It is represented as 1 - discount
                    which means it is a factor that the customer have to multiply
                    at the total payment amount
      Purpose: returns the amount of discount that the customer will have
   */
  public double getDiscount(double discount){
    return (1 - discount);
  }
}