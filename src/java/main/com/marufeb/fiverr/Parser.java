package com.marufeb.fiverr;

import ui.UIAuxiliaryMethods;

import java.util.Scanner;

/* For the end of year administration of Programming for History of Arts students you are
 * to write a program that has 2 functions:
 *    1. calculate a final grade
 *    2. print a small graph of similarity scores and, if applicable,
 *       list the students under investigation The input is structured as follows:
 *           Piet van Gogh_5 6 7 4 5 6 5=20=22=10=2=0=0=1=0=1;
 *           Vincent Appel, Johannes Mondriaan Karel van Rijn_7 8 6 6 2=30=15=8=4=3=2=0=0=0;
 * The first line should be interpreted as follows:
 *   <name of the student><underscore><one or more grades separated by spaces>
 * You have to calculate the final grade of the student. All grades have the same weight.
 * The final grade is rounded as follows:
 * • a grade that is >= 5.5 AND < 6 should be noted as a "6-"
 * • otherwise a grade will be rounded to the nearest half
 * The second line should be interpreted as follows:
 *    <10 numbers separated by ’=’>;<zero or more names separated by ’,’>
 *    The first 10 numbers are the similarity scores.
 * These scores represent the number of programs matching a certain percentage of
 * the current program in steps of 10%. This means the first numbers indicates the
 * matches from 1%-10% and the last number indicates the matches from 91%-100%.
 * Since this is not very readable, the professor would like a simple graph according to these rules:
 * • if there are zero matches, display an underscore: _
 * • if there are less than 20 matches, display a minus sign: −
 * • if there are 20 or more matches, display a caret: ∧ The names of the students after
 * the semicolon are the names of the students with matches in the final 3 categories.
 * The names of these students should be printed under the graph.
 * If there are no matches, the program should print "No matches found".
 * The output for the aforementioned input should be:
 */
public class Parser {
    private static class Student {
        private final int[] grades; // Array storing grades (type int) - final, so cannot change
        private int gradesSize; // The logical grades[] size
        private final String[] similarities; // Array storing similarities (type String) - final, so cannot change
        private int similaritySize; // The logical similarities[] size
        private final int[] similaritiesScore; // Array storing similaritiesScores (type int) - final, so cannot change
        private int similarityScoreSize; // The logical similaritiesScore[] size
        private final String name; // The student's name - final, so cannot change
        private float grade = 0; // The grade sum of the student

        public Student(String[] details) {
            Scanner scanner = new Scanner(details[0]).useDelimiter("_"); // Create a new scanner and set the delimiter
            name = scanner.next(); // Name is the first string
            Scanner gradesScanner = new Scanner(scanner.next()).useDelimiter(" "); // Create a new scanner from the old one
            grades = new int[100]; // Initialize the grades[] with a physical size of 100
            for (gradesSize = 0; gradesScanner.hasNext(); gradesSize++) { // For loop -> described like anywhere
                grades[gradesSize] = Integer.parseInt(gradesScanner.next()); // set the gradesSize cell of the grades vector to the parsed integer from the next string
                grade+=grades[gradesSize]; // Sum the new vote to the old one
            }
            grade/=gradesSize; // Calculate the media
            scanner.close(); // Close the first scanner
            gradesScanner.close(); // Close the second scanner

            scanner = new Scanner(details[1]).useDelimiter(";"); // Create a new scanner and set the delimiter

            Scanner scoresScanner = new Scanner(scanner.next()).useDelimiter("="); // Create a new scanner from the old one and also set the delimiter
            similaritiesScore = new int[10]; // Initialize the similaritiesScore[] with a physical size of 10
            for (similarityScoreSize = 0; similarityScoreSize < 10 && scoresScanner.hasNext(); similarityScoreSize++) { // Another for loop
                similaritiesScore[similarityScoreSize] = Integer.parseInt(scoresScanner.next()); // set the similarityScoreSize cell of the similaritiesScore vector to the parsed integer from the next string
            }
            scoresScanner.close(); // Close the second scanner


            similarities = new String[100]; // Initialize the similarity[] with a physical size of 100
            similaritySize = 0; // Initialize the similaritySize to 0

            if (scanner.hasNext()) { // If there's any similarity
                Scanner simScanner = new Scanner(scanner.next()).useDelimiter(","); // Create a new scanner from the old one

                for (similaritySize = 0; simScanner.hasNext(); similaritySize++) { // Another for loop
                    similarities[similaritySize] = simScanner.next(); // set the similaritySize cell of the similarities vector to the parsed integer from the next string
                }

                simScanner.close(); // Third scanner closed
            } else {
                similaritySize++; // Increase the size
                similarities[0] = null; // Set the first cell as null (when printing the null values are "No match found")
            }
            scanner.close(); // Close scanner
        }

