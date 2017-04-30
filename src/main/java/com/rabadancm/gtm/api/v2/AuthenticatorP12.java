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
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.tagmanager.TagManager;
import com.google.api.services.tagmanager.TagManagerScopes;
import com.google.api.services.tagmanager.model.Condition;
import com.google.api.services.tagmanager.model.Container;
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
public class AuthenticatorP12 {
          
  private static final String SERVICE_ACCOUNT_EMAIL = "account-service@hello-gtm.iam.gserviceaccount.com";
  private static final String SERVICE_ACCOUNT_PRIVATE_KEY = "hello-gtm-1bc81f6c88ee.p12";    
  private static final String APPLICATION_NAME = "hello-gtm";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static NetHttpTransport httpTransport;
  private static GoogleCredential credential;
  
  
  public TagManager getGtmObject() {    
    TagManager gtm = null;
    try{
      httpTransport = GoogleNetHttpTransport.newTrustedTransport();            
      // Authorization flow.
      Credential credential = authorize();
      gtm = new TagManager.Builder(httpTransport, JSON_FACTORY, credential)
              .setApplicationName(APPLICATION_NAME).build();      
    }catch (Exception e) {
      e.printStackTrace();
    }
    return gtm;
  }  
  
  private Credential authorize() throws Exception {
    
    // Construct a GoogleCredential object with the service account email
    // and p12 file downloaded from the developer console.
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    credential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
        .setServiceAccountPrivateKeyFromP12File( new File(SERVICE_ACCOUNT_PRIVATE_KEY)  )
        .setServiceAccountScopes(TagManagerScopes.all())
        .build();

    return credential;     
  }
    
}
