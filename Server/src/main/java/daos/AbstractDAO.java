package daos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.player_info.User;

abstract class AbstractDAO
{
	abstract boolean createTable();
	abstract boolean deleteTable();
	public void clearTable()
	{
		deleteTable();
		createTable();
	}
	byte[] objectToByteArray(Object object) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		User user = (User)object;
		oos.writeObject(user);
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
