package com.jsf.shoppingList;

import java.io.Serializable;
import com.jsf.shoppingList.DataLayer.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "index")
@SessionScoped
public class Index implements Serializable {

	private static final long serialVersionUID = -6913972022251814607L;

	private String s1;

	private boolean isConnected = false;

	public String getS1() {
		if (DbConnect.Connect("Recipes")) {
			isConnected =true;
			setS1("Připojeno!");
		}
		else{
			isConnected = false;
			setS1("Připojení se nepovedlo!");
		}
		System.out.println(s1);
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

}
