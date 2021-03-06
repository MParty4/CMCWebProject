/**
 * File: DBController.java
 */
package cmc.mario.controllers;
import dblibrary.project.csci230.*;

import java.util.*;

import cmc.mario.entities.Account;
import cmc.mario.entities.University;
import cmc.mario.entities.User;
/**
 * Database Controller to get data from DBLibrary for all controller
 * @author Kalila Moua, Tre Vazquez, Jing Thao, Yidan Zhang
 * @version 03/27/2017
 */
public class DBController {
	
	/**
	 * Database library is been using
	 */
	private UniversityDBLibrary univLib;
  /**
   * Create a new univeresityDBLibrary with user name and password
   */
  public DBController(){
    this.univLib = new UniversityDBLibrary("mariop4","mariop4","csci230");
  }
  
  public UniversityDBLibrary getUniversityLibrary(){
	  return this.univLib;
  }
  /**
   * This method allows the user to edit their own personal profile information. - Yidan 
   * @param firstName the first name to change the old first name to 
   * @param lastName the last name to change the old last name to
   * @param password the password to change the old password to
   * @param username the user's userName
   * @throws IllegalArgumentException if account cannot be found
   * @return true if edit successfully
   */ 
  public boolean editPersonalProfile(String userName, String firstName, String lastName, String password){ 
	  Account x = this.getSpecificUser(userName);
	  if(x==null)
	  {
		 return false;
	  }
	  else
	  {
		  char type = x.getTypeOfUser();
		  char status = x.getStatus();
		  
		  int i = univLib.user_editUser(userName, firstName, lastName, password, type, status);
		  if (i ==1)
			  {return true;}
	  }
	  return false;
  }


/**
 * This method adds the user into the database -admin only. - Tre
 * @param firstName first name to add
 * @param lastName last name to change
 * @param password the password to change the old password to
 * @param username the username of the account of new added user
 * @param type the character of which represents the type of user
 * @return true if add successfully
 */
public int addUser(String firstName, String lastName, String username, String password, char type){
	int error=4;
	  Account a = this.getSpecificUser(username);
	  if(a.getUsername()!=null){
		  error=2;
		  throw new IllegalArgumentException("Excisted Username!");
	  }
	  else{
		  int i=1;
		  if(type=='a'){
			i=  univLib.user_addUser(firstName, lastName, username, password, 'a');
			error=0;
		  }
		  else if(type=='u'){
			i=  univLib.user_addUser(firstName, lastName, username, password, 'u');
			error=1;
		  }
		  else{
			  error=3;
			  throw new IllegalArgumentException("Input Invaliad For Type Of User!");
		  }
		  if(!(i==1)){
			   error=4;
			   throw new IllegalArgumentException("Unknown Error!");
		  }
		  error=i;
	  }
	  return error;
  }
  
  /**
   * This method allows the admin to edit user information. - Kalila
   * @param firstName the first name to change the old first name to
   * @param lastName the last name to change the old last name to
   * @param password the password to change the old password to
   * @param type the character of which represents the type of user
   * @param status the status of the user, if active or not
   * @return true if edit successfully
   */
  public int editUser(String firstName, String lastName, String username, String password, char type, char status){
	  int error =4;
	  Account a = this.getSpecificUser(username);
	  if(a.getUsername()==null){
		  error=2;
		  throw new IllegalArgumentException("Cannot find this account!");
	  }	
	  else{
	   int i =1;
	   if(type=='a'||type=='u'){
		if(status=='Y'||status=='N'){
			i = univLib.user_editUser(username, firstName, lastName, password, type, status);
			if(i==1){
				error=i;
			}
			else{
				error=4;
				throw new IllegalArgumentException("Unknown Error!");
			}
		}
		else{
			error=0;
			throw new IllegalArgumentException("Input Invaliad For Status!");
		}
			}
	   else{
		 error=3;
		 throw new IllegalArgumentException("Input Invaliad For Type Of User!");
	   }
	  }
	  return error;
  }
  
