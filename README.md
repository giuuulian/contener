

# Guide de Découverte - Nouveau Utilisateur (je ne pouvais pas mettre de capture pour une raison inconu cela me faisais des erreurs que je n'ai pas réussis à résoudre donc j'ai fais un guide détaillé à la place)

## À propos du projet

Ce projet est un **application de gestion de tâches** avec 3 services:
- **Frontend** (Nginx) - Interface web
- **Backend** (Node.js) - API REST
- **Database** (MongoDB) - Base de données

Tout fonctionne dans des conteneurs Docker gérés par Kubernetes.

## Étape 1: Préparer l'environnement

Vérifiez que Docker et Minikube sont installés:
```
docker --version
minikube version
```

Lancez Minikube:
```
minikube start
```

## Étape 2: Construire les images Docker

```
cd app-task-manager/backend
docker build -t task-manager-api:1.0 .

cd ../frontend
docker build -t task-manager-frontend:1.0 .
```

Vérifiez que les images sont créées:
```
docker images | grep task-manager
```

## Étape 3: Déployé l'application sur Kubernetes

```
cd app-task-manager
kubectl apply -f k8s/
```

Vérifiez que tous les pods sont actifs:
```
kubectl get pods
```

Attendez que tous les pods passent en état "Running".

## Étape 4: Accéder à l'application

Récupérez l'URL:
```
minikube service task-manager-frontend --url
```

Ouvrez cette URL dans votre navigateur.

## Étape 5: Créer une tâche

1. Entrez un titre dans le champ "Titre de la tâche"
2. Ajoutez une description (optionnel)
3. Cliquez "Ajouter"
4. La tâche apparaît dans la liste

## Étape 6: Gérer les tâches

**Marquer comme complétée:**
- Cliquez "Fait" → la tâche devient grisée et barrée
- Cliquez "Annuler" → revenir en arrière

**Supprimer:**
- Cliquez "Supprimer" → la tâche disparaît

## Étape 7: Test de synchronisation en temps réel

1. Ouvrez l'application dans 2 onglets différents
2. Ajoutez une tâche dans l'onglet 1
3. Observez l'onglet 2 → la tâche apparaît automatiquement après 3 secondes

## Étape 8: Observer dans le Dashboard Kubernetes

Lancez:
```
minikube dashboard
```

Dans le dashboard:
1. Allez dans "Workloads" → "Pods"
2. Cliquez sur un pod `task-manager-backend-xxxx`
3. Allez dans l'onglet "Logs"
4. Ajoutez/supprimez des tâches dans l'app → voyez les requêtes API en direct

## Étape 9: Tester le Scaling (multiplier les instances)

Lancez:
```
kubectl scale deployment task-manager-backend --replicas=3
```

Vérifiez:
```
kubectl get pods
```

Vous devez voir 3 pods backend au lieu de 1. L'application continue de fonctionner normalement.

Dans le Dashboard, observez les 3 pods en fonctionnement.

Revenez à 1 replica:
```
kubectl scale deployment task-manager-backend --replicas=1
```

## Étape 10: Voir les détails d'un pod

```
kubectl describe pod <nom-du-pod>
```

Remplacez `<nom-du-pod>` par un nom de pod obtenu avec `kubectl get pods`.

## Étape 11: Accéder à l'intérieur d'un conteneur

```
kubectl exec -it <nom-du-pod> -- /bin/bash
```

Explorez:
```
ls          # Lister les fichiers
env         # Voir les variables d'environnement
exit        # Quitter
```

## Étape 12: Voir les logs

```
kubectl logs <nom-du-pod>
```

## Étape 13: Nettoyer l'environnement

Supprimer tous les pods, services et déploiements:
```
kubectl delete -f k8s/
```

Vérifiez:
```
kubectl get pods
```

Tous les pods doivent être supprimés.

