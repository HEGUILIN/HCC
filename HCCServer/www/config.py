#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
Configuration
'''


import config_default
import logging
import logging.handlers 

class Dict(dict):
    '''
    Simple dict but support access as x.y style.
    '''
    def __init__(self, names=(), values=(), **kw):
        super(Dict, self).__init__(**kw)
        for k, v in zip(names, values):
            self[k] = v

    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Dict' object has no attribute '%s'" % key)

    def __setattr__(self, key, value):
        self[key] = value

def merge(defaults, override):
    r = {}
    for k, v in defaults.iteritems():
        if k in override:
            if isinstance(v, dict):
                r[k] = merge(v, override[k])
            else:
                r[k] = override[k]
        else:
            r[k] = v
    return r

def toDict(d):
    D = Dict()
    for k, v in d.iteritems():
        D[k] = toDict(v) if isinstance(v, dict) else v
    return D

configs = config_default.configs

try:
    import config_override
    configs = merge(configs, config_override.configs)
except ImportError:
    pass

configs = toDict(configs)

LOG_FILE = "../log/logging.txt"  
handler = logging.handlers.RotatingFileHandler(LOG_FILE, maxBytes = 20*1024*1024, backupCount = 10);  
fmt = "%(asctime)s - %(name)s - %(levelname)s - %(message)s - [%(filename)s:%(lineno)s]"  
formatter = logging.Formatter(fmt);  
handler.setFormatter(formatter);         
  

logger = logging.getLogger('logging');    
logger.addHandler(handler);             
logger.setLevel(logging.DEBUG)

##ip='137.189.97.84'
port=50000

ip='0.0.0.0'

def confLogger(name):
    logger = logging.getLogger(name);    
    logger.addHandler(handler);             
    logger.setLevel(logging.DEBUG)
    return logger

def addLog(name,info):
    logger_ = logging.getLogger(name);    
    logger_.addHandler(handler);             
    logger_.setLevel(logging.DEBUG)
    logger_.info(info);  
    return 

def addError(name,info):
    logger_ = logging.getLogger(name);    
    logger_.addHandler(handler);             
    logger_.setLevel(logging.DEBUG)
    logger_.debug(info);  
    return 