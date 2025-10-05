package com.home.designspatterns.creational;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Singleton {

	public static void main(String[] args) {

		System.out.println("============Eager binding=============");

		// SingletonEagerBinding
		SingletonEagerBinding singletonEagerBindingInstance1 = SingletonEagerBinding.getInstance();
		SingletonEagerBinding singletonEagerBindingInstance2 = SingletonEagerBinding.getInstance();
		System.out.println("singletonEagerBindingInstance1->" + singletonEagerBindingInstance1.hashCode());
		System.out.println("singletonEagerBindingInstance2->" + singletonEagerBindingInstance2.hashCode());

		// Eager binding proven wrong by method parameter reflection
		SingletonEagerBinding singletonEagerBindingInstance3 = new SingletonEagerBinding();
		SingletonEagerBinding singletonEagerBindingInstance4 = null;
		@SuppressWarnings("rawtypes")
		Constructor[] constructors = SingletonEagerBinding.class.getDeclaredConstructors();

		for (Constructor<?> constructor : constructors) {

			constructor.setAccessible(true);
			try {
				singletonEagerBindingInstance4 = (SingletonEagerBinding) constructor.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

		System.out.println("singletonEagerBindingInstance3->" + singletonEagerBindingInstance3.hashCode());
		System.out.println("singletonEagerBindingInstance4->" + singletonEagerBindingInstance4.hashCode());

		// solution to Eager binding by java enum type
		SingletonEagerBindingEnum singletonEagerBindingEnumInstance1 = SingletonEagerBindingEnum.INSTANCE;
		SingletonEagerBindingEnum singletonEagerBindingEnumInstance2 = SingletonEagerBindingEnum.INSTANCE;

		
		System.out.println("===========enum start=================");	
		SingletonEagerBindingEnum singletonEagerBindingEnumInstance1234= SingletonEagerBindingEnum.INSTANCE;
		SingletonEagerBindingEnum singletonEagerBindingEnumInstance4567 = SingletonEagerBindingEnum.INSTANCE;
		System.out.println("=singletonEagerBindingEnumInstance1234========"+singletonEagerBindingEnumInstance1234.hashCode());
		System.out.println("=singletonEagerBindingEnumInstance4567========"+singletonEagerBindingEnumInstance4567.hashCode());
		System.out.println("============enum end==================");

		
		
		System.out.println(singletonEagerBindingEnumInstance1 == singletonEagerBindingEnumInstance2);

		System.out.println(singletonEagerBindingEnumInstance1.getData());
		System.out.println(singletonEagerBindingEnumInstance2.getData());

		singletonEagerBindingEnumInstance2.setData("newly initialized data");

		System.out.println(singletonEagerBindingEnumInstance1.getData());
		System.out.println(singletonEagerBindingEnumInstance2.getData());

		System.out.println(singletonEagerBindingEnumInstance1.hashCode());
		System.out.println(singletonEagerBindingEnumInstance2.hashCode());

		System.out.println("============Late Binding=====================");

		// SingletonLateBinding
		SingletonLateBinding singletonLateBindingInstance1 = SingletonLateBinding.getInstance();
		SingletonLateBinding singletonLateBindingInstance2 = SingletonLateBinding.getInstance();
		System.out.println("singletonLateBindingInstance1->" + singletonLateBindingInstance1.hashCode());
		System.out.println("singletonLateBindingInstance2->" + singletonLateBindingInstance2.hashCode());

		// SingletonLateSynchronized-first four threads
		SingletonLateSynchronized singletonLateSynchronizedInstance1 = SingletonLateSynchronized.getInstance();
		SingletonLateSynchronized singletonLateSynchronizedInstance2 = SingletonLateSynchronized.getInstance();
		System.out.println("singletonLateSynchronizedInstance1->" + singletonLateSynchronizedInstance1.hashCode());
		System.out.println("singletonLateSynchronizedInstance2->" + singletonLateSynchronizedInstance2.hashCode());

		// SingletonLateSynchronizedBlock-few threads
		SingletonLateSynchronizedBlock singletonLateSynchronizedBlockInstance1 = SingletonLateSynchronizedBlock
				.getInstance();
		SingletonLateSynchronizedBlock singletonLateSynchronizedBlockInstance2 = SingletonLateSynchronizedBlock
				.getInstance();
		System.out.println(
				"singletonLateSynchronizedBlockInstance1->" + singletonLateSynchronizedBlockInstance1.hashCode());
		System.out.println(
				"singletonLateSynchronizedBlockInstance2->" + singletonLateSynchronizedBlockInstance2.hashCode());

		// SingletonLateBindingHelper-many threads
		SingletonLateBindingHelper SingletonLateBindingHelperInstance1 = SingletonLateBindingHelper.getInstance();
		SingletonLateBindingHelper SingletonLateBindingHelperInstance2 = SingletonLateBindingHelper.getInstance();
		System.out.println("SingletonLateBindingHelperInstance1->" + SingletonLateBindingHelperInstance1.hashCode());
		System.out.println("SingletonLateBindingHelperInstance2->" + SingletonLateBindingHelperInstance2.hashCode());

		// SingletonLateBindingHelperSerializable- without file
		SingletonLateBindingHelperSerializable SingletonLateBindingHelperSerializableInstance1 = SingletonLateBindingHelperSerializable
				.getInstance();
		SingletonLateBindingHelperSerializable SingletonLateBindingHelperSerializableInstance2 = SingletonLateBindingHelperSerializable
				.getInstance();
		System.out.println("SingletonLateBindingHelperSerializableInstance1->"
				+ SingletonLateBindingHelperSerializableInstance1.hashCode());
		System.out.println("SingletonLateBindingHelperSerializableInstance2->"
				+ SingletonLateBindingHelperSerializableInstance2.hashCode());

		// Late binding proven wrong- with file using streams
		SingletonLateBindingHelperSerializable singletonLateBindingHelperSerializableIns1 = SingletonLateBindingHelperSerializable
				.getInstance();

		ObjectOutput objectOutput = null;
		try {
			objectOutput = new ObjectOutputStream(new FileOutputStream(
					"/Users/arunkumaria/Desktop/curricular/java-fullstack-workspace/java-practise-folder/serializable_file"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			objectOutput.writeObject(singletonLateBindingHelperSerializableIns1);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			objectOutput.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		ObjectInput objectInput = null;
		try {
			objectInput = new ObjectInputStream(new FileInputStream(
					"/Users/arunkumaria/Desktop/curricular/java-fullstack-workspace/java-practise-folder/serializable_file"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		SingletonLateBindingHelperSerializable singletonLateBindingHelperSerializableIns2 = null;
		try {
			singletonLateBindingHelperSerializableIns2 = (SingletonLateBindingHelperSerializable) objectInput
					.readObject();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			objectInput.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println(
				"singletonLateBindingHelperSerializableIns1->" + singletonLateBindingHelperSerializableIns1.hashCode());
		System.out.println(
				"singletonLateBindingHelperSerializableIns2->" + singletonLateBindingHelperSerializableIns2.hashCode());

	}

}

class SingletonEagerBinding {

	private static SingletonEagerBinding instance = new SingletonEagerBinding();

	public SingletonEagerBinding() {
	}

	public static SingletonEagerBinding getInstance() {
		return instance;
	}

}

enum SingletonEagerBindingEnum {
	INSTANCE;

	private String data;

	SingletonEagerBindingEnum() {
		this.data = "initailaized data";

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

class SingletonLateBinding {

	private static SingletonLateBinding instance;

	public SingletonLateBinding() {
	}

	public static SingletonLateBinding getInstance() {

		if (instance == null) {
			instance = new SingletonLateBinding();
		} else {
			return instance;
		}
		return instance;
	}

}

class SingletonLateSynchronized {

	private static SingletonLateSynchronized instance;

	public SingletonLateSynchronized() {
	}

	public static synchronized SingletonLateSynchronized getInstance() {

		if (instance == null) {
			instance = new SingletonLateSynchronized();
		} else {
			return instance;
		}
		return instance;
	}

}

class SingletonLateSynchronizedBlock {

	private static SingletonLateSynchronizedBlock instance;

	public SingletonLateSynchronizedBlock() {
	}

	public static SingletonLateSynchronizedBlock getInstance() {

		if (instance == null) {

			synchronized (SingletonLateSynchronizedBlock.class) {
				if (instance == null) {
					instance = new SingletonLateSynchronizedBlock();

				}

			}
		}

		return instance;
	}

}

class SingletonLateBindingHelper {

	public SingletonLateBindingHelper() {
	}

	public static class SingletonHelper {
		private static final SingletonLateBindingHelper INSTANCE = new SingletonLateBindingHelper();

	}

	public static SingletonLateBindingHelper getInstance() {

		return SingletonHelper.INSTANCE;
	}

}

class SingletonLateBindingHelperSerializable implements Serializable {

	private static final long serialVersionUID = 1L;

	public SingletonLateBindingHelperSerializable() {
	}

	public static class SingletonHelper {
		private static final SingletonLateBindingHelperSerializable INSTANCE = new SingletonLateBindingHelperSerializable();

	}

	public static SingletonLateBindingHelperSerializable getInstance() {

		return SingletonHelper.INSTANCE;
	}

	// solution to Late binding - with file using streams

	protected Object readResolve() {
		return getInstance();
	}

}
