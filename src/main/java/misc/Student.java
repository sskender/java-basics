package misc;

import java.util.*;


/**
 *
 */
public class Student implements Comparable<Student>, Iterable<Integer> {

    // keep track of total number of students
    private static Integer totalNumberOfStudents = 0;

    private final String firstName;
    private final String lastName;
    private final Integer ID;

    // list of students grades
    private List<Integer> grades;

    public Student(String firstName, String lastName, Integer ID) {
        totalNumberOfStudents++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.grades = new ArrayList<>();
    }

    public static Integer getTotalNumberOfStudents() {
        return totalNumberOfStudents;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getID() {
        return ID;
    }

    public void addGrade(Integer newGrade) {
        grades.add(newGrade);
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public Double getAverageGrade() {
        Double averageGrade =
                grades.stream()
                        .mapToDouble(g -> g)
                        .average()
                        .getAsDouble();

        return averageGrade;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student: ");
        sb
                .append(lastName)
                .append(", ")
                .append(firstName)
                .append(": ")
                .append(ID);

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public boolean equals(Object obj) {
        // two students are equal if their id is equal

        if (!(obj instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) obj;
        return this.ID.equals(otherStudent.getID());
    }

    @Override
    public int compareTo(Student otherStudent) {
        // students are compared by ID
        return this.ID.compareTo(otherStudent.getID());
    }

    @Override
    public Iterator<Integer> iterator() {
        // iterate through student grades
        return new StudentIterator();
    }

    private class StudentIterator implements Iterator<Integer> {

        int index = 0;

        public StudentIterator() {
            // TODO
        }

        @Override
        public boolean hasNext() {
            return index < grades.size();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return grades.get(index++);
        }

    }

}
