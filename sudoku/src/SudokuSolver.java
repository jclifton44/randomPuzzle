import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class SudokuCell {
	public int rowNumber;
	public int colNumber;
	public int value;
	public Set<Integer> possibleValues = new HashSet<Integer>();
	public SudokuCell(int value, int row, int col) {
		this.rowNumber = row;
		this.colNumber = col;
		this.value = value;
		possibleValues.add(1);
		possibleValues.add(2);
		possibleValues.add(3);
		possibleValues.add(4);
		possibleValues.add(5);
		possibleValues.add(6);
		possibleValues.add(7);
		possibleValues.add(8);
		possibleValues.add(9);
	}
		
}

class SudokuPuzzle {
	public SudokuCell[][] cells = new SudokuCell[9][];
	public SudokuCell[][] originalConfig = new SudokuCell[9][];
	public int[][] originalConfigOfIntegers = new int[9][];
	ArrayList<String> incompleteCoords = new ArrayList<String>();
	int numOfIncompleteCoords = 0;
	public SudokuPuzzle(int[][] matrix) {
		for(int i = 0; i < 9; i++) {
			originalConfigOfIntegers[i] = new int[9];
			cells[i] = new SudokuCell[9];
			originalConfig[i] = new SudokuCell[9];
		}
		initializeCells(matrix);	
		
	}
	public void convertIntegerMatrixToCells () {
		for(int i = 0; i < 81; i++) {
			cells[i / 9][i % 9].value = originalConfigOfIntegers[i / 9][i % 9];
		}
	}
	public SudokuCell[][] getOriginal(){
		SudokuCell[][] newCells = new SudokuCell[9][];
		for(int i = 0; i < 9; i++) {
			newCells[i] = new SudokuCell[9];
		}
		for(int i = 0; i < 81; i++) {
			newCells[i / 9][i % 9] = originalConfig[i / 9][i % 9];
		}
		return newCells;

	}
	public static int[][] readInput( String arg ) {
	    String [] args = arg.split("\\s");
	    return readInput( args );
	  }

