from flask import *
from pymongo import MongoClient

app = Flask(__name__)

client = MongoClient('mongodb://kaitoano0:boss359slasian!@maincluster-shard-00-00-resyk.mongodb.net:27017,maincluster-shard-00-01-resyk.mongodb.net:27017,maincluster-shard-00-02-resyk.mongodb.net:27017/visionMentor?ssl=true&replicaSet=mainCluster-shard-0&authSource=admin')
db = client.visionMentor
collection = db.user

#returns count of all the records in the database
@app.route('/',methods=['GET'])
def getUserCount():
    s = collection.count()
    return str(s)

#returns the details of the records with the matching username
@app.route('/user/<string:username>')
def getUserNames(username):
   output=""
   for coll in collection.find({"Username":username},{"_id":0}):
     output += str(coll['username']) + " - " + str(coll['email']) + " - " + str(coll['Points']) + " points - " + str(coll['Build_Data'][0]['Build_Errors'])+ " build errors so far - " +str(coll['Build_Data'][0]['Exceptions']) + " exceptions out of "+ str(coll['Build_Data'][0]['Builds']) + " builds"
   return output

#registers a new user and updates the total count of users
@app.route('/register/<string:username>')
def registerUser(username):
    if collection.count({"Username":username}) == 0:
        new_user = {"Username": username}
        collection.insert_one(new_user).inserted_id
        print ("Successfully registered")
        return str(collection.count())
    else:
        return "There is already someone with that username!"


#displays every username in the database
@app.route('/showAll')
def showAll():
    output=[]
    i=0
    sentence = ""
    for coll in collection.find():
        output.append(str(coll['Username']))
        i=i+1
    for j in range(i):
        sentence += (", " +output[j])
    return sentence

#deletes all the records in the table and returns casualties
@app.route('/deleteAll')
def deleteAll():
    deletion = collection.delete_many({})
    casualties = deletion.deleted_count
    return str(casualties) + " casualties have been found! Collection has been fully truncated!"

if __name__ == '__main__':
    app.run(debug=True)