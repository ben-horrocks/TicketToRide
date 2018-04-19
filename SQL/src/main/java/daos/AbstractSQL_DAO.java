package daos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Factory.ConnectionSetup;

abstract class AbstractSQL_DAO implements IDAO
{

	public AbstractSQL_DAO() {}

	abstract boolean tableExists();
	abstract boolean createTable();
	abstract boolean deleteTable();

	@Override
	public boolean clearData()
	{
		boolean cleared;
		cleared = deleteTable();
		if (!cleared) return false;
		cleared = createTable();
		return cleared;
	}

	byte[] objectToByteArray(Object object) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.close();
		return baos.toByteArray();
	}

	Object byteArrayToObject(byte[] bytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream inputStream = new ObjectInputStream(bais);
		Object object = inputStream.readObject();
		inputStream.close();
		return object;
	}

	// Helpful link for inserting/retrieving objects from DAO by turning them into byte[]
	// http://www.rgagnon.com/javadetails/java-0117.html
}
