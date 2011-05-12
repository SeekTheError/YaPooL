
#-------------------------User---------------------------
from couchdb.mapping import *
import dblayer
from dblayer import getDb, getServer, loadDatabase

#TODO add topics field
class User(Document) :
  _id = TextField()
  name = TextField()
  password_sha = TextField()
  roles=ListField(TextField())
  email = TextField()
  salt=TextField()
  activationCode = TextField()
  isActivated = BooleanField()
  type = TextField()
  TYPE = 'user'
  ID_PREFIX="org.couchdb.user:"

  def create(self) :
    self._id='org.couchdb.user:'+self.name
    print self._id
    if self.findByName() == None:
      if self.name and self.password_sha and self.email and self.activationCode:
        self.isActivated = False
        self.type = self.TYPE
        import uuid
        import hashlib
        self.salt=uuid.uuid1().get_hex()
        self.password_sha=hashlib.sha1(self.password_sha+self.salt).hexdigest()
        self.roles.append('yapooler')     
        #TODO: check if it return the last version
               
        profile=Profile(owner=self._id)
        print profile.owner
        profileCreated = profile.create()
        if profileCreated :
          self.store(getDb())
          return self
        else :
         return None
      else : 
        print 'required parameters have not been supplied: login, email and password needed'
        return None
    else :
      print 'username already in used: ', self.name
      return None

      
  def update(self) :
    '''
    update the user, only if he already exist
    '''  
    if self.id :
      self.store(getDb())
    else :
      print 'invalid state, attemp to update a non existing user'
      raise  IllegalAttempt



  def findByName(self) :
    ID_PREFIX="org.couchdb.user:"
    return User.load(dblayer.db,ID_PREFIX+self.name)
    

  def findByActivationCode(self) :
    view = dblayer.view('user/activationCode', self.activationCode)
    
    if len(view) == 0 :
      return None
    elif len(view) == 1:
      for u in view : return User.load(getDb(), u.id)
    else :
      print 'WARNING: critical error, more than one user with same activation Code '
      raise IntegrityConstraintException
      
  def findBySessionId(self) :
    view = dblayer.view("user/mail", self.sessionId)
    if len(view) == 0 :
      return None
    elif len(view) == 1:
      for u in view : return User.load(getDb(), u.id)
    else :
      print 'WARNING: critical error, more than one user with sthe same session Id '
      raise IntegrityConstraintException
    
  def findBySessionId(self) :
    view = dblayer.view("user/sessionId", self.sessionId)
    if len(view) == 0 :
      return None
    elif len(view) == 1:
      for u in view : return User.load(getDb(), u.id)
    else :
      print 'WARNING: critical error, more than one user with sthe same session Id '
      raise IntegrityConstraintException



class Profile(Document):
  owner = TextField()
  type = TextField()
  currentYapool=TextField()
  pastYapools=ListField(TextField())
  _id = TextField()
  def create(self):
    self.type='profile'
    if self.owner :
      self._id=
      self.store(loadDatabase (getServer(),dbname=dblayer.YAPOOL_DB))
      return True
    else : 
      return False
    

class IllegalAttempt(Exception) :
  pass

