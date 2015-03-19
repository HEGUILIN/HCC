#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = 'HE Guilin'

from models import User
from transwarp.orm import Model
from transwarp import db

db.create_engine(user='root', password='password', database='hccdatabase')

#u = User(name='HE Guilin', email='heguilinol@outlook.com', password='hgladmin', image='about:blank')

#u.insert()
if __name__=='__main__':
    u = User(name='HE Guilin', email='heguilinol@outlook.com', password='hgladmin', image='about:blank')
    u.insert()
    list=User.find_first('where email=?','heguilinol@outlook.com')
    if list!=None:
        print "has the user"
    print list