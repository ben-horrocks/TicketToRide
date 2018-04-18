package daos;

/**
 * Created by Kavika F.
 */
public interface IDAO
{
	boolean clearData();
	boolean openConnection();
	boolean commitConnection();
	boolean closeConnection();
}
