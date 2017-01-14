package FirstPackage;

import java.io.*;
import FirstPackage.Tree;

//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GangWars {

	static int matrix_size;
	static String run_type;
	static String first_turn;
	static int depth;
	private static int count = 0;
	HashMap<Integer, String> hmap = new HashMap<Integer, String>();

	public static String read_file(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while(line!=null){
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String file_value = sb.toString();
			return file_value;
		}
		finally{
			br.close();
		}
	}

	public static List<ArrayList<ArrayList<String>>> findValues() throws FileNotFoundException{
		Scanner s = new Scanner (new File("/Users/shruti5488/Documents/JAVA/JavaPractice/GangWars/src/input.txt"));
		List<String> lines = new ArrayList<String>();
		while(s.hasNext()){
			lines.add(s.nextLine());
		}

		Iterator<String> itr = lines.iterator();
		matrix_size = Integer.parseInt(itr.next());
		run_type =  itr.next();
		first_turn = itr.next();
		depth = Integer.parseInt(itr.next());

		String[] line_value;
		List<String> value;
		ArrayList<ArrayList<String>> matrix = new ArrayList<ArrayList<String>>();
		int line_number = 4;
		for (int i = 0; i<matrix_size;i++){
			line_value = lines.get(line_number).split(" ");
			line_number ++ ;
			value = Arrays.asList(line_value);

			for (int j=0;j<matrix_size;j++){	
				matrix.add(new ArrayList<String>());
				matrix.get(i).add(value.get(j));
			}
			//			System.out.println(matrix.get(i));
		}
		ArrayList<ArrayList<String>> turn_value = new ArrayList<ArrayList<String>>();
		for (int i = 0; i<matrix_size;i++){
			line_value = lines.get(line_number).split("");
			value = Arrays.asList(line_value);
			ArrayList<String> row = new ArrayList<String>();
			for (int j=0;j<matrix_size;j++){
				//				turn_value.add(new ArrayList<String>());

				row.add(value.get(j));
				if (row.get(j).equals(".")){
					count ++;
				}
			}
			turn_value.add(row);
			//			System.out.println(turn_value.get(i));
			line_number ++ ;
		}
		List<ArrayList<ArrayList<String>>> values = new ArrayList<ArrayList<ArrayList<String>>>();
		values.add(0, turn_value);
		values.add(1, matrix);
		//		System.out.println("count"+count);		
		return values;
	}


	//	public static ArrayList<String> try_value(ArrayList<String> turn_value_row){
	//		String check = first_turn;
	//		int c = 0;
	//		for ( int r=0;r<matrix_size;r++) {
	//			if ( turn_value_row.get(r).contains(".")) {
	//				turn_value_row.set(r, check);
	//				break;
	//			}
	//		}
	//		first_turn = player_value(check);
	//		return turn_value_row;
	//	}


	public static void printTree(Tree tree_root, String appender) {
		System.out.println(appender + tree_root.getScores());
		for (Tree each: tree_root.getChildren()){
			printTree(each, appender+appender);
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
		Tree tree = null ;
		ArrayList<ArrayList<String>> board_turn = findValues().get(0);
		ArrayList<ArrayList<String>> board_value = findValues().get(1);
		Tree tree_root = new Tree(null, matrix_size, first_turn, depth, board_value);
		Tree tree_root_points = new Tree(null, matrix_size, first_turn, depth, board_value);
		
		int count = tree.count_space(board_value, matrix_size);

		tree_root = tree.create_tree (tree_root, board_turn, count);
		int util_value = tree.calculate_utility(board_value, tree_root.getId());
		tree_root_points = tree.create_points(tree_root_points, tree_root, board_turn, util_value);
		tree.play_board(tree_root, tree_root_points);
//		printTree(tree_root_points, " ");	
	}
}

