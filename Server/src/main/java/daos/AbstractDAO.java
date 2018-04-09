package daos;

/**
 * Created by Kavika F.
 */
abstract class AbstractDAO
{
	abstract boolean createTable();
	abstract boolean deleteTable();
	public void clearTable()
	{
		deleteTable();
		createTable();
	}
}
