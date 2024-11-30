//package UML.Line;
//
//import Models.AssociationModel;
//import UML.Objects.UMLObject;
//import javafx.scene.layout.Pane;
//
//public class LineFactory {
//
//    public UML.Line.Line createLine(AssociationModel model){
//        String type = model.getType();
//        double startX = model.getStartX();
//        double startY = model.getStartY();
//        double endX = model.getEndX();
//        double endY =model.getEndY();
//        UMLObject startObject = createUMLObject(model.getStartModel());
//        UMLObject endObject = createUMLObject(model.getEndModel());
//        return createLine(type,startX,startY,endX,endY,canvas,model,startObject,endObject);
//    }
//    public UML.Line.Line createLine(String lineType, double startX, double startY, double endX, double endY, Pane canvas, AssociationModel associationModel, UMLObject object1, UMLObject object2){
//        UML.Line.Line line = null;
//        switch (lineType) {
//            case "Association":
//                line = new Association(startX, startY, endX, endY, canvas, associationModel, object1, object2);
//                break;
//            case "Aggregation":
//                line = new Aggregation(startX, startY, endX, endY, canvas, associationModel, object1, object2);
//                break;
//            case "Composition":
//                line = new Composition(startX, startY, endX, endY, canvas, associationModel, object1, object2);
//                break;
//            case "Inheritance":
//                line = new Inheritance(startX, startY, endX, endY, canvas, associationModel, object1, object2);
//                break;
//            default:
//                System.out.println("Invalid line type");
//        }
//        return line;
//    }
//
//
//}
