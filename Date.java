/*
   Class Name: Date
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines date, and it is used in calculating finances during different time intervals (ex.Monthly, daily). 
*/

class Date {
   //Encapsulation
   private int year, month, day;

   //Constructor
   public Date (int year, int month, int day) {
      if (checkValidDate (year, month, day)) {
         this.year = year;
         this.month = month;
         this.day = day;
      } else {
         this.year = 0;
         this.month = 0;
         this.day = 0;
      } 
   }
   
   //Accessors and Mutators
   //Encapsulation
   public int getDay() {
      return day;
   }
   
   public int getMonth() {
      return month;
   }
   
   public int getYear() {
      return year;
   }
   
   /*
      Parameters: 
         - currentDate: the standard date that the restaurant is following
      Return Value: This method returns a new Date. Depending on the user input,
                    it gives the next date corresponding to the current date field
      Purpose: To update the current date to the next day. 
   */
   public Date goToNextDay (Date currentDate) {
      if (currentDate.getDay() < Restaurant.DAYSPERMONTH) {
         return new Date (currentDate.getYear(), currentDate.getMonth(), currentDate.getDay() + 1);
      } else {
         if (currentDate.getMonth() < Restaurant.MONTHSPERYEAR) {
            return new Date (currentDate.getYear(), currentDate.getMonth() + 1, 1);
         } else {
            return new Date (currentDate.getYear() + 1, 1, 1);
         }
      }
   }
   
   /*
      Parameters: 
         - year: Integer value that user inputs
         - month: Integer value that user inputs
         - day: Integer value that user inputs
      Return Value: This method returns boolean expression. It returns true if all of the Date field matches
                    the value from Restaurant field
      Purpose: Checks if the given input is in the correct range of date. Month has 1 to 30 days, and there 12 
               months in a year.
   */
   public boolean checkValidDate (int year, int month, int day) {
      if (month < 1 || month > Restaurant.MONTHSPERYEAR || day < 1 || day > Restaurant.DAYSPERMONTH || year < 1) {
         return false;
      }
      return true;
   }
   
   /*
      Parameters: 
         - year: Integer value that user inputs
         - month: Integer value that user inputs
         - day: Integer value that user inputs
      Return Value: This method returns Date. New date object is created based on the
                    field information that the user inputs
      Purpose: Checks if the given input is valid input using checkValidDate method. If it is 
               valid, it creates a new custom date based on the user input. 
   */
   public Date setCustomDate (int year, int month, int day) {
      if (checkValidDate (year, month, day)) {
         return new Date (year, month, day);
      }   
      return null;
   }
   
   /*
      Parameters: 
         - other: Date that the user inputs
      Return Value: This method returns boolean expression, either true or false.
                    It returns true if year, month, and day is same, false if not
      Purpose: Checks if the explicit date and implicit date are equal or not. It is determined
               by having the same year, month, and day
   */
   public boolean isEquals (Date other) {
      if (other != null) {
         if ((this.year == other.year) && (this.month == other.month) && (this.day == other.day)) {
            return true;
         }   
      }
      return false;
   } 
   
   /*
      Parameters: 
         - other: Date that the user inputs
      Return Value: This method returns boolean expression, either true or false.
                    It returns true if year and month are same, false if not
      Purpose: To check if the explicit Date and implicit Date are equal or not. It
               is determined by the year and month
   */
   public boolean isSameMonth (Date other) {
      if (other != null) {
         if ((this.year == other.year) && (this.month == other.month)) {
            return true;
         }
      }    
      return false;   
   }
   
   /*
      Parameters: No parameter value
      Return Value: This method returns String value
      Purpose: To show the date in a written form
   */
   public String toString() {
      return year + " " + month + " " + day;
   }
}
  // public int compareToDate (Date other){
//     if (this.year == other.year)
//       if (this.month == other.month)
//         if (this.day == other.day)
//           return 0;
//         else
//           return this.day - other.day;
//       else
//         return this.month - other.month;
//     else
//       return this.year - other.year;
//   }