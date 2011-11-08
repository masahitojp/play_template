package controllers;

import models.Account;
import play.Logger;
import play.libs.Crypto;
import play.libs.Crypto.HashType;


public class SimpleSecurity extends Secure.Security {
    
    static boolean authenticate(String username, String password) {
    	 Account account = Account.find("name=?", username).first();
    	 Logger.info("account.name = %s", account.name);
        return (account != null && account.checkPassWord(password));
    }
    
    static boolean check(String profile) {
     if ("admin".equals(profile)) {
     return connected().equals("admin");
     }
     return false;
    }
    
    static void onAuthenticated() {
     Logger.info("Got auth for user %s", connected());
    }
    static void onDisconnect() {
     Logger.info("Got disconnected for user %s", connected());
    }
    static void onCheckFailed(String profile) {
     Logger.error("Failed auth for profile %s", profile);
     forbidden();
    }
}
