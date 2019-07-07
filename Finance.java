/*
   Class Name: Finance
   Author: King Lai, ZiCheng Huang, John Oh, Nancy Hao
   Date: January 10, 2019
   School: A.Y. Jackson Secondary School
   Purpose: This class calculates the profit and revenue for the restaurant. Also, it calculates
            the tip for each employee.
*/
import java.text.DecimalFormat;
import java.util.*;

class Finance {
   //Encapsulation
   private double totalRevenueMonth;
   private double totalRevenueDay;
   
   private double totalProfitDay;
   private double totalProfitMonth;
   
   private double totalSpentMonth;
   private double totalSpentDay;
   
   private double totalDailyTips;
   private double dailyTipsPerEmployee;
   
   private int numEmployees; 
   ArrayList <Order> ordersThisMonth; // Array of Object
   private Date restaurantDate;
     
   //Constructor
   public Finance (int numEmployees, Date restaurantDate) {
      totalRevenueMonth = -1;
      totalRevenueDay = -1;
      
      totalProfitDay = -1;
      totalProfitMonth = -1;
      
      totalSpentDay = -1;
      totalSpentMonth = -1;
      
      totalDailyTips = -1;
      dailyTipsPerEmployee = -1;
      this.numEmployees = numEmployees;
      
      ordersThisMonth = new ArrayList <Order>();
      
      this.restaurantDate = restaurantDate;
   }
   
   //Accessors and Mutators
   //Encapsulation
   public int getNumEmployees() {
      return numEmployees;
   }
   
   public Date getRestaurantDate() {
      return restaurantDate;
   }
   
   public double getTotalRevenueMonth () {
      return totalRevenueMonth;
   }
   
   public double getTotalRevenueDay () {
      return totalRevenueDay;
   }
   
   public double getTotalProfitDay () {
      return totalProfitDay;
   }
   
   public double getTotalProfitMonth () {
      return totalProfitMonth;
   }
   
   public double getTotalSpentDay () {
      return totalSpentDay;
   }
   
   public double getTotalSpentMonth () {
      return totalSpentMonth;
   }
   
   public double getDailyTipsPerEmployee () {
      return dailyTipsPerEmployee;
   }
   
   public List<Order> getOrdersThisMonth() {
      return ordersThisMonth;
   }
   
   public void setNumEmployees(int numEmployees) {
      this.numEmployees = numEmployees;
   }
   
   public void setRestaurantDate(Date restaurantDate) {
      this.restaurantDate = restaurantDate;  
   }
   
   /*
      Parameters: No Parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the monthly revenue of the restaurant by adding
            the total meony that the restaurant has earned in a month
   */
   public void calculateMonthlyRevenue() {
      totalRevenueMonth = 0;
      
      for (int i = 0; i < ordersThisMonth.size(); i++) {
         totalRevenueMonth += ordersThisMonth.get(i).getFinalTotal();
      }
   } 
   
   /*
      Parameters: No Parameter 
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the daily revenue of the restaurant by adding
               the total money that the restaurant has earned in particular day
   */
   public void calculateDailyRevenue() {
      ArrayList <Order> tempOrdersToday = new ArrayList <Order> ();
      
      for (int i = 0; i < ordersThisMonth.size(); i++) {
         if (ordersThisMonth.get(i).getOrderDate().isEquals (restaurantDate)) {
            tempOrdersToday.add (ordersThisMonth.get(i));
         }
      }
      
      totalRevenueDay = 0;
      
      for (int i = 0; i < tempOrdersToday.size(); i++) {
         totalRevenueDay += tempOrdersToday.get(i).getFinalTotal();
      }
   }
   
   /*
      Parameters: No parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the total amount of money spent in a month. It adds
               costs that was spent making the food from all of the the orders that was made in a month.
   */
   public void calculateSpentMonth(){
      totalSpentMonth = 0;
      
      for (int i = 0; i < ordersThisMonth.size(); i++) {
         totalSpentMonth += ordersThisMonth.get(i).getTotalCostToMake();
      }
   }
   
   /*
      Parameters: No parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the total amount of money spent in a day. It adds
               costs that was spent making the food from all of the the orders that was made in particular day.
   */
   public void calculateSpentDay(){
      ArrayList <Order> tempOrdersToday = new ArrayList <Order> ();
      
      for (int i = 0; i < ordersThisMonth.size(); i++) {
         if (ordersThisMonth.get(i).getOrderDate().isEquals (restaurantDate)) {
            tempOrdersToday.add (ordersThisMonth.get(i));
         }
      }
      
      totalSpentDay = 0;
      
      for (int i = 0; i < tempOrdersToday.size(); i++) {
         totalSpentDay += tempOrdersToday.get(i).getTotalCostToMake();
      }
   }
   
   /*
      Parameters: No Parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the monthly profit by subtracting totalRevenueMonth 
               to the totalSpentMonth
   */
   public void calculateMonthlyProfit() {
      totalProfitMonth = totalRevenueMonth - totalSpentMonth;
   }
   
