package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class Main extends Application {
    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lab 06");

        Group root = new Group();
        Canvas canvas = new Canvas(500,300);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //make function to calculate angles
        double sum=0;
        for(int i=0;i<purchasesByAgeGroup.length;i++){
            sum+=purchasesByAgeGroup[i];
        }

        double[] startPoint=new double[purchasesByAgeGroup.length];
        for(int i=0;i<purchasesByAgeGroup.length;i++){
            int length=purchasesByAgeGroup[i];
            if(i==0){
                startPoint[i]=0;
            }else{
                startPoint[i]=startPoint[i-1]+(360*purchasesByAgeGroup[i-1]/sum);
            }
        }
        for(int i=0;i<purchasesByAgeGroup.length;i++) {
            gc.setFill(pieColours[i]);
            gc.fillArc(275, 70, 200, 200, startPoint[i],purchasesByAgeGroup[i]*360 / sum, ArcType.ROUND);
        }
        double max=avgHousingPricesByYear[0];
        for(int i=1;i<avgHousingPricesByYear.length;i++){
            if (avgHousingPricesByYear[i]>max){
                max=avgHousingPricesByYear[i];
            }
        }
        for(int i=0;i<avgCommercialPricesByYear.length;i++){
            if (avgCommercialPricesByYear[i]>max){
                max=avgCommercialPricesByYear[i];
            }
        }

        for(int i=0;i<avgHousingPricesByYear.length;i++){
            gc.setFill(Color.RED);
            gc.fillRect(30+25*i,270-(200*avgHousingPricesByYear[i]/max),10,200*avgHousingPricesByYear[i]/max);
            gc.setFill(Color.BLUE);
            gc.fillRect(40+25*i,270-(200*avgCommercialPricesByYear[i]/max),10,200*avgCommercialPricesByYear[i]/max);
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
