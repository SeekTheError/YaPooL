#Author: RemiBouchar
from django.test import TestCase  
import dblayer
from dblayer import loadDatabase, getDb
from entities import User


class SimpleTest(TestCase):
      
  def test_read_user(self):
     print '__________read user test_________'
     u=User(name='ujlikes')
     u = u.findByName()
     print u
     print u.salt
     print u.password_sha
     
  def test_create_user(self):
     print '__________create user test_________'
     u=User(name='testbli',password_sha='testbli', email ='s@s.com',activationCode='111')
     u.create()
    
     
     

