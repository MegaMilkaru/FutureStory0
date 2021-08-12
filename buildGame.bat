@ECHO OFF
ECHO Building game:
gradlew desktop:dist
ECHO Done! You should see it at "Desktop -> build -> libs"
ECHO Just remember to put it in the 'products' folder, it needs the maps to run.
PAUSE