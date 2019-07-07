/*
   Class Name: Employee
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class defines an employee and initializes the fields that composes an employee
*/

class Employee {
   //Encapsulation
   private String occupation;
   private String name;
   private int employeeNumber;

   //Constructors
   public Employee(String name, String occupation, int employeeNumber) {
      this.name = name;
      this.occupation = occupation;
      this.employeeNumber = employeeNumber;
   }

   //Accessors and Mutators
   //Encapsulation
   public String getOccupation() {
      return occupation;
   }

   public String getName() {
      return name;
   }

   public int getEmployeeNumber() {
      return employeeNumber;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setEmployeeNumber(int employeeNumber) {
      this.employeeNumber = employeeNumber;
   }
  
   /*
      Parameters: 
         - other: Employee that the user inputs
      Return Value: This method returns boolean expression, either true or false.
                    It returns true if employeeNumber is same, false if not
      Purpose: Checks if the explicit employee and implicit employee are equal or not. It
               is determined by the employeeNumber
   */
   public boolean isEqual(Employee other) {
      if (this.employeeNumber == other.employeeNumber && this.employeeNumber != -1) {
         return true;
      }
      return false;
   }
   
   /*
      Parameters: No parameter value
      Return Value: This method returns String value
      Purpose: To show information of an employee in a written form
   */
   public String toString () {
      return name + ", " + employeeNumber;
   }
}