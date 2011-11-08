package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.*;
import play.libs.Crypto;
import play.libs.Crypto.HashType;

import javax.persistence.*;

import java.util.*;

@Entity @Table(name="USER_ACCOUNT")
public class Account extends GenericModel {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Required
	public String name;
	@Required
	@Email
	public String email;
	@Password @Required
	private String passWord;
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = Crypto.passwordHash(passWord, HashType.SHA512);
	}
	
	public Boolean checkPassWord(String password){
		return passWord.equals(Crypto.passwordHash(password, HashType.SHA512));
	}

}
