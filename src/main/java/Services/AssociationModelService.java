package Services;

import Models.AssociationModel;
import Repositories.AssociationModelRepository;

public class AssociationModelService {
    private final AssociationModelRepository associationModelRepository;

    public AssociationModelService() {
        associationModelRepository = new AssociationModelRepository();
    }

    // Method to save an AssociationModel
    public int saveAssociation(AssociationModel associationModel) {
        int returnedId = associationModelRepository.save(associationModel); // Save the model via the repository
        associationModel.setId(returnedId); // Set the ID after saving
        return returnedId; // Return the ID
    }

    // You can add more methods to handle other operations (e.g., find by ID, delete, etc.)
}
