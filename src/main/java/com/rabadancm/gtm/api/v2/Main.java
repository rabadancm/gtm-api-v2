/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabadancm.gtm.api.v2;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.TagManager.Accounts;
import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.Container;
import com.google.api.services.tagmanager.model.ContainerVersion;
import com.google.api.services.tagmanager.model.ContainerVersionHeader;
import com.google.api.services.tagmanager.model.ListAccountsResponse;
import com.google.api.services.tagmanager.model.ListContainersResponse;

import java.util.*;

/**
 *
 * @author crabadan
 */
public class Main {        
    
    public static void main(String[] args) {
        
      String gtmAccountId = "900965899";
      TagManager gtm = null;
      List<Account> accounts = null;
      List<Container> containers = null;
      
      try{          
        // Authenticate and get TagManager object
        //gtm = new AuthenticatorP12().getGtmObject();
        gtm = new AuthenticatorJson().getGtmObject();                   
        accounts = gtm.accounts().list().execute().getAccount();                 
        
        // Accounts
        for(Account acc : accounts) {
          System.out.println("Account Id = " + acc.getAccountId());
          System.out.println("Account Name = " + acc.getName());            
        }
        
        // ContainerVersions
        ContainerVersionHeader cvh = gtm.accounts().containers().versionHeaders().latest("accounts/900965899/containers/6004040").execute();        
        System.out.println("Name: "+ cvh.getName());
        System.out.println("Container: "+ cvh.getContainerId());
        System.out.println("Tags: "+ cvh.getNumTags());
        //accounts/900965899/containers/6004040
        //6004040
        
                
        // Containers
        Container c = gtm.accounts().containers().get("accounts/900965899/containers/6004040").execute();        
        System.out.println("Container Id = " + c.getContainerId());
        System.out.println("Container name = " + c.getName());
            
            
                
        
      }catch (Exception e) {
        System.err.println("There was a service error: ");
        e.printStackTrace();
      }
    }
        
}
