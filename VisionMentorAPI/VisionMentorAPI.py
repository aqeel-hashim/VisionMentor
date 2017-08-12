from flask import *
from flask_pymongo import PyMongo
from pymongo import MongoClient

app = Flask(__name__)

client = MongoClient('mongodb://kaitoano0:boss359slasian!@maincluster-shard-00-00-resyk.mongodb.net:27017,maincluster-shard-00-01-resyk.mongodb.net:27017,maincluster-shard-00-02-resyk.mongodb.net:27017/testDB?ssl=true&replicaSet=mainCluster-shard-0&authSource=admin')
db = client.testDB
collection = db.testData


@app.route('/')
def hello_world():
    s = collection.count()
    return str(s)


if __name__ == '__main__':
    app.run()