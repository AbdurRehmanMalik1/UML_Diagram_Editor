package UML;


import javafx.scene.Parent;

public abstract class Moveable extends Parent {
//    transient CustomPoint point;
//    protected Runnable redrawLine;
//    public Moveable(){
//        point = new CustomPoint(getLayoutX(),getLayoutY());
//        addMouseEvents();
//    }
//    protected void setRunnable(Runnable runnable){
//        this.redrawLine = runnable;
//    }
//    private void addMouseEvents() {
//        setOnMousePressed(event -> {
//            point.setLocation(event.getSceneX(), event.getSceneY());
//        });
//        setOnMouseDragged(event -> {
//            double deltaX = event.getSceneX() - point.getX();
//            double deltaY = event.getSceneY() - point.getY();
//
//            double newPointX = getLayoutX() + deltaX;
//            double newPointY = getLayoutY() + deltaY;
//
//            double parentWidth = getParent().getLayoutBounds().getWidth();
//            double parentHeight = getParent().getLayoutBounds().getHeight();
//
//            if (newPointX >= 0 && newPointX + getBoundsInParent().getWidth() <= parentWidth) {
//                setLayoutX(newPointX);
//            }
//            if (newPointY >= 0 && newPointY + getBoundsInParent().getHeight() <= parentHeight) {
//                setLayoutY(newPointY);
//            }
//            if(redrawLine!=null)
//                this.redrawLine.run();
//            point.setLocation(event.getSceneX(), event.getSceneY());
//
//        });
//    }
}