  /**
   * To deactivate user-admin only.
   * @param username the username of the user to be deleted - Yidan
   * @throws IllegalArgumentException if the account has already deactivated or it does not exist
   * @return true if deactivate successfully
   */
  public boolean deactivateUser(String username){
	  Account a = this.getSpecificUser(username);
	  if(a.getFirstName()==null||a.getStatus()=='N'){
		  return false;
	  }
	  a.setStatus('N');
	  int i = this.editUser(a.getFirstName(), a.getLastName(), a.getUsername(), a.getPassword(), a.getTypeOfUser(), a.getStatus());
	 if(i!=1){
		 return false;
	 }
	 return true;
  }
  
  /**
   * To reactivate user-admin only.
   * @param username the username of the user to be deleted - Yidan
   * @throws IllegalArgumentException if the account has already deactivated or it does not exist
   * @return true if reactivate successfully
   */
  public boolean reactivateUser(String username){
	  Account a = this.getSpecificUser(username);
	  if(a.getFirstName()==null||a.getStatus()=='Y'){
		  return false;
	  }
	  a.setStatus('Y');
	  int i = this.editUser(a.getFirstName(), a.getLastName(), a.getUsername(), a.getPassword(), a.getTypeOfUser(), a.getStatus());
	 if(i!=1){
		 return false;
	 }
	 return true;
  }
  /**
   * To delete user-admin only.
   * @param username the username of the user to be deleted - Yidan
   * @throws IllegalArgumentException if the account has already deactivated or it does not exist
   * @return true if delete successfully
   */
  public boolean delateUser(String username){
	  Account a = this.getSpecificUser(username);
	  if(a.getFirstName()==null){
		  return false;
	  }
	  int i = univLib.user_deleteUser(username);
	 if(i!=1){
		 return false;
	 }
	 return true;
  }
  
