# CareTrack

## Guide complet pour tester l'application en local

### 1. Comprendre l'architecture
- **Backend multi-modules.** Le projet Maven racine agrège les modules `core`, `persistence`, `security` et `api`, ce qui permet de partager les couches métier, l'accès aux données et la sécurité entre les services exposés. 【F:pom.xml†L13-L83】
- **API Spring Boot.** Le module `api` démarre une application Spring Boot scannée sur l'espace de noms `com.caretrack`, ce qui assemble automatiquement les composants métiers, persistance et sécurité. 【F:api/src/main/java/com/caretrack/api/CaretrackApplication.java†L1-L12】
- **Base de données relationnelle.** La configuration par défaut utilise PostgreSQL avec Flyway pour les migrations, et crée des jeux de données initiaux (comptes `admin` et `user`). 【F:api/src/main/resources/application.yml†L1-L23】【F:api/src/main/resources/db/migration/V1__create_tables.sql†L60-L79】
- **Front-end Angular.** L'application Web Angular consomme l'API sur `http://localhost:8080/api` en environnement de développement. 【F:caretrack-web/package.json†L1-L45】【F:caretrack-web/src/environments/environment.ts†L1-L4】【F:caretrack-web/src/app/core/services/api-configuration.service.ts†L1-L8】

### 2. Prérequis logiciels
1. **Java 17** et **Maven 3.9+** pour compiler et tester l'API Spring Boot. 【F:pom.xml†L26-L78】
2. **Docker** (ou un serveur PostgreSQL local) pour fournir la base `caretrack` utilisée par défaut par Spring Boot. 【F:api/src/main/resources/application.yml†L1-L15】
3. **Node.js 18+** et **npm** ainsi que l'Angular CLI 17 pour lancer les tests et le serveur de développement front-end. 【F:caretrack-web/package.json†L1-L45】

### 3. Préparer la base de données PostgreSQL
1. Démarrer un conteneur PostgreSQL aligné avec la configuration Spring :
   ```bash
   docker run --name caretrack-db -p 5432:5432 \
     -e POSTGRES_DB=caretrack \
     -e POSTGRES_USER=caretrack \
     -e POSTGRES_PASSWORD=caretrack \
     -d postgres:15
   ```
   Les identifiants correspondent aux propriétés `spring.datasource` livrées. 【F:api/src/main/resources/application.yml†L1-L5】
2. Vérifier la connectivité via `psql` ou un client SQL (lancer `
   psql postgresql://caretrack:caretrack@localhost:5432/caretrack`).
3. Lors du démarrage de l'API, Flyway appliquera automatiquement la migration `V1__create_tables.sql` pour créer le schéma et injecter des comptes de démonstration. 【F:api/src/main/resources/application.yml†L12-L15】【F:api/src/main/resources/db/migration/V1__create_tables.sql†L60-L79】
4. Si vous souhaitez définir un mot de passe connu, remplacez le hash Bcrypt des comptes dans la migration ou mettez à jour la table `user_accounts` avec un hash généré via `BCryptPasswordEncoder` avant d'exécuter les tests manuels. 【F:api/src/main/resources/db/migration/V1__create_tables.sql†L70-L73】【F:security/src/main/java/com/caretrack/security/config/SecurityConfig.java†L37-L66】

### 4. Installer les dépendances
- **Backend :**
  ```bash
  mvn clean install -DskipTests
  ```
  Cette commande construit tous les modules Maven tout en évitant l'exécution de tests si la base de données ou Docker ne sont pas prêts.
- **Front-end :**
  ```bash
  cd caretrack-web
  npm install
  cd ..
  ```
  Les paquets Angular, Karma et ESLint seront téléchargés conformément au `package.json`. 【F:caretrack-web/package.json†L1-L45】

### 5. Exécuter les tests automatisés du backend
1. **Tests unitaires et d'intégration complets :**
   ```bash
   mvn clean verify
   ```
   Maven parcourra l'ensemble des modules et utilisera Testcontainers pour provisionner un PostgreSQL éphémère si des tests d'intégration sont présents. Assurez-vous que Docker est disponible pour ces scénarios. 【F:persistence/pom.xml†L15-L41】
2. **Tests ciblés par module :**
   - `mvn -pl core test` pour n'exécuter que la couche métier.
   - `mvn -pl persistence test` pour tester la couche JPA.
   - `mvn -pl api test` pour les contrôleurs REST.
   L'option `-am` peut être ajoutée pour construire automatiquement les dépendances d'un module ciblé.
3. **Profils de test dédiés :** si vous créez un profil `test` personnalisant `application-test.yml`, exécutez `mvn -Dspring.profiles.active=test verify` pour l'activer.

