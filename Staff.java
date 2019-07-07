/*
   Class Name: Staff
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class manages the Employees functionalities under a Staff object.
*/

class Staff 
{
   private Employee[] employeeList;  //Array of Object
   private int currentEmployeeNumber;
   private int numEmployees;
   private int maxEmployee;

   /*
      Parameter(s):
         - maxEmployee: integer value passed from maxEmployee of the Restaurant class
      Description: This method the constructor of the Staff class.
      Purpose: Creates a Staff object which manages the Employees.
   */
   public Staff (int maxEmployee) {
      this.maxEmployee = maxEmployee;
      employeeList = new Employee[maxEmployee];
      currentEmployeeNumber = 0;
      numEmployees = 0;
   } 
   
   // Accessors
   // Encapsulation
   public Employee[] getEmployeeList() {
      return employeeList;
   }

   public int getCurrentEmployeeNumber() {
      return currentEmployeeNumber;
   }
 
   public int getNumEmployees() {
      return numEmployees;
   }
   
   /*
      Parameter(s):
         -name: the name of an Employee in String format
         -occupation: the occupation of an Employee in String format
      Description: This method is used to check if adding a new Employee is successful.
      Purpose: Checks to see if the current number of Employees is less than the maximum
               number of Employees allowed in a Restaurant.
               If less than maximum, a new Employee will be added onto the Employee list,
               the current number of Employees is added by 1, and returns true, otherwise
               returns false.
   */
   public boolean addEmployee(String name, String occupation) {
      if (numEmployees < maxEmployee) {
         employeeList[numEmployees] = new Employee(name, occupation, generateEmployeeNumber());
      	
         numEmployees++;
         
         return true;
      }
      
      return false;
   }
   
   /*
      Parameter(s):
         -name: the name of an Employee is String format
      Description: This method is used to remove an Employee.
      Purpose: If the name is found on the Employee list, the current
               index of the Employee object will be null, then shifts
               other Employee objects up.
   */
   public void deleteEmployee(String name) {
      Employee tempEmployee = searchByName(name);
      int index = -1;
    
      for (int i = 0; i < numEmployees; i++) {
         //Array of Object
         if (employeeList[i].isEqual(tempEmployee)) {
            index = i;
         }
      }
   
      if (index != -1) {
         employeeList[index] = null;
      
         for(int i = index; i < numEmployees - 1; i++) {
            employeeList[i] = employeeList[i + 1];
         }
      }
   }
   
   /*
      Parameter(s):
         -listNum: the integer index of an Employee object in the Employee list
      Description: This method is used to delete an Employee by their index.
      Purpose: For every index in the list it will delete the Employee object if the
               index is found, sets it to null, and shifts the other Employees up.
   */
   public void deleteEmployeeByIndex (int listNum) {
      int index = listNum - 1;
   
      if (index != -1) {
         for(int i = index; i < numEmployees - 1; i++) {
            employeeList[i] = employeeList[i + 1];
         }
         
         employeeList [numEmployees - 1] = null;
         numEmployees--;
      }
   }
   
   /*
      Description: This method is used to delete all Employees off of the Employee list.
      Purpose: For every index in the list it will delete the Employee object, and set
               the number of Employees to 0.
   */
   public void deleteAllEmployee() {
      for (int i = 0; i < numEmployees; i++) {
         employeeList[i] = null;
      }
      numEmployees = 0;
   }

   /*
      Parameter(s):
         -name: the name of an Employee in String format
      Description: This method is used to search an Employee by their name.
      Purpose: For every Employee in the list, if the name matches it will return
               the Employee object, otherwise return null.
   */
   public Employee searchByName(String name) {
      for (int i = 0; i < numEmployees; i++) {
         if (employeeList[i].getName().equals(name)) {
            return employeeList[i];
         }
      }
      return null; 
   }
   
   /*
      Description: This method is used to generate an Employee number.
      Purpose: Returns the current Employee number added by 1.
   */
   public int generateEmployeeNumber() {
      currentEmployeeNumber++;
      return currentEmployeeNumber;
   }
   
   /*
      Description: This method sorts employees in alphabetical order.
      Purpose: To allow owner to see all employee information sorting by alphabetical order.
   */
   public void sortByAlphaOrder ()
   {
      Employee temp;
      int j;
      
      for (int i = 1; i < numEmployees; i++)
      {
         temp = employeeList[i];
         j = i;
         while (j > 0 && temp.getName().compareTo (employeeList[j - 1].getName()) < 0)
         {            
            employeeList[j] = employeeList[j-1];
            j--;
         }
         employeeList[j] = temp;
      }      
   }
   
   /*
      Description: This method displays all of the employees.
      Purpose: To allow owner to see the information of the employees.
   */
   public void printAllEmployees() {
      for (int i = 0; i < numEmployees; i++)
      {
         System.out.println ((i+1) + ". "+ employeeList[i].getName() + ", #" + employeeList[i].getEmployeeNumber());
      }
   }

   // public boolean editEmployee(String name, String fieldName, String value) {
//       Employee tempEmployee = searchByName(name);
//       int indexEmployee = -1;
//       int indexField = -1;
//     
//       for (int i = 0; i < numEmployees; i++) {
//          if (employeeList[i].equals(tempEmployee)) {
//             indexEmployee = i;
//          }
//       }
//    
//       if (indexEmployee != -1) {
//          for (int i = 0; i < employeeFields.length; i++) {
//             if (employeeFields[i].getName().equals(fieldName)) {
//                indexField = i;
//             }
//          }
//       
//          if (indexField != -1) {
//             try {
//                if (employeeFields[indexField].getType().equals("class java.lang.String")) {
//                   employeeFields[0].set(employeeList[indexEmployee], value);
//                } else if (employeeFields[indexField].getType().equals("double")) {
//                   employeeFields[0].set(employeeList[indexEmployee], Double.parseDouble(value));
//                } else if (employeeFields[indexField].getType().equals("int")) {
//                   employeeFields[0].set(employeeList[indexEmployee], Integer.parseInt(value));
//                } else if (employeeFields[indexField].getType().equals("boolean")) {
//                   employeeFields[0].set(employeeList[indexEmployee], Boolean.parseBoolean(value));
//                } else if (employeeFields[indexField].getType().equals("char")) {
//                   employeeFields[0].set(employeeList[indexEmployee], value.charAt(0));
//                }
//                return true;
//             } catch (IllegalAccessException iaex) {
//             
//             }
//          }
//       }
//       return false;
//    }
   // public Field[] getEmployeeFields() {
//       return employeeFields;
//    }
}