   /*
      Parameters: No Parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the daily profit by subtracting totalRevenueDay
               to the totalSpentDay
   */
   public void calculateDailyProfit() {
      totalProfitDay = totalRevenueDay - totalSpentDay;
   }
   
   /*
      Parameters: No Parameter
      Return Value: A double representing the predicted revenue for the midle of next month.
                    Will return NaN if there are no orders, or orders of only one day is made,
                    Because the slope of a point is undefined.
      Purpose: Predicts the revenue next month using linear regression. The orders of the past 
               month are grouped into an array consisting of the average revenue for each day 
               in that month. Then, a slope is calculated by plotting all the averages, and
               the revenue for the middle of next month (15 days later) will be predicted.
   */
   public double predictRevenueNextMonth () {
      int n;
      double sigmaX = 0, sigmaY = 0, sigmaXY = 0, sigmaXSquared = 0, meanX = 0, meanY = 0;
      double m, b;
      
      n = Restaurant.DAYSPERMONTH;
      
      double[] datesAndTotalsAverage = new double[Restaurant.DAYSPERMONTH];
      for (int i = 1; i <= Restaurant.DAYSPERMONTH; i++) {
         ArrayList <Order> tempOrdersToday = new ArrayList <Order> ();
         
         for (int j = 0; j < ordersThisMonth.size(); j++) {
            if (ordersThisMonth.get(j).getOrderDate().getDay() == i) {
               tempOrdersToday.add (ordersThisMonth.get(j));
            }
         }
      		
         int ordersToday = tempOrdersToday.size();
      		
         for (int j = 0; j < ordersToday; j++) {
            datesAndTotalsAverage[i - 1] += tempOrdersToday.get(j).getFinalTotal();
         }	
         if (ordersToday != 0) {
            datesAndTotalsAverage[i - 1] /= ordersToday;
         } else {
            datesAndTotalsAverage[i - 1] = 0;
         }
      }
      
      for (int i = 0; i < Restaurant.DAYSPERMONTH; i++) {
         sigmaX += i;
         sigmaY += datesAndTotalsAverage[i];
         sigmaXY += i * datesAndTotalsAverage[i];
         sigmaXSquared += i * i;
      }
   		
      meanX = sigmaX / Restaurant.DAYSPERMONTH;
      meanY = sigmaY / Restaurant.DAYSPERMONTH;
   		
      if (n * sigmaXSquared - sigmaX * sigmaX == 0) {
         m = 0;
      } else {
         m = (n * sigmaXY - sigmaX * sigmaY) / (n * sigmaXSquared - sigmaX * sigmaX);
      }
      
      b = meanY - m * meanX;
   		
      return (Restaurant.DAYSPERMONTH + Restaurant.DAYSPERMONTH / 2) * m + b;
   }
   
   /*
      Parameters: No Parameter
      Return Value: The method does not return any value because it is a void
      Purpose: This method calculates the daily tips of an employee by adding all of the tips
               that was given in particular day and dividing by the number of employees.
   */
   public void calculateDailyTipsPerEmployee() {
      if (numEmployees == 0) {
         dailyTipsPerEmployee = 0;
      } else {
         totalDailyTips = 0;
      
         //Sequential Search
         for (int i = 0; i < ordersThisMonth.size(); i++) { 
            if (ordersThisMonth.get(i).getOrderDate().isEquals (restaurantDate)) {
               totalDailyTips += ordersThisMonth.get(i).getTip();
            }
         }
      
         dailyTipsPerEmployee = totalDailyTips / numEmployees;
      }
   }
   
   /*
      Parameters: N/A
      Description: Calls all the calculate methods within this class.
      Purpose: To calculate all the numbers for the daily/monthly summary.
   */
   public void calculateAllValues() {
      calculateMonthlyRevenue();
      calculateDailyRevenue();
      
      calculateSpentMonth();
      calculateSpentDay();
      
      calculateMonthlyProfit();
      calculateDailyProfit();
     
      calculateDailyTipsPerEmployee();
   }
   
   /*
      Parameters: N/A
      Description: Returns all the essential numbers for the daily/monthly summary.
      Purpose: To show the attributes of the finances.
   */
   DecimalFormat df = new DecimalFormat("0.00");
   
   public String toString() {
      return ("Daily Revenue: $" + df.format(getTotalRevenueDay()) +
              "\nMonthly Revenue: $" + df.format(getTotalRevenueMonth()) +
              
              "\n\nDaily Spendings: $" + df.format(getTotalSpentDay()) +
              "\nMonthly Spendings: $" + df.format(getTotalSpentMonth()) +
              
              "\n\nDaily Profit: $" + df.format(getTotalProfitDay()) +
              "\nMonthly Profit: $" + df.format(getTotalProfitMonth()) +
              
              "\n\nPredicted Revenue Next Month: $" + df.format(predictRevenueNextMonth()) +
              "\nDaily Tips Per Employee: $" + df.format(getDailyTipsPerEmployee()));
   }
}