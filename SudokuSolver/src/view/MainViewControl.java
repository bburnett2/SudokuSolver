package view;

import java.util.Arrays;

import com.mathworks.engine.MatlabEngine;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainViewControl {
	@FXML
	public GridPane gameboard;
	@FXML 
	public Button solveBtn;
	@FXML
	public Button newPuzzleBtn;
	@FXML 
	public Text topRowT;
	
	private TextField[][] cells;
	
	@FXML
	public void newPuzzleAction(){
		cells = new TextField[9][9];
		for(int r =0; r < 9; r++){
			for(int c = 0; c < 9; c++){
				TextField t =  new TextField();
				t.setFont(Font.font(30));
				t.setAlignment(Pos.CENTER);
				GridPane.setConstraints(t, c, r, 1, 1);
				gameboard.getChildren().add(t);
				cells[r][c] = t;
			}
		}
		solveBtn.setVisible(true);
	}
	
	@FXML
	public void solveAction(){
		ObservableList<Node> values = gameboard.getChildren();
		double[][] board = getGameBoard(values);
		try {
			MatlabEngine eng = MatlabEngine.startMatlab();
			String result =eng.feval("sudokuSolver", board, 1);
			displayResult(result);
			eng.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		solveBtn.setVisible(false);
	}
	
	private void displayResult(String res){
		String[] vals = res.split(",");
		System.out.println(res);
		System.out.println(res.length());
		int v = 0;
		for(int r = 0; r < 9; r++){
			for(int c = 0; c< 9; c++){
				cells[r][c].setText(vals[v]);
				v++;
			}
		}
	}
	
	private double[][] getGameBoard(ObservableList<Node> values){
		double[][] board = new double[9][9];
		for(int r =0; r < 9; r++){
			for(int c = 0; c < 9; c++){
				String val = cells[r][c].getText();
				if(!val.isEmpty()){
					board[r][c] = Double.parseDouble(val);
				}
			}
		}
		return board;
	}
}
