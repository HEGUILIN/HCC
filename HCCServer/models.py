#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = 'HE Guilin'

'''
Models for user.
'''

import time, uuid

from transwarp.db import next_id
from transwarp.orm import Model, StringField, BooleanField, FloatField, TextField, IntegerField

class User(Model):
    __table__ = 'users'

    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    email = StringField(updatable=True, ddl='varchar(50)')
    password = StringField(ddl='varchar(50)')
    admin = IntegerField()
    name = StringField(ddl='varchar(50)')
    image = StringField(ddl='varchar(500)')
    created_at = FloatField(updatable=False, default=time.time)
    online = BooleanField()
    port = IntegerField()
    ip = StringField(updatable=True, ddl='varchar(20)')
    login_at = FloatField(updatable=True, default=time.time)
