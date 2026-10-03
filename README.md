# Billing System - Complete Project

Two versions that share ONE database (billing_system):

1_JavaDesktopApp/   Java Swing + JDBC desktop app (main assignment)
2_PHPWebApp/        PHP + MySQL browser version (optional)
database/           billing_system.sql  (import once in phpMyAdmin)

Default login for both: admin / admin123

## Step 1 - Database (both versions)
1. Start MySQL in XAMPP Control Panel (start Apache too if you use phpMyAdmin or the PHP version).
2. Open http://localhost/phpmyadmin -> Import -> choose database/billing_system.sql -> Go.

## Step 2A - Run the Java desktop app
1. Download MySQL Connector/J (Platform Independent) from https://dev.mysql.com/downloads/connector/j/
   and copy mysql-connector-j-x.x.x.jar into 1_JavaDesktopApp/lib/
2. In VS Code: File -> Open Folder -> select the 1_JavaDesktopApp folder.
3. Open src/Main.java and press Run (or F5).
   OR double-click run.bat (Windows) / run ./run.sh (Mac/Linux).
   The Login window opens on the desktop (it is NOT a web page).
   Do not keep this folder inside xampp/htdocs.

Compiled classes are already in bin/ (Java 11+). run.bat recompiles them anyway.

## Step 2B - Run the PHP web version
1. Copy the folder 2_PHPWebApp/BillingSystemWeb into C:\xampp\htdocs\
2. Start Apache + MySQL.
3. Open http://localhost/BillingSystemWeb/  -> the Login page appears first.

## Folder layout of the Java app (inside src/)
Main.java
database/DatabaseConnection.java
model/Product.java
dao/UserDAO.java, ProductDAO.java
ui/LoginFrame, HomeFrame, AddItemFrame, UpdateItemFrame,
   DeleteItemFrame, ReportFrame, UIHelper, ProductFormPanel

## Common problems
- "Cannot connect to MySQL"      -> start MySQL in XAMPP.
- "Database does not exist"      -> import database/billing_system.sql.
- "Connector/J not found"        -> put the JAR in 1_JavaDesktopApp/lib/.
- "Index of /BillingSystem" page -> the Java app is not a website; run Main.java instead,
                                    or use the PHP version at /BillingSystemWeb/.
- Login fails                    -> re-import the SQL (password is stored as SHA-256).
