package fr.dauphine.javaavance.phineloops.view;

import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.Piece;
import fr.dauphine.javaavance.phineloops.model.PieceContract;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BasicUI extends Application {
    private static final int PIECE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;
    private static final int X_PIECES = W / PIECE_SIZE;
    private static final int Y_TILES = H / PIECE_SIZE;
    private Scene scene;

    private PieceUI[][] gridUI = new PieceUI[X_PIECES][Y_TILES];
    private Grid grid;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        grid=new Grid(20,20);
        grid.generateValidGrid();

        for (int y = 0; y < Y_TILES; y++) {
            for (int x = 0; x < X_PIECES; x++) {
                PieceUI tile = new PieceUI(x, y, grid.getGridMatrix()[x][y]);
                gridUI[x][y] = tile;
                root.getChildren().add(gridUI[x][y]);
            }
        }
        return root;
    }


    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }


    private class PieceUI extends StackPane {
        private int x, y;
        private PieceContract piece;
        // private Rectangle border = new Rectangle(PIECE_SIZE - 2, PIECE_SIZE - 2);
        private Text text = new Text();
        final ImageView selectedImage = new ImageView();



        public PieceUI(int x, int y, PieceContract p) {
            this.x = x;
            this.y = y;
            this.piece=p;
            //border.setStroke(Color.LIGHTGRAY);
            text.setFont(Font.font(18));
            text.setText(this.piece.toString());
            text.setFill(Color.WHITE);
            //Image image1;
            switch (p.getPieceType()){
                case 0:
                    Image image0 = new Image(BasicUI.class.getResourceAsStream("piece0.png"));
                    selectedImage.setImage(image0);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
                case 1:
                    Image image1 = new Image(BasicUI.class.getResourceAsStream("piece1.png"));
                    selectedImage.setImage(image1);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
                case 2:
                    Image image2 = new Image(BasicUI.class.getResourceAsStream("piece2.png"));
                    selectedImage.setImage(image2);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
                case 3:
                    Image image3 = new Image(BasicUI.class.getResourceAsStream("piece3.png"));
                    selectedImage.setImage(image3);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
                case 4:
                    Image image4 = new Image(BasicUI.class.getResourceAsStream("piece4.png"));
                    selectedImage.setImage(image4);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
                case 5:
                    Image image5 = new Image(BasicUI.class.getResourceAsStream("piece5.png"));
                    selectedImage.setImage(image5);
                    selectedImage.setRotate(selectedImage.getRotate() + 90*p.getPieceOrientation());
                    break;
            }
            selectedImage.setFitHeight(PIECE_SIZE);
            selectedImage.setFitWidth(PIECE_SIZE);
            getChildren().addAll(selectedImage);

            setTranslateX(x * PIECE_SIZE);
            setTranslateY(y * PIECE_SIZE);

            setOnMouseClicked(e -> rotate());
        }

        public void rotate() {
            this.piece=this.piece.afterRotating();
            text.setText(this.piece.toString());
            selectedImage.setRotate(selectedImage.getRotate() + 90);

        }
    }

    public static void main(String[] args) {
        //launch(args);
    }
}
