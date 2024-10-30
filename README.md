# Attendance-Authentication-using-QR-Code-
Our Project takes a leap forward from the conventional attendance tracking mechanism to the new way of creating an integrated platform for professors to manage classes and attendance via a web portal, and for students to view schedules and mark attendance via a mobile app by scanning the QR code displayed by the Professor. It streamlines administrative tasks, ensuring efficient data synchronization and enhancing communication between professors and students. thereby revolutionizing the digital means to get a comprehensive overview of student’s academic progress.

###
1.	ID: FR#1
Title:  QR code generation -  FR#1
•	REQ1:   Instructors should have the ability to generate a unique QR code for each class session
 Users should be able to register with the system providing necessary details.
•	Priority: 1
•	Dependency: None
2.	ID: FR#2
Title :Database Design 
	REQ2:  Database will be used in the project for storing and updating the information. 
    Integration with Student Information System (SIS):
	Priority: 1
	Dependency: None
        MySQL installation  and running of queries to fetch required results.
	Priority: 2
	Dependency: None

3.	ID: FR#3
Title: Scan QR Code: FR#3
•	REQ3: Students should be able to scan the QR code displayed during the class session.
  QR code needs to be time stamped and accordingly attendance must be posted for students as Present, late etc.
•	Priority: 1
•	Dependency: Dependent on Generated QR code. (FR#1)

4.	ID: FR#4
Title: Authentication: FR#4
•	REQ4: The student would be authenticated with their unique identifier – i.e Student ID .    All valid users would be granted access and their attendance would be posted. Additional functionality to handle invalid Student id during login.
•	Priority: 1
•	Dependency: Dependent on Database , which consists attributes of all students. (FR#2)

5.	ID: FR#5
Title: Course Creation: FR#5
•	REQ5: Coursework with unique CRN needs to be generated 
•	Priority: 1
•	Dependency: Dependent on QR code being generated, each course would have a separate QR code. (FR#1)

6.	ID:FR#6
Title: Add Attendance: FR#6
•	REQ5: Instructors should be able to add attendance records manually if needed.
•	Priority: 1
•	Dependency: Dependent on database. (FR#2)

7.	ID:FR#7
Title: Update Attendance: FR#7
•	REQ6: Instructors should be able to update attendance records manually if needed.
•	Priority: 1
•	Dependency: Dependent on database. (FR#2)

8.	ID:FR#8
Title: Delete Attendance: FR#8
•	REQ7: instructors should have access to delete attendance records.
•	Priority: 1
•	Dependency:  Dependent on database. (FR#8)

9.	ID:FR#9
Title: View Attendance: FR#9
•	REQ7: Both instructors and students should have access to view attendance records.
•	Priority: 1
•	Dependency:  Dependent on database. (FR#1)

### Installations
install *NodeJS*, *MariaDB*, *Android Studio*

