# fabfile.py
import os, re
from datetime import datetime


from fabric.api import *


env.user = 'hgl'

env.sudo_user = 'root'

env.hosts = ['137.189.97.84']


db_user = 'heguilin'
db_password = 'hgladmin'

_TAR_FILE = 'hccserver.tar.gz'

def build():
    includes = ['static', 'templates', 'transwarp', 'favicon.ico', '*.py']
    excludes = ['test', '.*', '*.pyc', '*.pyo']
    local('rm -f dist/%s' % _TAR_FILE)
    with lcd(os.path.join(os.path.abspath('.'), 'www')):
        cmd = ['tar', '--dereference', '-czvf', '../dist/%s' % _TAR_FILE]
        cmd.extend(['--exclude=\'%s\'' % ex for ex in excludes])
        cmd.extend(includes)
        local(' '.join(cmd))
