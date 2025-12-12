// Ilya Zeldner
package entities;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private String className;
    private List<Student> students;

    // Empty constructor for Kryo
    public Classroom() {
        this.students = new ArrayList<>();
    }

    public Classroom(String className) {
        this.className = className;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student s) {
        this.students.add(s);
    }
    
    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Classroom: " + className + " (Contains " + students.size() + " students)";
    }
}