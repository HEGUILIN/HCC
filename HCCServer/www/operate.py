#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
Created on Dec 7, 2014

@author: Guilin
'''
from models import User,Graph,Sentence
from transwarp import db
from transwarp import orm

import json

import os,sys

def deal_register(tcphandler,data):
    u= User()
    data.pop('OPERATION')
    for k,v in data.iteritems():
        u[k.lower()]=v
    print u
    if u.has_key("admin")==False:
        u["admin"]=0
    try:
        u.insert()
        send_data={}
        send_data['NAME']=data['NAME']
        send_data['OPERATION']='REGISTER'
        send_data['RESULT']='Success'
        send_data['ID']=u.id
    except BaseException,e:
        send_data={}
        send_data['NAME']=data['NAME']
        send_data['OPERATION']='REGISTER'
        send_data['RESULT']='Failure'
        print "Database insert error"
    finally:
        send_data_string=json.dumps(send_data)
        print send_data_string
        tcphandler.request.sendall(send_data_string)
        
def deal_login(tcphandler,data):
    u= User()
    data.pop('OPERATION')
    for k,v in data.iteritems():
        u[k.lower()]=v
    print u
    
    list=u.find_by('where name=?',u.name)
    if list!=[]:
        send_data={}
        send_data['NAME']=data['NAME']
        send_data['OPERATION']='Login'
        send_data['RESULT']='Success'
    else:
        send_data={}
        send_data['NAME']=data['NAME']
        send_data['OPERATION']='Login'
        send_data['RESULT']='Failure'
    
    send_data_string=json.dumps(send_data)
    print send_data_string
    tcphandler.request.sendall(send_data_string)

def deal_image(tcphandler,data):
    temp=data
    imagename=temp['image name']
    if temp['USAGE']=='Head Portrait':
        imagename2='portraits/'+imagename
        f = open(imagename2, 'wb')
        tcphandler.request.sendall(json.dumps({'RESULT':'Begin'}))
        i=0
        while i<int(temp['num']):
            i=i+1
            d1=tcphandler.request.recv(int(temp['unit length']))
            f.write(d1)
        d2=tcphandler.request.recv(int(temp['remainder']))
        f.write(d2)
        tcphandler.request.sendall(json.dumps({'RESULT':'Success'}))
        u=User()
        u.name=temp['User Name']
        temp=u.find_first("where name=?",u.name)
        print temp
        for k,v in temp.iteritems():
            u[k.lower()]=v
        u.id
        u['id']
        if u!=None:
            u.image=imagename2
            u.update()
            f.close()
        else:
            print 'no such an user'
    
def deal_test(tcphandler,data):
    
    return

def send_image(tcphandler,data):
    imageID=data['imageID']
    g=Graph()
    g=g.get(imageID)
    print g
    fn = os.path.join(os.path.dirname(__file__), g['GRAPH_LINK'])
    print fn
    if os.path.isfile(fn)==True: 
        f=open(fn,'rb')
        send_data={}
        send_data['RESULT']='True'
        send_data['unit length']=1024
        send_data['num']=int(os.path.getsize(fn)/1024)
        send_data['remainder']=os.path.getsize(fn)%1024
        send_data['image name']=os.path.basename(fn)
        str=json.dumps(send_data)
        print str
        tcphandler.request.sendall(str)
        tcphandler.request.recv(1024)
        i=0
        while i<int(os.path.getsize(fn)/1024):
            i=i+1
            s=f.read(1024)
            tcphandler.request.sendall(s)
        if os.path.getsize(fn)%1024!=0:
            s=f.read(os.path.getsize(fn)%1024)
            tcphandler.request.sendall(s)
        f.close()
    else:
        send_data={}
        send_data['RESULT']='False'
        send_data['REASON']='no such file'
        str=json.dumps(send_data)
        print str
        tcphandler.request.sendall(str)
        
def send_allimageid(tcphandler,data):
    g=Graph()
    image_list=g.find_all()
    ##print image_list
    send_data_pre={}
    send_data_pre['num']=str(len(image_list))
    
    if len(image_list)>=1 :
        send_data={}
        send_data['image id']=image_list[0]['GRAPH_ID']
        send_data_string=json.dumps(send_data)
        
        send_data_pre['unit length']=str(len(send_data_string.strip()))
    else:
        send_data_pre['unit length']=str(0)
    print send_data_pre
    tcphandler.request.sendall(json.dumps(send_data_pre))
    
    tcphandler.request.recv(1024)
    for l in image_list:
        print l['GRAPH_LINK']
        ##id_list.append({'image id':l['GRAPH_ID']})
        send_data={}
        send_data['image id']=l['GRAPH_ID']
        send_data_string=json.dumps(send_data)
        print send_data
        tcphandler.request.sendall(send_data_string)

def send_allsentenceid(tcphandler,data):
    s=Sentence()
    sentence_list=s.find_all()
    ##print sentence_list
    send_data_pre={}
    send_data_pre['num']=str(len(sentence_list))
    
    if len(sentence_list)>=1:
        send_data={}
        send_data['sentence id']=sentence_list[0]['SENTENCE_ID']
        send_data_string=json.dumps(send_data)
        
        send_data_pre['unit length']=str(len(send_data_string.strip()))
    else:
        send_data_pre['unit length']=str(0)
    print send_data_pre
    tcphandler.request.sendall(json.dumps(send_data_pre))
    
    tcphandler.request.recv(1024)
    for l in sentence_list:
        print l['SENTENCE_LINK']
        ##id_list.append({'image id':l['GRAPH_ID']})
        send_data={}
        send_data['sentence id']=l['SENTENCE_ID']
        send_data_string=json.dumps(send_data)
        print send_data
        tcphandler.request.sendall(send_data_string)

    