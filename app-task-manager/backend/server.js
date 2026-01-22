const express = require('express');
const { MongoClient, ObjectId } = require('mongodb');
const cors = require('cors');

const app = express();
const PORT = 5000;
const MONGODB_URI = 'mongodb://admin:password@task-manager-mongodb:27017/taskdb?authSource=admin';

app.use(cors());
app.use(express.json());

let db;

MongoClient.connect(MONGODB_URI).then(client => {
  db = client.db('taskdb');
  console.log('✓ MongoDB connecté');
}).catch(err => console.error('Erreur MongoDB:', err));

// Santé
app.get('/api/health', (req, res) => res.json({ status: 'OK' }));

// Récupérer les tâches
app.get('/api/tasks', async (req, res) => {
  try {
    const tasks = await db.collection('tasks').find().toArray();
    res.json(tasks);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Ajouter une tâche
app.post('/api/tasks', async (req, res) => {
  try {
    const { title, description } = req.body;
    const result = await db.collection('tasks').insertOne({
      title,
      description: description || '',
      completed: false,
      createdAt: new Date()
    });
    res.json({ _id: result.insertedId, title, description, completed: false });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Mettre à jour une tâche
app.put('/api/tasks/:id', async (req, res) => {
  try {
    const { completed } = req.body;
    await db.collection('tasks').updateOne(
      { _id: new ObjectId(req.params.id) },
      { $set: { completed } }
    );
    res.json({ ok: true });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Supprimer une tâche
app.delete('/api/tasks/:id', async (req, res) => {
  try {
    await db.collection('tasks').deleteOne({ _id: new ObjectId(req.params.id) });
    res.json({ ok: true });
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(PORT, () => console.log(`✓ API sur port ${PORT}`));
