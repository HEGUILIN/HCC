#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = 'HE Guilin'

from models import User,Graph
from transwarp.orm import Model
from transwarp import db

import sys

db.create_engine(user='root', password='password', database='hccdatabase')
##db.create_engine(user='heguilin', password='hgladmin', database='hccdatabase')
#u = User(name='HE Guilin', email='heguilinol@outlook.com', password='hgladmin', image='about:blank')

#u.insert()
if __name__=='__main__':
    ''''g=Graph()
    g.graph_link="images/image1.jpg"
    g.graph_description="image1"
    g.insert()
    g2=Graph()
    g2.graph_link="images/image2.jpg"
    g2.graph_description="image2"
    g2.insert()'''
    ''''g=Graph()
    image_list=g.find_all()
    id_list=[]
    for l in image_list:
        print l
        id_list.append(l['GRAPH_ID'])
    print id_list'''
    '''imageID='0014283321662968073487186a94207beeca082f84953b7000'
    g=Graph()
    g=g.get(imageID)
    print g'''
    '''
    import os
    fn = os.path.join(os.path.dirname(__file__), 'images/image1.jpg')
    print os.path.getsize(fn)'''
    '''print len("00142841110569081991cc1726b441a8e73943833c9837c000")
    print (sys.getsizeof("00142841110569081991cc1726b441a8e73943833c9837c000"))
    print [1,2][1]'''
    ##d=['1' '2']
    