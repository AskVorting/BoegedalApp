# BoegedalApp

### Requirements
1. You need android studio giraffe with jetpack compose
2. You need a google maps api key, a guide how to do so can be found [here](https://developers.google.com/maps/documentation/android-sdk/start)
3. After getting you API-key you need to setup a secrets.properties file in your project levet directory. After creating the file you can edit it and insert the following line
   `MAPS_API_KEY={YOUR_API_KEY_FROM_GOOGLE}`
   Replace the brackets with your own personal API-key and you should be up and running
4. You also need to set up a firebase emulator:
  - First download firebase tools 'npm install -g firebase-tools'
  - then type 'firebase login' and follow the steps
  - then 'firebase init'
    'set GOOGLE_APPLICATION_CREDENTIALS=path\to\google-services.json
firebase emulators:start'
   -  

