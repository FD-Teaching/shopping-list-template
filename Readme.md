# Editable Shopping List Template Solution

This project is a template solution for Assignment 2.

## Setup instructions
This project requires a Cloud Firestore database connection.

- Create a Firebase project on https://console.firebase.google.com/u/0/
- Add the app via its package name (de.praktikum.shoppinglist)
- Connect a Cloud Firestore database
- Download the generated google-services.json file and add it to one of the folders where AndroidStudio will search for it, e.g., the top-level "app" folder.

## A few notes
- The app still uses predefined images. However, these are now saved in the assets folder because unlike the automatically assigned resource IDs, the paths don't change. The images are still delivered with the app.
- The app uses the Coil library for easily loading the images from the file paths.