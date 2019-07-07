/*
   Class Name: PlatCustomer
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines what a Platinum Customer objecet is and
            can return the respecive discount.
*/

class PlatCustomer extends Customer //Inheritance
{
   /*
      Parameter(s):
         - customerID: String variable that the user inputs
         - customerPassword: String variable that the user inputs
      Description: This method is the constructor for the PlatCustomer class.
      Purpose: Creates a Platinum Customer by receiving a customer ID and password.
   */
   public PlatCustomer (String customerID, String customerPassword) {
      super(customerID, customerPassword);
   }

   /*
      Parameter(s):
         - discount:
      Description: This method is used to access the discount
                   percentage for a Platinum Customer.
      Purpose: Returns the discount rate for a Platinum Customer.
   */
   public double getDiscount(double discount){
      return discount;
   }
}