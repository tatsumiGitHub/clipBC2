package system;

import java.io.*;

public class ObjectIO {
	public static Object loadObject(String file_path) {
		Object object = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file_path)));
			object = (Object) in.readObject();
			in.close();
		} catch (IOException err) {
			System.out.println("ファイル\".object_list.dat\"が存在しないか，破損している可能性があります．");
			System.out.println("ファイル\".object_list.dat\"を初期化します．");
			try {
				ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(file_path)));
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static void saveObject(String file_path, Object object) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file_path)));
			out.writeObject(object);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
