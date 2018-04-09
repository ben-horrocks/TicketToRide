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

	// Helpful link for inserting/retrieving objects from DAO by turning them into byte[]
	// http://www.rgagnon.com/javadetails/java-0117.html
}
