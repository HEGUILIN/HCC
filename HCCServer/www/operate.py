#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
Created on Dec 7, 2014

@author: Guilin
'''
from models import User
from transwarp import db
from transwarp import orm

import json

def deal_login(data):
    username=data['username']
    u=User.find_first('where name=?',username)
    if u!=None:
        upassword=u.password
        if u.password==data['password']:
            return 'loginsuccess'
        else:
            return 'passworderror'
    else:
        return 'nouser'
    return

def deal_register(tcphandler,data):
    u= User()
    data.pop('OPERATION')
    for k,v in data.iteritems():
        u[k]=v
    print u
    if u.has_key("ADMIN")==False:
        u["ADMIN"]=0
            
    try:
        u.insert()
        send_data={}
        send_data['NAME']=data['NAME']
        send_data['OPERATION']='REGISTER'
        send_data['RESULT']='SUCCESS'
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