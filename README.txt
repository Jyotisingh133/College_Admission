College Admission Management System (Java + JDBC + MySQL)
----------------------------------------------------------

How to Run in IntelliJ IDEA:

1. Open IntelliJ IDEA
   - Click "Open" -> Select the 'CollegeAdmissionSystem' folder.

2. Configure SDK:
   - Go to File > Project Structure > Project SDK > Add JDK (Java 17+ recommended).

3. Add MySQL Connector Library:
   - Download mysql-connector-j.jar from MySQL site.
   - File > Project Structure > Modules > Dependencies > '+' > JARs > select jar file.

4. Setup Database:
   - Open MySQL or phpMyAdmin.
   - Run the SQL script inside folder 'sql/schema.sql'.

5. Update Credentials (if needed):
   - Open DBConnection.java
   - Change DB_USER / DB_PASSWORD if your MySQL login differs.

6. Run the program:
   - Right-click on AdmissionManager.java
   - Choose 'Run AdmissionManager.main()'

7. Console Menu appears:
   1) Register Student
   2) Apply for Course
   3) Run Allocation
   4) Export CSV
   5) List Courses
   6) Exit

Enjoy your project!
