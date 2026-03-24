
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Student{
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }
    @Override
    public String toString(){
        return name + " - " + score;
    }
    
}



public class StudentRecordProcessor {
    private final List<Student> students = new ArrayList<>();
    private double averageScore;
    private Student highestStudent;
    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
 try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        System.out.println("Invalid data: " + line);
                        continue;
                    }

                    String name = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    validateScore(score);

                    students.add(new Student(name, score));
                    System.out.println("Valid line: " + line);

                } 
                catch (NumberFormatException | InvalidScoreException e) {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        // TODO: реализуйте чтение файла здесь
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь

        if (students.isEmpty()) {
        System.out.println("No valid student data to process.");
        return;
        }
        double sum = 0;
        highestStudent = students.get(0);

        for (Student s : students) {
            sum += s.getScore();

            if (s.getScore() > highestStudent.getScore()) {
                highestStudent = s;
            }
        }

        averageScore = sum / students.size();

        students.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

        System.out.println("Average: " + averageScore);
        System.out.println("Highest: " + highestStudent);
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try (BufferedWriter br = new BufferedWriter(new FileWriter("report.txt"))){
            br.write("Average: " + averageScore);
            br.newLine();
            br.write("Highest: " + highestStudent);
            br.newLine();
            br.newLine();

            br.write("Sorted students:");
            br.newLine();

            for (Student s : students) {
                br.write(s.toString());
                br.newLine();
            }
        }
        catch(IOException e){
            System.out.println("Error writing file");
        }
    }
    private void validateScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("Score must be between 0 and 100: " + score);
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}


class InvalidScoreException extends Exception{
    public InvalidScoreException(String msg){
        super(msg);
    }
}

