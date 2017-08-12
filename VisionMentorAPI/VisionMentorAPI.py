from flask import *
from flask_pymongo import PyMongo
from pymongo import MongoClient
import pprint

app = Flask(__name__)

client = MongoClient('mongodb://kaitoano0:boss359slasian!@maincluster-shard-00-00-resyk.mongodb.net:27017,maincluster-shard-00-01-resyk.mongodb.net:27017,maincluster-shard-00-02-resyk.mongodb.net:27017/testDB?ssl=true&replicaSet=mainCluster-shard-0&authSource=admin')
db = client.civilian
collection = db.city

#returns count of all the records in the database
@app.route('/',methods=['GET'])
def getUserCount():
    s = collection.count()
    return str(s)

#returns the details of the records with the matching username
@app.route('/user/<string:name>')
def getUserNames(name):
   for coll in collection.find({"Name":name},{"_id":0}):
       print str(coll['Name'])
   return str(coll['Name'])

if __name__ == '__main__':
    app.run()