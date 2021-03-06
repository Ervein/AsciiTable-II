package ascii;
import ascii.*;
import validation.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class AsciiTable extends Ascii{
	AsciiValidation val = new AsciiValidation();
	Ascii ascii = new Ascii();
    List<Map<String, String>> row = ascii.getRow();
    Map<String, String> column = ascii.getColumn();
    public void displayTable() {
        row = ascii.getRow();
        column = ascii.getColumn();
        String tempNumberOfRowDigits = new Integer(row.size()).toString();
        String tempNumberOfRowDigits2;
        int NumberOfRowDigits2;
        for (int NumberOfRowDigits = tempNumberOfRowDigits.length(); NumberOfRowDigits > 1; NumberOfRowDigits--) {
            System.out.print(" ");
        }
        for (int colNumber = 1; colNumber <= column.size(); colNumber++) {
            if (colNumber == 1) {
                System.out.print("       "+colNumber);
                colNumber++;
            }
            System.out.print("         "+colNumber);
        }
        
        System.out.print("\n\n");
        
        Map<String, String> currentMap;
        for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
            tempNumberOfRowDigits2 = new Integer(rowIndex+1).toString();
            NumberOfRowDigits2 = tempNumberOfRowDigits2.length();
            for (int NumberOfRowDigits = tempNumberOfRowDigits.length(); NumberOfRowDigits>0; NumberOfRowDigits--) {
                if (NumberOfRowDigits2 < NumberOfRowDigits) {
                    System.out.print(" ");
                }
            }
            System.out.print(rowIndex+1);
            
            currentMap = row.get(rowIndex);
            for (Map.Entry<String, String> map : currentMap.entrySet())
            {
                System.out.print("   " + map.getKey() + "—" + map.getValue());
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    void editCell() {
        row = ascii.getRow();
        column = ascii.getColumn();
		String tempRowIndex = "";
		String tempColumnIndex = "";
		int rowIndexFromUser = 0;
		int columnIndexFromUser = 0;
		int result;
		int result2;
		boolean repeat = false;
    	JTextField rowField = new JTextField(5);
    	JTextField columnField = new JTextField(5);
    	JPanel cellPanel = new JPanel();
    	cellPanel.add(new JLabel("Row:"));
    	cellPanel.add(rowField);
    	cellPanel.add(new JLabel("Column:"));
    	cellPanel.add(columnField);
    	
    	do{
	    	result = JOptionPane.showConfirmDialog(null, cellPanel, "Enter Row and Column Reference", JOptionPane.OK_CANCEL_OPTION);
	    	if (result == 0) {
		    	tempRowIndex = rowField.getText();
		    	tempColumnIndex = columnField.getText();
		    	repeat = false;
	    	}
	    	else {
	    		break;
	    	}
	    	
	    	if (val.checkRow(tempRowIndex) == true) {	
	    		repeat = true;
	    	}
	    	else if (val.checkColumn(tempColumnIndex) == true) {
	    		repeat = true;
	    	}
	    	else {
	    		repeat = false;
	    	}
    	} while (repeat);
    	
    	if (result != 0) {
    	    System.exit(0);
    	}
    	
    	String key = "";
		String value = "";
    	JTextField keyField = new JTextField(5);
    	JTextField valueField = new JTextField(5);
    	JPanel keyPanel=new JPanel();
    	keyPanel.add(new JLabel("New Key:"));
    	keyPanel.add(keyField);
    	keyPanel.add(new JLabel("New Value:"));
    	keyPanel.add(valueField);
    	do{
	    	result2 = JOptionPane.showConfirmDialog(null, keyPanel, "Enter Key and Value", JOptionPane.OK_CANCEL_OPTION);
	    	if (result2 == 0) {
		    	key = keyField.getText();
		    	value = valueField.getText();
		    	repeat = false;
	    	}
	    	else{
	    		 break;
	    	}
	    		    
	        rowIndexFromUser = Integer.parseInt(tempRowIndex);
		    columnIndexFromUser = Integer.parseInt(tempColumnIndex);
	    	if (val.checkKey(key, rowIndexFromUser, columnIndexFromUser) == true) {	
	    		repeat = true;
	    	}
	    	
	    	if (val.checkValue(value) == true) {	
	    		repeat = true;
	    	}
	    } while (repeat);

	    if (result2 != 0) {
    	    System.exit(0);
    	}
    	
        Map<String, String> currentMap;
        String originalMapKey = "";
	    String originalMapValue = "";
	    Map<String, String> newMap;
    	for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
    	    if (rowIndex == (rowIndexFromUser-1)) {
                currentMap = row.get(rowIndex);
                int columnIndex = 1;
                newMap = new LinkedHashMap<String, String>();
                for (Map.Entry<String, String> map : currentMap.entrySet()) {
                    if (columnIndex == (columnIndexFromUser)) {
                        originalMapKey = map.getKey();
                        originalMapValue = map.getValue();
                        newMap.put(key, value);
                    }
                    else {
                        newMap.put(map.getKey(), map.getValue());
                    }
                    row.set(rowIndex, newMap);
                    columnIndex++;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Key is modified from " + originalMapKey + " to " + key + 
            " while its Value is modified from " + originalMapValue + " to " + value);
        System.out.println("New TABLE:");
        writeFile(row);
        setTable();
        displayTable();
    }
    
    void searchTable() {
        row = ascii.getRow();
        column = ascii.getColumn();
        String searchValue = "";
        int tableAppearance = 0;
        int cellKeys = 0;
        int cellValues = 0;
        boolean repeat;
        do{
            repeat = false;
	    	searchValue = JOptionPane.showInputDialog(null, "Type one to three ASCII characters to search:", "Enter Character/s");
	    	try {
            	if (val.checkSearchValue(searchValue) == true) {	
            		repeat = true;
                }
            }
            catch (NullPointerException e) {
                System.exit(0);
            }
	    } while (repeat);
	    
	    Map<String, String> currentMap;
        String compareKey = "";
	    String compareValue = "";
	    int cellAppearance;
	    System.out.println("SEARCH RESULT:");
    	for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
            currentMap=row.get(rowIndex);
            int columnIndex = 1;
            
            for (Map.Entry<String, String> map : currentMap.entrySet()) {
                compareKey = map.getKey();
                compareValue = map.getValue();
                //if search character=1
                if (searchValue.length() == 1) {
                	cellAppearance = countAppearance1(compareKey, searchValue);
                	tableAppearance = tableAppearance + cellAppearance;
                	if (cellAppearance == 1 ) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					else if (cellAppearance == 2) {
					    System.out.println("Character "+searchValue+" appeared twice in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					else if (cellAppearance == 3) {
					    System.out.println("Character "+searchValue+" appeared thrice in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					
					cellAppearance = countAppearance1(compareValue, searchValue);
					tableAppearance = tableAppearance + cellAppearance;
                	if (cellAppearance == 1) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
					else if (cellAppearance == 2) {
					    System.out.println("Character "+searchValue+" appeared twice in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
					else if (cellAppearance == 3) {
					    System.out.println("Character "+searchValue+" appeared thrice in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
					
                }
                //if search character=2
                if (searchValue.length() == 2) {	
	                cellAppearance = countAppearance2(compareKey, searchValue);
					tableAppearance = tableAppearance + cellAppearance;
			        if (cellAppearance == 1) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					else if (cellAppearance == 2) {
					    System.out.println("Character "+searchValue+" appeared twice in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					
					cellAppearance = countAppearance2(compareValue, searchValue);
					tableAppearance = tableAppearance + cellAppearance;
			        if (cellAppearance == 1) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
					else if (cellAppearance == 2) {
					    System.out.println("Character "+searchValue+" appeared twice in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
                }
                if (searchValue.length() == 3) {
                	cellAppearance = 0;
                	if (compareKey.equals(searchValue)) {
						cellAppearance = 1;
						tableAppearance = tableAppearance + cellAppearance;
					}
					if (cellAppearance == 1) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [KEY]");
						cellKeys = cellKeys + 1;
					}
					if (compareValue.equals(searchValue)) {
						cellAppearance = 1;
						tableAppearance = tableAppearance + cellAppearance;
					}
					if (cellAppearance == 1) {
					    System.out.println("Character "+searchValue+" appeared once in cell "+(rowIndex+1)+"-"+columnIndex + " [VALUE]");
						cellValues = cellValues + 1;
					}
                }
                columnIndex++;
            }
        }
		if (tableAppearance > 0) {
			System.out.println("SUMMARY: Character/s "+searchValue+" appeared in the table "+tableAppearance+"X." );
			System.out.println("       : from "+cellKeys+" different KEY/S" );
			System.out.println("       : and from "+cellValues+" different VALUE/S" );
		}
		else {
			System.out.println("Character/s "+searchValue+" didn't appear in any cell of the table.");
		}
		System.out.println("\n");
    }
    
    int countAppearance1(String compare, String searchValue) {
    	String compareChar;
	    int cellAppearance = 0;
		
    	for (int a = 0; a <= 2; a++) {
			compareChar = compare.substring(a, a+1);
			if (compareChar.equals(searchValue)) {
				cellAppearance = cellAppearance + 1;
			}
		}
		return cellAppearance;
    }
    
    int countAppearance2(String compare, String searchValue) {
    	String compareChar;
	    int cellAppearance = 0;
		compareChar = compare.substring(0, 2);
		if (compareChar.equals(searchValue)) {
			cellAppearance = cellAppearance + 1;
		}
		compareChar = compare.substring(1, 3);
		if (compareChar.equals(searchValue)) {
			cellAppearance = cellAppearance + 1;
		}
		return cellAppearance;
    }
    
    void addRow() {
        row = ascii.getRow();
        column = ascii.getColumn();
    	boolean repeat =  false;
    	String tempRowNumber = "";
    	int rowNumber;
    	do{
	    	tempRowNumber = JOptionPane.showInputDialog(null, "Input number of rows to add:");
	    	try {
            	if (val.checkRowNumber(tempRowNumber) == true) {	
            		repeat = true;
            	}
            	else {
            		repeat = false;
            	}
            }
            catch (NullPointerException e) {
                System.exit(0);
            }
            
	    } while (repeat);
	    
	    rowNumber = Integer.parseInt(tempRowNumber);
	    Map<String, String> currentMap;
	    Map<String, String> newMap;
	    int currentRowIndex = 0;
	    int currentColumnIndex = 0;
	    
	    System.out.println("");
	    
    	for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
            newMap = new LinkedHashMap<String, String>();
            for (int columnIndex = 0; columnIndex < column.size(); columnIndex++) {
                if (columnIndex == 0) {
                    currentRowIndex = rowIndex + row.size() + 1;
                }
                else {
            	    currentRowIndex = rowIndex + row.size();
            	}
	    		currentColumnIndex = columnIndex + 1;
                String[] keyAndValue = generateKeyAndValue(currentRowIndex, currentColumnIndex);
                String key = keyAndValue[0];
                String value = keyAndValue[1];
                newMap.put(key, value);
                currentRowIndex = currentRowIndex - 1;
                if (columnIndex == 0) {
                    row.add(currentRowIndex, newMap);
                }
                else {
                    row.set(currentRowIndex, newMap);
                }
            }
            
        }
        System.out.println("WITH " + rowNumber + " NEWLY ADDED ROW/S");
        writeFile(row);
        setTable();
        displayTable();
    }
    
    String[] generateKeyAndValue(int currentRow, int currentColumn) {
        row = ascii.getRow();
        column = ascii.getColumn();
    	String key = "";
    	String value = "";
		int result;
		boolean repeat = false;
    	JTextField keyField = new JTextField(5);
    	JTextField valueField = new JTextField(5);
    	JPanel keyPanel = new JPanel();
    	keyPanel.add(new JLabel("Enter Key:"));
    	keyPanel.add(keyField);
    	keyPanel.add(new JLabel("Enter Value:"));
    	keyPanel.add(valueField);
    	do{
    		String tempCurrentRow = Integer.toString(currentRow);
    		String tempCurrentColumn = Integer.toString(currentColumn);
    		String message="Enter Key and Value for cell: " + tempCurrentRow + "-" + tempCurrentColumn;
	    	result = JOptionPane.showConfirmDialog(null, keyPanel, message, JOptionPane.OK_CANCEL_OPTION);
	    	if (result == 0) {
                key = keyField.getText();
                value = valueField.getText();
                repeat = false;
	    	}
	    	else {
	    		 break;
	    	}
	    		    
	    	if (val.checkKey(key, currentRow, currentColumn)  == true) {	
	    		repeat = true;
	    	}
	    	
	    	if (val.checkValue(value) == true) {
	    		repeat = true;
	    	}
	    } while (repeat);
		
	    if (result != 0) {
    	    System.exit(0);
    	}
    	String[] keyAndValue = {key, value};
    	return keyAndValue;
    }
    
    void sortRows() {
        row = ascii.getRow();
        column = ascii.getColumn();
        List<Set<String>> tempRow = new ArrayList<Set<String>>();
        Map<String, String> currentMap;
	    Map<String, String> newMap;
        Set<String> tempSet;
	    String[] options = {"Sort by Ascending", "Sort by Descending"};
	    
        String sortAction = (String) JOptionPane.showInputDialog(null, "Choose type of Sort:", "OPTIONS",
            JOptionPane.PLAIN_MESSAGE, null, options, "Sort table");
        if (sortAction == "Sort by Ascending") {
            for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                currentMap = row.get(rowIndex);
                tempSet = new LinkedHashSet<String>();
                for (Map.Entry<String, String> map : currentMap.entrySet()) {
                    String keyAndValue = map.getKey() + map.getValue();
                    tempSet.add(keyAndValue);
                }
                tempRow.add(tempSet);
            }
	        
        	for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                tempSet = tempRow.get(rowIndex);
                newMap = new TreeMap<String, String>();
                for (String keyAndValue : tempSet) {
                    String key = keyAndValue.substring(0, 3);
                    String value = keyAndValue.substring(3, 6);
                    newMap.put(key, value);
                }
                row.set(rowIndex, newMap);
            }
        }
        else if (sortAction == "Sort by Descending") {
	        for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                currentMap = row.get(rowIndex);
                tempSet = new TreeSet<String>();
                for (Map.Entry<String, String> map : currentMap.entrySet()) {
                    String keyAndValue = map.getKey() + map.getValue();
                    tempSet.add(keyAndValue);
                }
                tempRow.add(tempSet);
            }
	        
	        for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                List<String> tempList = new ArrayList<>(tempRow.get(rowIndex));
                newMap = new LinkedHashMap<String, String>();
                for (int setIndex = (tempList.size()-1); setIndex >= 0; setIndex--) {
                    String keyAndValue = tempList.get(setIndex);
                    String key = keyAndValue.substring(0, 3);
                    String value = keyAndValue.substring(3, 6);
                    newMap.put(key, value);
                }
                row.set(rowIndex, newMap);
            }
	    }
	    else {
            JOptionPane.showMessageDialog(null, "No action chosen. \nProgram terminates now.", "Exit Program", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        
        
        System.out.println("SORTED ROW/S:");
        writeFile(row);
        setTable();
        displayTable();
    }
}

