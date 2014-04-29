/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.client.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mmchess.client.connection.Connection;
import mmchess.client.model.Bishop;
import mmchess.client.model.Board;
import mmchess.client.model.King;
import mmchess.client.model.Knight;
import mmchess.client.model.Model;
import mmchess.client.model.Move;
import mmchess.client.model.Pawn;
import mmchess.client.model.Piece;
import mmchess.client.model.Queen;
import mmchess.client.model.Rook;

/**
 *
 * @author Matthew
 */
public class Controller implements Initializable, Observer {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int x = 0;
        int y = 0;
        for (Node child : boardGrid.getChildren()) {
            // Setting up the grid array
            boardCells[x][y] = (ImageView)((StackPane)child).getChildren().get(0);
            // setting cell data: x, y coord, & selected flag
            boardCells[x][y].setUserData(new int[]{x,y});
            x++;
            if (x == 8) {
                y++;
                x = 0;
            }
        }
        movesListObservable = movesList.getChildren();
        model = new Model();
        currentBoard = model.getBoard();
        
//        currentBoard.addListenerToChanged(new ChangeListener<Boolean>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, 
//                    Boolean oldValue, Boolean newValue) {
//                updateBoard();
//            }
//            
//        });
        
        //model.addObserverToBoard(this);
        connection = new Connection(this);
        
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateBoard();
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Move) {
            // update board state/GUI
            model.doMove((Move)arg);
            //updateBoard();
        } else if (arg instanceof String) {
            if (((String)arg).equals("TRN")) {
                model.setPlayerTurn(true);
            } else if (((String)arg).equals("CLR W")) {
                model.setPlayerColor(Piece.WHITE);
            } else if (((String)arg).equals("CLR B")) {
                model.setPlayerColor(Piece.BLACK);
            }
        }
    }
    
    @FXML
    public void exitApplication()
    {
        Platform.exit();
    }
    
    @FXML
    public void newGame() {
        model.newGame();
        
        boardCells[0][0].setImage(Controller.blackRook);
        boardCells[1][0].setImage(Controller.blackKnight);
        boardCells[2][0].setImage(Controller.blackBishop);
        boardCells[3][0].setImage(Controller.blackQueen);
        boardCells[4][0].setImage(Controller.blackKing);
        boardCells[5][0].setImage(Controller.blackBishop);
        boardCells[6][0].setImage(Controller.blackKnight);
        boardCells[7][0].setImage(Controller.blackRook);
        
        for (int i = 0; i < 8; i++) {
            boardCells[i][1].setImage(Controller.blackPawn);
            boardCells[i][6].setImage(Controller.whitePawn);
        }
        
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boardCells[j][i].setImage(null);
            }
        }
        
        boardCells[0][7].setImage(Controller.whiteRook);
        boardCells[1][7].setImage(Controller.whiteKnight);
        boardCells[2][7].setImage(Controller.whiteBishop);
        boardCells[3][7].setImage(Controller.whiteQueen);
        boardCells[4][7].setImage(Controller.whiteKing);
        boardCells[5][7].setImage(Controller.whiteBishop);
        boardCells[6][7].setImage(Controller.whiteKnight);
        boardCells[7][7].setImage(Controller.whiteRook);
        
        while (movesListObservable.size() != 0) {
            movesListObservable.remove(0);
        }
    }
    
    @FXML
    public void updateBoard() {        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece currentPiece = currentBoard.getPiece(x, y);
                
                if (currentPiece == null) {
                    boardCells[x][y].setImage(null);
                } else if (currentPiece instanceof Rook) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackRook);
                    } else {
                        boardCells[x][y].setImage(whiteRook);
                    }
                } else if (currentPiece instanceof Bishop) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackBishop);
                    } else {
                        boardCells[x][y].setImage(whiteBishop);
                    }
                } else if (currentPiece instanceof Knight) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackKnight);
                    } else {
                        boardCells[x][y].setImage(whiteKnight);
                    }
                } else if (currentPiece instanceof King) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackKing);
                    } else {
                        boardCells[x][y].setImage(whiteKing);
                    }
                } else if (currentPiece instanceof Queen) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackQueen);
                    } else {
                        boardCells[x][y].setImage(whiteQueen);
                    }
                } else if (currentPiece instanceof Pawn) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackPawn);
                    } else {
                        boardCells[x][y].setImage(whitePawn);
                    }
                } 
            }
        }
        /*
        // Code to rotate the board so black player is on bottom
        if (boardGrid.getRotate() == 0) {
            boardGrid.setRotate(180);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardCells[i][j].setRotate(-180);
                }
            }
        } else {
            boardGrid.setRotate(0);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardCells[i][j].setRotate(0);
                }
            }
        }
        */
    }
    
    @FXML
    public void clickCell(Event e) {
        if (selectedCell == null) { 
            // If no other cell clicked
            selectedCell = (ImageView)e.getSource();
            if (selectedCell.getImage() != null) {
                
                // Draw selected cell indicator
                ((StackPane)selectedCell.getParent()).getStyleClass().add("selectedCell");
                
                // Draw Valid Moves indicators
                int[] moveFrom = (int[]) selectedCell.getUserData();
                // END DEBUG
                validMoves = model.getValidMoves(moveFrom[0], moveFrom[1]);
                for (Move move : validMoves) {
                    ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                            .getStyleClass().add("validMove");
                }
            } else {
                selectedCell = null;
            }
        } else if (selectedCell == (ImageView)e.getSource()) {
            // If the clicked cell is already selected
            // Deselect the cell
            ((StackPane)selectedCell.getParent()).getStyleClass().removeAll("selectedCell");
            for (Move move : validMoves) {
                ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                        .getStyleClass().removeAll("validMove");
            }

            selectedCell = null;
        } else {
            // If the clicked cell is different from the selected cell
            // TODO: insert code to make a move here
            int[] moveTo = (int[]) ((ImageView)e.getSource()).getUserData();
            int[] moveFrom = (int[]) selectedCell.getUserData();
            // Deselect the cell
            ((StackPane)selectedCell.getParent()).getStyleClass().removeAll("selectedCell");
            selectedCell = null;
            for (Move move : validMoves) {
                ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                        .getStyleClass().removeAll("validMove");
            }
            
            Move newMove = new Move(moveFrom[0], moveFrom[1], moveTo[0], moveTo[1]);
            
            for (Move move : validMoves) {
                if (move.equals(newMove)) {
                    newMove = move;
                }
            }
            
            if (model.isMoveValid(newMove)) {
                connection.sendMove(newMove);
                model.setPlayerTurn(false);
            }
        }
    }
    
    public void doMove(Move move) {
        // model.doMove(move);
        
        if (move.isCapture()) {
            //TODO: if capture move, add captured piece to capture box/list
        }

        if (move.isCheck()) {
            //TODO: write code here
        }

        if (move.isCheckMate()) {
            //TODO: write code here
        }

        if (move.isLongCastle()) {
            boardCells[3][move.getStartPosY()].setImage(
                    boardCells[0][move.getStartPosY()].getImage());
            boardCells[0][move.getStartPosY()].setImage(null);
        }

        if (move.isShortCastle()) {
            boardCells[5][move.getStartPosY()].setImage(
                    boardCells[7][move.getStartPosY()].getImage());
            boardCells[7][move.getStartPosY()].setImage(null);
        }

        boardCells[move.getEndPosX()][move.getEndPosY()].setImage(
                boardCells[move.getStartPosX()][move.getStartPosY()].getImage());
        boardCells[move.getStartPosX()][move.getStartPosY()].setImage(null);
        
        Text temp = new Text(movesListObservable.size() + ". " + move.toString());
        temp.setFont(new Font("System", 14));
        movesListObservable.add(temp);

    }
    
    @FXML
    private GridPane boardGrid;
    @FXML
    private VBox movesList;
    
    private ImageView[][] boardCells = new ImageView[8][8];
    private ImageView selectedCell = null;
    private ObservableList<Node> movesListObservable;
    private Model model;
    private Move[] validMoves;
    private Connection connection;
    private Board currentBoard;
    private static final Image whitePawn = new Image("/mmchess/client/gui/images/wP.png", 46, 46, true, true);
    private static final Image whiteRook = new Image("/mmchess/client/gui/images/wR.png", 46, 46, true, true);
    private static final Image whiteBishop = new Image("/mmchess/client/gui/images/wB.png", 46, 46, true, true);
    private static final Image whiteKnight = new Image("/mmchess/client/gui/images/wH.png", 46, 46, true, true);
    private static final Image whiteKing = new Image("/mmchess/client/gui/images/wK.png", 46, 46, true, true);
    private static final Image whiteQueen = new Image("/mmchess/client/gui/images/wQ.png", 46, 46, true, true);
    private static final Image blackPawn = new Image("/mmchess/client/gui/images/bP.png", 46, 46, true, true);
    private static final Image blackRook = new Image("/mmchess/client/gui/images/bR.png", 46, 46, true, true);
    private static final Image blackBishop = new Image("/mmchess/client/gui/images/bB.png", 46, 46, true, true);
    private static final Image blackKnight = new Image("/mmchess/client/gui/images/bH.png", 46, 46, true, true);
    private static final Image blackKing = new Image("/mmchess/client/gui/images/bK.png", 46, 46, true, true);
    private static final Image blackQueen = new Image("/mmchess/client/gui/images/bQ.png", 46, 46, true, true);
    
}
