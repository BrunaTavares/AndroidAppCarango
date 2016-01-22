package br.com.caelum.fj59.carangos.gcm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

/**
 * Created by android5628 on 21/01/16.
 */
public class InformacoesDoUsuario {

    public static String getEmail(Context context){
        AccountManager accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null){
            return null;
        }
        return account.name;
    }

    public static Account getAccount(AccountManager accountManager){
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account = null;

        if (accounts.length > 0){
            account = accounts[0];
        }
        return account;
    }


}
