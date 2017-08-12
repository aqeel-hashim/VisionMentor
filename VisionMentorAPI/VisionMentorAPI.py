from flask import *
from flask_pymongo import PyMongo
from pymongo import MongoClient
import pprint

app = Flask(__name__)

client = MongoClient('mongodb://kaitoano0:boss359slasian!@maincluster-shard-00-00-resyk.mongodb.net:27017,maincluster-shard-00-01-resyk.mongodb.net:27017,maincluster-shard-00-02-resyk.mongodb.net:27017/testDB?ssl=true&replicaSet=mainCluster-shard-0&authSource=admin')
db = client.civiliand
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

#registers a new user and updates the total count of users
@app.route('/register/<string:name>')
def registerUser(name):
    if collection.count({"Name":name}) == 0:
        new_user = {"Name": name}
        collection.insert_one(new_user).inserted_id
        print ("Successfully registered")
    else:
        print "There is already someone with that username!"
    return str(collection.count())

#displays every username in the database
@app.route('/showAll')
def showAll():
    output=[]
    i=0
    sentence = ""
    for coll in collection.find():
        output.append(str(coll['Name']))
        i=i+1
    for j in range(i):
        sentence += (", " +output[j])
    return sentence



if __name__ == '__main__':
    app.run()