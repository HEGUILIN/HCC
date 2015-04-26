'''
Created on Apr 24, 2015

@author: Guilin
'''
from flask import Flask
app = Flask(__name__)
from flask import render_template
from flask import url_for
from flask import send_from_directory
from flask import send_file
from flask import request
from models import GS,Sentence,User,Graph,Group_Chat
from transwarp import db
from config import configs
import json
from werkzeug.utils import secure_filename
import os
from config import ip
from config import port
from config import logger
from config import addLog
from config import addError
 
@app.route('/')
def hello_world():
     return 'hello world'

@app.route('/create_sentence',methods=['post'])
def create_sentence():
    operation=request.form['operation']
    sentence_description=request.form['sentence description']
    logger.info('someone is to create sentence sentence_description')
    print sentence_description
    num=request.form['num']
    print num
    graph_id_list=[]
    for i in range(0,int(num)):
        graph_id_list.append(request.form["graph_id"+str(i)])
    print graph_id_list
    sentence=Sentence()
    sentence.sentence_description=sentence_description
    sentence.insert()
    print sentence.sentence_id
    for i in range(0,int(num)):
        gs=GS()
        print "graph_id"+str(i)
        gs.GRAPH_ID=graph_id_list[i]
        gs.SENTENCE_ID=sentence.sentence_id
        gs.GS_SEQUENCE=i
        gs.insert()
    return "create success"

@app.route('/getAllSentenceId',methods=['get'])
def getAllSentenceId():
    s=Sentence()
    sentence_list=s.find_all()
    print sentence_list
    ##print sentence_list
    send_data_all={}
    send_data=[]
    for l in sentence_list:
        d={}
        d['sentence id']=l['SENTENCE_ID']
        d['sentence description']=l['SENTENCE_DESCRIPTION']
        d_g_l=[]
        gs=GS()
        gs_list=gs.find_by("where SENTENCE_ID =? order by ? ",l['SENTENCE_ID'],'GS_SEQUENCE')
        print gs_list
        for gs in gs_list:
            d_g_l.append({'graph id':gs['GRAPH_ID'],'sequence':str(gs['GS_SEQUENCE'])})
        d['graph id list']=d_g_l
        send_data.append(d)
    send_data_all['sentence id list']=send_data
    send_data_string=json.dumps(send_data_all)
    return send_data_string

@app.route('/register',methods=['get'])
def register():
    try:
        username=request.args.get('username')
        password=request.args.get('password')
        admin=request.args.get('admin',0)
        email=request.args.get('email',"")
        u= User()
        u.name=username
        u.password=encryptMD5(password)
        u.admin=admin
        u.email=email
        logger.info(u.name+' is to register')
        u.insert()
        logger.info(u.name+' registered successfully')
        return 'register success'
    except BaseException,e:
        print "exception:", e
        logger.info(u.name+' failed to register')
        return 'register failure'

def encryptMD5(password):
    try:
        import hashlib
        hash = hashlib.md5(password)
    except ImportError:
        # for Python << 2.5
        import md5
        hash = md5.new()
    ##hash.update('spam,spam,and egges')
    print hash.hexdigest()  
    return hash.hexdigest()

@app.route('/login',methods=['get'])
def login():
    try:
        u= User()
        username=request.args.get('username')
        logger.info(username+' is to login')
        password=request.args.get('password')
        u.name=username
        u.password=encryptMD5(password)
        list=u.find_by('where name=?',u.name)
        print list
        if list!=[]:
            if list[0]['PASSWORD']==u.password:
                logger.info(username+' logined successfully')
                return "login success"
            else:
                return "password error"
        else:
            return "no such user"
    except BaseException,e:
        print "exception:", e
        return "format error"
    
@app.route('/getAllGraphId',methods=['get'])
def getAllGraphId():
    send_data={}
    g=Graph()
    image_list=g.find_all()
    list=[]
    link_pre='http://137.189.97.84:8080/'
    for i in image_list:
        list.append({'graph id':i["GRAPH_ID"],'graph link':link_pre+i['GRAPH_LINK'],'graph description':i['GRAPH_DESCRIPTION']})
    send_data['image list']=list
    send_data_string=json.dumps(send_data)
    return send_data_string

@app.route('/upload_headportrait',methods=['post','get'])
def upload_headportrait():
    try:
        f = request.files['imagebody']
        filename=request.args.get('username','')
        addLog(filename, ' is to upload headportrait')
        if filename=='':
            return 'no username'
        else:
            u=User()
            u.name=filename
            list=u.find_by("where name=?",u.name)
            if list==[]:
                return 'no such user'
            else:
                f.save(os.path.join("static/head_portrait", filename+".png"))
                addLog(filename, ' upload headportrait successfully')
                return "upload head portrait success"
    except BaseException,e:
        print "exception:", e
        return "upload head portrait failure"
    
@app.route('/getHeadPortrait',methods=['get'])
def getHeadPortrait():
    username=request.args.get('username','')
    if username=='':
        return 'no username'
    else:
        u=User()
        u.name=username
        list=u.find_by("where name=?",u.name)
        if list==[]:
            return 'no such user'
        else:
            if(os.path.exists(os.path.join("static/head_portrait", username+".png"))):
                return "http://137.189.97.84:8080/head_portrait/"+username+".png"
            else:
                return 'no head portrait stored'
    return ''

@app.route('/getGraph',methods=['get'])
def getGraph():
    graph_id=request.args.get('graph_id',"")
    if graph_id=='':
        return 'no graph id'
    g=Graph()
    g.graph_id=graph_id
    list=g.find_by("where graph_id = ?",g.graph_id)
    if list==[]:
        return "no such graph id"
    else:
        if len(list)==1:
            return 'http://137.189.97.84:8080/'+list[0]['GRAPH_LINK']
        else:
            return 'server internal error'
    return ''

@app.route('/send_sentence',methods=['post'])
def send_sentence():
    username=request.form('username')
    sentence_id=request.form('sentence id')
    time=request.form('time')
    return

@app.route('/create_chatting_room',methods=['post'])
def create_chatting_room():
    try:
        username=request.form.get('username')
        roomname=request.form.get('roomname')
        beaconid=request.form.get('beaconid')
        addLog(username, ' is to create new chatting room with beacon id = '+beaconid)
        newgroup=Group_Chat()
        newgroup.beacon_id=beaconid
        newgroup.group_name=roomname
        
        u=User()
        u.name=username
        list=u.find_by('where name=?',u.name)
        if list==[]:
            return 'no such user'
        else:
            if list[0]['ADMIN']==0:
                return 'user authority limited'
            elif list[0]['ADMIN']==1:
                u=list[0]
                newgroup.group_created_by=u['ID']
                newgroup=newgroup.insert()
                addLog(username, ' created new chatting room with beacon id = '+beaconid+" successfully")
                return newgroup.group_id
    except BaseException,e:
        print 'exception', e
        return 'parameter error'

if __name__=='__main__':
    ##encryptMD5("hgladmin")
    ##encryptMD5("hgladmin2")
    db.create_engine(**configs.db)
    app.run(ip,port)