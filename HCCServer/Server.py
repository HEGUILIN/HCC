# -*- coding: utf-8 -*-

#!/usr/bin/python

#coding=utf-8
'''
Created on Dec 4, 2014

@author: Guilin
'''
import socket,threading,time
import json
from idlelib.IOBinding import encoding
from models import User
import SocketServer

from transwarp import db
from config import configs
import operate
import data

##db.create_engine(**configs.db)
db.create_engine(user='root', password='password', database='hccdatabase')
class MyTCPHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        print 'a client enters'
        self.data = self.request.recv(1024)
        print self.data
        data2=json.loads(self.data)
        print data2
        operation=data2['OPERATION']
        if operation=='Register':
            u= User()
            data2.pop('OPERATION')
            for k,v in data2.iteritems():
                u[k]=v
            print u
            if u.has_key("ADMIN")==False:
                u["ADMIN"]=0
            u.insert()
            send_data={}
            send_data['NAME']=data2['NAME']
            send_data['OPERATION']='REGISTER'
            send_data['RESULT']='SUCCESS'
            send_data['ID']=u.id
            send_data_string=json.dumps(send_data)
            print send_data_string
            self.request.sendall(send_data_string)
        ##self.request.sendall("Response from server...")
 
if __name__=='__main__':
    
    host = "0.0.0.0" 
    port = 50000
    
    ## Create the server using the specified host and port 
    server = SocketServer.TCPServer((host, port), MyTCPHandler)
    
    ## Start the server and let it run until the program is interrupted 
    server.serve_forever()

