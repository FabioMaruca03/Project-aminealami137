package com.marufeb.fiverr;

import ui.LibUIScanner;
import ui.UIAuxiliaryMethods;

import java.io.File;
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
    private class Student {
        private final int[] grades;
        private final String[] similarities;
        private final String name;
        private int grade = 0;

        public Student(String[] details) {
            String[] d0 = details[0].split("_");
            name = d0[0];
            grades = new int[d0[1].split(" ").length];
            for (int i = 0; i < grades.length; i++) {
                grades[i] = Integer.parseInt(d0[1].split(" ")[i]);
                grade+=grades[i];
            }
            grade/=grades.length;
            String[] d1 = details[1].split(";");
        }

        public int getGradesSum() {
            return grade;
        }

        public int getGradesSize() {
            return grades.length;
        }
    }

    private int getFinalGrade(Student student) {
        return student.getGradesSum()/student.getGradesSize();
    }

    private void printSimilarity() {
    }

    private void parse(Scanner s) {

    }

    public static void main(String[] args) {
        Parser p = new Parser();
        Scanner scanner = UIAuxiliaryMethods.askUserForInput().getScanner();
        p.parse(scanner);

    }

}
