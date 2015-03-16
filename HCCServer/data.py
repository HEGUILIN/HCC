#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
Created on Dec 7, 2014

@author: Guilin
'''
import re
def getValue(s,key):
    key=key+':'
    list1= re.split(key, s)
    for i in range(len(list1)):
        if list1[i][-1]==',' or list1[i][-1]=='{':
            list2=re.split(r',|}',list1[i+1])
            return list2[0]
        
    return 'Nokey'

if __name__=='__main__':
    key='value'
    s='{name:Login,value: ,operation:Login,username:HE Guilin,password:hgladmin}'
    print getValue(s, key)