package sample;

public class StudentRecord {
    private String studentID;
    private float assignments;
    private float midterm;
    private float finalExam;
    private float finalMark;
    public static void main(String[] args){

    }
    public StudentRecord(String sId,float assign,float midterm,float finalExam){
        studentID=sId;
        assignments=assign;
        this.midterm=midterm;
        this.finalExam=finalExam;
        this.finalMark=this.assignments*0.2f+this.midterm*0.3f+this.finalExam*0.5f;
    }

    public float getAssignments() {
        return assignments;
    }

    public float getFinalExam() {
        return finalExam;
    }

    public float getFinalMark() {
        return finalMark;
    }

    public float getMidterm() {
        return midterm;
    }

    public String getStudentID() {
        return studentID;
    }
    public String getStudentGrade(){
        if (finalMark>=80&&finalMark<=100){
            return "A";
        }else if (finalMark>=70){
            return "B";
        }else if (finalMark>=60){
            return "c";
        }else if (finalMark>=50){
            return "D";
        }else{
            return "F";
        }
    }
    public String toString(){
        return studentID+","+Float.toString(assignments)+
                            ","+Float.toString(midterm)+
                            ","+Float.toString(finalExam)+
                            ","+Float.toString(finalMark)+
                            ","+getStudentGrade();

    }
}