        public String getTransformedSimilaritiesScores() {
            String result = "";
            for (int j : similaritiesScore) { // For each -> int student = similaritiesScore[i] when i increases by one every loop done
                // The "graph" pattern
                if (j == 0) result += "_"; // String concatenation
                else if (j >= 20) result += "^"; // String concatenation
                else result += "-"; // String concatenation
            }
            return result;
        }

        public String getFinalGrade() {
            if (grade<6 && grade >= 5.5) // If 5.5 <= grade < 6.0
                return 6 + "-"; // return "6-"
            else {
                boolean decimal = grade % 1 != 0.5; // Has a decimal part != 0.5
                return String.valueOf(decimal ? Math.round(grade)+".0" : grade); // Basically round to the next integer and add ".0" to mach the output requirement
            }
        }
    }

    private final Student[] students = new Student[100]; // 100 IS THE PHYSICAL SIZE, NOT HE LOGICAL (see below)
    private int size = 0; // THIS IS THE LOGICAL SIZE, NOT THE PHYSICAL SIZE

    private void parse(Scanner s) {
        String[] details = new String[2]; // Array of length two
        String temp;
        while (s.hasNextLine()) { // While has next line
            temp = s.nextLine(); // Temp string set
            details[0] = temp; // Setting details[0] -> First line
            if (!temp.isBlank() && s.hasNextLine()) { // If this temp string isn't blank ("" || " ") and there's another line to read
                temp = s.nextLine(); // Temp equals the new line
                if (!temp.isBlank()) { // If the new line isn't blank ("" || " ")
                    details[1] = temp; // details[1] -> Second line
                    students[size] = new Student(details); // Add the new student to the students array
                    size++; // Increment the size counter
                }
            }
        }
    }

    public static void main(String[] args) {
        Parser p = new Parser(); // new instance of Parser class
        Scanner scanner = UIAuxiliaryMethods.askUserForInput().getScanner(); // Get scanner from this library
        p.parse(scanner); // Parses the file
        System.out.println(p.getOutput());
    }

    private String getOutput() {
        String result = ""; // Initialization -> Needed to make available the string concatenation
        for (Student student : students) { // For each cycle -> Student student = students[i] when i increases by one every loop done
            if (student == null)
                continue; // Skip the current loop cycle
            result += student.name; // Appends with string concatenation the current student name
            result += " has an average of ";
            result += student.getFinalGrade(); // Appends with string concatenation the final grade (grade's sum / grade's size)
            result += "\n"; // Appends with string concatenation the "new line" character
            result += "\t"; // Appends with string concatenation the "tab" character
            result += student.getTransformedSimilaritiesScores(); // Appends with string concatenation the similarity graph
            result += "\n"; // Appends with string concatenation the "new line" character
            if (student.similaritySize != 0) // If similarity array isn't empty (else throws NullPointerException)
                for (int i = 0; i < student.similaritySize; i++) { // For loop -> i increases while i is less than similarityScoreSize
                    result += "\t";
                    result += student.similarities[i] == null ? "No matches found" : student.similarities[i]; // If null means that no matches are found, else print these matches
                    result += "\n";
                }
        }
        return result; // return the final result
    }

}
