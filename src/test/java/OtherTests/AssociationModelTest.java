package OtherTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import Models.AssociationModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;


public class AssociationModelTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testAssociationModel() {
        // Create a new AssociationModel
        AssociationModel associationModel = new AssociationModel("association");

        // Test setting and getting values
        associationModel.setId(1);
        associationModel.setAssociationName("Test Association");
        associationModel.setType("aggregation");
        associationModel.setStartX(100.0);
        associationModel.setStartY(150.0);
        associationModel.setEndX(200.0);
        associationModel.setEndY(250.0);
        associationModel.setStartMultiplicity("1");
        associationModel.setEndMultiplicity("0..1");

        // Test getters
        assertEquals(1, associationModel.getId());
        assertEquals("Test Association", associationModel.getAssociationName());
        assertEquals("aggregation", associationModel.getType());
        assertEquals(100.0, associationModel.getStartX());
        assertEquals(150.0, associationModel.getStartY());
        assertEquals(200.0, associationModel.getEndX());
        assertEquals(250.0, associationModel.getEndY());
        assertEquals("1", associationModel.getStartMultiplicity());
        assertEquals("0..1", associationModel.getEndMultiplicity());
    }

    @Test
    public void testAssociationModelSerialization() throws JsonProcessingException {
        // Create a new AssociationModel
        AssociationModel associationModel = new AssociationModel("association");
        associationModel.setId(1);
        associationModel.setAssociationName("Test Association");
        associationModel.setType("aggregation");
        associationModel.setStartX(100.0);
        associationModel.setStartY(150.0);
        associationModel.setEndX(200.0);
        associationModel.setEndY(250.0);
        associationModel.setStartMultiplicity("1");
        associationModel.setEndMultiplicity("0..1");

        // Serialize the object to JSON
        String jsonString = OBJECT_MAPPER.writeValueAsString(associationModel);

        // Deserialize the JSON back into an AssociationModel
        AssociationModel deserializedModel = OBJECT_MAPPER.readValue(jsonString, AssociationModel.class);

        // Test deserialization
        assertEquals(associationModel.getId(), deserializedModel.getId());
        assertEquals(associationModel.getAssociationName(), deserializedModel.getAssociationName());
        assertEquals(associationModel.getType(), deserializedModel.getType());
        assertEquals(associationModel.getStartX(), deserializedModel.getStartX());
        assertEquals(associationModel.getStartY(), deserializedModel.getStartY());
        assertEquals(associationModel.getEndX(), deserializedModel.getEndX());
        assertEquals(associationModel.getEndY(), deserializedModel.getEndY());
        assertEquals(associationModel.getStartMultiplicity(), deserializedModel.getStartMultiplicity());
        assertEquals(associationModel.getEndMultiplicity(), deserializedModel.getEndMultiplicity());
    }

}
