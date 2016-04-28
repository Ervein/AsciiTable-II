package validation;

import ascii.*;
import validation.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class AsciiValidation implements Validation{
    Ascii ascii = new Ascii();
    private final int MIN = 32;
    private final int MAX = 126;
    
    public boolean checkRow(String tempRowIndex) {
        List<Map<String, String>> row = ascii.getRow();
        int rowIndex = 0;
    	
    	try {
    		rowIndex = Integer.parseInt(tempRowIndex);
    	}
    	catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Please enter a valid row number!");
	        return true;
	    }
	    
	    if (rowIndex < 1 || rowIndex > row.size()) {
	        JOptionPane.showMessageDialog(null, "Row " + rowIndex + " does not exist!");
	        return true;
	    }
        return false;
    }
    
    public boolean checkColumn(String tempColumnIndex) {
        Map<String, String> column = ascii.getColumn();
        int columnIndex = 0;
    	try {
    		 columnIndex = Integer.parseInt(tempColumnIndex);
    	}
    	catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Please enter a valid column number!");
	        return true;
	    }
	    
	    if (columnIndex < 1 || columnIndex > column.size()) {
	        JOptionPane.showMessageDialog(null, "Column " + columnIndex + " does not exist!");
	        return true;
	    }
        return false;
    }
    
    public boolean checkKey(String key, int rowIndexFromUser, int columnIndexFromUser) {
        List<Map<String, String>> row = ascii.getRow();
        Map<String, String> column = ascii.getColumn();
        if (key.length() != 3) {
	        JOptionPane.showMessageDialog(null,"Invalid number of KEY characters!");
	        return true;
	    }
	    
	    for (int a = 0; a < key.length(); a++) {
	        char[] checkCharacter = key.toCharArray();
	        int checkCharacterInt = (int)checkCharacter[a];
	        if (checkCharacterInt<MIN || checkCharacterInt>MAX) {
	            JOptionPane.showMessageDialog(null,"Invalid Key Character/s!");
	            return true;
	        }
	    }
	    
	    Map<String, String> newMap = new LinkedHashMap<>();
	    Map<String, String> currentMap;
	    String currentMapKey = "";
	    String currentMapValue = "";
        for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
            currentMap = row.get(rowIndex);
            int columnIndex = 1;
            for (Map.Entry<String, String> map : currentMap.entrySet()) {
                currentMapKey = (String) map.getKey();
                currentMapValue = (rowIndex) + "," + columnIndex;
                newMap.put(currentMapValue, currentMapKey);
                columnIndex++;
            }
        }

        for (Map.Entry<String, String> map : newMap.entrySet()) {
            currentMapKey = (String) map.getKey();
            currentMapValue = (String) map.getValue();
            if (key.equals(currentMapValue)) {
                String parts[] = currentMapKey.split(",");
                String strRowIndex = Integer.toString(rowIndexFromUser-1);
                String strColumnIndex = Integer.toString(columnIndexFromUser);
                if (strRowIndex.equals(parts[0])) {
                    if (strColumnIndex.equals(parts[1])) {
                        //retain key in same cell reference
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Key already exists!");
                        return true;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Key already exists!");
                    return true;
                }
            }
        }

        return false;
    }
    
    public boolean checkValue(String value) {
        if (value.length() != 3) {
	        JOptionPane.showMessageDialog(null,"Invalid number of VALUE characters!");
	        return true;
	    }
        
        for (int a = 0; a < value.length(); a++) {
	        char[] checkCharacter = value.toCharArray();
	        int checkCharacterInt = (int) checkCharacter[a];
	        if (checkCharacterInt < MIN || checkCharacterInt > MAX) {
	            JOptionPane.showMessageDialog(null,"Invalid Value Character/s!");
	            return true;
	        }
	    }
        return false;
    }
    
    public boolean checkSearchValue(String searchValue) {
        int checkCharacterInt = 0;
		char[] checkCharacter = new char[0];

        if(searchValue.length() < 1  ||  searchValue.length() > 3) {
	        JOptionPane.showMessageDialog(null,"Invalid number of characters!");
	        return true;
	    }
        for (int a = 0; a < searchValue.length(); a++) {
	        checkCharacter = searchValue.toCharArray();
	        checkCharacterInt = (int) checkCharacter[a];
	        if (checkCharacterInt < MIN || checkCharacterInt > MAX) {
	            JOptionPane.showMessageDialog(null, "Invalid ASCII Character!");
	            return true;
	        }
	    }
	    
        return false;
    }
    
    public boolean checkRowNumber(String tempRowNumber) {
    	int rowNumber = 0;
    	try {
    		 rowNumber = Integer.parseInt(tempRowNumber);
    	}
    	catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Input number only!");
	        return true;
	    }
	    
	    if (rowNumber < 1 ) {
	        JOptionPane.showMessageDialog(null, "Invalid Row Number!");
	        return true;
	    }
        return false;
    }
}
