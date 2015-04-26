#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = 'HE Guilin'

'''
Models for user.
'''

import time, uuid
from config import configs
from transwarp.db import next_id
from transwarp.orm import Model, StringField, BooleanField, FloatField, TextField, IntegerField
from transwarp import db

class User(Model):
    __table__ = 'users'

    id = StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
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

class Graph(Model):
    __table__='graphs'
    
    graph_id= StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
    graph_link=StringField(ddl='varchar(100)',default='')
    graph_voice=StringField(ddl='varchar(100)')
    graph_description=StringField(ddl='varchar(100)')
    graph_element=IntegerField()
    firstgraph_followed_id=StringField(ddl='varchar(50)')
    firstgraph_followed_frequency=IntegerField()
    secondgraph_followed_id=StringField(ddl='varchar(50)')
    secondgraph_followed_frequency=IntegerField()
    thirdgraph_followed_id=StringField(ddl='varchar(50)')
    thirdgraph_followed_frequency=IntegerField()

class Sentence(Model):
    __table__='sentences'
    
    sentence_id= StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
    sentence_voice=StringField(ddl='varchar(100)')
    sentence_description=StringField(ddl='varchar(100)')
    sentence_frequency=IntegerField()
    
class GS(Model):
    __table__='gs'
    
    GS_ID=StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
    GRAPH_ID=StringField(ddl='varchar(50)')
    SENTENCE_ID=StringField(ddl='varchar(50)')
    GS_SEQUENCE=IntegerField()
    
class Group_Chat(Model):
    __table__='group_chat'
    
    group_id=StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
    group_created_at=FloatField(updatable=True, default=time.time)
    group_name=StringField(ddl='varchar(50)')
    beacon_id=StringField(ddl='varchar(50)')
    group_created_by=StringField(ddl='varchar(50)')
    
class Group_Message(Model):
    __table__='group_message'
    
    gm_id=IntegerField(updatable=False,primary_key=True)
    group_id=StringField(ddl='varchar(50)')
    sentence_id=StringField(ddl='varchar(50)')
    id=StringField(ddl='varchar(50)')
    gm_created_at=FloatField(updatable=True, default=time.time)
    
class U_G_C(Model):
    __table__='u_g_c'
    
    u_g_c_id=StringField(primary_key=True,updatable=False, default=next_id, ddl='varchar(50)')
    group_id=StringField(ddl='varchar(50)')
    join_time=FloatField(updatable=True, default=time.time)
    id=StringField(ddl='varchar(50)')
    
if __name__=='__main__':
    ##db.create_engine(user='root', password='password', database='hccdatabase')
    db.create_engine(**configs.db)
    import xlrd,os
    fn = os.path.join(os.path.dirname(__file__)[0:-4], 'imageinfo.xlsx')
    print fn
    imageinfo = xlrd.open_workbook(fn)

    worksheet = imageinfo.sheet_by_name('sheet1')
    
    num_rows = worksheet.nrows - 1
    num_cells = worksheet.ncols - 1
    curr_row = -1
    while curr_row < num_rows:
        curr_row += 1
        row = worksheet.row(curr_row)
        print row
        print 'Row:', curr_row
        curr_cell = -1
        if curr_row>0:
            graph_link=''
            graph_voice=''
            graph_description=''
            graph_element=0
            
            while curr_cell < num_cells:
                curr_cell += 1
                # Cell Types: 0=Empty, 1=Text, 2=Number, 3=Date, 4=Boolean, 5=Error, 6=Blank
                cell_type = worksheet.cell_type(curr_row, curr_cell)
                cell_value = worksheet.cell_value(curr_row, curr_cell)
                if curr_cell==1:
                    graph_link=cell_value
                elif curr_cell==2:
                    graph_voice=cell_value
                elif curr_cell==3:
                    graph_description=cell_value
                elif curr_cell==4:
                    graph_element=cell_value
                print '    ', cell_type, ':', cell_value
            g=Graph()
            g.graph_link=graph_link
            g.graph_voice=graph_voice
            g.graph_description=graph_description
            g.graph_element=graph_element
            g.insert()