  /**
   * This method add a specific university - admin only. - Yidan
    * @param schoolName university to update
    * @param state which is new to update
    * @param location which is new to update
    * @param control which is new to update
    * @param numOfStu which is number of students to update
    * @param perFem which is percentage of female to update
    * @param satVerbal which is sat verbal score to update
    * @param satMath which is sat math score to update
    * @param price which is expense to update
    * @param finAid which is financial aid student can get from school to update
    * @param numOfApp which is number of applicants to update
    * @param perAdmit which is percentage of admit to update
    * @param perEnroll which is percentage of enroll to update
    * @param control which is new to update
    * @param academicScale which is scale of acedmic to update
    * @param socialScale which is scale of social to update
    * @param lifeScale which is scale of life to update
    * @param popMajor which is the emphases majors of this school to update
    * @return true if it successfully
   */
  public boolean addUniversity(String school, String state, String location, String control, String numOfStudents, String perFemales, String SATVerb, String SatMath, 
		  String exp, String perFinancialAid, String numOfApplicants, String perAdmitted, String perEnrolled, 
		  String acadeScale, String soScale, String qualOfLifeScale, String[] popMajors){
	  for(int i=0;i<school.length();i++){
			if(Character.isLetter(school.charAt(i))){
				continue;
			}
			else{
				throw new IllegalArgumentException("The input of name cannot include any number or symbol!");
			}
		}
		for(int i=0;i<state.length();i++){
			if(Character.isLetter(state.charAt(i))){
				continue;
			}
			else{
				throw new IllegalArgumentException("The input of state cannot include any number or symbol!");
			}
		}
		for(int i=0;i<location.length();i++){
			if(Character.isLetter(location.charAt(i))){
				continue;
			}
			else{
				throw new IllegalArgumentException("The input of location cannot include any number or symbol!");
			}
		}
		for(int i=0;i<control.length();i++){
			if(Character.isLetter(control.charAt(i))){
				continue;
			}
			else{
				throw new IllegalArgumentException("The input of control cannot include any number or symbol!");
			}
		}
	  int numberOfStudents=-1,  percentFemales=-1, SATVerbal=-1,SATMath=-1, 
	  expenses=-1,  percentFinancialAid=-1, numberOfApplicants=-1,  percentAdmitted=-1, percentEnrolled=-1;
	  double academicsScale=-1.0, socialScale=-1.0,  qualityOfLifeScale=-1.0;
	  try{
			if(numOfStudents!="")
			{
				numberOfStudents = Integer.parseInt(numOfStudents);
				if(numberOfStudents<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
		 	try{
			if(perFemales!="")
			{
				percentFemales = Integer.parseInt(perFemales);
				if(percentFemales<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
		 	try{
			if(SATVerb!="")
			{
				SATVerbal = Integer.parseInt(SATVerb);
				if(SATVerbal<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(SatMath!="")
			{
				SATMath = Integer.parseInt(SatMath);
				if(SATMath<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(exp!="")
			{
				expenses = Integer.parseInt(exp);
				if(expenses<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(perFinancialAid!="")
			{
				percentFinancialAid = Integer.parseInt(perFinancialAid);
				if(percentFinancialAid<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(numOfApplicants!="")
			{
				numberOfApplicants = Integer.parseInt(numOfApplicants);
				if(numberOfApplicants<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(perAdmitted!="")
			{
				percentAdmitted = Integer.parseInt(perAdmitted);
				if(percentAdmitted<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(perEnrolled!="")
			{
				percentEnrolled = Integer.parseInt(perEnrolled);
				if(percentEnrolled<-1){
					throw new IllegalArgumentException("Number can't be negative!");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(acadeScale!="")
			{
				academicsScale = Double.parseDouble(acadeScale);
				if(academicsScale<-1||academicsScale==0){
					throw new IllegalArgumentException("Number can't be negative or zero!");
				}
				if(academicsScale>5){
					throw new IllegalArgumentException("The scale can't be more than 5");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(soScale!="")
			{
				socialScale = Double.parseDouble(soScale);
				if(socialScale<-1||socialScale==0){
					throw new IllegalArgumentException("Number can't be negative or zero!");
				}
				if(socialScale>5){
					throw new IllegalArgumentException("The scale can't be more than 5");
				}
			}}catch(NumberFormatException ne){}
			try{
			if(qualOfLifeScale!="")
			{
				qualityOfLifeScale = Double.parseDouble(qualOfLifeScale);
				if(qualityOfLifeScale<-1||qualityOfLifeScale==0){
					throw new IllegalArgumentException("Number can't be negative or zero!");
				}
				if(qualityOfLifeScale>5){
					throw new IllegalArgumentException("The scale can't be more than 5");
				}
			}}catch(NumberFormatException ne){}
	  University u = this.viewSpecificSchool(school);
	  if(u.getSchoolName()!=null){
		  throw new IllegalArgumentException("Excisted School!");
	  }
	  int i =univLib.university_addUniversity(school, state, location, control, 
			  numberOfStudents, percentFemales, SATVerbal, SATMath, expenses, percentFinancialAid, 
			  numberOfApplicants, percentAdmitted, percentEnrolled, (int)academicsScale, (int)socialScale, (int)qualityOfLifeScale);
	  if(!(i==1)){
		  throw new IllegalArgumentException("Unknown Error!");
	  }

    return true;
  }
  /**
   * To delete user-admin only.
   * @param schoolName the schoolName of the user to be deleted 
   * @throws IllegalArgumentException if the account has already deactivated or it does not exist
   * @return true if delete successfully
   */
  public boolean deleteUniversity(String schoolName){


	  

	  this.deleteEmphasis(schoolName);
	  int i = univLib.university_deleteUniversity(schoolName);
	  

	  University u = this.viewSpecificSchool(schoolName);

	  

	 if(i!=1){
		 return false;
	 }
	 return true;
  }
  /**
   * To delete user-admin only.
   * @param schoolName the schoolName of the user to be deleted 
   * @throws IllegalArgumentException if the account has already deactivated or it does not exist
   * @return true if delete successfully
   */
  public void deleteEmphasis(String schoolName){
	  List<String> list = this.getEmphasesForUniversity(schoolName);
	  for(int l =0;l <list.size();l++){
		  String emphasis = list.get(l);
		   univLib.university_removeUniversityEmphasis(schoolName, emphasis);

		  }
  }
  
  /**
   * This method edits a specific university - admin only. - Yidan
    * @param schoolName university to update
    * @param state which is new to update
    * @param location which is new to update
    * @param control which is new to update
    * @param numOfStu which is number of students to update
    * @param perFem which is percentage of female to update
    * @param satVerbal which is sat verbal score to update
    * @param satMath which is sat math score to update
    * @param price which is expense to update
    * @param finAid which is financial aid student can get from school to update
    * @param numOfApp which is number of applicants to update
    * @param perAdmit which is percentage of admit to update
    * @param perEnroll which is percentage of enroll to update
    * @param control which is new to update
    * @param academicScale which is scale of acedmic to update
    * @param socialScale which is scale of social to update
    * @param lifeScale which is scale of life to update
    * @param popMajor which is the emphases majors of this school to update
    * @return true if edit successfully
   */
  public boolean editUniversity(String school, String state, String location, String control, int numberOfStudents, int percentFemales, int SATVerbal, int SATMath, 
		  int expenses, int percentFinancialAid, int numberOfApplicants, int percentAdmitted, int percentEnrolled, 
		  double academicsScale, double socialScale, double qualityOfLifeScale){
	  University u = this.viewSpecificSchool(school);
	  
	  if(u==null){
		  return false;
	  }
	  int i = univLib.university_editUniversity(school, state, location, control, 
			  numberOfStudents, percentFemales, SATVerbal, SATMath, expenses, percentFinancialAid, 
			  numberOfApplicants, percentAdmitted, percentEnrolled, (int)academicsScale, (int)socialScale, (int)qualityOfLifeScale);
	  if(!(i==1)){
		  return false;
	  }
	  return true;

  }
  
  /**
   * Gets the list of accounts. - Kalila
   * @return returns a list accounts
   */
  public List<Account> getAccountList(){
	    String[][] userList =univLib.user_getUsers();
	    List<Account> userListConvert = new ArrayList<Account>();
	    for(String[] arr : userList){
	    	Account a = new Account(arr[0],arr[1],arr[2],arr[3],arr[4].charAt(0),arr[5].charAt(0));	
	    	userListConvert.add(a);
	    }
	    return userListConvert;
	  }

  
  /**
   * Gets a specific user.
   * @param username of the user need to view - Yidan
   * @return account to return
   */
  public User getSpecificUser(String username){
	  User a = new User();
	  try{
	  String[][] userList =univLib.user_getUsers();
	  
	  for(String[] arr : userList){
		  if(arr[2].equals(username)){
	    	a.setFirstName(arr[0]);
	    	a.setLastName(arr[1]);
	    	a.setUsername(arr[2]);
	    	a.setPassword(arr[3]);
	    	a.setTypeOfUser(arr[4].charAt(0));
	    	a.setStatus(arr[5].charAt(0));
	  }
	  }
	  return a;
	  }catch(Exception e){
		    e.printStackTrace();
			return a;
	  }
  }

  
  /**
   * Get universities list.
   * @return list of universities in database. -Tre
   */
  public List<University> getUniversities(){
    String[][] univList =univLib.university_getUniversities();
    List<University> list = new ArrayList<University>();
    for(String[] schoolC : univList){
    	University u = new University(schoolC[0],schoolC[1],schoolC[2],schoolC[3],
				Integer.parseInt(schoolC[4]),Integer.parseInt(schoolC[5]),Integer.parseInt(schoolC[6]),Integer.parseInt(schoolC[7]),
				Integer.parseInt(schoolC[8]),Integer.parseInt(schoolC[9]),Integer.parseInt(schoolC[10]),Integer.parseInt(schoolC[11]),
				Integer.parseInt(schoolC[12]),Double.parseDouble(schoolC[13]),Double.parseDouble(schoolC[14]),Double.parseDouble(schoolC[15]));
		list.add(u);
    }
    return list;
  }

  /**
   * To view specific university. - Tre
   * @param university name
   * @return the university
   */
  public University viewSpecificSchool(String schoolName){
    String[][] univList =univLib.university_getUniversities();
    University u = new University();
    for(String[] arr: univList){
    	 if(arr[0].equals(schoolName)){
	    	u.setSchoolName(arr[0]);
	    	u.setState(arr[1]);
	    	u.setLocation(arr[2]);
	    	u.setControl(arr[3]);
	    	u.setNumOfStu(Integer.parseInt(arr[4]));
	    	u.setPerFem(Integer.parseInt(arr[5]));
	    	u.setSatVerbal(Integer.parseInt(arr[6]));
	    	u.setSatMath(Integer.parseInt(arr[7]));
	    	u.setPrice(Integer.parseInt(arr[8]));
	    	u.setFinAid(Integer.parseInt(arr[9]));
	    	u.setNumOfApp(Integer.parseInt(arr[10]));
	    	u.setPerAdmit(Integer.parseInt(arr[11]));
	    	u.setPerEnroll(Integer.parseInt(arr[12]));
	    	u.setAcademicScale(Double.parseDouble(arr[13]));
	    	u.setSocialScale(Double.parseDouble(arr[14]));
	    	u.setLifeScale(Double.parseDouble(arr[15]));
    	 }
    }
    return u;
  }

  /**
   * Method to retrieve list of saved schools for a specified user - Tre
   * @param user the user whose saved schools are being retrieved
   * @return list of saved schools
   */
 
	  public List<University> getSavedSchools(String username){
			// TODO Auto-generated method stub
			  String[][] userList =univLib.user_getUsernamesWithSavedSchools();
			  String[][] schoolList =univLib.university_getUniversities();
			  List<University> list = new ArrayList<University>();
			    for(String[] userC : userList){
			    	if(userC[0].equals(username)){
			    		for(String[] schoolC: schoolList){
			    			if(userC[1].equals(schoolC[0])){
			    				University u = new University(schoolC[0],schoolC[1],schoolC[2],schoolC[3],
			    						Integer.parseInt(schoolC[4]),Integer.parseInt(schoolC[5]),Integer.parseInt(schoolC[6]),Integer.parseInt(schoolC[7]),
			    						Integer.parseInt(schoolC[8]),Integer.parseInt(schoolC[9]),Integer.parseInt(schoolC[10]),Integer.parseInt(schoolC[11]),
			    						Integer.parseInt(schoolC[12]),Double.parseDouble(schoolC[13]),Double.parseDouble(schoolC[14]),Double.parseDouble(schoolC[15]));
			    				list.add(u);
			    			}
			    		}
			    	}
			    } 
			    return list;
		
}

  /**
   * Method to remove a selected school from a user's saved school list - Tre
   * @param user the user who is removing a school from their list
   * @param schoolName the school object to be removed
   * @param school the name of the school to be removed
   * @throws NoSuchElementException if the user's saved list is empty
   * @return true if successfully
   */
	  public boolean removeSavedSchool(User user, String schoolName) { 
			// TODO Auto-generated method stub
			  List<University> userList =this.getSavedSchools(user.getUserName());
			  if(userList.isEmpty())
			  {
				  throw new NoSuchElementException("The User's saved list is empty, you cannot remove from an empty list");
			  }
			  for(University u: userList)
			  {
				  if(u.equals(schoolName))
				  { 
					  //userList.remove(u);
					  return true;
				  }
			  }
				int i = univLib.user_removeSchool(user.getUsername(),schoolName);
	
				if(i==1){
					return true;
				}
				return false;
			  
			  //univLib.user_removeSchool(user.getUsername(),schoolName.getSchoolName());
		}
/**
 * Method that will add a selected school to a specified user's saved school list - JING
 * @param user user who will be adding to their list
 * @param schoolName name of school to be added to the saved school list
 * @return true add successfully
 */
	  public boolean addSavedSchool(User user, String schoolName) {
			List<University> list = this.getSavedSchools(user.getUsername());
			for(University u: list){
				if(u.getSchoolName().equals(schoolName)){
				return false;
				}
			}
			int i = univLib.user_saveSchool(user.getUsername(), schoolName);
			if(i==1){
				return true;
			}
			return false;
			
		}

	/**
	 * Method to get emphases for specific university - helper method - JING
	 * @param universityName the name of university
	 * @return list of strings of emphases for university
	 */
	public List<String> getEmphasesForUniversity(String universityName){
		String[][] uniE = univLib.university_getNamesWithEmphases();
		List<String> emphasesList = new ArrayList<String>();
		for(String[] em: uniE){
			if(em[0].equals(universityName)){
				for(int i =1; i<em.length; i++){
					emphasesList.add(em[i]);
				}
			}
		}
		return emphasesList;
	}
	public void setEmphasisForUniversity(String universityName,String[] list){
		UniversityDBLibrary u = new UniversityDBLibrary("mariop4", "mariop4", "csci230");
		  for(int k=0; k<list.length; k++){
			  String pop = list[k];
			  
			  u.university_addUniversityEmphasis(universityName, pop);
		  }
		 

	}
		
	
	
	/**
	 * Method to return a list of universities with attributes that fit in the criteria entered by user - Kalila
	 * @param schoolName
	 *            name of the school
	 * @param state
	 *            state of school
	 * @param location
	 *            location of school
	 * @param control
	 *            control of school
	 * @param numOfStu
	 *            number of students
	 * @param perFem
	 *            percentage of female
	 * @param satVerbal
	 *            sat verbal score
	 * @param satMath
	 *            sat math score
	 * @param price
	 *            expenses for university
	 * @param finAid
	 *            financial aid percentage 
	 * @param numOfApp
	 *            number of applicants
	 * @param perAdmit
	 *            percentage of admitted students
	 * @param perEnroll
	 *            percentage of enroll
	 * @param academicScale
	 *            scale of academic life
	 * @param socialScale
	 *            scale of social life
	 * @param lifeScale
	 *            scale of life quality
	 * @param popMajor
	 *            the list of emphases for a specific school
	 * @return list of universities meet search conditions
	 */

	public List<University> searchResults(String schoolName, String state, String location, String control, String numOfStuStart, String numOfStuEnd, 
			 String perFemStart,String perFemEnd, String satVerbalStart, String satVerbalEnd, String satMathStart, String satMathEnd, String priceStart, String priceEnd,
			 String finAidStart,String finAidEnd, String numOfAppStart, String numOfAppEnd, String perAdmitStart, String perAdmitEnd, String perEnrollStart, 
			 String perEnrollEnd, String academicScaleStart, String academicScaleEnd, String socialScaleStart, String socialScaleEnd, String lifeScaleStart,
			 String lifeScaleEnd, String[] popMajor){
			List<University> results = new ArrayList<University>();
			String[][] univList = univLib.university_getUniversities();
			List<University> resultT = new ArrayList<University>();
			for(int i=0;i<schoolName.length();i++){
				if(Character.isLetter(schoolName.charAt(i))){
					continue;
				}
				else{
					throw new IllegalArgumentException("The input of name cannot include any number or symbol!");
				}
			}
			for(int i=0;i<state.length();i++){
				if(Character.isLetter(state.charAt(i))){
					continue;
				}
				else{
					throw new IllegalArgumentException("The input of state cannot include any number or symbol!");
				}
			}
			for(int i=0;i<location.length();i++){
				if(Character.isLetter(location.charAt(i))){
					continue;
				}
				else{
					throw new IllegalArgumentException("The input of location cannot include any number or symbol!");
				}
			}
			for(int i=0;i<control.length();i++){
				if(Character.isLetter(control.charAt(i))){
					continue;
				}
				else{
					throw new IllegalArgumentException("The input of control cannot include any number or symbol!");
				}
			}
			int numMax=-1,numMin=-1,FemMin=-1,FemMax=-1,VerbMin=-1,VerbMax=-1,MathMin=-1,MathMax=-1,expenMin=-1,expenMax=-1,AidMin=-1,
				 	AidMax=-1,apMin=-1,apMax=-1,admMin=-1,admMax=-1,enrMin=-1,enrMax=-1;
				 	double aScalMin=-1.0,aScalMax=-1.0,socScalMin=-1.0,socScalMax=-1.0,qScalMin=-1.0,qScalMax=-1.0;
				 	try{
					if(numOfStuEnd!="")
					{
						numMax = Integer.parseInt(numOfStuEnd);
						if(numMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				 	try{
					if(numOfStuStart!="")
					{
						numMin = Integer.parseInt(numOfStuStart);
						if(numMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				 	try{
					if(perFemStart!="")
					{
						FemMin = Integer.parseInt(perFemStart);
						if(FemMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				 	try{
					if(perFemEnd!="")
					{
						FemMax = Integer.parseInt(perFemEnd);
						if(FemMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				 	try{
					if(satVerbalStart!="")
					{
						VerbMin = Integer.parseInt(satVerbalStart);
						if(VerbMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				 	try{
					if(satVerbalEnd!="")
					{
						VerbMax = Integer.parseInt(satVerbalEnd);
						if(VerbMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(satMathStart!="")
					{
						MathMin = Integer.parseInt(satMathStart);
						if(MathMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(satMathEnd!="")
					{
						MathMax = Integer.parseInt(satMathEnd);
						if(MathMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(priceStart!="")
					{
						expenMin = Integer.parseInt(priceStart);
						if(expenMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(priceEnd!="")
					{
						expenMax = Integer.parseInt(priceEnd);
						if(expenMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(finAidStart!="")
					{
						AidMin = Integer.parseInt(finAidStart);
						if(AidMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(finAidEnd!="")
					{
						AidMax = Integer.parseInt(finAidEnd);
						if(AidMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(numOfAppStart!="")
					{
						apMin = Integer.parseInt(numOfAppStart);
						if(apMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(numOfAppEnd!="")
					{
						apMax = Integer.parseInt(numOfAppEnd);
						if(apMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(perAdmitStart!="")
					{
						admMin = Integer.parseInt(perAdmitStart);
						if(admMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(perAdmitEnd!="")
					{
						admMax = Integer.parseInt(perAdmitEnd);
						if(admMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(perEnrollStart!="")
					{
						enrMin = Integer.parseInt(perEnrollStart);
						if(enrMin<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(perEnrollEnd!="")
					{
						enrMax = Integer.parseInt(perEnrollEnd);
						if(enrMax<-1){
							throw new IllegalArgumentException("Number can't be negative!");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(academicScaleStart!="")
					{
						aScalMin = Double.parseDouble(academicScaleStart);
						if(aScalMin<-1||aScalMin==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(aScalMin>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(academicScaleEnd!="")
					{
						aScalMax = Double.parseDouble(academicScaleEnd);
						if(aScalMax<-1||aScalMax==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(aScalMax>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(socialScaleStart!="")
					{
						socScalMin = Double.parseDouble(socialScaleStart);
						if(socScalMin<-1||socScalMin==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(socScalMin>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(socialScaleEnd!="")
					{
						socScalMax = Double.parseDouble(socialScaleEnd);
						if(socScalMax<-1||socScalMax==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(socScalMax>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(lifeScaleStart!="")
					{
						qScalMin = Double.parseDouble(lifeScaleStart);
						if(qScalMin<-1||qScalMin==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(qScalMin>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
					try{
					if(lifeScaleEnd!="")
					{
						qScalMax = Double.parseDouble(lifeScaleEnd);
						if(qScalMax<-1||qScalMax==0){
							throw new IllegalArgumentException("Number can't be negative or zero!");
						}
						if(qScalMax>5){
							throw new IllegalArgumentException("The scale can't be more than 5");
						}
					}}catch(NumberFormatException ne){throw new IllegalArgumentException("It's not a numberic format");}
				
			 for(String[] arr: univList){
				 if((arr[0].contains(schoolName)|| (schoolName == "")) 
							&& ((arr[1] .contains(state)) ||(state == "")) 
							&& ((arr[2] .contains(location)) ||(location == "")) 
							&& ((arr[3] .contains(control)) ||(control == "")) 
							&& (((numMin<=Integer.parseInt(arr[4]))&&(Integer.parseInt(arr[4])<= numMax)) || ((numMin<=Integer.parseInt(arr[4]))&&(numMax==-1))||((numMin==-1)&&(Integer.parseInt(arr[4])<= numMax))||((numMin==-1)&&(numMax==-1)))
							&& (((FemMin<=Integer.parseInt(arr[5]))&&(Integer.parseInt(arr[5])<= FemMax)) || ((FemMin<=Integer.parseInt(arr[5]))&&(FemMax==-1))||((FemMin==-1)&&(Integer.parseInt(arr[5])<= FemMax))||((FemMin==-1)&&(FemMax==-1)))
							&& (((VerbMin<=Integer.parseInt(arr[6]))&&(Integer.parseInt(arr[6])<= VerbMax)) || ((VerbMin<=Integer.parseInt(arr[6]))&&(VerbMax==-1))||((VerbMin==-1)&&(Integer.parseInt(arr[6])<= VerbMax))||((VerbMin==-1)&&(VerbMax==-1)))
							&& (((MathMin<=Integer.parseInt(arr[7]))&&(Integer.parseInt(arr[7])<= MathMax)) || ((MathMin<=Integer.parseInt(arr[7]))&&(MathMax==-1))||((MathMin==-1)&&(Integer.parseInt(arr[7])<= MathMax))||((MathMin==-1)&&(MathMax==-1)))
							&& (((expenMin<=Integer.parseInt(arr[8]))&&(Integer.parseInt(arr[8])<= expenMax)) ||((expenMin<=Integer.parseInt(arr[8]))&&(expenMax==-1))||((expenMin==-1)&&(Integer.parseInt(arr[8])<= expenMax))||((expenMin==-1)&&(expenMax==-1)))
							&& (((AidMin<=Integer.parseInt(arr[9]))&&(Integer.parseInt(arr[9])<= AidMax)) || ((AidMin<=Integer.parseInt(arr[9]))&&( AidMax==-1))||((AidMin==-1)&&(Integer.parseInt(arr[9])<= AidMax))||((AidMin==-1)&&(AidMax==-1)))
							&& (((apMin<=Integer.parseInt(arr[10]))&&(Integer.parseInt(arr[10])<= apMax)) || ((apMin<=Integer.parseInt(arr[10]))&&(apMax==-1))||((apMin==-1)&&(Integer.parseInt(arr[10])<= apMax))||((apMin==-1)&&(apMax==-1)))
							&& (((admMin<=Integer.parseInt(arr[11]))&&(Integer.parseInt(arr[11])<= admMax)) || ((admMin<=Integer.parseInt(arr[11]))&&(admMax==-1))||((admMin==-1)&&(Integer.parseInt(arr[11])<= admMax))||((admMin==-1)&&(admMax==-1)))
							&& (((enrMin<=Integer.parseInt(arr[12]))&&(Integer.parseInt(arr[12])<= enrMax)) || ((enrMin<=Integer.parseInt(arr[12]))&&(enrMax==-1))||((enrMin==-1)&&(Integer.parseInt(arr[12])<= enrMax))||((enrMin==-1)&&(enrMax==-1)))
							&& (((aScalMin<=Double.parseDouble(arr[13]))&&(Double.parseDouble(arr[13])<= aScalMax)) || ((aScalMin<=Double.parseDouble(arr[13]))&&(aScalMax==-1))||((aScalMin==-1)&&(Double.parseDouble(arr[13])<= aScalMax))||((aScalMin==-1)&&(aScalMax==-1)))
							&& (((socScalMin<=Double.parseDouble(arr[14]))&&(Integer.parseInt(arr[14])<= socScalMax)) || ((socScalMin<=Double.parseDouble(arr[14]))&&(socScalMax==-1))||((socScalMin==-1)&&(Integer.parseInt(arr[14])<= socScalMax))||((socScalMin==-1)&&(socScalMax==-1)))
							&& (((qScalMin<=Double.parseDouble(arr[15]))&&(Integer.parseInt(arr[15])<= qScalMax)) || ((qScalMin<=Double.parseDouble(arr[15]))&&(qScalMax==-1))||((qScalMin==-1)&&(Integer.parseInt(arr[15])<= qScalMax))||((qScalMin==-1)&&(Integer.parseInt(arr[15])<= qScalMax))||((qScalMin==-1)&&(qScalMax==-1)))
							){
			
						University u = new University(arr[0],arr[1],arr[2],arr[3],
	    						Integer.parseInt(arr[4]),Integer.parseInt(arr[5]),Integer.parseInt(arr[6]),Integer.parseInt(arr[7]),
	    						Integer.parseInt(arr[8]),Integer.parseInt(arr[9]),Integer.parseInt(arr[10]),Integer.parseInt(arr[11]),
	    						Integer.parseInt(arr[12]),Double.parseDouble(arr[13]),Double.parseDouble(arr[14]),Double.parseDouble(arr[15]));
						if(!resultT.contains(u))
							resultT.add(u); //if list does not have school then add to list
					}	
				}
			 
			 String[][] emphases = univLib.university_getNamesWithEmphases();
			 if(popMajor.length==0){
				 return resultT;
			 }
				for(String[] em : emphases){
					for(int i =0;i<resultT.size();i++){
					if(em[0].equals(resultT.get(i).getSchoolName())){
						for(int j =1; j<em.length; j++){
							for(String s:popMajor){
							 if(em[j].equals(s)){
								 if(!results.contains(resultT.get(i)))
										results.add(resultT.get(i)); 
							 }
							}
					}
					}
				}
			}
			
			return results; // returns the list of matching universities with its attributes
		}
	
}
