package ascii;
import ascii.*;
import validation.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Ascii extends NewAscii{
    private static List<Map<String, String>> row;
    private static Map<String, String> column;
    static BufferedReader in;
    static BufferedWriter writer;
    private final int MIN = 32;
    private final int MAX = 126;
    
    public static void main(String args[]) {
        AsciiTable ascii = new AsciiTable();
        setTable();
        String[] options = {"Print table", "Edit Cell", "Search character", "Add Row/s", "Sort Row/s", "Reset Table", "Exit"};
        boolean repeat = true;
        do {
            String action = (String) JOptionPane.showInputDialog(null, "Choose an action to perform:", "OPTIONS",
                JOptionPane.PLAIN_MESSAGE, null, options, "Print table");
            if (action == "Print table") {
                ascii.displayTable();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat=false;
                }
            } 
            else if (action == "Edit Cell") {
                ascii.editCell();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat = false;
                }
            }
            else if (action == "Search character") {
                ascii.searchTable();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat = false;
                }
            }
            else if (action == "Add Row/s") {
                ascii.addRow();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat = false;
                }
            }
            else if (action == "Sort Row/s") {
                ascii.sortRows();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat = false;
                }
            }
            else if (action == "Reset Table") {
                new Ascii().resetTable();
                int n = JOptionPane.showConfirmDialog(null, "Would you like to perform another action?","Another Action", JOptionPane.YES_NO_OPTION);
                if (n == 1) {
                    repeat = false;
                }
            }
            else if (action == "Exit") {
                repeat = false;
            }
            else {
                JOptionPane.showMessageDialog(null, "No action chosen. \nProgram terminates now.", "Exit Program", JOptionPane.PLAIN_MESSAGE);
                repeat = false;
            }
        } while (repeat);
        
        System.exit(0);
    }
    
    public List<Map<String, String>> getRow() {
            return row;
    }
    
    public Map<String, String> getColumn() {
            return column;
    }
    
    public static void setTable() {
        row = new ArrayList<Map<String, String>>();
        try {
            in = new BufferedReader(new FileReader("ascii/AsciiTable.txt"));
            String line = "";
            while ((line = in.readLine()) != null) {
                String parts[] = line.split("¦¦");
                column = new LinkedHashMap<String, String>();
                for (int a = 0; a < parts.length; a++) {
                    String value[] = parts[a].split("—");
                    column.put(value[0], value[1]);
                }
                row.add(column);
            }
            
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException e) {
            }
        }
    }
    
    public void resetTable() {
        row = new ArrayList<Map<String, String>>();
        Random random = new Random();
        AsciiTable ascii = new AsciiTable();
        int rowNumber = 0;
        int columnNumber = 0;
        boolean rowError = false;
        boolean columnError = false;
        do{
            try{
                rowError = false;
                rowNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of row/s: ", "Enter Row"));
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid row number!");
                rowError = true;
                rowNumber = 1;
            }
            if (rowNumber == 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid row number!");
                rowError = true;
            }
        } while (rowError);
		
        do{
            try {
                columnError=false;
                columnNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of column/s: ", "Enter Column"));
            }
            catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Please enter a valid column number!");
                columnError=true;
                columnNumber=1;
            }
            if (columnNumber==0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid column number!");
                columnError=true;
            }
        } while (columnError);
        
        String key;
        String value;
        char tempChar;
        int randomChar;
        
        for (int rowln = 1; rowln <= rowNumber; rowln++) {
            column = new LinkedHashMap<String, String>();
	        for (int columnln = 1; columnln <= columnNumber; columnln++) {
	            //generate key
	            key = "";
		        for (int a = 1; a <= 3; a++) {
			        randomChar = random.nextInt(MAX - MIN + 1) + MIN;
			        if (a == 1) {
				        tempChar = (char) randomChar;
				        key = String.valueOf(tempChar);
			        }
			        else {
				        key += (char) randomChar;
			        }
		        }
		        
		        //generate value
	            value = "";
		        for (int a = 1; a <= 3; a++) {
			        randomChar = random.nextInt(MAX - MIN + 1) + MIN;
			        if (a == 1) {
				        tempChar = (char) randomChar;
				        value = String.valueOf(tempChar);
			        }
			        else {
				        value += (char) randomChar;
			        }
		        }
		        column.put(key, value);
	        }
	        row.add(column);
        }
        
        System.out.println("NEW TABLE:");
        writeFile(row);
        setTable();
        ascii.displayTable();
    }
    
    static void writeFile(List<Map<String, String>> row) {
        try{
            String line = "";
            writer = new BufferedWriter (new FileWriter("ascii/AsciiTable.txt"));
            Map<String, String> currentMap;
            for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                currentMap = row.get(rowIndex);
                int columnIndex = 0;
                for (Map.Entry<String, String> map : currentMap.entrySet()) {
                    if (columnIndex < (currentMap.size()-1)) {
                        line = (map.getKey() + "—" + map.getValue() + "¦¦");
                    }
                    else {
                        line = (map.getKey() + "—" + map.getValue());
                    }
                    writer.write(line);
                    columnIndex++;
                }
                writer.write("\n");
            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            try {
                writer.close();
            } 
            catch (Exception ex) {
            }
        }
    }
}
