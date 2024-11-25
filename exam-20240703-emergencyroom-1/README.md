Emergency Room
===============

Write a program capable of managing the reception of patients at the emergency room, specialist visits, discharges, and hospitalizations. The classes are located in the package it.polito.emergency. The main class is EmergencyApp, while in the test package, there is the class TestExample that provides examples of the main methods' usage and examples of the required checks.
Implement only the required checks.
The exceptions in the methods described below are of the type EmergencyException.

The JDK documentation can be found on the [local server](https://oop.polito.it/api/).

The Italian version of these requirements is available in [README_it.md](README_it.md).

### R1. Professionals and Departments

The emergency room performs the functions of first patient reception, engaging specialists, and sending patients to hospital departments.

The method `addProfessional()` allows the emergency room to add a  medical specialist (i.e., a professional) on duty. For simplicity, it is assumed that the professional is always available (24/7) during the specified period. The period is indicated in the format `"yyyy-MM-dd to yyyy-MM-dd"` where `yyyy` represents the year, `MM` the month, and `dd` the day. Each professional is characterized by a specialization, an ID number, a first name, and a last name.

The method `getProfessionalById()` allows the emergency room to retrieve the professional's details. If the ID is not found, an `EmergencyException` will be generated.

The method `getProfessionals()` allows the emergency room to retrieve the IDs of professionals with a particular specialization. If no specialists are present, the program will throw an `EmergencyException`.

The method `getProfessionalsInService()` allows the emergency room to obtain the IDs of professionals on duty for the requested period with a specific specialization. The period is always specified in the format `"yyyy-MM-dd to yyyy-MM-dd"`. If no professionals are present with the requested specialization during the requested period, the program will throw an `EmergencyException`.

The method `addDepartment()` allows the emergency room to add hospital departments associated with the emergency room, characterized by a name, a maximum number of patients, and the list of patients under care. It is assumed that the department name is unique.

The method `getDepartments()` allows the emergency room to get the complete list of department names associated with the structure. If no department is connected to the emergency room, the program will throw an `EmergencyException`.

The method `readFromFileProfessionals()` receives a Reader object associated to the file containing the list of professionals and their availabilities. The methods saves these professionals, and returns the number of professionals read from the file. 

The method `readFromFileDepartments()` receives a Reader object associated to the file containing the list of departments connected with the emergency room, saves the departments, and returns the number of departments read from the file. In both methods, if the Reader is null, the program throws an IOException. 
At the end of the README, two files, professionals.csv and departments.csv, are provided as examples of documents read by the Reader objects.

### R2. Patient Reception

The method `addPatient()` registers a new patient using their first name, last name, date of birth, tax code, reason, and the date they were admitted to the emergency room. The date follows the format `yyyy-MM-dd` where `yyyy` represents the year, `MM` the month, and `dd` the day. The method returns the patient object. If the patient already exists, it returns the existing object. The patient will assume the status of admitted (`ADMITTED`). Hint: Use the `PatientStatus` enum as a reference for the statuses assumed by the patients.

The method `getPatient()` accepts a tax code or a last name as a parameter and returns a list containing the patient or patients that match the request. In the case of a search by last name, if there are multiple patients with the same last name, the method will return all these patients in the list. If no patient is found with that tax code or surname, the program will return an empty list.

The method `getPatientsByDate()` returns the tax codes of patients who registered on the specified day, sorted by last name and first name. If no patients are present, the program will return an empty collection.

### R3. Assigning the Patient to a Specialist and Recording the Outcome

The method `assignPatientToProfessional()` receives the patient's tax code and the required specialization of the professional as parameters. The method selects a professional available at the time of the patient's admission. If multiple specialists are available, the first one in order of ID is chosen. If the tax code is not found among the admitted patients, the program will throw an `EmergencyException`. If no professional with the required specialization is found or if no professional with that specialization is available on the date of the patient's admission, the program will throw an `EmergencyException`. 
If successful, the method returns the specialist's ID.

The method `saveReport()` receives the specialist's ID, the patient's tax code, the visit date, the report description as parameters, and returns the report. If the specialist's ID is not found, the program will throw an `EmergencyException`.

### R4. Patient Discharge or Assignment to a Department

The method `dischargeOrHospitalize()` handles the patient's assignment to a department. If a bed is available in the department, the patient's status is changed to `HOSPITALIZED`. If no bed is available, the patient is discharged, changing their status to `DISCHARGED`, while keeping the patient's data in the records. If the patient or the department does not exist, an `EmergencyException` is thrown.

The method `verifyPatient()` receives the patient's tax code and verifies the patient's status, whether they are discharged (`DISCHARGED`) or in a department (`HOSPITALIZED`). It returns 0 if discharged, 1 if in a department. If the patient does not exist, the program will throw an `EmergencyException`.

### R5. Calculating Presence Statistics in the Emergency Room

The method `getNumberOfPatients()` returns the number of patients admitted to the emergency room and still being managed (i.e., patients with status `ADMITTED`).

The method `getNumberOfPatientsByDate()` returns the total number of patients admitted to the emergency room, both managed and not yet managed, on a specific day.

The method `getNumberOfPatientsHospitalizedByDepartment()` returns the number of patients admitted to the emergency room and sent to the requested department or generates an `EmergencyException` if the department name does not exist.

The method `getNumberOfPatientsDischarged()` returns the number of patients admitted to the emergency room and discharged.

The method `getNumberOfPatientsAssignedToProfessionalDischarged()` receives a specialization and returns the number of patients discharged after being seen by a professional with that specialization.

## Sample Input Files

### specialists.csv
```
id,name,surname,specialization,period,
1,Mario,Rossi,Cardiology,2024-06-11 to 2024-06-24 
2,Laura,Bianchi,Orthopedics,2024-06-16 to 2024-06-30
```
### departments.csv
```
departmentName,maxPatients  
Cardiology,20   
Orthopedics,15
```
