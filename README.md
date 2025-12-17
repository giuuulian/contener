J'ai commencé par cloner le projet :
```
git clone https://github.com/charroux/ingnum.git
```

Puis j'ai supprimer l'origin et ajouter celle d'un repository que j'ai créer au préalable:

```
git remote remove origin
git remote add origin https://github.com/giuuulian/contener.git
```

Ensuite j'ai installé Java JDK 21:
```
winget install Oracle.JDK.21

```
Je suis aller dans le projet (rentalservice) et je l'ai build:

```
gradlew build
```
J'ai tester voir si le projet ce lancait:

```
java -jar build/libs/RentalService-0.0.1-SNAPSHOT.jar
```
et je suis allé vérifier à l'url: http://localhost:8080/bonjour

J'ai créer un fichier dockerfile dans le dossier rentalservice contenant le code: 
```
FROM eclipse-temurin:21

VOLUME /tmp

EXPOSE 8080

ADD ./build/libs/RentalService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

Ensuite j'ai créer mon image avce la commande: 
```
docker build –t image .
```
Puis j'ai run le docker:
```
docker run –p 8080:8080 image
```
Je me suis ensuite connecté à mon compte avec:

```
git login
```

Pour finir j'ai fais: 

```
 docker tag 98529790c322 giulanp/image:1
 docker push giulianp/image:1
```
