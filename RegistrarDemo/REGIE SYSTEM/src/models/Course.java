package models;

// Courses available for student to take
public class Course {

    private String courseName;
    private String department;
    private int courseId;
    private int credit;

    /**
     * @param courseName : The name of the course
     * @param department : The department the course belongs to
     * @param courseId : The course ID:
     * @param credit : The amout of Credit the course offers
     */
    public Course(String courseName, String department, int courseId, int credit){
        this.courseName = courseName;
        this.department = department;
        this.courseId = courseId;
        this.credit = credit;
    }

    // Returns the courseName
    public String getCourseName(){
        return courseName;
    }

    // Sets the courseName
    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    // Returns the department for the course
    public String getDepartment(){
        return department;
    }

    // Sets the department for the course
    public void setDepartment(String department){
        this.department = department;
    }

    // Returns the couresId for the course
    public int getId(){
        return courseId;
    }

    // Sets the couresId for the course
    public void setId(int courseId){
        this.courseId = courseId;
    }
    // Returns the credit for the course
    public int getCredit(){
        return credit;
    }

    // Sets the credit for the course
    public void setCredit(int credit){
        this.credit = credit;
    }
}