	public static int[][] readInput( String[] args ) {
		int[][] result = new int[9][];
		for ( int k = 0 ; k < 9; k++ ) {
			result[k] = new int[9];
	    }
	    for ( int k = 0 ; k < args.length; k++ ) {
	    	int val = new Integer( args[k] );
	    	// format: 634 -> in row 6, col 4 value is 4
	    	result[val / 100][(val % 100) / 10] = (val % 10);
	    }
	    	return result;
	}
	public int[][] convertToMatrix() {
		int N = 9;
		int[][] result = new int[N][];
	    for ( int k = 0 ; k < N; k++ ) {
	    	result[k] = new int[N];
	    }
	    for( int i = 0; i < 9; i++) {
	    	for( int j = 0; j < 9; j++) {
	    		result[i][j] = this.cells[i][j].value;
	    	}
	    }
	    return result;
	}
	static String matrixToString(int[][] matrix) {
	     String result = "";
	     for ( int i  = 0 ; i < matrix.length; i++ ) {
	       for ( int j  = 0 ; j < matrix[0].length; j++ ) {
	         result = result + i + j + matrix[i][j] + " ";
	       }
	     } 
	     return result;
	}
	static int findBlockNum(int row, int col) {
		if(row < 3) {
			if(col < 3) {
				return 1;
			} else if (col < 6) {
				return 2;
			} else {
				return 3;
			}
				
		} else if( row < 6) {
			if(col < 3) {
				return 4;
			} else if (col < 6) {
				return 5;
			} else {
				return 6;
			}	
		} else {
			if(col < 3) {
				return 7;
			} else if (col < 6) {
				return 8;
			} else {
				return 9;
			}
		}
	}
	public void initializeCells(int[][] matrix) {
		for(int i = 0; i < 81; i++) {
			originalConfigOfIntegers[i / 9][i % 9] = matrix[(i / 9)][i % 9];
			cells[((int)i / 9)][i % 9] = new SudokuCell(matrix[((int)(i / 9)) % 9][i % 9], ((int)(i / 9)) % 9, ((int)(i / 9)));
			originalConfig[((int)i / 9)][i % 9] = new SudokuCell(matrix[((int)(i / 9)) % 9][i % 9], ((int)(i / 9)) % 9, ((int)(i / 9)));
		}
		
	}
	public void updateCell (int row, int col) {
		if(this.cells[row][col].value != 0) {
			cells[row][col].possibleValues.clear();
			return;
		}
		HashSet<Integer> rowElements = new HashSet<Integer>();
		HashSet<Integer> colElements = new HashSet<Integer>();
		HashSet<Integer> blockElements = new HashSet<Integer>();
		
		
		for(int i = 0; i < 9; i++) {
			if(this.cells[row][i].value != 0) {
				rowElements.add(this.cells[row][i].value);
				
			}
		}
		for(int i = 0; i < 9; i++) {
			if(this.cells[i][col].value != 0) {
				colElements.add(this.cells[i][col].value);
			}
		}
		ArrayList<Integer> blockMembers = getBlockMembers(findBlockNum(row,col));
		for(Integer cellID: blockMembers) {
			if(this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].value != 0) {
				blockElements.add(this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].value);
			}
		}
		cells[row][col].possibleValues.removeAll(rowElements);
		cells[row][col].possibleValues.removeAll(colElements);
		cells[row][col].possibleValues.removeAll(blockElements);
		if(cells[row][col].possibleValues.size() == 1 ) {
			ArrayList<Integer> temp = new ArrayList<Integer>(cells[row][col].possibleValues);
			cells[row][col].value = (temp.get(0));
		}
		
	}
	static ArrayList<Integer> getBlockMembers(int blockNum) {
		ArrayList<Integer> blockList = new ArrayList();
		blockNum--;
		blockList.add(1 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(2 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(3 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(10 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(11 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(12 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(19 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(20 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		blockList.add(21 + (blockNum % 3) * 3 + ((int)blockNum / 3) * 27);
		return blockList;
	}
	public boolean updateBlock(int blockNum) {
		ArrayList<Integer> blockList = new ArrayList();
		ArrayList<Integer> numsToUpdate = new ArrayList();
		ArrayList<Integer> possibleValuesForBlock = new ArrayList();
		blockList = getBlockMembers(blockNum);
		boolean updatedValue = false;
		for(Integer cellID: blockList) {
			if(cellID == 1) {
				int a = 0;
			}
			if(this.cells[(int)((cellID - 1) / 9)][(int)((cellID - 1) % 9)].value == 0) {
				possibleValuesForBlock.addAll(this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].possibleValues);
			}
		}
		for(int i = 1; i < 10; i++) {
			if(Collections.frequency(possibleValuesForBlock, (Integer) i) == 1) {
				numsToUpdate.add(i);
			}
		}
		for(Integer num: numsToUpdate){
			for(Integer cellID: blockList) {
				if(this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].possibleValues.contains(num) && this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].value == 0) {
					this.cells[(int)((cellID - 1) / 9)][((cellID - 1) % 9)].value = num;
					updatedValue = true;
				}	
			}
		}
		return updatedValue;
	}
	public boolean updateRow(int row) {
		ArrayList<Integer> rowList = new ArrayList();
		ArrayList<Integer> numsToUpdate = new ArrayList();
		ArrayList<Integer> possibleValuesForRow = new ArrayList();	
		boolean updatedValue = false;
		for(int i = 0; i < 9; i++) {
			rowList.add(i);
		}
		for(Integer colID: rowList) {

			if(this.cells[row][colID].value == 0) {
				possibleValuesForRow.addAll(this.cells[row][colID].possibleValues);
			}
		}
		for(int i = 1; i < 10; i++) {
			if(Collections.frequency(possibleValuesForRow, (Integer) i) == 1) {
				numsToUpdate.add(i);
			}
		}
		for(Integer num: numsToUpdate){
			for(Integer colID: rowList) {
				if(this.cells[row][colID].possibleValues.contains(num)) {
					this.cells[row][colID].value = num;
					updatedValue = true;
				}	
			}
		}
		return updatedValue;
	}
	public boolean updateCol(int col) {
		ArrayList<Integer> colList = new ArrayList();
		ArrayList<Integer> numsToUpdate = new ArrayList();
		ArrayList<Integer> possibleValuesForCol = new ArrayList();	
		boolean updatedValue = false;
		for(int i = 0; i < 9; i++) {
			colList.add(i);
		}
		for(Integer rowID: colList) {

			if(this.cells[rowID][col].value == 0) {
				possibleValuesForCol.addAll(this.cells[rowID][col].possibleValues);
			}
		}
		for(int i = 1; i < 10; i++) {
			if(Collections.frequency(possibleValuesForCol, (Integer) i) == 1) {
				numsToUpdate.add(i);
			}
		}
		for(Integer num: numsToUpdate){
			for(Integer rowID: colList) {
				if(this.cells[rowID][col].possibleValues.contains(num)) {
					this.cells[rowID][col].value = num;
					updatedValue = true;
				}	
			}
		}
		return updatedValue;
	}
	ArrayList<String> getIncompleteCoords() {
		ArrayList<String> iCoords = new ArrayList<String>();
		for(int i = 0; i < 81; i++) {
			if ( this.cells[i / 9][i % 9].value == 0) {
				iCoords.add(i + "");
			}
		}	
		return iCoords;
	}
	public void updatePossibleValues() {
		incompleteCoords.clear();
		for(int i = 0; i < 81; i++) {
			incompleteCoords.add(i + "");
		} 
		int counter = 0;
		boolean solvable = true;
		boolean unsolved = true;
		boolean updatingPossibleValues = true;
		boolean restart = false;
		while(solvable && unsolved && updatingPossibleValues) { 
			//SudokuSolver.print("",this.convertToMatrix());
			  restart = false;
			  int coords = Integer.parseInt(incompleteCoords.get(counter));
			  int row = (int)(coords / 9);
			  int col = (int)(coords % 9); 
			  if(this.cells[row][col].value == 0) {
				  this.updateCell(row,col);
				  if(this.cells[row][col].value != 0) {
					  incompleteCoords.remove("" + coords);
					  restart = true;
				  }
			  } else {
				  this.cells[row][col].possibleValues.clear();
				  incompleteCoords.remove("" + coords);
				  restart = true;
			  }
		
			  if(incompleteCoords.size() == 0) {
				  unsolved = false;
			  }
			  if(incompleteCoords.size() == 0 || counter == incompleteCoords.size() - 1) {
				  updatingPossibleValues = false;
				  
			  }
			  if(restart) {
				  counter = 0;
			  } else {
				  counter = (counter + 1);
			  }
		}
	}
	public boolean fillRandomCell() {
		boolean cellFilled = false;
		boolean puzzleComplete = true;
		boolean puzzleSolvable = true;
		Random ran = new Random();
		ArrayList<String> IC = getIncompleteCoords();
		for( int i = 0; i < 81; i++)  {
			if(cells[i /9][i % 9].value == 0) {
				puzzleComplete = false;
				break;
			}
		}
		while(!cellFilled && puzzleSolvable && !puzzleComplete) {
			//SudokuSolver.print("",this.convertToMatrix());
			int randCoordinateIndex = (new Random()).nextInt(IC.size());
			int randomRow = Integer.parseInt(IC.get(randCoordinateIndex)) / 9;
			int randomCol = Integer.parseInt(IC.get(randCoordinateIndex)) % 9;
			IC.remove(randCoordinateIndex);
			if(this.cells[randomRow][randomCol].possibleValues.size() >= 1 && this.cells[randomRow][randomCol].value == 0) {
				ArrayList<Integer> temp = new ArrayList<Integer>(this.cells[randomRow][randomCol].possibleValues);
				this.cells[randomRow][randomCol].value = temp.get(0);
				cellFilled = true;
			}
			if(IC.size() == 0) {
				puzzleSolvable = false;
			}
			
		}
		if(cellFilled) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isSolution() {
		for(int i = 0; i < 81; i++) {
			if (cells[i /9][i % 9].value == 0) {
				return false;
			}
		}
		return true;
	}
	/*public String solveRecursively() {
		
	}*/
	//****************************************************
	//Adapted from http://www.colloquial.com/games/sudoku/java_sudoku.html
	//
	//
	static boolean solve(int i, int j, int[][] cells) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0)  // skip filled cells
            return solve(i+1,j,cells);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i,j,val,cells)) {
                cells[i][j] = val;
                if (solve(i+1,j,cells))
                    return true;
            }
        }
        cells[i][j] = 0; // reset on backtrack
        return false;
    }

    static boolean legal(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k)  // row
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k) // col
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3)*3;
        int boxColOffset = (j / 3)*3;
        for (int k = 0; k < 3; ++k) // box
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset+k][boxColOffset+m])
                    return false;

        return true; // no violations, so it's legal
    }
    //**********************************************
	public String solveThisBitch() {
		boolean attemptingToSolve = true;
		boolean updatedByBlock = false;
		boolean updatedByRow = false;
		boolean updatedByCol = false;
		this.updatePossibleValues();
		for( int i = 0; i < 25; i++) {
			while(attemptingToSolve) {
				 updatedByBlock = false;
				 updatedByRow = false;
				 updatedByCol = false;
				//SudokuSolver.print("",this.convertToMatrix());
				 for(int m = 0; m< 9 ; m++)  {
					if(this.updateBlock(m + 1)){
						updatedByBlock = true;
							this.updatePossibleValues();
						}
				 	}
				//SudokuSolver.print("",this.convertToMatrix());
					for(int m = 0; m< 9 ; m++)  {
						if(this.updateRow(m)) {
							updatedByRow = true;
							this.updatePossibleValues();
						}
					}
				//SudokuSolver.print("",this.convertToMatrix());
					for(int m = 0; m< 9 ; m++)  {
						if(this.updateCol(m)) {
							updatedByCol = true;
							this.updatePossibleValues();
						}
					}	
					if(updatedByBlock == false && updatedByRow == false && updatedByCol == false) {
						if(fillRandomCell()) {
							this.updatePossibleValues();
						} else {
							attemptingToSolve = false;
						}
					}
				}
				
				if(isSolution()) {
					break;
				} else {
					attemptingToSolve = true;
					this.cells = getOriginal();
				}
		}
		if( isSolution() ) {
			return this.matrixToString(this.convertToMatrix());
		} else {
			if(solve(0,0,originalConfigOfIntegers)) {
				return matrixToString(originalConfigOfIntegers);
			}
			else {
				return "UNSOLVABLE";
			}
		}
	}
}

