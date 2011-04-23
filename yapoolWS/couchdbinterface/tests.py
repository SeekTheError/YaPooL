#Author: RemiBouchar
from django.test import TestCase  
import dblayer
from dblayer import loadDatabase, getDb
from entities import User


class SimpleTest(TestCase):

  def test_entity_manipulation(self):
    db = dblayer.getDb()
    print db
    
    for x in db.__iter__() :
      print x
      
  def test_create_user(self):
     u=User(login='thenewguy',password='azerazer')
     print u
     u.create()