### 6. Lancer l'API pour les tests manuels
1. Vérifiez que PostgreSQL (local ou Docker) est disponible.
2. Démarrez l'API avec recompilation automatique des dépendances :
   ```bash
   mvn -pl api -am spring-boot:run
   ```
3. Les points d'entrée REST sont exposés sous `http://localhost:8080/api`. Toutes les routes sauf `/api/auth/**` nécessitent un JWT, conformément à la configuration de sécurité. 【F:security/src/main/java/com/caretrack/security/config/SecurityConfig.java†L47-L80】
4. La documentation interactive OpenAPI est accessible sur `http://localhost:8080/swagger-ui/index.html` pour explorer et tester les endpoints. 【F:api/src/main/java/com/caretrack/api/config/OpenApiConfig.java†L8-L18】

### 7. Tester l'authentification et les endpoints REST
1. **Authentification :** envoyez une requête `POST /api/auth/login` avec un corps JSON `{ "username": "admin", "password": "<votre_mot_de_passe>" }`. Le filtre d'authentification génère un couple de jetons (accès + rafraîchissement) en cas de succès. 【F:security/src/main/java/com/caretrack/security/jwt/JwtAuthenticationFilter.java†L25-L61】【F:security/src/main/java/com/caretrack/security/jwt/JwtTokenService.java†L33-L77】
2. **Appels authentifiés :** réutilisez le jeton d'accès dans l'en-tête `Authorization: Bearer <token>` pour interroger :
   - `GET /api/users/me` afin de récupérer votre profil. 【F:api/src/main/java/com/caretrack/api/user/UserController.java†L33-L38】
   - `GET /api/patients` pour lister les patients puis `POST /api/patients` pour créer un patient de test. 【F:api/src/main/java/com/caretrack/api/patient/PatientController.java†L30-L66】
   - `GET /api/alerts`, `/api/medications`, `/api/vitals` ou `/api/treatment-plans` selon les besoins fonctionnels (structures similaires aux patients dans leurs contrôleurs respectifs).
3. **Renouvellement de jeton :** testez `POST /api/auth/refresh` avec le refresh token pour vérifier le cycle de vie des JWT. 【F:api/src/main/java/com/caretrack/api/security/AuthController.java†L23-L47】
4. **Gestion des utilisateurs :** avec un compte `ADMIN`, validez `POST /api/users` (création) ou `PATCH /api/users/{id}/roles` (changement de rôles). 【F:api/src/main/java/com/caretrack/api/user/UserController.java†L39-L58】

### 8. Tests automatisés du front-end
1. Lancer les tests unitaires Angular (Karma + Jasmine) :
   ```bash
   cd caretrack-web
   npm test
   ```
   Cette commande ouvre Karma en mode watch ; utilisez `npm test -- --watch=false` pour une exécution CI.
2. Vérifier les règles de linting :
   ```bash
   npm run lint
   ```
3. Les tests end-to-end (`ng e2e`) sont déclarés mais nécessitent de configurer un projet d'e2e dédié avant exécution (aucune cible n'est définie dans `angular.json`).
4. Revenez à la racine du dépôt après les tests (`cd ..`).

### 9. Tests manuels du front-end
1. Démarrez le serveur Angular :
   ```bash
   cd caretrack-web
   npm start
   ```
   Le client est disponible sur `http://localhost:4200` et consomme l'API via `ApiConfigurationService`. 【F:caretrack-web/src/environments/environment.ts†L1-L4】【F:caretrack-web/src/app/core/services/auth.service.ts†L16-L35】
2. Connectez-vous avec les identifiants configurés pour valider les flux : tableau de bord, gestion des patients, des plans de soins et des alertes.
3. Pour pointer vers une autre URL d'API (ex. serveur distant), modifiez `environment.ts` ou utilisez un fichier d'environnement personnalisé.

### 10. Nettoyage et dépannage
- Arrêtez l'API avec `Ctrl+C` et le serveur Angular de la même manière.
- Stoppez la base PostgreSQL Docker :
  ```bash
  docker stop caretrack-db
  docker rm caretrack-db
  ```
- En cas d'erreur d'authentification, vérifiez le hash des mots de passe en base ou recréez la base pour réexécuter les migrations initiales.
- Pour diagnostiquer les problèmes de JWT, inspectez les logs Spring Security (niveau INFO par défaut). 【F:api/src/main/resources/application.yml†L15-L18】

En suivant ces étapes, vous pouvez exécuter les tests automatisés et réaliser des scénarios manuels complets couvrant l'API et l'interface Angular de CareTrack.
