INSTALLATION:
_ J'ai utilisé Intellij IDEA pour la création de ce projet.
_ Il faudra soit utiliser un sdk 17 ou supérieur ou bien modifier le build dans le pom.xml et le mettre à la version souhaité
_ Sinon il faut juste l'ouvrir avec Intellij

UTILISATION
l'utilisation est montré dans le rapport avec des screenshots
_ Pour lancer l'application : lancer le main dans src/main/java/application/main/Main
_ Pour lancer l'application avec le logging : lancer le main dans src/main/java/applicationSpooned/main/Main
le logging généré se trouve dans /logs/out
_ Pour lancer la génération du code par Spoon : lancer le main dans src/main/java/spooning/main/Main
le code généré se trouvera dans /spooned
_ Pour lancer l'analyse : lancer le main dans src/main/java/analyzer/main/Main
l'analyse se fera sur le documents /logs/examples/tp_example2.json. Pour changer le document de loggings, changer le path sur la ligne 21 du Main.class
