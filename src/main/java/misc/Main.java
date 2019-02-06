package misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static Integer numberOfGradesPerStudent = 10;

    public static List<Student> generateStudents(int numberOfStudentsToGenerate) {
        List<Student> students = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numberOfStudentsToGenerate; i++) {
            students.add(new Student("Name" + (i + 1), "Surname" + (i + 1), i + 1));

            for (int j = 0; j < numberOfGradesPerStudent; j++) {
                Integer randomGrade = rand.nextInt(5) + 1;
                students.get(i).addGrade(randomGrade);
            }
        }

        return students;
    }

    public static void main(String[] args) {

        List<Student> students = generateStudents(100);

        // all students names
        // this stream can be used only once
        Stream<Student> studentStream = students.stream();
        studentStream.forEach(s -> System.out.println(s.getFirstName()));

        // reverse sort (by id)
        students.stream()
                .sorted(Collections.reverseOrder())
                .forEach(s -> System.out.println(s.getLastName()));

        // students with specific first name
        students.stream()
                .filter(s -> s.getFirstName().matches("Name[45][3-8]"))
                .forEach(s -> System.out.println(s));

        // students with specific last name
        students.stream()
                .filter(s -> s.getLastName().endsWith("e4"))
                .forEach(s -> System.out.println(s));

        // total number of students
        long countStudentsViaStream = students.stream().count();
        assert countStudentsViaStream == students.size();

        // average grade higher or equal to 3.75
        // reverse sort (by id)
        students.stream()
                .filter(s -> s.getAverageGrade() >= 3.75)
                .sorted(Collections.reverseOrder())
                .forEach(s -> System.out.println(s + "\t" + s.getGrades()));

        // number of students with average grade higher or equal to 3.75
        long countStudentsHighGrade =
                students.stream()
                        .mapToDouble(s -> s.getAverageGrade())
                        .filter(g -> g >= 3.75)
                        .count();
        System.out.println("Number of students with high average grade: " + countStudentsHighGrade);

        // collection of students IDs with average grade higher or equal to 3.75
        // and specific name
        List<Integer> studentsHighGrade =
                students.stream()
                        .filter(s -> s.getAverageGrade() >= 3.75)
                        .filter(s -> s.getFirstName().matches(".*[4-9]$"))
                        .map(s -> s.getID())
                        .collect(Collectors.toList());

        System.out.println(studentsHighGrade);
    }

}
