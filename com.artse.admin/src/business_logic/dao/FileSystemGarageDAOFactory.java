package business_logic.dao;

public class FileSystemGarageDAOFactory implements GarageDAOFactory {
    public GarageDAO createGarageDAO(){
        return  new FileSystemGarageDAO();
    }
}