public class SudokuSolver {

   // "Near worst case" example from
   // http://en.wikipedia.org/wiki/Sudoku_algorithms
 /* public static final String testCaseString =
                "153 " + "178 " + "185 " +
                 "221 " + "242 " +
                  "335 " + "357 " +
                 "424 " + "461 " +
                 "519 " +
                 "605 " + "677 " + "683 " +
                 "722 " + "741 " +
                 "844 " + "889 ";
*/

	  //public static String testCaseString = "111 222 333 444 555 666 777 888 000 ";
	public static final String testCaseString = "023 104 148 173 186 228 261 314 346 377 383 439 552 585 624 647 676 688 706 807 836 865";
  public static final int N = 9;

  public static void main(String[] args) {
    String result = solve( testCaseString );
    boolean correct = isLegalSolution( result, testCaseString );
    System.out.println("Your solver ouput is " + (correct ? "wrong" : "right"));
  }

  public static String solve( String s ) {
    //TODOBEGIN(EE422C)
	  int [][] puzzle = SudokuPuzzle.readInput(s); 
	  
	  SudokuPuzzle myPuz = new SudokuPuzzle(puzzle);
	  return myPuz.solveThisBitch();
	  
    //TODOEND(EE422C)
  }

    //TODOBEGIN(EE422C)

