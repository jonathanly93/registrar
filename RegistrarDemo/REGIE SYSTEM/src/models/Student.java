package models;

/**
 * Student object has a composition of a RegisteredUser object to provide the basic
 * information for hte user.
 *
 * Student object has a composition of a RestrictionList object to provide a list
 * of restriction which can prevent a student from registering for a course.
 *
 * Student object has a composition of a StudentCourseList object to provide a list of courses
 * the student is enrolled in.
 *
 * Student can only enroll in a maximum of 3 courses.
 */
public class Student{

    private int maxEnrolledCourse;
    private RestrictionList restrictionList;
    private StudentCourseList studentCourseList;
    private RegisteredUser registeredUser;


    /**
     * Constructor for the Student Class
     * @param restrictionList : a list of restriction which prevents enrollment
     * @param studentCourseList : a list of courses the student is enrolled in
     * @param registeredUser : the basic information of the student
     */
    public Student(RestrictionList restrictionList, StudentCourseList studentCourseList, RegisteredUser registeredUser){
        this.restrictionList = restrictionList;
        this.studentCourseList = studentCourseList;
        this.registeredUser = registeredUser;
        this.maxEnrolledCourse = 3;
    }

    public Student(){ this.maxEnrolledCourse = 3;}

    // Returns the maximum number of courses a student can take
    public int getMaxEnrolledCourse(){
        return maxEnrolledCourse;
    }

    // Sets the maximum number of course a student can take. The default is 3
    public void setMaxEnrolledCourse(int maxEnrolledCourse){this.maxEnrolledCourse = maxEnrolledCourse;}

    // Returns the restrictionList
    public RestrictionList getRestrictionList(){return restrictionList;}

    // Sets the restrictionList
    public void setRestrictionList(RestrictionList restrictionList){this.restrictionList = restrictionList;}

    // Returns the studentCourseList
    public StudentCourseList getStudentCourseList(){
        return studentCourseList;
    }

    // Sets the studentCourseList
    public void setStudentCourseList(StudentCourseList studentCourseList){this.studentCourseList = studentCourseList;}

    // Returns the registeredUser
    public RegisteredUser getRegisteredUser(){
        return registeredUser;
    }

    // Sets the registeredUser
    public void setRegisteredUser(RegisteredUser registeredUser){this.registeredUser = registeredUser;}

}
