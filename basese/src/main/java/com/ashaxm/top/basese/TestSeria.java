package com.ashaxm.top.basese;

import java.beans.Transient;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
public class TestSeria implements Externalizable{
	
	private transient String msg = "我是否可以";
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(msg);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		msg = (String) in.readObject();
	}
	
	public static void main(String[] args) {
		try {
			TestSeria et = new TestSeria();
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File("serialtest")));
			out.writeObject(et);

			ObjectInput in = new ObjectInputStream(new FileInputStream(new File(
			        "serialtest")));
			et = (TestSeria) in.readObject();
			System.out.println(et.msg);

			out.close();
			in.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
