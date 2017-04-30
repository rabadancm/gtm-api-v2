/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabadancm.gtm.api.v2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.TagManagerScopes;
import com.google.api.services.tagmanager.model.Account;
import com.google.api.services.tagmanager.model.Condition;
import com.google.api.services.tagmanager.model.Container;
import com.google.api.services.tagmanager.model.ListAccountsResponse;
import com.google.api.services.tagmanager.model.Parameter;
import com.google.api.services.tagmanager.model.Tag;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author crabadan
 */
public class AuthenticatorJson {
            
  private static final String CLIENT_SECRET_JSON_RESOURCE = "/client_secrets.json";  
  
  // The directory where the user's credentials will be stored for the application.
  private static final File DATA_STORE_DIR = new File(System.getProperty("user.dir")+"/resources");

  private static final String APPLICATION_NAME = "hello-gtm";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static NetHttpTransport httpTransport;
  private static FileDataStoreFactory dataStoreFactory;

  public TagManager getGtmObject() {    
      
    TagManager gtm = null;
    Credential credential = null;    
    try {
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

      // Authorization flow.
      credential = authorize();
      gtm = new TagManager.Builder(httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();
                         
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gtm;
  }

  private Credential authorize() throws Exception {
    // Load client secrets.
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(AuthenticatorJson.class.getResourceAsStream(CLIENT_SECRET_JSON_RESOURCE)));
          
    // Set up authorization code flow for all auth scopes.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport,
        JSON_FACTORY, clientSecrets, TagManagerScopes.all()).setDataStoreFactory(dataStoreFactory)
        .build();

    // Authorize.
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
  }
    
}