    //TODOEND(EE422C)

  public static boolean isLegalSolution( String solution, String initialConfig ) {

    int [][] solutionmatrix = readInput( solution );
    int [][] initialConfigMatrix = readInput( initialConfig );

    if ( !isValid (solutionmatrix) ) {
      return false;
    }

    // check that it's fully filled in
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if ( solutionmatrix[i][j] == 0
                 || solutionmatrix[i][j] < 0
                 || solutionmatrix[i][j] > 9 ) {
          return false;
        }
        // check that it matches with initialConfigMatrix
        if ( initialConfigMatrix[i][j] != 0  &&
             initialConfigMatrix[i][j] != solutionmatrix[i][j] ) {
           return false;
         }
      }
    }
    return true;
  }

  // Check if a partially filled matrix has any conflicts
  public static boolean isValid(int[][] matrix) {
    // Row constraints
    for (int i = 0; i < N; ++i) {
      boolean[] present = new boolean[N + 1];
      for (int j = 0; j < N; ++j) {
        if (matrix[i][j] != 0 && present[matrix[i][j]]) {
          return false;
        } else {
          present[matrix[i][j]] = true;
        }
      }
    }

    // Column constraints
    for (int j = 0; j < N; ++j) {
      boolean[] present = new boolean[N + 1];
      for (int i = 0; i < N; ++i) {
        if (matrix[i][j] != 0 && present[matrix[i][j]]) {
          return false;
        } else {
          present[matrix[i][j]] = true;
        }
      }
    }

    // Region constraints
    for (int I = 0; I < 3; ++I) {
      for (int J = 0; J < 3; ++J) {
        boolean[] present = new boolean[N + 1];
        for (int i = 0; i < 3; ++i) {
          for (int j = 0; j < 3; ++j) {
            if (matrix[3 * I + i][3 * J + j] != 0 &&
                present[matrix[3 * I + i][3 * J + j]]) {
              return false;
            } else {
              present[matrix[3 * I + i][3 * J + j]] = true;
            }
          }
        }
      }
    }
    return true;
  }

  public static int[][] readInput( String arg ) {
    String [] args = arg.split("\\s");
    return readInput( args );
  }

   public static int[][] readInput( String[] args ) {
     int[][] result = new int[N][];
     for ( int k = 0 ; k < N; k++ ) {
       result[k] = new int[N];
     }
     for ( int k = 0 ; k < args.length; k++ ) {
       int val = new Integer( args[k] );
       // format: 634 -> in row 6, col 4 value is 4
       result[val / 100][(val % 100) / 10] = (val % 10);
     }
     return result;
   }

   static String matrixToString(int[][] matrix) {
     String result = "";
     for ( int i  = 0 ; i < matrix.length; i++ ) {
       for ( int j  = 0 ; j < matrix[0].length; j++ ) {
         result = result + i + j + matrix[i][j] + " ";
       }
     }
     return result;
   }


   static void print(String msg, int[][] matrix) {
     System.out.println(msg);
     for ( int i  = 0 ; i < matrix.length; i++ ) {
       for ( int j  = 0 ; j < matrix[0].length; j++ ) {
         System.out.print(matrix[i][j] + ( (j < 8) ? " " : "\n" ) );
       }
     }
   }
}