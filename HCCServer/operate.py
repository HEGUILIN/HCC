#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
Created on Dec 7, 2014

@author: Guilin
'''
from models import User
from transwarp import db
from transwarp import orm

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

def deal_register(data):
    newuser=User()
    newuser.name=data['username']
    newuser.password=data['password']
    newuser.email=data['email']
    try:
        newuser.insert()
        return 'registersuccess'
    except:
        return 'registerfailure'