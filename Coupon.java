/*
   Class Name: Coupon
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines a Coupon object, and contains
            accessors and mutators for its respective fields.
*/

class Coupon {
   //Encapsulation
   private String code;
   private double discount;

   //Constructors
   public Coupon () {
      code = null;
      discount = 0;
   }
 
   public Coupon (String code, double discount) {
      this.code = code;
      this.discount = discount;
   }
   
   //Accessors and Mutators
   //Encapsulation
   public String getCode() {
      return code;
   }
   
   public double getDiscount() {
      return discount;
   }
   
   public void setCode(String code) {
      this.code = code;
   } 
   
   public void setDiscount(Double discount) {
      this.discount = discount;
   }  
}
   // public void loadNewCoupons (String fileName) {
//       for (int i = 0; i < this.numCoupons; i++) {
//          couponList [i] = null;
//       }
//    
//       this.numCoupons = 0;
//    
//       try {
//          BufferedReader in = new BufferedReader (new FileReader (fileName));
//          this.numCoupons = Integer.parseInt(in.readLine());
//       
//          if (this.numCoupons > maxCoupon) {
//             this.numCoupons = maxCoupon;
//          }
//       
//          for (int i = 0; i < this.numCoupons; i++) {
//             couponList [i] = in.readLine();
//          }
//       } catch (IOException iox) {
//       }
//    }
// 
//    public boolean checkForValidCoupon (String couponCode) {
//       for (int i = 0; i < numCoupons; i++) {
//          if (couponList [i].equals (couponCode)) {
//             return true;
//          }
//       }
//    
//       return false;
//    }














